package com.gdb.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class AccountRulesPropertiesLoader {
    private Properties properties = new Properties();

    public AccountRulesPropertiesLoader(String configPath) {
        loadProperties(configPath);
    }

    private void loadProperties(String configPath) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configPath);
            if (inputStream == null && new File(configPath).exists()) {
                inputStream = new FileInputStream(configPath);
            }
            if (inputStream != null) {
                try (InputStream stream = inputStream) {
                    properties.load(stream);
                }
            }
        } catch (Exception exception) {
            System.err.println("Warning: Could not load rules from " + configPath);
        }
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public double getDouble(String key, double defaultValue) {
        try {
            return Double.parseDouble(getProperty(key, Double.toString(defaultValue)).trim());
        } catch (NumberFormatException exception) {
            return defaultValue;
        }
    }

    public int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(getProperty(key, Integer.toString(defaultValue)).trim());
        } catch (NumberFormatException exception) {
            return defaultValue;
        }
    }
}
