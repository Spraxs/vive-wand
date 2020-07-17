package nl.spraxs.vivewand.listeners;

import nl.spraxs.vivewand.ViveWand;
import nl.spraxs.vivewand.framework.Spell;
import nl.spraxs.vivewand.framework.Wand;
import nl.spraxs.vivewand.utils.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;
import org.vivecraft.VSE;
import org.vivecraft.VivePlayer;

/**
 * Created by Spraxs
 * Date: 12/4/2019
 */

public class PlayerInteractWandListener implements Listener {

    public PlayerInteractWandListener() {
        this.viveWand = ViveWand.Instance;
    }

    private ViveWand viveWand;

    @EventHandler
    public void onInteractWand(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        final Player player = event.getPlayer();
        final VivePlayer vivePlayer = VSE.vivePlayers.get(player.getUniqueId());
        if (!vivePlayer.isVR()) return;
        if (!ItemUtil.hasPersistentData(ViveWand.Instance, event.getItem(), "wand", PersistentDataType.INTEGER)) return;

        int id = ItemUtil.getPersistentData(ViveWand.Instance, event.getItem(), "wand", PersistentDataType.INTEGER);

        Wand wand = viveWand.getWands().get(id);

        if (wand == null) {
            event.getPlayer().sendMessage(ChatColor.RED + "Wand is not saved in current cache! Please use a new one.");
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            event.setCancelled(true);

            Spell spell = wand.nextSpell();

            if (spell == null) {
                player.sendMessage(ChatColor.GOLD + "Er staan nog geen spells op deze wand!");
                return;
            }

            player.sendMessage(ChatColor.GOLD + "Geselecteerd: [" + ChatColor.GRAY + spell.getName() + ChatColor.GOLD + "]");

            player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, vivePlayer.getControllerPos(0), 5, .1f, .1f, .1f, 0.5D);

            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 4.0f, 1.0f);

        } else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            event.setCancelled(true);

            wand.castSpell(vivePlayer, player.getWorld());
        }
    }
}
