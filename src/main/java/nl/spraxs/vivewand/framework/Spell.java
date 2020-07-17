package nl.spraxs.vivewand.framework;

import lombok.Getter;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.vivecraft.VivePlayer;

/**
 * Created by Spraxs
 * Date: 12/4/2019
 */

public abstract class Spell {

    protected @Getter String name;
    private @Getter JavaPlugin plugin;

    public Spell(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

    public abstract void onCast(VivePlayer vivePlayer, World world);
}
