package me.xra1ny.vital.core;

import lombok.NonNull;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.annotation.VitalExceptionHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

/**
 * Describes a class implementation that defines methods to handle uncaught exception during runtime.
 *
 * @author xRa1ny
 */
@Log
public class VitalExceptionsHandler implements VitalComponent {
    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }

    @NonNull
    public List<Method> getAnnotatedMethodsList() {
        return Stream.of(getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(VitalExceptionHandler.class))
                .toList();
    }

    public List<Method> getMethodsListByException(@NonNull Class<? extends Throwable> exceptionClass) {
        return getAnnotatedMethodsList().stream()
                .filter(method -> method.getAnnotation(VitalExceptionHandler.class).value().equals(exceptionClass))
                .toList();
    }

    public void executeAnnotatedMethodsByException(@NonNull Throwable throwable) {
        getMethodsListByException(throwable.getClass())
                .forEach(method -> executeAnnotatedMethod(method, throwable));
    }

    public void executeAnnotatedMethod(@NonNull Method method, @NonNull Throwable throwable) {
        try {
            method.invoke(this, throwable);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.severe("internal error while executing annotated exception handler method");
        }
    }
}
