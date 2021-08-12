package net.knawk.mc.reorienter.orient;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;

public class RotatableOrienter implements Orienter {
    @Override
    public boolean act(OrientAction action, Block block, BlockFace clickedFace, BlockData blockData) {
        final Rotatable rotatable = (Rotatable) blockData;
        switch (action) {
            case SET -> {

            }
            case CYCLE -> {
            }
        }
        block.setBlockData(blockData);
        return true;
    }
}
