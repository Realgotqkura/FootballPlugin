package com.realgotqkura.npcs;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.realgotqkura.footballplugin.FootballPlugin;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CreateNPC {

    private static Map<Integer, EntityPlayer> NPCs = new HashMap<>();

    public static void createNPC(String npcname,Location loc, String worldname, Player player, String skin){
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Objects.requireNonNull(Bukkit.getWorld(worldname))).getHandle();
        GameProfile profile = new GameProfile(UUID.randomUUID(),npcname);
        EntityPlayer npc = new EntityPlayer(server,world,profile, new PlayerInteractManager(world));
        npc.setLocation(loc.getX() + 1 * loc.getDirection().getX(), loc.getY(), loc.getZ() + 1 * loc.getDirection().getZ(),
                loc.getYaw(), loc.getPitch());

        String[] name = getSkin(player, skin);
        profile.getProperties().put("textures", new Property("textures", name[0], name[1]));


        addNPCPacket(npc);
        NPCs.put(npc.getId(),npc);
    }

    public static void loadNPC(Location loc, GameProfile profile){
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld)loc.getWorld()).getHandle();
        EntityPlayer npc = new EntityPlayer(server,world,profile, new PlayerInteractManager(world));
        npc.setLocation(loc.getX(), loc.getY(), loc.getZ(),
                loc.getYaw(), loc.getPitch());
        npc.setNoGravity(false);



        addNPCPacket(npc);
        NPCs.put(npc.getId(),npc);
    }

    public static EntityPlayer getSpecificNPC(org.bukkit.World world, GameProfile profile){
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldserver = ((CraftWorld)world).getHandle();
        EntityPlayer npc = new EntityPlayer(server,worldserver,profile, new PlayerInteractManager(worldserver));

        return npc;
    }

    public static void addNPCPacket(EntityPlayer npc){
        for(Player player : Bukkit.getOnlinePlayers()){
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        }
    }

    public static void addJoinPacket(Player player){
        NPCs.forEach((k,v)->{
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, v));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(v));

        });
    }

    public static void removeNPC(Player player, EntityPlayer npc){
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
    }

    public static Map<Integer, EntityPlayer> getNPCs(){
        return NPCs;
    }

    public static String[] getSkin(Player player, String name){
        try{
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            String uuid = new JsonParser().parse(reader).getAsJsonObject().get("id").getAsString();
            URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader2 = new InputStreamReader(url2.openStream());
            JsonObject property = new JsonParser().parse(reader2).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = property.get("value").getAsString();
            String signature = property.get("signature").getAsString();
            return new String[]{texture, signature};

        }catch(Exception e){
            return null;
        }
    }
}
