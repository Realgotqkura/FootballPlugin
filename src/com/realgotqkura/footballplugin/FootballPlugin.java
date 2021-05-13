package com.realgotqkura.footballplugin;

import com.realgotqkura.commands.EquipMovement;
import com.realgotqkura.commands.MovementCompleter;
import com.realgotqkura.commands.SpawnBall;
import com.realgotqkura.listeners.Abilities.DeathZone;
import com.realgotqkura.listeners.Abilities.DragonCrash;
import com.realgotqkura.listeners.Abilities.FireTornado;
import com.realgotqkura.listeners.BasicMovementListeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class FootballPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getCommand("spawnball").setExecutor(new SpawnBall(this));
        this.getCommand("equipmovement").setExecutor(new EquipMovement());
        this.getCommand("equipmovement").setTabCompleter(new MovementCompleter());

        this.getServer().getPluginManager().registerEvents(new BasicMovementListeners(this), this);
        this.getServer().getPluginManager().registerEvents(new DragonCrash(this), this);
        this.getServer().getPluginManager().registerEvents(new FireTornado(this), this);
        this.getServer().getPluginManager().registerEvents(new DeathZone(), this);
        this.getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {


    }

    @EventHandler
    public void j(PlayerJoinEvent e){
        e.getPlayer().setGravity(true);
    }
}
