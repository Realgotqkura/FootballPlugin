package com.realgotqkura.listeners;

import com.realgotqkura.commands.SpawnBall;
import com.realgotqkura.footballplugin.FootballPlugin;
import com.realgotqkura.items.BasicMovement;
import com.realgotqkura.utils.Bezier;
import com.realgotqkura.utils.Utilities;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class BasicMovementListeners implements Listener {

    private int directionSpeed = 0;
    private int yValueSpeed = 0;

    private FootballPlugin plugin;
    public BasicMovementListeners(FootballPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Location ploc = player.getLocation();
        if(player.getInventory().getItemInMainHand().equals(BasicMovement.StrongKick())){
            if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
                if(event.getHand() == EquipmentSlot.HAND) {

                    Location firstloc = new Location(player.getWorld(), ploc.getX() + 2, ploc.getY() + 2,ploc.getZ() - 2);
                    Location secondLoc = new Location(player.getWorld(), ploc.getX() - 2, ploc.getY() - 2, ploc.getZ() + 2);
                    ArmorStand stand = SpawnBall.publicStand;
                    if(Utilities.isInside(stand.getLocation(), firstloc, secondLoc)){
                    Location point1 = stand.getLocation();
                    Location point2 = new Location(player.getWorld(), point1.getX() + 10 * player.getLocation().getDirection().getX(), point1.getY() + 10, point1.getZ() + 10 * player.getLocation().getDirection().getZ());
                    Location point3 = new Location(player.getWorld(), point1.getX() + 20 * player.getLocation().getDirection().getX(), point1.getY(), point1.getZ() + 20 * player.getLocation().getDirection().getZ());

                    List<Location> curve = Bezier.bezierCurve(25, point1, point2, point3);
                    new BukkitRunnable() {

                        int i = 0;

                        @Override
                        public void run() {
                            Location tploc = curve.get(i);
                            tploc.setYaw(tploc.getYaw() + i * 10);
                            stand.teleport(tploc);
                            i += 1;
                            if (i > 23) {
                                Location loc = new Location(player.getWorld(), stand.getLocation().getX(),
                                        plugin.getConfig().getLocation("FootballPlugin.ball_location").getY(),
                                        stand.getLocation().getZ());
                                stand.teleport(loc);
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
