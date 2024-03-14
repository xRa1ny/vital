package me.xra1ny.vital.commands;

import lombok.Getter;
import lombok.NonNull;
import me.xra1ny.essentia.inject.annotation.AfterInit;
import me.xra1ny.vital.commands.annotation.VitalCommandArg;
import me.xra1ny.vital.commands.annotation.VitalCommandArgHandler;
import me.xra1ny.vital.commands.annotation.VitalCommandInfo;
import me.xra1ny.vital.core.AnnotatedVitalComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Abstract base class for custom Minecraft commands using the Vital framework.
 * Provides functionality for command execution, tab completion, and argument handling.
 *
 * @author xRa1ny
 */
public abstract class VitalCommand implements AnnotatedVitalComponent<VitalCommandInfo>, CommandExecutor, TabExecutor {
    /**
     * The name of the command.
     */
    @Getter
    @NonNull
    private final String name;

    /**
     * The required permission to execute this command.
     */
    @Getter
    @NonNull
    private final String permission;

    /**
     * Flag indicating if this command requires a player sender.
     */
    @Getter
    private final boolean requiresPlayer;

    /**
     * Array of VitalCommandArgs describing command arguments.
     */
    @Getter
    @NonNull
    private final VitalCommandArg[] vitalCommandArgs;

    /**
     * Constructor for VitalCommand.
     */
    public VitalCommand() {
        final VitalCommandInfo vitalCommandInfo = getRequiredAnnotation();

        name = vitalCommandInfo.value();
        permission = vitalCommandInfo.permission();
        requiresPlayer = vitalCommandInfo.requiresPlayer();
        vitalCommandArgs = vitalCommandInfo.args();
    }

    /**
     * Grabs the `@VitalCommandInfo` Annotation associated with this Command, if any.
     *
     * @param type The Type of your implementing `VitalCommand` Child Class.
     * @return An Optional holding either the Annotation Info you provided, or empty.
     */
    public static Optional<VitalCommandInfo> getVitalCommandInfo(@NonNull Class<? extends VitalCommand> type) {
        return Optional.ofNullable(type.getDeclaredAnnotation(VitalCommandInfo.class));
    }

    /**
     * Grabs the Name of this Command, specified in its respective `@VitalCommandInfo` Annotation on the underlying Child Class, if any.
     *
     * @param type The Type of your implementing `VitalCommand` Child Class.
     * @return An Optional holding either the Name of this Command, or empty.
     */
    public static Optional<String> getName(@NonNull Class<? extends VitalCommand> type) {
        final Optional<VitalCommandInfo> optionalVitalCommandInfo = getVitalCommandInfo(type);

        return optionalVitalCommandInfo.map(VitalCommandInfo::value);
    }

    /**
     * Grabs the Description of this Command, specified in its respective `@VitalCommandInfo` Annotation on the underlying Child Class, if any.
     *
     * @param type The Type of your implementing `VitalCommand` Child Class.
     * @return An Optional holding either the Description of this Command, or empty.
     */
    public static Optional<String> getDescription(@NonNull Class<? extends VitalCommand> type) {
        final Optional<VitalCommandInfo> optionalVitalCommandInfo = getVitalCommandInfo(type);

        return optionalVitalCommandInfo.map(VitalCommandInfo::description);
    }

    /**
     * Grabs the aliases of this Command, specified in its respective `@VitalCommandInfo` Annotation on the underlying Child Class, if any.
     *
     * @param type The Type of your implementing `VitalCommand` Child Class.
     * @return An Optional holding either the aliases of this Command, or empty.
     */
    public static Optional<String[]> getAliases(@NonNull Class<? extends VitalCommand> type) {
        final Optional<VitalCommandInfo> optionalVitalCommandInfo = getVitalCommandInfo(type);

        return optionalVitalCommandInfo.map(VitalCommandInfo::aliases);
    }

    /**
     * Grabs the Usage of this Command, specified in its respective `@VitalCommandInfo` Annotation on the underlying Child Class, if any.
     *
     * @param type The Type of your implementing `VitalCommand` Child Class.
     * @return An Optional holding either the Usage of this Command, or empty.
     */
    public static Optional<String> getUsage(@NonNull Class<? extends VitalCommand> type) {
        final Optional<VitalCommandInfo> optionalVitalCommandInfo = getVitalCommandInfo(type);

        return optionalVitalCommandInfo.map(VitalCommandInfo::usage);
    }

    /**
     * Grabs the Permission of this Command, specified in its respective `@VitalCommandInfo` Annotation on the underlying Child Class, if any.
     *
     * @param type The Type of your implementing `VitalCommand` Child Class.
     * @return An Optional holding either the Permission of this Command, or empty.
     */
    public static Optional<String> getPermission(@NonNull Class<? extends VitalCommand> type) {
        final Optional<VitalCommandInfo> optionalVitalCommandInfo = getVitalCommandInfo(type);

        return optionalVitalCommandInfo.map(VitalCommandInfo::permission);
    }

    /**
     * Grabs if this Command requires a Player, specified in its respective `@VitalCommandInfo` Annotation on the underlying Child Class, if any.
     *
     * @param type The Type of your implementing `VitalCommand` Child Class.
     * @return An Optional holding either if this Command requires a Player, or empty.
     */
    public static Optional<Boolean> getRequiresPlayer(@NonNull Class<? extends VitalCommand> type) {
        final Optional<VitalCommandInfo> optionalVitalCommandInfo = getVitalCommandInfo(type);

        return optionalVitalCommandInfo.map(VitalCommandInfo::requiresPlayer);
    }

    /**
     * Grabs the VitalArgs of this Command, specified in its respective `@VitalCommandInfo` Annotation on the underlying Child Class, if any.
     *
     * @param type The Type of your implementing `VitalCommand` Child Class.
     * @return An Optional holding either the VitalArgs of this Command, or empty.
     */
    public static Optional<VitalCommandArg[]> getVitalArgs(@NonNull Class<? extends VitalCommand> type) {
        final Optional<VitalCommandInfo> optionalVitalCommandInfo = getVitalCommandInfo(type);

        return optionalVitalCommandInfo.map(VitalCommandInfo::args);
    }

    /**
     * Grabs the Args of this Command, specified in its respective `@VitalCommandInfo` Annotation on the underlying Child Class, if any.
     *
     * @param type The Type of your implementing `VitalCommand` Child Class.
     * @return An Optional holding either the Args of this Command, or empty.
     */
    public static Optional<String[]> getArgs(@NonNull Class<? extends VitalCommand> type) {
        final Optional<VitalCommandInfo> optionalVitalCommandInfo = getVitalCommandInfo(type);

        return optionalVitalCommandInfo.map(VitalCommandInfo::args)
                .map(vitalCommandArgs -> {
                    final List<String> args = new ArrayList<>();

                    for (VitalCommandArg vitalCommandArg : vitalCommandArgs) {
                        args.add(vitalCommandArg.value());
                    }

                    return args.toArray(String[]::new);
                });
    }

    @AfterInit
    public final void afterInit(JavaPlugin javaPlugin) {
        javaPlugin.getCommand(name).setExecutor(this);
    }

    @Override
    public final void onRegistered() {

    }

    @Override
    public final void onUnregistered() {
    }

    @Override
    public Class<VitalCommandInfo> requiredAnnotationType() {
        return VitalCommandInfo.class;
    }

    /**
     * called when this command is executed with only the base command (/commandname)
     *
     * @param sender the sender
     * @return the status of this command execution
     */
    @NonNull
    protected VitalCommandReturnState executeBaseCommand(@NonNull CommandSender sender) {
        return VitalCommandReturnState.INVALID_ARGS;
    }

    @Override
    public final boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        // Check if the command requires a player sender.
        if (requiresPlayer) {
            // Check if the sender is not a Player.
            if (!(sender instanceof Player)) {
                // Execute the onCommandRequiresPlayer method and return true.
                onCommandRequiresPlayer(sender);

                return true;
            }
        }

        // Check if a permission is required and if the sender has it.
        if (!permission.isBlank() && !sender.hasPermission(permission)) {
            // Execute the onCommandRequiresPermission method and return true.
            onCommandRequiresPermission(sender);

            return true;
        }

        try {
            // Initialize a StringBuilder to format arguments.
            final StringBuilder formattedArgsBuilder = new StringBuilder();
            // Initialize a list to store argument values.
            final List<String> values = new ArrayList<>();

            // Loop through the provided arguments.
            for (String arg : args) {
                // Initialize a boolean flag to check if the argument is recognized.
                boolean contains = false;

                // Loop through the command arguments specified for this command.
                for (VitalCommandArg commandArg : vitalCommandArgs) {
                    // Split the command argument into individual parts.
                    final String[] splitCommandArg = commandArg.value().split(" ");
                    // Initialize a flag to check if the argument is recognized for this commandArg.
                    boolean containsArg = false;

                    // Loop through the parts of the command argument.
                    for (String singleCommandArg : splitCommandArg) {
                        // Check if the argument matches any part of the command argument.
                        if (singleCommandArg.equalsIgnoreCase(arg)) {
                            // Set the flag to true and break.
                            containsArg = true;
                            break;
                        }
                    }

                    // Check if the argument is recognized for this commandArg.
                    if (containsArg) {
                        // Set the flag to true.
                        contains = true;
                        break;
                    }
                }

                // Check if the argument is recognized for this command.
                if (contains) {
                    // Append the argument to the formattedArgsBuilder.
                    formattedArgsBuilder.append(!formattedArgsBuilder.isEmpty() ? " " : "").append(arg);
                    continue;
                }

                // If the argument is not recognized, replace it with a "?" and add it to the values list.
                formattedArgsBuilder.append(!formattedArgsBuilder.isEmpty() ? " " : "").append("?");
                values.add(arg);
            }

            // Initialize the finalCommandArg to null.
            VitalCommandArg finalCommandArg = null;

            // Loop through the specified command arguments for this command.
            for (VitalCommandArg commandArg : vitalCommandArgs) {
                // Replace placeholders with "?" and check if it matches the formattedArgsBuilder.
                if (commandArg.value().replaceAll("%[A-Za-z0-9]*%", "?").equalsIgnoreCase(formattedArgsBuilder.toString())) {
                    // Assign the matched commandArg to finalCommandArg.
                    finalCommandArg = commandArg;
                    break;
                }
            }

            // Initialize the commandReturnState to null.
            VitalCommandReturnState commandReturnState = null;

            // Check if finalCommandArg is not null.
            if (finalCommandArg != null) {
                // Execute the corresponding commandArg handler method and get the return state.
                commandReturnState = executeCommandArgHandlerMethod(sender, finalCommandArg, values.toArray(new String[0]));
            } else {
                // Loop through the specified command arguments for this command.
                for (VitalCommandArg commandArg : vitalCommandArgs) {
                    // Check if formattedArgsBuilder starts with a recognized command argument.
                    if (!formattedArgsBuilder.toString().startsWith(commandArg.value().replaceAll("%[A-Za-z0-9]*%", "?").replace("*", ""))) {
                        continue;
                    }

                    // Execute the corresponding commandArg handler method and get the return state.
                    commandReturnState = executeCommandArgHandlerMethod(sender, commandArg, values.toArray(new String[0]));
                    break;
                }
            }

            // If the commandReturnState is still null, execute the base command.
            if (commandReturnState == null) {
                commandReturnState = executeBaseCommand(sender);
            }

            // Handle the command return state.
            switch (commandReturnState) {
                case ERROR -> onCommandError(sender);
                case INTERNAL_ERROR -> onCommandInternalError(sender);
                case INVALID_ARGS -> onCommandInvalidArgs(sender);
                case NO_PERMISSION -> onCommandRequiresPermission(sender);
            }

            return true;
        } catch (Exception ex) {
            // Print any exceptions that occur during command execution.
            //noinspection CallToPrintStackTrace
            ex.printStackTrace();
        }

        return true;
    }

    @NonNull
    private VitalCommandReturnState executeCommandArgHandlerMethod(@NonNull CommandSender sender, @NonNull VitalCommandArg commandArg, @NonNull String @NonNull [] values) throws InvocationTargetException, IllegalAccessException {
        // Initialize a variable to hold the handler method.
        Method commandArgHandlerMethod = null;

        // Iterate through the methods of the current class.
        for (Method method : getClass().getDeclaredMethods()) {
            // Retrieve the `@VitalCommandArgHandler` annotation for the method.
            final VitalCommandArgHandler commandArgHandler = method.getDeclaredAnnotation(VitalCommandArgHandler.class);

            // Check if the method does not have the annotation or the annotation value does not match the commandArg value.
            if (commandArgHandler == null || !List.of(commandArgHandler.value()).contains(commandArg.value())) {
                continue; // Skip this method if the condition is not met.
            }

            // If the method's return type does not match `VitalCommandReturnState`, cancel operation.
            if (!VitalCommandReturnState.class.isAssignableFrom(method.getReturnType())) {
                continue;
            }

            // Set the commandArgHandlerMethod to the matching method.
            commandArgHandlerMethod = method;

            break; // Exit the loop after finding the matching method.
        }

        // Check if no handler method was found.
        if (commandArgHandlerMethod == null) {
            return VitalCommandReturnState.INVALID_ARGS; // Return INVALID_ARGS if no handler method is found.
        }

        // If the handler method was found, dynamically inject each parameter supported for its implementation...
        final List<Object> injectedParameters = new ArrayList<>();

        for (Parameter parameter : commandArgHandlerMethod.getParameters()) {
            // If the parameters managed type is of instance of `CommandSender`, inject either `CommandSender` or `Player`
            if (CommandSender.class.isAssignableFrom(parameter.getType())) {
                injectedParameters.add(sender);
            } else if (VitalCommandArg.class.isAssignableFrom(parameter.getType())) {
                // inject `VitalCommandArg`
                injectedParameters.add(commandArg);
            } else if (String[].class.isAssignableFrom(parameter.getType())) {
                // if parameters managed type is an instance of `String[]` inject the values of this command execution.
                injectedParameters.add(values);
            }

            // If the above set conditions are not met, do not inject anything as the type is not supported by Vital.
        }

        // invoke the handler method with the dynamically fetched args...
        return (VitalCommandReturnState) commandArgHandlerMethod.invoke(this, injectedParameters.toArray());
    }

    /**
     * Called upon requesting any tab-completion content.
     *
     * @param sender The {@link CommandSender} that sent the command.
     * @param args   The arguments used in chat.
     * @return A {@link List} of strings to show to the player as tab-completion suggestions.
     */
    @NonNull
    public List<String> onCommandTabComplete(@NonNull CommandSender sender, @NonNull String args) {
        return List.of();
    }

    @NonNull
    public final List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String alias, @NonNull String @NonNull [] args) {
        // Initialize a list to store tab-completed suggestions.
        final List<String> tabCompleted = new ArrayList<>();

        // Loop through the specified command arguments for this command.
        for (VitalCommandArg arg : vitalCommandArgs) {
            // Split the value of the command argument into individual parts.
            final String[] originalArgs = arg.value().split(" ");
            // Clone the originalArgs to avoid modification.
            final String[] editedArgs = originalArgs.clone();

            // Check if the originalArgs length is greater than or equal to the provided args length
            // or if the last element of originalArgs ends with "%*".
            if (originalArgs.length >= args.length || originalArgs[originalArgs.length - 1].endsWith("%*")) {
                // Loop through the provided args.
                for (int i = 0; i < args.length; i++) {
                    // Determine the original argument at the current index.
                    final String originalArg = i >= originalArgs.length ? originalArgs[originalArgs.length - 1] : originalArgs[i];

                    // Check if the original argument does not start with "%" and does not end with "%" or "%*".
                    if (!originalArg.startsWith("%") && !(originalArg.endsWith("%") || originalArg.endsWith("%*"))) {
                        continue; // Skip this iteration if the condition is not met.
                    }

                    // Replace the edited argument at the corresponding index with the provided argument.
                    editedArgs[i >= editedArgs.length ? editedArgs.length - 1 : i] = args[i];
                }

                // Determine the final argument from originalArgs and args.
                final String finalArg = originalArgs[args.length - 1 >= originalArgs.length ? originalArgs.length - 1 : args.length - 1];

                // Check if the joined editedArgs start with the joined provided args.
                if (!String.join(" ", editedArgs).startsWith(String.join(" ", args))) {
                    continue; // Skip this iteration if the condition is not met.
                }

                // Check if the final argument starts with "%" and ends with "%*".
                if (finalArg.startsWith("%") && finalArg.endsWith("%*")) {
                    // Add the final argument with "%" and "%*" removed to the tabCompleted list.
                    tabCompleted.add(finalArg.replace("%", "").replace("%*", ""));

                    break; // Exit the loop.
                }

                // Check if the final argument is equal to "PLAYER".
                if (finalArg.equalsIgnoreCase(VitalCommandArg.PLAYER)) {
                    // Loop through online players.
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        // Check if the player name is already in the tabCompleted list.
                        if (tabCompleted.contains(player.getName())) {
                            continue; // Skip this iteration if the condition is met.
                        }

                        // Add the player name to the tabCompleted list.
                        tabCompleted.add(player.getName());
                    }
                } else if (finalArg.startsWith(VitalCommandArg.NUMBER)) {
                    // Add "0" to the tabCompleted list.
                    tabCompleted.add("0");
                } else if (finalArg.startsWith(VitalCommandArg.BOOLEAN)) {
                    // Add "true" and "false" to the tabCompleted list.
                    tabCompleted.add("true");
                    tabCompleted.add("false");
                } else if (finalArg.startsWith("%") && (finalArg.endsWith("%") || finalArg.endsWith("%*"))) {
                    // Add the final argument with "%" and "%*" removed to the tabCompleted list.
                    tabCompleted.add(finalArg.replace("%", "").replace("%*", ""));
                } else {
                    // Add the final argument to the tabCompleted list.
                    tabCompleted.add(finalArg);
                }
            }
        }

        final String formattedArgs = String.join(" ", Stream.of(args)
                .map(arg -> "?")
                .toList());
        final List<String> commandTabCompleted = onCommandTabComplete(sender, formattedArgs);

        // when our OWN implementation is not empty, clear all of Vital's defaults.
        if (!commandTabCompleted.isEmpty()) {
            tabCompleted.clear();
        }

        // finally add further tab-completed suggestions implemented by the developer.
        tabCompleted.addAll(commandTabCompleted);

        return tabCompleted; // Return the list of tab-completed suggestions.
    }

    /**
     * Called when this VitalCommand has been executed using invalid Arguments
     *
     * @param sender The CommandSender
     */
    protected void onCommandInvalidArgs(@NonNull CommandSender sender) {

    }

    /**
     * Called when this VitalCommand has been executed and an internal Error has occurred
     *
     * @param sender The CommandSender
     */
    protected void onCommandInternalError(@NonNull CommandSender sender) {

    }

    /**
     * Called when this VitalCommand has been executed and an Error has occurred
     *
     * @param sender The CommandSender
     */
    protected void onCommandError(@NonNull CommandSender sender) {

    }

    /**
     * Called when this VitalCommand has been executed without needed Permissions
     *
     * @param sender The CommandSender
     */
    protected void onCommandRequiresPermission(@NonNull CommandSender sender) {

    }

    /**
     * Called when this VitalCommand has been executed as a non Player Object while requiring a Player to be executed
     *
     * @param sender The CommandSender
     */
    protected void onCommandRequiresPlayer(@NonNull CommandSender sender) {

    }
}
