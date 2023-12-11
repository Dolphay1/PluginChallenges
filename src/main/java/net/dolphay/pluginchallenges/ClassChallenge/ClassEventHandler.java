package net.dolphay.pluginchallenges.ClassChallenge;

import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;


public class ClassEventHandler implements Listener {

    ConfigurationSection config;

    public ClassEventHandler(YamlConfiguration config) {
        this.config = config.getConfigurationSection("mage");
    }

    @EventHandler
    public void throwFireBall(PlayerInteractEvent e) {
        // Ensure we are right-clicking with a stick in hand
        if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(e.getItem() == null) return;
        if(e.getItem().getType() != Material.STICK) return;

        // Check to see if we are using a stick with the nbt tag 'type' assigned to 'wand'
        net.minecraft.world.item.ItemStack nmsStick = CraftItemStack.asNMSCopy(e.getItem());
        if(nmsStick.u() == null || !nmsStick.u().l("type").equals("wand")) return;

        // Get the cooldown timer off the item
        long cooldown = nmsStick.u().i("cooldown");

        // This is here to prevent a bug where the event is sent twice for the same action, for 10 milliseconds after use the wand will be unusable
        if(System.currentTimeMillis() - (cooldown - config.getLong("wand-cooldown") ) < 10 && System.currentTimeMillis() - (cooldown - config.getLong("wand-cooldown")) > 0) return;

        // If the cooldown is not up, tell the player and return
        if(System.currentTimeMillis() < cooldown) {
            e.getPlayer().sendMessage("Item is still on cooldown!");
            return;
        }

        // Get the current information from the item and set the cooldown to the new value
        NBTTagCompound newCooldown = nmsStick.u();
        newCooldown.a("cooldown", System.currentTimeMillis() + config.getLong("wand-cooldown"));
        nmsStick.c(newCooldown);

        // Return the item back to a Bukkit item
        ItemStack wand = CraftItemStack.asBukkitCopy(nmsStick);

        // Find which hand the player used the item from and replace it with the new wand and timer
        if(e.getItem().isSimilar(e.getPlayer().getInventory().getItemInMainHand())) e.getPlayer().getInventory().setItemInMainHand(wand);
        else e.getPlayer().getInventory().setItemInOffHand(wand);

        // Create the location that the fireball will be spawned at.
        // We multiply the vector by a little bit to ensure the fireball does not collide with the player if they are moving
        // We also add 1 to the Y as it will normally spawn at the players feet, we want it to spawn at the players head
        Location loc = e.getPlayer().getLocation();
        loc.add((new Vector(1.5,0,1.5).multiply(loc.getDirection())).add(new Vector(0,1,0)));

        // Spawn the fireball
        Bukkit.getWorld(e.getPlayer().getWorld().getUID()).spawn(loc, Fireball.class);
    }
}
