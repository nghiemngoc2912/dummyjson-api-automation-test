package config;

import java.util.Properties;

public class ConfigManager {
    private static final Properties prop = new Properties();

    static {
        String env = System.getProperty("env", "qa");
        String file = "config-" + env + ".properties";

        try (var is = ConfigManager.class
                .getClassLoader()
                .getResourceAsStream(file)) {
            prop.load(is);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static String get(String key) {
        return prop.getProperty(key);
    }
}
