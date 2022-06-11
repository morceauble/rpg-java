package gui;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.Player;
import main.GameState;

public class selection {

	
	static GameState gamestate;
	public selection(GameState gs) {
		selection.gamestate = gs;
	}
	
	
	public String getItemselected() {

			Player p = gamestate.getplayer();
			int nb_molette = gamestate.getSouris().nb_molette;
			//Pour éviter qu'il y est une exception "outofbounds"//
			if(nb_molette == p.getItemnbSize() && nb_molette>0) { 
				gamestate.getSouris().nb_molette--;
				nb_molette--;
			}
			return p.getItemString(nb_molette);
	}
}
