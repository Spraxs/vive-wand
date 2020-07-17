package nl.spraxs.vivewand;

import lombok.Getter;
import nl.spraxs.vivewand.framework.Wand;
import nl.spraxs.vivewand.listeners.PlayerInteractWandListener;
import nl.spraxs.vivewand.spells.SpellSpark;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.vivecraft.VSE;

import java.util.HashMap;
import java.util.Map;

public final class ViveWand extends JavaPlugin implements Listener {

    public static ViveWand Instance;

    private @Getter Map<Integer, Wand> wands = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Instance = this;

        Bukkit.getLogger().info("Enabled: " + VSE.me.isEnabled());

        Bukkit.getPluginManager().registerEvents(new PlayerInteractWandListener(), this);

        Wand wand = new Wand(Material.BLAZE_ROD, ChatColor.RED + "Kut Wand");

        wand.addSpell(new SpellSpark(this));

        wand.save();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.getPlayer().getInventory().addItem(wands.get(0).getItemStack());
    }
}
