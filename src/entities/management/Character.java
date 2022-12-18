package entities.management;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.Main_Game;
import core.playerInput.KeyInput;
import entities.EntityTypes;

public class Character extends EntityObject{

	private Main_Game game;
	private Handler handler;

	private BufferedImage CharacterSprite;
	private BufferedImage CharacterBoatSprite;

	//this integer controls which sprite on the sheet is being used.
	//each number reads the sprite left to right and at the end of the row, goes to the next column and continues
	private int CurrentSprite = 0;
	//These are the widths and heights of each sprite on the sprite sheet
	private final int SpriteSheetWidth = 170;
	private final int SpriteSheetHeight = 210;
	//Location of where to get the sprite from
	private int SpriteSheetLocX = 0;
	private int SpriteSheetLocY = 0;
	private int Timer = 1;

	public Character(int x, int y, EntityTypes id, Main_Game game, Handler handler, KeyInput keyinput) {
		super(x, y, id);
		this.game = game;
		this.handler = handler;

		game.CharacterWidth = Main_Game.WIDTH/20;
		game.CharacterHeight = (game.CharacterWidth*(21/17));

		try {
			CharacterSprite = ImageIO.read(getClass().getResourceAsStream("/Character_Sprite_Sheet.png"));
			CharacterBoatSprite = ImageIO.read(getClass().getResourceAsStream("/Character_Boat.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	//The sprite can go from 0 to 27 since we have 28 sprites
	public void ChangeSprite(int Sprite) {
		//System.out.println(Sprite);
		if (!game.inBoat) {
			if (Sprite != CurrentSprite) {
				CurrentSprite = Sprite;
				int SpriteRow = 0;
				int SpriteColumn;

				while (Sprite > 5) {
					SpriteRow++;
					Sprite-=6;
				}
				SpriteColumn = Sprite;

				SpriteSheetLocX = SpriteSheetWidth*SpriteColumn;
				SpriteSheetLocY = SpriteSheetHeight*SpriteRow;
			}
		}
		else {
				CurrentSprite = Sprite;
				int SpriteRow = 0;
				int SpriteColumn = 0;
				if (Sprite == 0) {
					if (Timer >= 1 && Timer <= 15) {
						SpriteColumn = 3;
					}
					else if (Timer >= 16 && Timer <= 30) {
						SpriteColumn = 0;
						SpriteRow = 1;
					}
					else if (Timer >= 31 && Timer <= 45){
						SpriteColumn = 1;
						SpriteRow = 1;
					}
					else if (Timer >= 46 && Timer <= 60) {
						SpriteColumn = 0;
						SpriteRow = 1;
					}
				}
				else {
					if (Sprite == 1) { //Moving up boat sprites
						Sprite = 0;
					}
					else if (Sprite == 2 || Sprite == 4) {
						Sprite = 1;
					}
					else if (Sprite == 3) {
						Sprite = 2;
					}
					else if (Sprite == 5) { //Moving down boat sprites
						Sprite = 3;
					}
					else if (Sprite == 6 || Sprite == 8) {
						Sprite = 4;
					}
					else if (Sprite == 7) {
						Sprite = 5;
					}
					else if (Sprite == 9 || Sprite == 10 || Sprite == 11) { //Moving left boat sprites
						Sprite = 6;
					}
					else if (Sprite == 12 || Sprite == 13 || Sprite == 17 || Sprite == 18) {
						Sprite = 7;
					}
					else if (Sprite == 14 || Sprite == 15 || Sprite == 16) {
						Sprite = 8;
					}
					else if (Sprite == 19 || Sprite == 20 || Sprite == 21) { //Moving right boat sprites
						Sprite = 9;
					}
					else if (Sprite == 22 || Sprite == 23 || Sprite == 27 || Sprite == 28) {
						Sprite = 10;
					}
					else if (Sprite == 24 || Sprite == 25 || Sprite == 26) {
						Sprite = 11;
					}
					
					while (Sprite > 3) {
						SpriteRow++;
						Sprite-=4;
					}
					SpriteColumn = Sprite;
				}
				SpriteSheetLocX = SpriteSheetWidth*SpriteColumn;
				SpriteSheetLocY = SpriteSheetHeight*SpriteRow;
		}
	}

	public void ControlSprite() {
		if (Timer >= 60) {
			Timer = 1;
		}
		if (game.VelX < 0) {
			Timer+= 2;
			if (CurrentSprite < 9 || CurrentSprite > 18) {
				ChangeSprite(9);
			}
			else {
				if (Timer >= 1 && Timer <= 6) {
					ChangeSprite(9);
				}
				else if (Timer >= 7 && Timer <= 12) {
					ChangeSprite(10);
				}
				else if (Timer >= 13 && Timer <= 18) {
					ChangeSprite(11);
				}
				else if (Timer >= 19 && Timer <= 24) {
					ChangeSprite(12);
				}
				else if (Timer >= 25 && Timer <= 30) {
					ChangeSprite(13);
				}
				else if (Timer >= 31 && Timer <= 36) {
					ChangeSprite(14);
				}
				else if (Timer >= 37 && Timer <= 42) {
					ChangeSprite(15);
				}
				else if (Timer >= 43 && Timer <= 48) {
					ChangeSprite(16);
				}
				else if (Timer >= 49 && Timer <= 54) {
					ChangeSprite(17);
				}
				else if (Timer >= 55 && Timer <= 60) {
					ChangeSprite(18);
				}
			}
		}
		else if (game.VelX > 0) {
			Timer+= 2;
			if (CurrentSprite < 19) {
				ChangeSprite(19);
			}
			else {
				if (Timer >= 1 && Timer <= 6) {
					ChangeSprite(19);
				}
				else if (Timer >= 7 && Timer <= 12) {
					ChangeSprite(20);
				}
				else if (Timer >= 13 && Timer <= 18) {
					ChangeSprite(21);
				}
				else if (Timer >= 19 && Timer <= 24) {
					ChangeSprite(22);
				}
				else if (Timer >= 25 && Timer <= 30) {
					ChangeSprite(23);
				}
				else if (Timer >= 31 && Timer <= 36) {
					ChangeSprite(24);
				}
				else if (Timer >= 37 && Timer <= 42) {
					ChangeSprite(25);
				}
				else if (Timer >= 43 && Timer <= 48) {
					ChangeSprite(26);
				}
				else if (Timer >= 49 && Timer <= 54) {
					ChangeSprite(27);
				}
				else if (Timer >= 55 && Timer <= 60) {
					ChangeSprite(28);
				}
			}
		}
		else if (game.VelY > 0) {
			Timer+= 3;
			if (CurrentSprite == 0 || CurrentSprite > 4) {
				ChangeSprite(1);
			}
			else {
				if (Timer >= 1 && Timer <= 15) {
					ChangeSprite(1);
				}
				else if (Timer >= 16 && Timer <= 30) {
					ChangeSprite(2);
				}
				else if (Timer >= 31 && Timer <= 45) {
					ChangeSprite(3);
				}
				else if (Timer >= 46 && Timer <= 60) {
					ChangeSprite(4);
				}
			}
		}
		else if (game.VelY < 0) {
			Timer+= 3;
			if (CurrentSprite < 5 || CurrentSprite > 8) {
				ChangeSprite(5);
			}
			else {
				if (Timer >= 1 && Timer <= 15) {
					ChangeSprite(5);
				}
				else if (Timer >= 16 && Timer <= 30) {
					ChangeSprite(6);
				}
				else if (Timer >= 31 && Timer <= 45) {
					ChangeSprite(7);
				}
				else if (Timer >= 46 && Timer <= 60) {
					ChangeSprite(8);
				}
			}
		}
		else {
			Timer++;
			ChangeSprite(0);
		}
	}

	public void Remove() {
		handler.removeObject(this);
	}

	public void tick() {
		if (game.resized == true) {
			game.CharacterWidth = Main_Game.WIDTH/20;
			game.CharacterHeight = (game.CharacterWidth*(21/17));
			game.x = 0;
			game.y = 0;
		}
		ControlSprite();
		this.x = (Main_Game.WIDTH/2)-game.CharacterWidth/2;
		this.y = (Main_Game.HEIGHT/2)-game.CharacterHeight/2;
		game.characterX = this.x;
		game.characterY = this.y;
	}

	public void render(Graphics g) {
		//((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.7)); //Here is how you make an image transparent
		if (!game.inBoat) {
			g.drawImage(CharacterSprite, x, y, x+game.CharacterWidth, y+game.CharacterHeight, SpriteSheetLocX, SpriteSheetLocY, SpriteSheetLocX+SpriteSheetWidth, SpriteSheetLocY+SpriteSheetHeight, null);
		}
		else {
			g.drawImage(CharacterBoatSprite, x, y, x+game.CharacterWidth, y+game.CharacterHeight, SpriteSheetLocX, SpriteSheetLocY, SpriteSheetLocX+SpriteSheetWidth, SpriteSheetLocY+SpriteSheetHeight, null);
		}
		//g.fillRect(x+game.CharacterWidth/2, y+game.CharacterHeight/2, 2, 2);
		//((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1));
		//g.fillRect(game.characterX+game.CharacterWidth/5, game.characterY+game.CharacterHeight/5, game.TileWidth/3, game.TileHeight/3); //Testing where the space is for items to fly towards the player after collection
	}


}
