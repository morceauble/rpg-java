package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import javax.swing.*;

import main.GameState;

public class Timeur {

	
	GameState gamestate;
	
	public int time_sec_Enemy_move;
	public int time_sec_Enemy_CanMOVE;
	
	public int time_sec_Enemy_zombie_canmove;
	
	public int time_sec_Player_attack;
	public int time_sec_Player_invinsible_anim;
	
	public int time_sec_Player_invinsible;
	
	
	public int time_sec_GUI_Successtimerate;
	public int time_sec_GUI_Successopacity;
	public int time_sec_Enemy_missiletimerate;
	public int time_sec_Enemy_missilespeedrate;
	public int time_sec_Map_interactporte;
	
	public int menuprincipal_animopacity;
	
	 protected Timer Timer_range;
	 
	 protected Timer timer_mp;
	
	public Timeur(GameState gs) {
		gamestate = gs;
	}
	
	public void updateTimer() {
		int delay = 1000; //milliseconds
		  ActionListener taskPerformer = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		    	  
		    	  if(!gamestate.inmenu_principal) {
		    	  time_sec_Enemy_move++;
		    	  time_sec_Enemy_CanMOVE++;
		    	  time_sec_Enemy_missiletimerate++;
		    	  time_sec_Enemy_missilespeedrate++;
		    	  
		    	  time_sec_Player_attack++;
		    	  time_sec_Player_invinsible++;
		    	  time_sec_Player_invinsible_anim++;
		    	  
		    	  time_sec_GUI_Successtimerate++;
		    	  time_sec_GUI_Successopacity++;
		    	  time_sec_Enemy_zombie_canmove++;
		    	  time_sec_Map_interactporte++;
		    	 
		    	  }
		    	  
		    	  else 
		    		  menuprincipal_animopacity++;
		    	  
		    	  
		    	 // time_sec_GUI_Durability++;
		      }
		  };
		  Timer_range = new Timer(delay, taskPerformer);
		  Timer_range.start();
		  }
	

	public void resettime_sec_Enemy_move() {
		time_sec_Enemy_move = 0;
	}
	
	public void resettime_sec_Enemy_CanMOVE() {
		time_sec_Enemy_CanMOVE = 0;
	}
	
	public void resettime_sec_Player_attack() {
		time_sec_Player_attack = 0;
	}
	
	public void resettime_sec_Player_invinsible() {
		time_sec_Player_invinsible = 0;
	}
	
	public void resettime_sec_GUI_Successtimerate() {
		time_sec_GUI_Successtimerate = 0;
	}
	
	public void resettime_sec_GUI_Successopacity() {
		time_sec_GUI_Successopacity = 0;
	}
	
	public void resettime_sec_Enemy_missiletimerate() {
		time_sec_Enemy_missiletimerate = 0;
	}
	
	
	public void resettime_sec_Enemy_missilespeedrate() {
		time_sec_Enemy_missilespeedrate = 0;
	}
	
	
	
}
