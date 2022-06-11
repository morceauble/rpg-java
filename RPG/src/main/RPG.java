package main;
import javax.swing.JFrame;


public class RPG{

public static void main(String[] args) {
	JFrame window = new JFrame();
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.setResizable(true);
	window.setTitle("Game");
	
	
	GameState gamestate = new GameState();
	
	window.add(gamestate);
	
	
	window.setLocationRelativeTo(null);
	window.setVisible(true);
	
	
	gamestate.startGame();
	
	


	}
}