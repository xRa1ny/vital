package me.xra1ny.vital;

import lombok.NonNull;
import me.xra1ny.essentia.inject.EssentiaInject;
import me.xra1ny.except.EssentiaExcept;
import me.xra1ny.vital.core.VitalCore;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main class of the Vital-Framework.
 *
 * @param <T> Your plugins main class.
 * @author xRa1ny
 * @apiNote This class is used for a complete package of Vital.
 */
@SuppressWarnings("unused")
public final class Vital<T extends JavaPlugin> extends VitalCore<T> {
    /**
     * Constructs a new instance of the Vital-Framework.
     *
     * @param javaPlugin The {@link JavaPlugin} this Vital-Framework instance belongs to.
     */
    public Vital(@NonNull T javaPlugin) {
        super(javaPlugin);

        registerComponent(this);
    }

    /**
     * Gets this generic {@link Vital} instance.
     *
     * @return This generic {@link Vital} instance.
     */
    public static Vital<?> getVitalInstance() {
        return (Vital<?>) getVitalCoreInstance();
    }

    /**
     * Gets this {@link Vital} instance with the {@link JavaPlugin} type specified.
     *
     * @param type The type of your {@link JavaPlugin} implementation.
     * @param <T>  The type of your {@link JavaPlugin}. implementation.
     * @return This generic accurate {@link Vital} instance.
     */
    public static <T extends JavaPlugin> Vital<T> getVitalInstance(@NonNull Class<T> type) {
        return (Vital<T>) getVitalCoreInstance(type);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
        // register both plugin package and Vital's package for dependency injection using essentia-inject.
        EssentiaInject.run(this, getJavaPlugin().getClass().getPackageName(), getClass().getPackageName());

        // register both plugin package and Vital's package for exception handling using essentia-except.
        EssentiaExcept.run(getJavaPlugin().getLogger(), getJavaPlugin().getClass().getPackageName(), getClass().getPackageName());
    }
}