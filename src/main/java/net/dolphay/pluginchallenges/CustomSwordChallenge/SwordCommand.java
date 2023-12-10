package net.dolphay.pluginchallenges.CustomSwordChallenge;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class SwordCommand implements CommandExecutor {
    YamlConfiguration config;
    public SwordCommand(YamlConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player to run this command.");
            return false;
        }

        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = sword.getItemMeta();



        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,
                new AttributeModifier(UUID.randomUUID(),
                        "generic.attackdamage",
                        config.getDouble("attack-damage"),
                        AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND
                )
        );

        meta.setDisplayName(config.getString("name"));

        sword.setItemMeta(meta);

        ((Player)commandSender).getInventory().addItem(sword);

        return true;
    }
}
