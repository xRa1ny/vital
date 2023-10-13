package me.xra1ny.vital.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to define arguments for Vital commands.
 * Command arguments are placeholders that can be used within command handlers.
 * For example, "%PLAYER%" can be replaced with the player's name.
 *
 * @author xRa1ny
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface VitalCommandArg {
    String PLAYER = "%PLAYER%";
    String BOOLEAN = "%BOOLEAN%";
    String NUMBER = "%NUMBER%";

    /**
     * Placeholder value for the command argument.
     * Examples include "%PLAYER%", "%BOOLEAN%", and "%NUMBER%".
     *
     * @return The value of the command argument placeholder.
     */
    String value();

    /**
     * Optional permission node associated with the command argument.
     * Defines the required permission to use this argument in a command.
     *
     * @return The permission node (default is an empty string).
     */
    String permission() default "";

    /**
     * Flag indicating if this argument is specific to players.
     * If set to true, the argument is only applicable to player senders.
     *
     * @return True if the argument is for players only; false otherwise (default is false).
     */
    boolean player() default false;
}
