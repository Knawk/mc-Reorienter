package net.knawk.mc.reorienter.orient;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;

public class StairOrienter implements Orienter {
    @Override
    public boolean act(OrientAction action, Block block, BlockFace blockFace, BlockData blockData) {
        return false;
    }
}
