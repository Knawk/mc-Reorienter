package net.knawk.mc.reorienter.orient;

import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;

import java.util.Set;

public class OrientableOrienter implements Orienter {
    @Override
    public boolean act(OrientAction action, Block block, BlockFace clickedFace, BlockData blockData) {
        if (!isReorientable(block.getType())) return false;

        final Orientable orientable = (Orientable) blockData;
        final Axis newAxis = switch (action) {
            case SET -> blockFaceToAxis(clickedFace);
            case CYCLE -> cycleAxis(orientable.getAxis(), clickedFace);
        };
        orientable.setAxis(newAxis);
        block.setBlockData(blockData);
        return true;
    }

    private static final Set<Material> REORIENTABLE_ORIENTABLES = Set.of(
            Material.HAY_BLOCK,
            Material.CHAIN,
            Material.BASALT,
            Material.POLISHED_BASALT,
            Material.QUARTZ_PILLAR,
            Material.PURPUR_PILLAR,
            Material.BONE_BLOCK
    );

    private static boolean isReorientable(final Material material) {
        return Tag.LOGS.isTagged(material) || REORIENTABLE_ORIENTABLES.contains(material);
    }

    private static Axis blockFaceToAxis(final BlockFace blockFace) {
        return switch (blockFace) {
            case EAST, WEST -> Axis.X;
            case UP, DOWN -> Axis.Y;
            case NORTH, SOUTH -> Axis.Z;
            default -> throw new UnsupportedOperationException("No axis corresponding to block face");
        };
    }

    private static Axis cycleAxis(final Axis axis, final BlockFace blockFace) {
        return switch (blockFace) {
            case NORTH, EAST, SOUTH, WEST -> {
                if (axis == Axis.Y) yield blockFaceToAxis(blockFace);
                else yield axis == Axis.X ? Axis.Z : Axis.X;
            }
            case UP, DOWN -> Axis.Y;
            default -> throw new UnsupportedOperationException();
        };
    }
}
