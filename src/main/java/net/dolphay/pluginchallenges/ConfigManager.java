package net.dolphay.pluginchallenges;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    public static void createConfig(String fileName, JavaPlugin plugin, boolean replace) {
        // Location of the expected configuration
        File config = new File(plugin.getDataFolder(), fileName);

        config.getParentFile().mkdirs();
        plugin.saveResource(fileName, replace);
    }


    // Loads the configuration from file
    public static YamlConfiguration loadConfig(String fileName, JavaPlugin plugin) {
        // Location of the expected configuration
        File config = new File(plugin.getDataFolder(), fileName);

        // If the configuration does not exist we create it
        if(!config.exists()) createConfig(fileName, plugin, false);

        YamlConfiguration yamlConfig = new YamlConfiguration();

        try {
            // Load the file into yamlConfig
            yamlConfig.load(config);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return yamlConfig;
    }

    public static void saveConfig(String fileName, YamlConfiguration yamlConfig, JavaPlugin plugin) {
        File configFile = new File(plugin.getDataFolder(), fileName);

        try {
            yamlConfig.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
