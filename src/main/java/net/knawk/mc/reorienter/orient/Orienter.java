package net.knawk.mc.reorienter.orient;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;

public interface Orienter {
    boolean act(OrientAction action, final Block block, final BlockFace blockFace, final BlockData blockData);
}
