package com.realgotqkura.commands;

import com.realgotqkura.footballplugin.FootballPlugin;
import com.realgotqkura.utils.SkullCreator;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Objects;

public class SpawnBall implements CommandExecutor {

    private final String ballskin = "8e4a70b7bbcd7a8c322d522520491a27ea6b83d60ecf961d2b4efbbf9f605d";
    public static ArmorStand publicStand;

    private FootballPlugin plugin;
    public SpawnBall(FootballPlugin plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("Bad");
            return false;
        }

        if (label.equalsIgnoreCase("spawnball")) {
            Player player = (Player) sender;
            Location ploc = player.getLocation();
            World world = player.getWorld();
            Location spawnloc = new Location(world, ploc.getX() + 2 * ploc.getDirection().getX(), ploc.getY() - 1.4, ploc.getZ() + 2 * ploc.getDirection().getZ());
            ArmorStand stand = (ArmorStand) world.spawnEntity(spawnloc, EntityType.ARMOR_STAND);
            stand.setGravity(false);
            stand.setVisible(false);
            stand.setInvulnerable(true);
            Objects.requireNonNull(stand.getEquipment()).setHelmet(SkullCreator.itemFromUrl(ballskin));
            publicStand = stand;
            plugin.getConfig().set("FootballPlugin.ball_location", spawnloc);
            plugin.saveConfig();
            return true;
        }
        return false;
    }
}
