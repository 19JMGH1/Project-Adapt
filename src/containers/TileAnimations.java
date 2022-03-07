package containers;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TileAnimations {
	
	public BufferedImage animationImage;
	
	public TileAnimations() {
		try {
			animationImage = ImageIO.read(getClass().getResourceAsStream("/Tile Additions.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
