package com.realgotqkura.utils;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Validate;
import org.bukkit.util.Vector;

import java.util.Objects;

public class VectorHelper {

    public void drawLine(Location point1, Location point2, double space) {
        World world = point1.getWorld();
        Validate.isTrue(point2.getWorld().equals(world), "Lines cannot be in different worlds!");
        double distance = point1.distance(point2);
        Vector p1 = point1.toVector();
        Vector p2 = point2.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
        double length = 0;
        for (; length < distance; p1.add(vector)) {
            Objects.requireNonNull(world).spawnParticle(Particle.REDSTONE, p1.getX(), p1.getY(), p1.getZ(),5 , 1, 36F / 255, 156F / 255, 1, new Particle.DustOptions(Color.fromRGB(147, 23, 255), 0.8F));
            length += space;
        }
    }
}
