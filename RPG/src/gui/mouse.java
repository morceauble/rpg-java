package gui;
import java.awt.MouseInfo;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;

import main.GameState;

public class mouse  extends MouseAdapter implements MouseListener{

	double x,y;
	public Boolean leftPressed;
	public Boolean rightPressed;
	
	public int nb_molette;
	
	GameState gamestate;
	
	
	
	public mouse(GameState gs) {
		gamestate = gs;
		 x = 0;
		 y = 0;
		 leftPressed = false;
		 rightPressed = false;
		 nb_molette = 0;
	}
	
	public double getXMouse() {
		return x;
	}
	
	public double getYMouse() {
		return y;
	}
	
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			System.out.println("Left click !!" + e.getPoint());
			leftPressed = true;
		}
		else  if (e.getButton() == MouseEvent.BUTTON3) {
			System.out.println("Right click !!" + e.getPoint());
			rightPressed = true;
		}
		
		
		else {
		leftPressed = false;
		rightPressed = false;
		}
	}
	
	
	public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
       
        if (notches < 0) {
        	//vers le haut(donc vers la droite)
        	
        	
        	
        	nb_molette++;
        } 
        else {
        	//vers le bas(donc vers la gauche)
        	nb_molette--;
        }
        
        
        if(nb_molette>gamestate.getplayer().getItemTabSize() - 1)
        	nb_molette = 0;
        else if (nb_molette<0)
        	nb_molette = gamestate.getplayer().getItemTabSize() - 1;
    }
	//}
	
	public void setLeftMousetofalse() {
		leftPressed = false;
	}
	
	public void setRightMousetofalse() {
		rightPressed = false;
	}
	
	public void update() {
	x = MouseInfo.getPointerInfo().getLocation().getX();
	y = MouseInfo.getPointerInfo().getLocation().getY();
	}
	
}
