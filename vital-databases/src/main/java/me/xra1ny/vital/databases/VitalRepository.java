package me.xra1ny.vital.databases;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import me.xra1ny.vital.core.VitalComponent;
import org.hibernate.Session;

import java.util.*;

/**
 * The {@link VitalRepository} class represents a generic repository for managing entities in the Vital-Framework.
 * It is intended to provide common database operations for entities.
 *
 * @param <Entity> The type of entity to be managed, extending the {@link VitalEntity} interface.
 * @param <Id>     The type of the entity's identifier.
 * @author xRa1ny
 */
public abstract class VitalRepository<Entity extends VitalEntity, Id> implements VitalComponent {
    private final VitalDatabase vitalDatabase;

    /**
     * Constructs a new repository with the specified {@link VitalDatabase}.
     *
     * @param vitalDatabase The {@link VitalDatabase} this repository belongs to.
     */
    protected VitalRepository(@NonNull VitalDatabase vitalDatabase) {
        this.vitalDatabase = vitalDatabase;
    }

    /**
     * Checks if an entity with a specific identifier exists.
     *
     * @param entityClass The class of the entity.
     * @param id          The identifier of the entity.
     * @return {@code true} if an entity with the specified identifier exists, otherwise {@code false}.
     */
    public final boolean existsById(@NonNull Class<Entity> entityClass, @NonNull Id id) {
        return findById(entityClass, id).isPresent();
    }

    /**
     * Finds an entity by its identifier.
     *
     * @param entityClass The class of the entity.
     * @param id          The identifier of the entity.
     * @return An {@link Optional} containing the entity if found, or an empty {@link Optional} if not found.
     */
    public final Optional<Entity> findById(@NonNull Class<Entity> entityClass, @NonNull Id id) {
        try (Session session = vitalDatabase.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(entityClass, id));
        }
    }

    /**
     * Finds all entities of a specific class.
     *
     * @param entityClass The class of the entity.
     * @return A {@link List} of entities matching the specified class.
     */
    public final List<Entity> findAll(@NonNull Class<Entity> entityClass) {
        try (Session session = vitalDatabase.getSessionFactory().openSession()) {
            final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            final CriteriaQuery<Entity> criteriaQuery = criteriaBuilder.createQuery(entityClass);
            final Root<Entity> root = criteriaQuery.from(entityClass);

            criteriaQuery.select(root);

            final TypedQuery<Entity> typedQuery = session.createQuery(criteriaQuery);

            return typedQuery.getResultList();
        }
    }

    /**
     * Checks if entities with specific criteria exist.
     *
     * @param entityClass        The class of the entity.
     * @param columnValueEntries An array of column-value pairs to search for.
     * @return {@code true} if entities matching the criteria exist, otherwise {@code false}.
     */
    public final boolean existsAll(@NonNull Class<Entity> entityClass, @NonNull Map.Entry<String, Object>... columnValueEntries) {
        return !findAll(entityClass, columnValueEntries).isEmpty();
    }

    /**
     * Finds all entities matching specific criteria.
     *
     * @param entityClass        The class of the entity.
     * @param columnValueEntries An array of column-value pairs to search for.
     * @return A {@link List} of entities matching the specified criteria.
     */
    public final List<Entity> findAll(@NonNull Class<Entity> entityClass, @NonNull Map.Entry<String, Object>... columnValueEntries) {
        try (Session session = vitalDatabase.getSessionFactory().openSession()) {
            final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            final CriteriaQuery<Entity> criteriaQuery = criteriaBuilder.createQuery(entityClass);
            final Root<Entity> root = criteriaQuery.from(entityClass);

            criteriaQuery.select(root);

            final List<Predicate> predicateList = new ArrayList<>();

            for (Map.Entry<String, Object> columValueEntry : columnValueEntries) {
                final Predicate predicate = criteriaBuilder.equal(root.get(columValueEntry.getKey()), columValueEntry.getValue());

                predicateList.add(predicate);
            }

            criteriaQuery.where(predicateList.toArray(new Predicate[0]));

            final TypedQuery<Entity> typedQuery = session.createQuery(criteriaQuery);

            try {
                return typedQuery.getResultList();
            } catch (NoResultException e) {
                return Collections.emptyList();
            }
        }
    }

    /**
     * Persists an entity in the database.
     *
     * @param entity The entity to be persisted.
     */
    public final void persist(@NonNull Entity entity) {
        try (Session session = vitalDatabase.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
        }
    }

    /**
     * Removes an entity from the database.
     *
     * @param entity The entity to be removed.
     */
    public final void remove(@NonNull Entity entity) {
        try (Session session = vitalDatabase.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
        }
    }

    /**
     * The type this repository manages.
     *
     * @return The type this repository manages.
     */
    public abstract Class<Entity> managedEntityType();

    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }
}
