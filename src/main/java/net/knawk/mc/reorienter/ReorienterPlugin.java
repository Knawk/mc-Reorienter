package net.knawk.mc.reorienter;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ReorienterPlugin extends JavaPlugin {
    private final Logger log;
    private final NamespacedKey reorienterKey;

    public ReorienterPlugin() {
        log = getLogger();
        reorienterKey = new NamespacedKey(this, "reorienter");
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager()
                .registerEvents(new ReorienterListener(this), this);

        getServer().addRecipe(Util.createReorienterRecipe(reorienterKey));
    }

    public NamespacedKey getReorienterKey() {
        return reorienterKey;
    }
}
