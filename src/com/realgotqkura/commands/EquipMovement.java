package com.realgotqkura.commands;

import com.realgotqkura.items.Abilities;
import com.realgotqkura.items.BasicMovement;
import com.realgotqkura.utils.ColorOf;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EquipMovement implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }

        Player player = (Player) sender;
        Location ploc = player.getLocation();
        if(label.equalsIgnoreCase("equipmovement")){
            if(args.length == 0){
                player.getInventory().setItem(0, BasicMovement.WeakKick());
                player.getInventory().setItem(1, BasicMovement.StrongKick());
                player.sendMessage(ColorOf.colorof("&aEquipped the default movements."));
                return true;
            }

            if(args[0].equalsIgnoreCase("kevin")){
                player.getInventory().setItem(0, BasicMovement.WeakKick());
                player.getInventory().setItem(1, BasicMovement.StrongKick());
                player.getInventory().setItem(8, Abilities.DragonCrashItem());
                player.getInventory().setItem(7, Abilities.FireTornadoItem());
                player.sendMessage(ColorOf.colorof("&aEquipped the Kevin movements."));
            }


        }
        return false;
    }
}
