package net.knawk.mc.reorienter;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Util {
    public static boolean isReorienterItem(final ItemStack itemStack) {
        // TODO allow fancy compass only
        return itemStack != null && itemStack.getType().equals(Material.COMPASS);
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
