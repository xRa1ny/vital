package me.xra1ny.vital.tasks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to provide information about the interval of a repeatable task.
 * This annotation is used in conjunction with classes that extend VitalRepeatableTask.
 *
 * @author xRa1ny
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface VitalRepeatableTaskInfo {
    /**
     * Defines the interval at which the repeatable task should execute, in milliseconds.
     *
     * @return The interval for the repeatable task execution.
     */
    int interval();
}

