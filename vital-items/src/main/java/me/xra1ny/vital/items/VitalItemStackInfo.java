package me.xra1ny.vital.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a class to provide information about a custom item stack that can be interacted with in the game.
 * This annotation is used to configure various properties of the custom item stack, such as its name, lore, type, and more.
 *
 * @author xRa1ny
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VitalItemStackInfo {
    /**
     * Defines the name of the custom item stack.
     *
     * @return The name of the item stack.
     */
    @NotNull
    String name();

    /**
     * Defines an array of lore lines for the custom item stack.
     * Lore lines provide additional information about the item.
     *
     * @return An array of lore lines.
     */
    @NotNull
    String[] lore() default {};

    /**
     * Defines the amount of items in the stack (stack size).
     *
     * @return The stack size, which is the number of items in the stack.
     */
    int amount() default 1;

    /**
     * Specifies the material or type of the custom item stack.
     *
     * @return The material/type of the item stack, represented by the Material enum.
     */
    @NotNull
    Material type();

    /**
     * Defines an array of item flags that control specific display properties of the item stack.
     * Item flags can be used to hide attributes, enchantments, and more.
     *
     * @return An array of item flags affecting the item's display.
     */
    @NotNull
    ItemFlag[] itemFlags() default {};

    /**
     * Defines the cooldown time (in seconds) for the custom item stack's interaction.
     * The cooldown restricts how frequently the item can be used.
     *
     * @return The cooldown time in seconds. A value of 0 indicates no cooldown.
     */
    int cooldown() default 0;

    /**
     * Indicates whether the custom item stack should have enchantment visual effects.
     *
     * @return True if the item should appear as if enchanted; false otherwise.
     */
    boolean enchanted() default false;
}
