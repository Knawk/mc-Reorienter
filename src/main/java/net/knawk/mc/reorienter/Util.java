package net.knawk.mc.reorienter;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Util {
    private static final String REORIENTER = "Reorienter";

    public static ItemStack getReorienterItem() {
        final ItemStack itemStack = new ItemStack(Material.COMPASS);
        final ItemMeta itemMeta = Objects.requireNonNull(itemStack.getItemMeta());
        itemMeta.addEnchant(Enchantment.MENDING, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setDisplayName(ChatColor.GREEN + REORIENTER);
        itemMeta.setLore(List.of("""
                Reorients orientable blocks.
                
                Left-click to set a block's orientation.
                Right-click to cycle through a block's
                vertical or horizontal orientations,
                depending on which side you click.""".split("\n")));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ShapedRecipe createReorienterRecipe(final NamespacedKey key) {
        final ItemStack reorienterItem = getReorienterItem();
        final ShapedRecipe recipe = new ShapedRecipe(key, reorienterItem);
        recipe.shape("SSS", "SCS", "SRS");
        recipe.setIngredient('S', Material.AMETHYST_SHARD);
        recipe.setIngredient('C', Material.COMPASS);
        recipe.setIngredient('R', Material.REDSTONE);
        return recipe;
    }

    public static boolean isReorienterItem(final ItemStack itemStack) {
        return getReorienterItem().isSimilar(itemStack);
    }

    public static boolean isVertical(final BlockFace blockFace) {
        return blockFace == BlockFace.DOWN || blockFace == BlockFace.UP;
    }

    private static final BlockFace[] HORIZONTAL_BLOCK_FACES = {
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST,
    };

    public static BlockFace nextHorizontal(final BlockFace blockFace) {
        return nextInCycle(HORIZONTAL_BLOCK_FACES, blockFace);
    }

    public static <T> T nextInCycle(final T[] choices, final T current) {
        final int currentIndex = Arrays.asList(choices).indexOf(current);
        if (currentIndex == -1) {
            throw new UnsupportedOperationException();
        }
        final int nextIndex = (currentIndex + 1) % choices.length;
        return choices[nextIndex];
    }
}
