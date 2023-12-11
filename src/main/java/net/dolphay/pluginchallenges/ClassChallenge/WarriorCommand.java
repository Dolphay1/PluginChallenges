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

public class WarriorCommand implements CommandExecutor {

    ConfigurationSection config;

    public WarriorCommand(YamlConfiguration config) {
        this.config = config.getConfigurationSection("warrior");
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
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);

        // Create the sword and fishing rod
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        ItemStack fishingrod = new ItemStack(Material.FISHING_ROD);

        // Assign the names to each of the armor elements
        helmet = ItemUtil.setItemName(helmet, config.getString("helmet-name"));
        chestplate = ItemUtil.setItemName(chestplate, config.getString("chestplate-name"));
        leggings = ItemUtil.setItemName(leggings, config.getString("leggings-name"));
        boots = ItemUtil.setItemName(boots, config.getString("boots-name"));

        // Clear the players inventory and give the player each of the items.
        playerInventory.clear();
        playerInventory.setArmorContents(new ItemStack[]{boots, leggings, chestplate, helmet});
        playerInventory.setItem(0, sword);
        playerInventory.setItem(1, fishingrod);

        return true;
    }
}
