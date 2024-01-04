package me.xra1ny.vital.statistics;

import lombok.NonNull;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for fetching heap statistics like class instance counts.
 */
public class VitalStatisticsUtils {
    /**
     * Defines the object name for the diagnostic command.
     */
    private static final String diagnosticCommandObjectName = "com.sun.management:type=DiagnosticCommand";

    /**
     * Defines the operation name for the garbage collectors histogram.
     */
    private static final String histogramOperationName = "gcClassHistogram";

    /**
     * Fetches the class instance count of the given fully qualified class name.
     *
     * @param className The fully qualified name to fetch the instance count for.
     * @return The instance count.
     * @throws Exception If an error occurs while fetching.
     */
    public static int getClassCount(@NonNull String className) throws Exception {
        final Pattern pattern = Pattern.compile("\\s*\\d+:\\s*(\\d+)\\s*\\d+\\s*" + className);
        final String classHistogram = getClassHistogram(getMBeanServer());

        return Arrays.stream(classHistogram.split("\\n"))
                .filter(line -> pattern.matcher(line).matches())
                .mapToInt(line -> {
                    final Matcher matcher = pattern.matcher(line);

                    matcher.matches();

                    return Integer.parseInt(matcher.group(1));
                }).findAny().orElse(0);
    }

    /**
     * Fetches the class histogram by the supplied {@link MBeanServer} instance.
     *
     * @param beanServer The {@link MBeanServer} instance.
     * @return The class histogram fetched from the given {@link MBeanServer} instance.
     * @throws Exception If an error occurs.
     */
    @NonNull
    private static String getClassHistogram(@NonNull MBeanServer beanServer) throws Exception {
        final ObjectName objectName = ObjectName.getInstance(diagnosticCommandObjectName);

        return (String) beanServer.invoke(objectName, histogramOperationName, new Object[]{new String[]{}},
                new String[]{String[].class.getName()});
    }

    @NonNull
    private static MBeanServer getMBeanServer() {
        return ManagementFactory.getPlatformMBeanServer();
    }
}
