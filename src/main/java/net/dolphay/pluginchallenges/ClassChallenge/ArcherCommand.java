package net.dolphay.pluginchallenges.ClassChallenge;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class ArcherCommand implements CommandExecutor {

    ConfigurationSection config;

    public ArcherCommand(YamlConfiguration config) {
        this.config = config.getConfigurationSection("archer");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player to run this command.");
            return false;
        }

        Player player = (Player)commandSender;
        PlayerInventory playerInventory = player.getInventory();

        // Create one of each armor element
        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);

        // Create the bow and arrows
        ItemStack bow = new ItemStack(Material.BOW);
        ItemStack arrows = new ItemStack(Material.ARROW);

        // We are adding arrows by the stack so set the amount to 64
        arrows.setAmount(64);

        // Set the names of the armor
        helmet = ItemUtil.setItemName(helmet, config.getString("helmet-name"));
        chestplate = ItemUtil.setItemName(chestplate, config.getString("chestplate-name"));
        leggings = ItemUtil.setItemName(leggings, config.getString("leggings-name"));
        boots = ItemUtil.setItemName(boots, config.getString("boots-name"));

        // Clear the players inventory and add the armor and bow
        playerInventory.clear();
        playerInventory.setArmorContents(new ItemStack[]{boots, leggings, chestplate, helmet});
        playerInventory.setItem(0, bow);

        // Loop through the expected stack counts and add them to the players inventory
        for(int i = 0; i < config.getInt("arrow-stack-count") && i < 35; i++) {
            playerInventory.setItem(i+1, arrows);
        }

        return true;
    }
}
