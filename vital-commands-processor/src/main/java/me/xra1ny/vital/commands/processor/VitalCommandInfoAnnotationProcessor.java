package me.xra1ny.vital.commands.processor;

import me.xra1ny.vital.commands.annotation.VitalCommandInfo;
import me.xra1ny.vital.core.processor.VitalPluginInfoAnnotationProcessor;
import me.xra1ny.vital.core.processor.VitalPluginInfoHolder;
import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Annotation Processor responsible for working with the default `VitalPluginInfoAnnotationProcessor` to extend its content with automatic command name registration in `plugin.yml`.
 *
 * @author xRa1ny
 */
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class VitalCommandInfoAnnotationProcessor extends AbstractProcessor {
    private boolean ran;
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if(ran) {
            return true;
        }

        // Make sure the basic processor for `plugin.yml` meta information generation runs before this one.
        final VitalPluginInfoAnnotationProcessor vitalPluginInfoAnnotationProcessor = new VitalPluginInfoAnnotationProcessor();

        vitalPluginInfoAnnotationProcessor.init(processingEnv);
        vitalPluginInfoAnnotationProcessor.process(annotations, roundEnv);

        final List<VitalCommandInfo> vitalCommandInfoList = new ArrayList<>();

        // Scan for all commands annotated with `VitalCommandInfo.
        for(Element element : roundEnv.getElementsAnnotatedWith(VitalCommandInfo.class)) {
            final VitalCommandInfo vitalCommandInfo = element.getAnnotation(VitalCommandInfo.class);

            vitalCommandInfoList.add(vitalCommandInfo);
        }

        // finally generate the `plugin.yml` commands.
        generatePluginYmlCommands(vitalCommandInfoList);

        ran = true;

        return true;
    }

    /**
     * Generates the `plugin.yml` if non-existent, or adds to the content, the necessary command name information for automatic command registration.
     *
     * @param vitalCommandInfoList The list of CommandInfo annotations.
     */
    private void generatePluginYmlCommands(@NotNull List<VitalCommandInfo> vitalCommandInfoList) {
        try {
            // Create the new `plugin.yml` file resource as the basic processor left it uncreated.
            final FileObject pluginYmlFileObject = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "plugin.yml");

            // append all necessary meta-information for all commands to the content builder.
            VitalPluginInfoHolder.PLUGIN_INFO.append("commands:");
            VitalPluginInfoHolder.PLUGIN_INFO.append("\n");

            for(VitalCommandInfo vitalCommandInfo : vitalCommandInfoList) {
                final String vitalCommandName = vitalCommandInfo.value();
                final String vitalCommandDescription = vitalCommandInfo.description();
                final String vitalCommandPermission = vitalCommandInfo.permission();
                final String vitalCommandUsage = vitalCommandInfo.usage();
                final String[] vitalCommandAliases = vitalCommandInfo.aliases();

                VitalPluginInfoHolder.PLUGIN_INFO.append("  ").append(vitalCommandName).append(":");
                VitalPluginInfoHolder.PLUGIN_INFO.append("\n");
                VitalPluginInfoHolder.PLUGIN_INFO.append("    description: ").append(vitalCommandDescription);
                VitalPluginInfoHolder.PLUGIN_INFO.append("\n");
                VitalPluginInfoHolder.PLUGIN_INFO.append("    permission: ").append(vitalCommandPermission);
                VitalPluginInfoHolder.PLUGIN_INFO.append("\n");
                VitalPluginInfoHolder.PLUGIN_INFO.append("    usage: ").append(vitalCommandUsage);
                VitalPluginInfoHolder.PLUGIN_INFO.append("\n");

                if(vitalCommandAliases.length > 0) {
                    VitalPluginInfoHolder.PLUGIN_INFO.append("    aliases: ");

                    for(String alias : vitalCommandAliases) {
                        VitalPluginInfoHolder.PLUGIN_INFO.append("      - ").append(alias);
                    }
                }
            }

            // finally write the builder content to the newly created `plugin.yml` resource.
            try(Writer writer = pluginYmlFileObject.openWriter()) {
                writer.write(VitalPluginInfoHolder.PLUGIN_INFO.toString());
            }
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Error while generating plugin.yml commands");
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "If this error persists, please open an issue on Vital's GitHub page!");
            e.printStackTrace();
        }
    }
}
