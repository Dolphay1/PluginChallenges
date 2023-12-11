package net.dolphay.pluginchallenges;

import net.dolphay.pluginchallenges.ClassChallenge.ArcherCommand;
import net.dolphay.pluginchallenges.ClassChallenge.ClassEventHandler;
import net.dolphay.pluginchallenges.ClassChallenge.MageCommand;
import net.dolphay.pluginchallenges.ClassChallenge.WarriorCommand;
import net.dolphay.pluginchallenges.CustomSwordChallenge.SwordCommand;
import net.dolphay.pluginchallenges.GreetChallenge.GreetCommand;
import net.dolphay.pluginchallenges.GreetChallenge.GreetCommandTabCompleter;
import net.dolphay.pluginchallenges.GreetChallenge.GreetEventHandler;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginChallenges extends JavaPlugin {
    private static final String GREET_CONFIG_FILE_NAME = "GreetConfiguration.yml";
    private static final String SWORD_CONFIG_FILE_NAME = "SwordConfiguration.yml";
    private static final String CLASS_CONFIG_FILE_NAME = "ClassConfiguration.yml";

    private YamlConfiguration challengeConfig;
    private YamlConfiguration greetConfig;
    private YamlConfiguration swordConfig;
    private YamlConfiguration classConfig;

    private GreetEventHandler greetEventHandler;
    private ClassEventHandler classEventHandler;


    @Override
    public void onEnable() {
        this.loadConfigs();
        this.loadEventManagers();
        this.loadCommands();
    }

    @Override
    public void onDisable() {

    }

    // Load each of the config files for each of the challenges
    public void loadConfigs() {
        this.greetConfig = ConfigManager.loadConfig(GREET_CONFIG_FILE_NAME, this);
        this.swordConfig = ConfigManager.loadConfig(SWORD_CONFIG_FILE_NAME, this);
        this.classConfig = ConfigManager.loadConfig(CLASS_CONFIG_FILE_NAME, this);
    }

    // Load the event managers for the challenges that require them
    public void loadEventManagers() {
        this.greetEventHandler = new GreetEventHandler();
        this.classEventHandler = new ClassEventHandler(this.classConfig);

        getServer().getPluginManager().registerEvents(this.greetEventHandler, this);
        getServer().getPluginManager().registerEvents(this.classEventHandler, this);
    }

    // Load the commands for each of the challenges as well as the primary command
    public void loadCommands() {
        this.getCommand("challenges").setExecutor(new ChallengesCommand(this));
        this.getCommand("greet").setExecutor(new GreetCommand(this.greetConfig, this.greetEventHandler));
        this.getCommand("sword").setExecutor(new SwordCommand(this.swordConfig));
        this.getCommand("warrior").setExecutor(new WarriorCommand(this.classConfig));
        this.getCommand("archer").setExecutor(new ArcherCommand(this.classConfig));
        this.getCommand("mage").setExecutor((new MageCommand(this.classConfig)));

        this.getCommand("greet").setTabCompleter(new GreetCommandTabCompleter());
    }

    // Save all the configurations, to be implemented
    public void saveConfigs() {
        ConfigManager.saveConfig(GREET_CONFIG_FILE_NAME, this.greetConfig, this);
        ConfigManager.saveConfig(SWORD_CONFIG_FILE_NAME, this.swordConfig, this);
        ConfigManager.saveConfig(CLASS_CONFIG_FILE_NAME, this.classConfig, this);
    }


}
