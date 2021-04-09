package com.realgotqkura.utils;

import com.realgotqkura.footballplugin.FootballPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Utilities {

    public static boolean isInside(Location loc, Location l1, Location l2) {
        int x1 = Math.min(l1.getBlockX(), l2.getBlockX());
        int y1 = Math.min(l1.getBlockY(), l2.getBlockY());
        int z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
        int x2 = Math.max(l1.getBlockX(), l2.getBlockX());
        int y2 = Math.max(l1.getBlockY(), l2.getBlockY());
        int z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        return x >= x1 && x <= x2 && y >= y1 && y <= y2 && z >= z1 && z <= z2;
    }

    public static void AnimatedTitle(String s, FootballPlugin plugin, Player player, int index1, String color){
        char[] chars = s.toCharArray();
        new BukkitRunnable(){

            String message = "";
            int index = index1;
            @Override
            public void run() {
                if(index == chars.length){
                    player.sendTitle("",ColorOf.colorof("&a&k&lE " + ChatColor.of(color) +  s + " &a&k&lE"), 5,20,20);
                    cancel();
                }
                if(index == 0){
                    player.sendTitle("", ChatColor.of(color) + "" + chars[index], 5,20,20);
                    index++;
                    return;
                }

                for(int i = 0; i <= index; i++){
                    message += chars[i];
                }
                player.sendTitle("",ChatColor.of(color) + message, 5,20,20);
                message = "";
                index++;
            }

        }.runTaskTimer(plugin, 3,2);
    }



    public static void UnfinishedAnimatedTitle(String s, FootballPlugin plugin, Player player){
        char[] chars = s.toCharArray();
        new BukkitRunnable(){

            String message = "";
            int index = 0;
            @Override
            public void run() {
                if(index == chars.length){
                    player.sendTitle("",ColorOf.colorof("&3" + s + ""), 5,20,20);
                    cancel();
                }
                if(index == 0){
                    player.sendTitle("",ColorOf.colorof("&3" + chars[index] + ""), 5,20,20);
                    index++;
                    return;
                }

                for(int i = 0; i <= index; i++){
                    message += chars[i];
                }
                player.sendTitle("",ColorOf.colorof("&3" + message + ""), 5,20,20);
                message = "";
                index++;
            }

        }.runTaskTimer(plugin, 3,2);
    }
}
