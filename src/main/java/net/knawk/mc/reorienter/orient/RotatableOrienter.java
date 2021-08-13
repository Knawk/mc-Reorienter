package net.knawk.mc.reorienter.orient;

import net.knawk.mc.reorienter.Util;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.type.Sign;

import java.util.Arrays;
import java.util.Set;

public class RotatableOrienter implements Orienter {
    @Override
    public boolean act(OrientAction action, Block block, BlockFace clickedFace, BlockData blockData) {
        final Rotatable rotatable = (Rotatable) blockData;
        if (!isOrientable(rotatable)) return false;

        final BlockFace newRotation = switch (action) {
            case SET -> clickedFace;
            case CYCLE -> nextRotation(rotatable.getRotation());
        };
        if (Arrays.asList(ROTATIONS).contains(newRotation)) {
            rotatable.setRotation(newRotation);
            block.setBlockData(blockData);
        }
        return true;
    }

    private static final BlockFace[] ROTATIONS = {
            BlockFace.NORTH,
            BlockFace.NORTH_NORTH_EAST,
            BlockFace.NORTH_EAST,
            BlockFace.EAST_NORTH_EAST,
            BlockFace.EAST,
            BlockFace.EAST_SOUTH_EAST,
            BlockFace.SOUTH_EAST,
            BlockFace.SOUTH_SOUTH_EAST,
            BlockFace.SOUTH,
            BlockFace.SOUTH_SOUTH_WEST,
            BlockFace.SOUTH_WEST,
            BlockFace.WEST_SOUTH_WEST,
            BlockFace.WEST,
            BlockFace.WEST_NORTH_WEST,
            BlockFace.NORTH_WEST,
            BlockFace.NORTH_NORTH_WEST,
    };

    private static final Set<Material> ORIENTABLE_HEADS = Set.of(
            Material.PLAYER_HEAD,
            Material.CREEPER_HEAD,
            Material.DRAGON_HEAD,
            Material.SKELETON_SKULL,
            Material.WITHER_SKELETON_SKULL,
            Material.ZOMBIE_HEAD
    );

    private static boolean isOrientable(final Rotatable rotatable) {
        return (rotatable instanceof Sign)
                || ORIENTABLE_HEADS.contains(rotatable.getMaterial());
    }

    private static BlockFace nextRotation(final BlockFace rotation) {
        return Util.nextInCycle(ROTATIONS, rotation);
    }
}
