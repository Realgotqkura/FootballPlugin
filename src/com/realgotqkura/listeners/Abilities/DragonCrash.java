package com.realgotqkura.listeners.Abilities;

import com.realgotqkura.commands.SpawnBall;
import com.realgotqkura.footballplugin.FootballPlugin;
import com.realgotqkura.items.Abilities;
import com.realgotqkura.utils.Bezier;
import com.realgotqkura.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.util.List;

public class DragonCrash implements Listener {

    private boolean run = true;

    private FootballPlugin plugin;
    public DragonCrash(FootballPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location ploc = player.getLocation();
        if (player.getInventory().getItemInMainHand().equals(Abilities.DragonCrashItem())) {
            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (event.getHand() == EquipmentSlot.HAND) {
                    Location firstloc = new Location(player.getWorld(), ploc.getX() + 2, ploc.getY() + 2,ploc.getZ() - 2);
                    Location secondLoc = new Location(player.getWorld(), ploc.getX() - 2, ploc.getY() - 2, ploc.getZ() + 2);
                    ArmorStand stand = SpawnBall.publicStand;
                    if (Utilities.isInside(stand.getLocation(), firstloc, secondLoc) && run){
                        Location point1 = stand.getLocation();
                        Location point2 = new Location(player.getWorld(), point1.getX() + 13 * player.getLocation().getDirection().getX(), point1.getY() + 4, point1.getZ() + 13 * player.getLocation().getDirection().getZ());
                        Location point3 = new Location(player.getWorld(), point1.getX() + 26 * player.getLocation().getDirection().getX(), point1.getY(), point1.getZ() + 26 * player.getLocation().getDirection().getZ());

                        List<Location> curve = Bezier.bezierCurve(21, point1, point2, point3);


                                new BukkitRunnable() {

                                    int i = 0;

                                    @Override
                                    public void run() {
                                        Location tploc = curve.get(i);
                                        tploc.setYaw(tploc.getYaw() + i * 10);
                                        stand.teleport(tploc);
                                        player.getWorld().spawnParticle(Particle.REDSTONE, tploc.getX(), tploc.getY() + 1.6, tploc.getZ(),7 , 1, 36F / 255, 156F / 255, 1, new Particle.DustOptions(Color.fromRGB(36,156,255), 2)); //Blue, red, green
                                        i += 1;
                                        if (i > 19) {
                                            Location loc = new Location(player.getWorld(), stand.getLocation().getX(),
                                                    plugin.getConfig().getLocation("FootballPlugin.ball_location").getY(),
                                                    stand.getLocation().getZ());
                                            stand.teleport(loc);
                                            Utilities.AnimatedTitle("Dragon Crash", plugin, player, 0);
                                            cancel();
                                        }
                                    }

                                }.runTaskTimer(plugin, 1, 0);



                    }
                }
            }
        }
    }
}
