package net.knawk.mc.reorienter;

import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;
import java.util.logging.Logger;

public class ReorienterListener implements Listener {
    private final Logger log;

    ReorienterListener(final ReorienterPlugin plugin) {
        log = plugin.getLogger();
    }

    private enum Action {
        SET,
        CYCLE,
    }

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (!Util.isReorienterItem(event.getItem())) return;

        final Action action = switch (event.getAction()) {
            case LEFT_CLICK_BLOCK -> Action.SET;
            case RIGHT_CLICK_BLOCK -> Action.CYCLE;
            default -> null;
        };
        if (action == null) return;

        log.info(String.format("%s on %s of %s",
                action,
                event.getBlockFace().name(),
                Objects.requireNonNull(event.getClickedBlock()).getType().name()));

        assert  event.getClickedBlock() != null;
        final BlockData blockData = event.getClickedBlock().getBlockData();

        final BlockFace clickedBlockFace = event.getBlockFace();
        if (blockData instanceof Orientable orientable) {
            log.info(String.format("clicked on %s face of block oriented along %s axis",
                    clickedBlockFace.name(), orientable.getAxis().name()));
        }

        event.setCancelled(true);
    }
}
