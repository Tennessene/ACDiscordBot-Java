package me.acclashcorporation.files;

import me.acclashcorporation.DiscordBot;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Config {

    private static final Properties properties = new Properties();

    public static void setup() {
        String fileName = "config.properties";
        try (InputStream input = DiscordBot.class.getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + fileName);
                return;
            }
            // Load a properties file from class path, inside static method
            properties.load(input);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static List<String> getListProperty(String key) {
        String property = properties.getProperty(key);
        if (property != null) {
            return Arrays.asList(property.split(","));
        }
        return null;
    }

}
