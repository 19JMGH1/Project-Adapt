package core;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import core.Main_Game.State;

public class Window extends Canvas{

	private static final long serialVersionUID = 9034494958129720942L;
	
	private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	
	public JFrame frame;
	public JTextField textField;
	
	public Dimension dims;
	
	public Window(int width, int height, String title, Main_Game game) {
		dims = new Dimension(width, height);
		frame = new JFrame(title);
		frame.setPreferredSize(dims);
		frame.setSize(dims);
		//frame.setMaximumSize(new Dimension(width, height));
		//frame.setMinimumSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		
		//Makes the current file save whenever the window is closed
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (game.AdaptState == State.InWorld) {
					game.SaveFile();
				}
				game.close();
				}
			});
		resize(game);
		ResizeDetector(game);
		game.start();
	}
	
	public void setFullScreen(boolean fullScreen) {
		if (fullScreen) {
			//frame.setUndecorated(true);
			device = frame.getGraphicsConfiguration().getDevice();
			device.setFullScreenWindow(frame);
		}
		else {
			device.setFullScreenWindow(null);
			//frame.setUndecorated(true);
		}
	}
	
	public void ResizeDetector(Main_Game game) {
		frame.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
		        //System.out.println(true);
		    	resize(game);
		    	//System.out.println(frame.getSize()+"\n"+dims);
		    }
		});
	}
	public void resize(Main_Game game) {
		Insets insects = frame.getInsets();
		//System.out.println(insects.top+", "+insects.bottom);
		Main_Game.WIDTH = frame.getWidth();
    	Main_Game.HEIGHT = frame.getHeight();
    	if (Main_Game.WIDTH < Main_Game.HEIGHT) {
    		Main_Game.WIDTH = Main_Game.HEIGHT;
    	}
    	if (Main_Game.HEIGHT < Main_Game.WIDTH/3) {
    		Main_Game.HEIGHT = (Main_Game.WIDTH)/2;
    	}
    	dims.width = Main_Game.WIDTH;
    	dims.height = Main_Game.HEIGHT;
    	frame.setSize(dims);
    	Main_Game.WIDTH -= insects.left+insects.right;
    	Main_Game.HEIGHT -= insects.bottom+insects.top;
    	//System.out.println(Main_Game.WIDTH+", "+Main_Game.HEIGHT);
    	System.out.println(frame.getWidth()+", "+frame.getHeight());
    	
    	//Variable that tells all objects to resize themselves
    	game.resized = true;
    	//System.out.println("resized: " +dims);
	}
}