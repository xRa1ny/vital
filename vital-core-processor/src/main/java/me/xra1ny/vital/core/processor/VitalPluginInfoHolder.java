package me.xra1ny.vital.core.processor;

/**
 * Generic Interface responsible for holding the contents for automatic `plugin.yml` creation between multiple Annotation Processor modules.
 *
 * @author xRa1ny
 */
public interface VitalPluginInfoHolder {
    StringBuilder PLUGIN_INFO = new StringBuilder();
}
