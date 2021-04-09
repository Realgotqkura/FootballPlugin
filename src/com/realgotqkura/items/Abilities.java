package com.realgotqkura.items;

import com.realgotqkura.utils.ColorOf;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Abilities {

    public static ItemStack DragonCrashItem(){
        ItemStack stack = new ItemStack(Material.SOUL_CAMPFIRE);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.of("#1ac7a7") + "Dragon Crash");
        List<String> lore = new ArrayList<>();
        lore.add(ColorOf.colorof("&7Ability Owner: &bKevin"));
        lore.add("");
        lore.add(ColorOf.colorof("&7Ability: &3Makes a powerful shot"));
        lore.add(ColorOf.colorof("&7Combos: &cFire Tornado"));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack FireTornadoItem(){
        ItemStack stack = new ItemStack(Material.CAMPFIRE);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.of("#fc9d03") + "Fire Tornado");
        List<String> lore = new ArrayList<>();
        lore.add(ColorOf.colorof("&7Ability Owner: &cAxel"));
        lore.add("");
        lore.add(ColorOf.colorof("&7Ability: &6Makes a powerful shot in the air"));
        lore.add(ColorOf.colorof("&7Combos: &3Dragon Crash"));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }
}
