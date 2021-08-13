package net.knawk.mc.reorienter.orient;

import net.knawk.mc.reorienter.Util;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Stairs;

public class StairOrienter implements Orienter {
    @Override
    public boolean act(OrientAction action, Block block, BlockFace clickedFace, BlockData blockData) {
        final Stairs stairs = (Stairs) blockData;

        if (Util.isVertical(clickedFace)) {
            final Bisected.Half half = stairs.getHalf();
            final Bisected.Half newHalf = switch (action) {
                case SET -> blockFaceToHalf(clickedFace.getOppositeFace());
                case CYCLE -> otherHalf(half);
            };
            stairs.setHalf(newHalf);
        } else {
            final BlockFace face = stairs.getFacing();
            final BlockFace newFace = switch (action) {
                case SET -> clickedFace.getOppositeFace();
                case CYCLE -> Util.nextHorizontal(face);
            };
            stairs.setFacing(newFace);
        }

        block.setBlockData(blockData);
        return true;
    }

    private static Bisected.Half blockFaceToHalf(final BlockFace blockFace) {
        return switch (blockFace) {
            case UP -> Bisected.Half.TOP;
            case DOWN -> Bisected.Half.BOTTOM;
            default -> throw new UnsupportedOperationException();
        };
    }

    private static Bisected.Half otherHalf(final Bisected.Half half) {
        return switch (half) {
            case TOP -> Bisected.Half.BOTTOM;
            case BOTTOM -> Bisected.Half.TOP;
        };
    }
}
