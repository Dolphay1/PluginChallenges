package net.dolphay.pluginchallenges.ClassChallenge;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {
    // Utility function so we don't have to get the item meta, set the name, and reset the item meta everytime we want to name an item
    protected static ItemStack setItemName(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);

        return item;
    }

}
