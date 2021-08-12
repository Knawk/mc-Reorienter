package net.knawk.mc.reorienter;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Util {
    public static boolean isReorienterItem(final ItemStack itemStack) {
        // TODO allow fancy compass only
        return itemStack != null && itemStack.getType().equals(Material.COMPASS);
    }
}
