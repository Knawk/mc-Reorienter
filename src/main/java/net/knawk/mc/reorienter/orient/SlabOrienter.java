package net.knawk.mc.reorienter.orient;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;

public class SlabOrienter implements Orienter {
    @Override
    public boolean act(OrientAction action, Block block, BlockFace clickedFace, BlockData blockData) {
        final Slab slab = (Slab) blockData;
        final Slab.Type slabType = slab.getType();
        if (slabType == Slab.Type.DOUBLE) return false;

        final Slab.Type newType = switch (action) {
            case SET -> switch (clickedFace) {
                case UP -> Slab.Type.TOP;
                case DOWN -> Slab.Type.BOTTOM;
                default -> slabType;
            };
            case CYCLE -> switch (slabType) {
                case TOP -> Slab.Type.BOTTOM;
                case BOTTOM -> Slab.Type.TOP;
                //noinspection ConstantConditions
                case DOUBLE -> throw new IllegalStateException();
            };
        };

        slab.setType(newType);
        block.setBlockData(blockData);
        return true;
    }
}
