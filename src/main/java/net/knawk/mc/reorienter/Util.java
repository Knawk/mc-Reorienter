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
        final int currentIndex = Arrays.asList(HORIZONTAL_BLOCK_FACES).indexOf(blockFace);
        if (currentIndex == -1) {
            throw new UnsupportedOperationException();
        }
        final int nextIndex = (currentIndex + 1) % HORIZONTAL_BLOCK_FACES.length;
        return HORIZONTAL_BLOCK_FACES[nextIndex];
    }
}
