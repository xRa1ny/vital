package me.xra1ny.vital.inventories;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to provide information about a VitalInventoryMenu.
 *
 * @author xRa1ny
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface VitalInventoryInfo {
    /**
     * The title of this inventory menu.
     *
     * @return The title of the inventory menu.
     */
    @NotNull
    String title();

    /**
     * The size in slots of this inventory menu. Default is 9 (one row).
     *
     * @return The size of the inventory menu.
     */
    @Range(from = 9, to = 54)
    int size() default 9;

    /**
     * The material used as the background of this inventory menu. Default is AIR.
     *
     * @return The background material.
     */
    @NotNull
    Material background() default Material.AIR;
}
