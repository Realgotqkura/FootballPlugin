package com.realgotqkura.listeners.Abilities;

import com.realgotqkura.commands.SpawnBall;
import com.realgotqkura.footballplugin.FootballPlugin;
import com.realgotqkura.items.Abilities;
import com.realgotqkura.items.BasicMovement;
import com.realgotqkura.utils.Bezier;
import com.realgotqkura.utils.Utilities;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;

public class FireTornado implements Listener {

    private FootballPlugin plugin;
    public FireTornado(FootballPlugin plugin){
        this.plugin = plugin;
    }

    private void launch(Player player, ArmorStand stand){
        Location loc = player.getLocation();
        Location sloc = stand.getLocation();
        float yaw = player.getLocation().getYaw();
        Location p2 = new Location(player.getWorld(), loc.getX(),loc.getY() + 7, loc.getZ());
        Location p3 = new Location(player.getWorld(), loc.getX(),loc.getY() + 14, loc.getZ());
        Location stand_p2 = new Location(player.getWorld(), sloc.getX(),sloc.getY() + 7, sloc.getZ());
        Location stand_p3 = new Location(player.getWorld(), sloc.getX(),sloc.getY() + 14, sloc.getZ());
        List<Location> curve = Bezier.bezierCurve(20, loc, p2, p3);
        List<Location> standcurve = Bezier.bezierCurve(20, sloc, stand_p2, stand_p3);
        new BukkitRunnable() {

            int i = 0;

            @Override
            public void run() {
                Location tploc = curve.get(i);
                Location standtploc = standcurve.get(i);
                player.teleport(tploc);
                stand.teleport(standtploc);
                i++;
                if (i > 18) {

                    player.getWorld().getBlockAt(new Location(player.getWorld(), tploc.getX(), tploc.getY() - 1, tploc.getZ())).setType(Material.BARRIER);
                    new BukkitRunnable(){

                        int j = 0;
                        @Override
                        public void run() {
                            j++;
                            tploc.setYaw(tploc.getYaw() + 20);
                            player.teleport(tploc);
                            if(j > 12){
                                double y = standtploc.getY() - sloc.getY();
                                tploc.setYaw(yaw);
                                player.teleport(tploc);
                                player.getWorld().getBlockAt(new Location(player.getWorld(), tploc.getX(), tploc.getY() - 1, tploc.getZ())).setType(Material.AIR);
                                Location ball_p2 = new Location(player.getWorld(), standtploc.getX() + 15 * tploc.getDirection().getX(), sloc.getY() + 7, standtploc.getZ() + 15 * tploc.getDirection().getZ());
                                Location ball_p3 = new Location(player.getWorld(), standtploc.getX() + 30 * tploc.getDirection().getX(), sloc.getY(), standtploc.getZ() + 30 * tploc.getDirection().getZ());
                                List<Location> ballcurve = Bezier.bezierCurve(20, standtploc, ball_p2, ball_p3);
                                new BukkitRunnable(){

                                    int k = 0;
                                    @Override
                                    public void run() {
                                        Location balltploc = ballcurve.get(k);
                                        stand.teleport(balltploc);
                                        for(int i = 0; i <360; i+=5){
                                            balltploc.setZ(balltploc.getZ() + Math.cos(i)*1);
                                            balltploc.setX(balltploc.getX() + Math.sin(i)*1);
                                            Objects.requireNonNull(balltploc.getWorld()).spawnParticle(Particle.REDSTONE, balltploc.getX(), balltploc.getY() + 2F, balltploc.getZ(),7 , 1, 36F / 255, 156F / 255, 1, new Particle.DustOptions(Color.fromRGB(240,124,41), 1)); //Blue, red, green
                                        }
                                        k++;
                                        if(k == 18){
                                            Location finalloc = new Location(player.getWorld(), stand.getLocation().getX(),
                                                    plugin.getConfig().getLocation("FootballPlugin.ball_location").getY(),
                                                    stand.getLocation().getZ());
                                            Utilities.AnimatedTitle("Fire Tornado", plugin, player, 0, "#ffa10a");
                                            stand.teleport(finalloc);

                                            cancel();
                                        }
                                    }

                                }.runTaskTimer(plugin, 1, 0);
                                cancel();
                            }
                        }

                    }.runTaskTimer(plugin, 3,0);
                    cancel();
                }
            }

        }.runTaskTimer(plugin, 1, 0);

    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Location ploc = player.getLocation();
        if(player.getInventory().getItemInMainHand().equals(Abilities.FireTornadoItem())) {
            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (event.getHand() == EquipmentSlot.HAND) {
                    Location firstloc = new Location(player.getWorld(), ploc.getX() + 2, ploc.getY() + 2,ploc.getZ() - 2);
                    Location secondLoc = new Location(player.getWorld(), ploc.getX() - 2, ploc.getY() - 2, ploc.getZ() + 2);
                    ArmorStand stand = SpawnBall.publicStand;
                    if(Utilities.isInside(stand.getLocation(), firstloc, secondLoc))
                     launch(player, stand);
                }
            }
        }

    }
}
