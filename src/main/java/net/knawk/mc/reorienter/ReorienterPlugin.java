package net.knawk.mc.reorienter;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ReorienterPlugin extends JavaPlugin {
    private final Logger log;

    public ReorienterPlugin() {
        log = getLogger();
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager()
                .registerEvents(new ReorienterListener(this), this);

        // TODO register crafting recipe
    }
}
