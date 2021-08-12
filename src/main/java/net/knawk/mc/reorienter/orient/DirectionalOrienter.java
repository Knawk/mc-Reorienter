package net.knawk.mc.reorienter.orient;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.Piston;

import java.util.Set;

public class DirectionalOrienter implements Orienter {
    @Override
    public boolean act(OrientAction action, Block block, BlockFace blockFace, BlockData blockData) {
        if (!isReorientable(block)) return false;

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

    private static final Set<Material> REORIENTABLE_DIRECTIONALS = Set.of(
            Material.ENDER_CHEST,
            Material.CARVED_PUMPKIN,
            Material.JACK_O_LANTERN,
            Material.END_ROD,
            Material.LIGHTNING_ROD,
            Material.BARREL,
            Material.WHITE_GLAZED_TERRACOTTA,
            Material.ORANGE_GLAZED_TERRACOTTA,
            Material.MAGENTA_GLAZED_TERRACOTTA,
            Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
            Material.YELLOW_GLAZED_TERRACOTTA,
            Material.LIME_GLAZED_TERRACOTTA,
            Material.PINK_GLAZED_TERRACOTTA,
            Material.GRAY_GLAZED_TERRACOTTA,
            Material.LIGHT_GRAY_GLAZED_TERRACOTTA,
            Material.CYAN_GLAZED_TERRACOTTA,
            Material.PURPLE_GLAZED_TERRACOTTA,
            Material.BLUE_GLAZED_TERRACOTTA,
            Material.BROWN_GLAZED_TERRACOTTA,
            Material.GREEN_GLAZED_TERRACOTTA,
            Material.RED_GLAZED_TERRACOTTA,
            Material.BLACK_GLAZED_TERRACOTTA,
            Material.DISPENSER,
            Material.DROPPER,
            Material.OBSERVER,
            Material.HOPPER
    );

    private static boolean isReorientable(final Block block) {
        final Material material = block.getType();
        final BlockData blockData = block.getBlockData();

        return Tag.BEEHIVES.isTagged(material)
                || (blockData instanceof Chest chest && chest.getType() == Chest.Type.SINGLE)
                || (blockData instanceof Piston piston && !piston.isExtended())
                || REORIENTABLE_DIRECTIONALS.contains(material);
    }
}
