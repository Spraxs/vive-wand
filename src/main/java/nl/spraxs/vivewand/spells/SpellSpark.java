package nl.spraxs.vivewand.spells;

import net.minecraft.server.v1_14_R1.Vec3D;
import nl.spraxs.vivewand.ViveWand;
import nl.spraxs.vivewand.framework.Spell;
import nl.spraxs.vivewand.utils.InstantFirework;
import org.bukkit.*;
import org.bukkit.entity.SmallFireball;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.vivecraft.VivePlayer;

/**
 * Created by Spraxs
 * Date: 12/4/2019
 */

public class SpellSpark extends Spell {

    public SpellSpark(ViveWand plugin) {
        super(plugin, "Spark");
    }

    @Override
    public void onCast(VivePlayer vivePlayer, World world) {
        Location startPosition = vivePlayer.getControllerPos(0);

        Vec3D handDirection = vivePlayer.getControllerDir(0);

        SmallFireball projectile = world.spawn(startPosition, SmallFireball.class);

        projectile.setDirection(new Vector(handDirection.getX(), handDirection.getY(), handDirection.getZ()));

        new BukkitRunnable() {

            @Override
            public void run() {
                if (!projectile.isValid()) {
                    cancel();

                    InstantFirework.createFireworkEffect(FireworkEffect.builder().with(FireworkEffect.Type.BURST)
                            .withColor(Color.BLACK).withFade(Color.PURPLE).flicker(true).build(), projectile.getLocation());

                    world.createExplosion(projectile.getLocation(), 4.0F);
                    world.spawnParticle(Particle.SMOKE_LARGE, projectile.getLocation(), 40, 2f, 2f, 2f, 0.5D);
                    return;

                }

                InstantFirework.createFireworkEffect(FireworkEffect.builder().with(FireworkEffect.Type.BURST)
                        .withColor(Color.BLACK).withFade(Color.PURPLE).flicker(true).build(), projectile.getLocation());

                world.spawnParticle(Particle.SMOKE_LARGE, projectile.getLocation(), 10, .4f, .4f, .4f, 0.08D);
                world.spawnParticle(Particle.SPELL_WITCH, projectile.getLocation(), 5, .4f, .4f, .4f, 0.03D);

            }
        }.runTaskTimer(getPlugin(), 0L, 5L);
    }
}
