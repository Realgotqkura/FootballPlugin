package com.realgotqkura.utils;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Bezier {

    public static Location bezierPoint(float t, Location p0, Location p1, Location p2)
    {
        float a = (1-t)*(1-t);
        float b = 2*(1-t)*t;
        float c = t*t;

        Location p = p0.clone().multiply(a).add(p1.clone().multiply(b)).add(p2.clone().multiply(c));
        //System.out.println(p);
        return p;
    }

    public static List<Location> bezierCurve(int segmentCount, Location p0, Location p1, Location p2)
    {
        List<Location> points = new ArrayList<Location>();
        for(int i = 1; i < segmentCount; i++)
        {
            float t = i / (float) segmentCount;
            points.add(bezierPoint(t, p0, p1, p2));
        }
        return points;
    }
}
