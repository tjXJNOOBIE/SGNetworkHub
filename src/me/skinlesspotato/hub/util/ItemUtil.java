package me.skinlesspotato.hub.util;

/* Class created 07/25/2018. 
Editing of this code without permission from the author is prohibited.
The following code is licensed to who it was made for.
Contact Skinlesspotato#6044 on discord for distribution rights.
*/

import me.skinlesspotato.hub.Hub;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemUtil {

    private Hub plugin;
    public ItemUtil(Hub plugin) {
        this.plugin = plugin;
    }

    private String formatMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public ItemStack createItem(Material material, String displayName, String lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(formatMessage(displayName));
        meta.setLore(Arrays.asList(formatMessage(lore)));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack createItemNoLore(Material material, String displayName) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(formatMessage(displayName));
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
