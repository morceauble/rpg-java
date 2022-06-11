package main;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GameState;

public class portal {

	
	GameState gamestate;
	
	BufferedImage portal_up;
	BufferedImage portal_down;
	int x,y;
	
	public portal(GameState gs){
		gamestate = gs;
		
		initTextures();
		
		x = -100;
		y = -600;
	}
	
	private void initTextures(){
		String basePath = new File("").getAbsolutePath();
		try {
		portal_up = ImageIO.read(new File(basePath+"/res/img/portal/portal_up.png"));
		portal_down = ImageIO.read(new File(basePath+"/res/img/portal/portal_down.png"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	public void setCoor(int i,int j) {
		x = i;
		y = j;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public void render(Graphics2D g) {
			g.drawImage(portal_down, x, y + gamestate.TileSize, gamestate.TileSize, gamestate.TileSize,null);
			g.drawImage(portal_up, x, y, gamestate.TileSize, gamestate.TileSize,null);
	}
	
}
