package net.dolphay.pluginchallenges;

import net.dolphay.pluginchallenges.CustomSwordChallenge.SwordCommand;
import net.dolphay.pluginchallenges.GreetChallenge.GreetCommand;
import net.dolphay.pluginchallenges.GreetChallenge.GreetCommandCompleter;
import net.dolphay.pluginchallenges.GreetChallenge.GreetEventManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

public final class PluginChallenges extends JavaPlugin {
    private static final String GREET_CONFIG_FILE_NAME = "GreetConfiguration.yml";
    private static final String SWORD_CONFIG_FILE_NAME = "SwordConfiguration.yml";

    private YamlConfiguration challengeConfig;
    private YamlConfiguration greetConfig;
    private YamlConfiguration swordConfig;

    private GreetEventManager greetEventManager;


    @Override
    public void onEnable() {
        this.loadEventManagers();
        this.loadConfigs();
        this.loadCommands();
    }

    @Override
    public void onDisable() {

    }

    public void loadEventManagers() {
        this.greetEventManager = new GreetEventManager();

        getServer().getPluginManager().registerEvents(this.greetEventManager, this);
    }

    public void loadConfigs() {
        this.greetConfig = ConfigManager.loadConfig(GREET_CONFIG_FILE_NAME, this);
        this.swordConfig = ConfigManager.loadConfig(SWORD_CONFIG_FILE_NAME, this);

    }

    public void loadCommands() {
        this.getCommand("challenges").setExecutor(new ChallengesCommand(this));
        this.getCommand("greet").setExecutor(new GreetCommand(this.greetConfig, this.greetEventManager));
        this.getCommand("sword").setExecutor(new SwordCommand(this.swordConfig));

        this.getCommand("greet").setTabCompleter(new GreetCommandCompleter());
    }

    public void saveConfigs() {
        ConfigManager.saveConfig(GREET_CONFIG_FILE_NAME, this.greetConfig, this);
    }


}
