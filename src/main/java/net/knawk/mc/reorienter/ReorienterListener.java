package net.knawk.mc.reorienter;

import com.sun.jdi.InvocationException;
import net.knawk.mc.reorienter.orient.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReorienterListener implements Listener {
    private final Logger log;

    ReorienterListener(final ReorienterPlugin plugin) {
        log = plugin.getLogger();
    }

    private static final Map<Class<? extends BlockData>, Orienter> ORIENTER_BY_BLOCK_DATA;
    static {
        ORIENTER_BY_BLOCK_DATA = new HashMap<>();
        ORIENTER_BY_BLOCK_DATA.put(Orientable.class, new OrientableOrienter());
        ORIENTER_BY_BLOCK_DATA.put(Rotatable.class, new RotatableOrienter());
        ORIENTER_BY_BLOCK_DATA.put(Directional.class, new DirectionalOrienter());
    }

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (!Util.isReorienterItem(event.getItem())) return;

        final OrientAction action = switch (event.getAction()) {
            case LEFT_CLICK_BLOCK -> OrientAction.SET;
            case RIGHT_CLICK_BLOCK -> OrientAction.CYCLE;
            default -> null;
        };
        if (action == null) return;
        assert event.getClickedBlock() != null;
        final Block clickedBlock = event.getClickedBlock();
        final BlockData blockData = clickedBlock.getBlockData();
        final BlockFace clickedBlockFace = event.getBlockFace();

        log.info(String.format("%s on %s of %s",
                action,
                event.getBlockFace().name(),
                Objects.requireNonNull(event.getClickedBlock()).getType().name()));

        final Optional<Orienter> orienter = ORIENTER_BY_BLOCK_DATA.entrySet()
                .stream()
                .filter(entry -> entry.getKey().isInstance(blockData))
                .map(Map.Entry::getValue)
                .findFirst();
        if (orienter.isEmpty()) {
            return;
        }

        if (orienter.get().act(action, clickedBlock, clickedBlockFace, blockData)) {
            event.setCancelled(true);
        }
    }
}
