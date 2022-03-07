package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;

//TODO Use this class to make lights on the screen after the daylight cycle is introduced

public class Light {
   private static final Color transparency = new Color(0, 0, 0, 0);
   private static final int WIDTH = 500;
   private static final int HEIGHT = 500;

   public static void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Point[] lights = { new Point(WIDTH, HEIGHT)};
        for (Point center : lights) {
        	center.x += 180;
        	center.y -= 150;
            paintlight(g2d, center);
        }
       g2d.dispose();
   }

   private static void paintlight(Graphics2D g2d, Point center) {
	   g2d.setColor(new Color(0, 0, 0, 100));
	   g2d.fillRect(0, 0, Main_Game.WIDTH, Main_Game.HEIGHT); //This makes a dark layer that shows nightime. It's color is defined in the line above
       float[] dist = { 0.2f, 1.0f };
       Color[] color = { new Color(230, 230, 180, 150), transparency };
       int size = 500;
       RadialGradientPaint p = new RadialGradientPaint(center, size, dist, color);
       g2d.setPaint(p);
       g2d.fillRect(center.x - size, center.y - size, size*2, size*2);
    }
}