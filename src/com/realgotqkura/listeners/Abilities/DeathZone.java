package com.realgotqkura.listeners.Abilities;

import com.realgotqkura.commands.SpawnBall;
import com.realgotqkura.footballplugin.FootballPlugin;
import com.realgotqkura.items.Abilities;
import com.realgotqkura.npcs.CreateNPC;
import com.realgotqkura.utils.Bezier;
import com.realgotqkura.utils.Utilities;
import com.realgotqkura.utils.VectorHelper;
import net.minecraft.server.v1_16_R2.EntityPlayer;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntity;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_16_R2.PlayerConnection;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import javax.rmi.CORBA.Util;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DeathZone implements Listener {


    public ItemStack Heads(){
        ItemStack head1 = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head1.getItemMeta();
        meta.setOwner("MBMercBlade");
        head1.setItemMeta(meta);
        return head1;
    }
    public ItemStack Head1(){
        ItemStack head2 = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta1 = (SkullMeta) head2.getItemMeta();
        meta1.setOwner("MCL0rd");
        head2.setItemMeta(meta1);
        return head2;
    }

    private void doStuff(Player player, ArmorStand stand){
        Location sloc = stand.getLocation();
        Location ploc = player.getLocation();
        Location newPloc = new Location(player.getWorld(), sloc.getX() - 4 * ploc.getDirection().getX(), ploc.getY(),
                sloc.getZ() - 3 * ploc.getDirection().getZ());
        player.teleport(newPloc);
        ArmorStand stand1 = (ArmorStand) player.getWorld().spawnEntity(new Location(ploc.getWorld(), newPloc.getX() + 6, newPloc.getY(), newPloc.getZ() + 7), EntityType.ARMOR_STAND);
        stand1.getEquipment().setHelmet(Heads());
        stand1.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        stand1.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        stand1.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
        stand1.setGravity(false);
        stand1.setArms(true);
        stand1.setCustomName("MBMercBlade");
        stand1.setCustomNameVisible(true);
        ArmorStand stand2 = (ArmorStand) player.getWorld().spawnEntity(new Location(ploc.getWorld(), newPloc.getX() - 6, newPloc.getY(), newPloc.getZ() + 7), EntityType.ARMOR_STAND);
        stand2.getEquipment().setHelmet(Head1());
        stand2.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        stand2.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        stand2.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
        stand2.setArms(true);
        stand2.setGravity(false);
        stand2.setCustomName("MCL0rd");
        stand2.setCustomNameVisible(true);
        Location point1 = new Location(stand.getWorld(), stand.getLocation().getX(), stand.getLocation().getY() + 30, stand.getLocation().getZ());
        Location point2 = new Location(point1.getWorld(), stand.getLocation().getX() + 10 * newPloc.getDirection().getX(), stand.getLocation().getY() + 15, stand
        .getLocation().getZ() + 10 * newPloc.getDirection().getZ());
        Location point3 = new Location(point1.getWorld(), stand.getLocation().getX() + 20 * newPloc.getDirection().getX(), stand.getLocation().getY(), stand
                .getLocation().getZ() + 20 * newPloc.getDirection().getZ());
       List<Location> locs =  Bezier.bezierCurve(25, point1, point2, point3);
        new BukkitRunnable(){

            @Override
            public void run() {
                new BukkitRunnable(){
                    int i = 0;

                    @Override
                    public void run() {
                        i++;
                        stand1.teleport(new Location(player.getWorld(), stand1.getLocation().getX(), stand1.getLocation().getY() + 1, stand1.getLocation().getZ(), stand1.getLocation().getYaw() + 10, stand1.getLocation().getPitch()));
                        stand2.teleport(new Location(player.getWorld(), stand2.getLocation().getX(), stand2.getLocation().getY() + 1, stand2.getLocation().getZ(), stand2.getLocation().getYaw() + 10, stand1.getLocation().getPitch()));
                        player.teleport(new Location(player.getWorld(), newPloc.getX(), player.getLocation().getY() + 1, newPloc.getZ()));
                        stand.teleport(new Location(player.getWorld(), sloc.getX(), stand.getLocation().getY() + 1, sloc.getZ()));
                        if(i == 30){
                            player.getWorld().getBlockAt((int) player.getLocation().getX(), (int) player.getLocation().getY() - 1, (int) player.getLocation().getZ())
                                    .setType(Material.BARRIER);
                            new BukkitRunnable(){

                                int i = 0;
                                @Override
                                public void run() {
                                    i++;
                                    stand1.teleport(new Location(player.getWorld(), stand1.getLocation().getX(), stand1.getLocation().getY(), stand1.getLocation().getZ(), stand1.getLocation().getYaw() + 10, stand1.getLocation().getPitch()));
                                    stand2.teleport(new Location(player.getWorld(), stand2.getLocation().getX(), stand2.getLocation().getY(), stand2.getLocation().getZ(), stand2.getLocation().getYaw() + 10, stand1.getLocation().getPitch()));
                                    VectorHelper helper = new VectorHelper();
                                    helper.drawLine(stand1.getLocation(), stand2.getLocation(), 1);
                                    helper.drawLine(stand1.getLocation(), player.getLocation(), 1);
                                    helper.drawLine(player.getLocation(), stand2.getLocation(), 1);
                                    if(i == 50){
                                        new BukkitRunnable(){

                                            int j = 0;
                                            @Override
                                            public void run() {
                                                Location tploc = locs.get(j);
                                                stand1.teleport(new Location(player.getWorld(), stand1.getLocation().getX(), stand1.getLocation().getY(), stand1.getLocation().getZ(), stand1.getLocation().getYaw() + 10, stand1.getLocation().getPitch()));
                                                stand2.teleport(new Location(player.getWorld(), stand2.getLocation().getX(), stand2.getLocation().getY(), stand2.getLocation().getZ(), stand2.getLocation().getYaw() + 10, stand1.getLocation().getPitch()));
                                                VectorHelper helper = new VectorHelper();
                                                helper.drawLine(stand1.getLocation(), stand2.getLocation(), 1);
                                                helper.drawLine(stand1.getLocation(), player.getLocation(), 1);
                                                helper.drawLine(player.getLocation(), stand2.getLocation(), 1);
                                                //System.out.println(j);
                                                stand.teleport(tploc);
                                                j++;
                                                Objects.requireNonNull(stand.getWorld()).spawnParticle(Particle.REDSTONE, tploc.getX(), tploc.getY(), tploc.getZ(),7 , 1, 36F / 255, 156F / 255, 1, new Particle.DustOptions(Color.fromRGB(147, 23, 255), 1));
                                                if(j == 23){
                                                    stand.teleport(new Location(player.getWorld(), stand.getLocation().getX(),
                                                            FootballPlugin.getPlugin(FootballPlugin.class).getConfig().getLocation("FootballPlugin.ball_location").getY(),
                                                            stand.getLocation().getZ()));
                                                    stand1.remove();
                                                    stand2.remove();
                                                    player.getWorld().getBlockAt((int) player.getLocation().getX(), (int) player.getLocation().getY() - 1, (int) player.getLocation().getZ())
                                                            .setType(Material.AIR);
                                                    player.teleport(new Location(player.getWorld(), newPloc.getX(), newPloc.getY(), newPloc.getZ()));
                                                    Utilities.AnimatedTitle("Death Zone", FootballPlugin.getPlugin(FootballPlugin.class), player, 0, "#6c0bbf");
                                                    cancel();
                                                }
                                            }

                                        }.runTaskTimer(FootballPlugin.getPlugin(FootballPlugin.class), 2, 1);
                                        cancel();
                                    }
                                }


                            }.runTaskTimer(FootballPlugin.getPlugin(FootballPlugin.class), 5, 1);
                            cancel();
                        }
                    }

                }.runTaskTimer(FootballPlugin.getPlugin(FootballPlugin.class), 20,1);
                cancel();
            }


        }.runTaskLater(FootballPlugin.getPlugin(FootballPlugin.class), 10);

    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Location ploc = player.getLocation();
        if(player.getInventory().getItemInMainHand().equals(Abilities.DeathZoneItem())) {
            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (event.getHand() == EquipmentSlot.HAND) {
                    Location firstloc = new Location(player.getWorld(), ploc.getX() + 2, ploc.getY() + 2,ploc.getZ() - 2);
                    Location secondLoc = new Location(player.getWorld(), ploc.getX() - 2, ploc.getY() - 2, ploc.getZ() + 2);
                    ArmorStand stand = SpawnBall.publicStand;
                    if(Utilities.isInside(stand.getLocation(), firstloc, secondLoc)){
                       doStuff(player,stand);
                    }
                }
            }
        }

    }
}
