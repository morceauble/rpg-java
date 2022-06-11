package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class menuprincipal {

	
	Font font = new Font(Font.SANS_SERIF,Font.BOLD,30);
	
	 GameState gamestate;
	 
	 
	 float opacity;
	 
	 Boolean clickedjouer;
	
	int background_x,background_y;
	
	int background_xsize,background_ysize;
	
	int dup_x,dup_y;
	
	public Boolean game_action;
	
	public menuprincipal(GameState gs) {
		gamestate = gs;
		
		background_x = 0;
		background_y = 0;
		
		background_xsize = 1300;
		background_ysize = 700;
		
		dup_x = background_x + 100;
		dup_y = background_y + 130;
		
		clickedjouer = false;
		
		opacity = 1;
		game_action = false;
	}
	
	public void update() {
		
		
		if(opacity <= 0.9) {
			gamestate.setingame(true);
		
	}
		if(opacity<=0.08 && gamestate.getplayer().initimage) {
			gamestate.setinmenu_principal(false);
			game_action = true;
			opacity = 1;
			clickedjouer = false;
		}
		
		
		
		}
	
	public void render(Graphics2D g) {
		if(!clickedjouer) {
		Background(g);
		buttons(g);
		}
		else	animationfrommenutogame(g);
		
			
		
	}
	
	private void Background(Graphics2D g) {
		g.setColor(Color.darkGray);
		g.fillRoundRect(background_x,background_y,background_xsize,background_ysize,5,5);
		
		g.setColor(Color.lightGray);
		g.fillRoundRect(background_x + 80,background_y + 80,background_xsize - 160,background_ysize - 160,20,20);
		
	}
	
	
	private void animationfrommenutogame(Graphics2D g) {
		
		g.setColor(Color.BLACK);
		
		
		
		if(gamestate.getTimer().menuprincipal_animopacity>20) {
			opacity-=0.01;
			
			
			if(opacity<=0) {
				opacity = 0;
			}
			gamestate.getTimer().menuprincipal_animopacity = 0;
		}
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)opacity));
		
		g.fillRect(background_x, background_y, background_xsize + 100, background_ysize + 100);
		
		
	}
	
	private void buttons(Graphics2D g) {
		g.setFont(font);
		
		
		
		if(sourissurbuttons()) {
			g.setColor(Color.YELLOW);
			
			if(gamestate.getSouris().leftPressed) {
				gamestate.getSouris().setLeftMousetofalse();
				
				gamestate.chargermap("init");
				clickedjouer = true;
				
				System.out.println();
				System.out.println("DEMARRER UNE PARTIEEEEEEEE" + '\n');
				
				gamestate.getMap().fin = false;
				
			}
		}
		else g.setColor(Color.RED);
		
		
		g.drawString("Démarrer une partie",dup_x , dup_y);
		
		
		
	}
	
	private Boolean sourissurbuttons() {
		
		double souris_x = gamestate.getSouris().getXMouse();
		double souris_y = gamestate.getSouris().getYMouse();
		
		//System.out.println("souris_x : " + souris_x + "souris_y : " + souris_y);
		if(souris_x>dup_x &&
			souris_x < dup_x + 287 &&
			souris_y > dup_y &&
			souris_y<dup_y + 30) {
			return true;
			
		}
		return false;
		
	}
	
}
