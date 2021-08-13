package net.knawk.mc.reorienter.orient;

import com.google.common.collect.Sets;
import net.knawk.mc.reorienter.Util;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.Piston;
import org.bukkit.block.data.type.Switch;

import java.util.Optional;
import java.util.Set;

public class DirectionalOrienter implements Orienter {
    @Override
    public boolean act(OrientAction action, Block block, BlockFace clickedFace, BlockData blockData) {
        final Optional<DirectionalType> optionalDirectionalType = getDirectionalType(block);
        if (optionalDirectionalType.isEmpty()) return false;
        final DirectionalType directionalType = optionalDirectionalType.get();

        final Directional directional = (Directional) blockData;
        final Set<BlockFace> validFaces = directional.getFaces();
        final BlockFace newBlockFace = switch (action) {
            case SET -> switch (directionalType) {
                case FOUR, SIX -> clickedFace;
                case FIVE -> validFaces.contains(clickedFace) ? clickedFace : clickedFace.getOppositeFace();
            };
            case CYCLE -> switch (directionalType) {
                case FOUR, FIVE -> cycleHorizontalBlockFace(directional.getFacing(), clickedFace);
                case SIX -> cycleAnyBlockFace(directional.getFacing(), clickedFace);
            };
        };
        if (directional.getFaces().contains(newBlockFace)) {
            directional.setFacing(newBlockFace);
            block.setBlockData(blockData);
        }
        return true;
    }

    /**
     * Cycling a vertical-facing block returns the clicked face if that face is horizontal, or NORTH otherwise.
     * Cycling a horizontal-facing blocks returns the next face in the horizontal cycle.
     */
    private static BlockFace cycleHorizontalBlockFace(final BlockFace currentBlockFace, final BlockFace clickedBlockFace) {
        assert clickedBlockFace.isCartesian();
        if (Util.isVertical(currentBlockFace)) {
            return Util.isVertical(clickedBlockFace)
                ? BlockFace.NORTH
                : clickedBlockFace;
        }
        return Util.nextHorizontal(currentBlockFace);
    }

    /**
     * Clicking on a vertical face cycles between vertical faces; clicking on a horizontal face cycles between
     * horizontal faces. If the current face is already in the cycle, returns the next face in the cycle; otherwise,
     * returns the clicked face.
     */
    private static BlockFace cycleAnyBlockFace(final BlockFace currentBlockFace, final BlockFace clickedBlockFace) {
        assert clickedBlockFace.isCartesian();

        if (Util.isVertical(clickedBlockFace)) {
            return Util.isVertical(currentBlockFace)
                    ? currentBlockFace.getOppositeFace()
                    : clickedBlockFace;
        }
        return Util.isVertical(currentBlockFace)
                ? clickedBlockFace
                : Util.nextHorizontal(currentBlockFace);
    }

    private static final Set<Material> SIX_FACE_DIRECTIONALS = Set.of(
            Material.BARREL,
            Material.OBSERVER,
            // Rods
            Material.END_ROD,
            Material.LIGHTNING_ROD,
            // Dispensers
            Material.DISPENSER,
            Material.DROPPER,
            // Pistons
            Material.PISTON,
            Material.STICKY_PISTON
    );

    private static final Set<Material> FIVE_FACE_DIRECTIONALS = Set.of(Material.HOPPER);

    private static final Set<Material> FOUR_FACE_DIRECTIONALS = Sets.union(
            Set.of(
                    // Chests
                    Material.CHEST,
                    Material.TRAPPED_CHEST,
                    Material.ENDER_CHEST,
                    // Pumpkins
                    Material.CARVED_PUMPKIN,
                    Material.JACK_O_LANTERN,
                    // Apiculture
                    Material.BEEHIVE,
                    Material.BEE_NEST,
                    // Glazed terracotta
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
                    // Campfires
                    Material.CAMPFIRE,
                    Material.SOUL_CAMPFIRE,
                    // Furnaces
                    Material.FURNACE,
                    Material.BLAST_FURNACE,
                    // Other job blocks
                    Material.SMOKER,
                    Material.LECTERN,
                    Material.LOOM,
                    Material.STONECUTTER,
                    Material.GRINDSTONE,
                    // Redstone components (buttons considered separately)
                    Material.REPEATER,
                    Material.COMPARATOR,
                    Material.LEVER
            ),
            Tag.BUTTONS.getValues()
    );

    private enum DirectionalType { FOUR, FIVE, SIX }

    private static Optional<DirectionalType> getDirectionalType(final Block block) {
        if (isNonOrientableDirectional(block)) return Optional.empty();
        final Material material = block.getType();
        if (SIX_FACE_DIRECTIONALS.contains(material)) return Optional.of(DirectionalType.SIX);
        if (FIVE_FACE_DIRECTIONALS.contains(material)) return Optional.of(DirectionalType.FIVE);
        if (FOUR_FACE_DIRECTIONALS.contains(material)) return Optional.of(DirectionalType.FOUR);
        return Optional.empty();
    }

    private static boolean isNonOrientableDirectional(final Block block) {
        final BlockData blockData = block.getBlockData();
        return (blockData instanceof Chest chest && chest.getType() != Chest.Type.SINGLE)
                || (blockData instanceof Switch aSwitch && aSwitch.getAttachedFace() == FaceAttachable.AttachedFace.WALL)
                || (blockData instanceof Piston piston && piston.isExtended());
    }
}
