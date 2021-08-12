package net.knawk.mc.reorienter.orient;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

public class DirectionalOrienter implements Orienter {
    @Override
    public boolean act(OrientAction action, Block block, BlockFace blockFace, BlockData blockData) {
        final Directional directional = (Directional) blockData;
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
