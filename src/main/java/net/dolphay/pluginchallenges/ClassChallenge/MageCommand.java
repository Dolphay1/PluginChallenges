package net.dolphay.pluginchallenges.ClassChallenge;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Date;


public class MageCommand implements CommandExecutor {
    ConfigurationSection config;

    public MageCommand(YamlConfiguration config) {
        this.config = config.getConfigurationSection("mage");
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String alias, String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player to run this command.");
            return false;
        }

        Player player = (Player)commandSender;
        PlayerInventory playerInventory = player.getInventory();

        // Create the item
        ItemStack wand = new ItemStack(Material.STICK);

        // Get the NMS version of the item, so we can modify the NBT
        net.minecraft.world.item.ItemStack nmsWand = CraftItemStack.asNMSCopy(wand);

        // Create the NBTTagCompound which will hold the wand data.
        NBTTagCompound tag = new NBTTagCompound();
        // Add a tag 'type' and assign it the value 'wand' to identify the item
        tag.a("type", "wand");
        // Add the cooldown tag for after use
        tag.a("cooldown", 0);

        // Add the tag to the item
        nmsWand.c(tag);

        // Return the wand back to the Bukkit version of the item to add item metadata
        wand = CraftItemStack.asBukkitCopy(nmsWand);

        // Set the wand name
        ItemUtil.setItemName(wand, config.getString("wand-name"));

        // Clear the players inventory and give the player the item
        playerInventory.clear();
        playerInventory.addItem(wand);

        return true;
    }
}
