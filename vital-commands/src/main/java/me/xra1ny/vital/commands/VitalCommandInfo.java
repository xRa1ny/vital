package me.xra1ny.vital.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to provide metadata for Vital commands.
 * If combined with the `vital-core-processor` and `vital-commands-processor` dependency as annotation processor, can automatically defines all commands in `plugin.yml` during compile-time.
 *
 * @author xRa1ny
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface VitalCommandInfo {
    /**
     * Defines the name of this command, excluding the slash `/`.
     *
     * @return The name of the command.
     */
    String value();

    /**
     * Defines the description of this command.
     *
     * @return The description of this command.
     */
    String description() default "A Vital Command";

    /**
     * The aliases of this command.
     *
     * @return The aliases of this command.
     */
    String[] aliases() default {};

    /**
     * The usages message of this command.
     *
     * @return The usages message of this command.
     */
    String usage() default "";

    /**
     * Defines the permission required to run this command.
     *
     * @return The required permission (default is an empty string).
     */
    String permission() default "";

    /**
     * Defines if this command can only be executed by a player.
     *
     * @return True if the command requires a player; false otherwise (default is true).
     */
    boolean requiresPlayer() default true;

    /**
     * Defines all valid arguments of this command.
     * Supported patterns include "%PLAYER%", "%BOOLEAN%", and "%NUMBER%".
     * All other patterns will not be converted in auto tab complete.
     *
     * @return An array of valid command arguments (default is an empty array).
     */
    VitalCommandArg[] args() default {};
}
