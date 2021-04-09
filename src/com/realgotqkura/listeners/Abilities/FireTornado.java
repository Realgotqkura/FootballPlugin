package com.realgotqkura.listeners.Abilities;

import com.realgotqkura.footballplugin.FootballPlugin;
import com.realgotqkura.items.Abilities;
import com.realgotqkura.items.BasicMovement;
import com.realgotqkura.utils.Bezier;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class FireTornado implements Listener {

    private FootballPlugin plugin;
    public FireTornado(FootballPlugin plugin){
        this.plugin = plugin;
    }

    private void launchplayer(Player player){
        Location loc = player.getLocation();
        float yaw = player.getLocation().getYaw();
        Location p2 = new Location(player.getWorld(), loc.getX(),loc.getY() + 7, loc.getZ());
        Location p3 = new Location(player.getWorld(), loc.getX(),loc.getY() + 14, loc.getZ());
        List<Location> curve = Bezier.bezierCurve(25, loc, p2, p3);
        new BukkitRunnable() {

            int i = 0;

            @Override
            public void run() {
                Location tploc = curve.get(i);
                player.teleport(tploc);
                i += 1;
                if (i > 23) {
                    player.getWorld().getBlockAt(new Location(player.getWorld(), tploc.getX(), tploc.getY() - 1, tploc.getZ())).setType(Material.BARRIER);
                    new BukkitRunnable(){

                        int j = 0;
                        @Override
                        public void run() {
                            j++;
                            tploc.setYaw(tploc.getYaw() + 20);
                            player.teleport(tploc);
                            if(j > 12){
                                tploc.setYaw(yaw);
                                player.teleport(tploc);
                                player.getWorld().getBlockAt(new Location(player.getWorld(), tploc.getX(), tploc.getY() - 1, tploc.getZ())).setType(Material.AIR);
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
        if(player.getInventory().getItemInMainHand().equals(Abilities.FireTornadoItem())) {
            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (event.getHand() == EquipmentSlot.HAND) {
                    player.sendMessage("E");
                     launchplayer(player);
                }
            }
        }

    }
}
