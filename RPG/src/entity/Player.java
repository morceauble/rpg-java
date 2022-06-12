package entity;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.TimerTask;
import javax.swing.*;

import javax.imageio.ImageIO;
import javax.swing.text.html.ImageView;

import main.GameState;
import main.KeyHandler;

public class Player extends Entity{

	static GameState gamestate;
	KeyHandler keyhandler;
	Boolean up = false,down = false,left = false,right = false;
	int x,y;
	float speed;
	static int vie;
	static int vie_max;
	static int atq;
	
	int atq_stone_sword;
	int atq_stone_axe;
	int atq_iron_sword;
	int atq_golden_sword;
	
	float durability_stoneaxe;
	static float durability_stoneaxe_total;
	
	float durability_stonesword;
	static float durability_stonesword_total;
	
	float durability_goldensword;
	static float durability_goldensword_total;
	
	float durability_ironsword;
	static float durability_ironsword_total;
	
	int enemy_index;
	public int pnj_index;
	
	static int attack_time;
	
	static int invinsibilitytime;
	 
	 
	 protected Timer Timer_range;
	 
	 Boolean animation_invinsible;
	 
	 public Boolean initimage;
	 
	 int time_sec;
	 
	 
	 BufferedImage player_up;
	 BufferedImage player_down;
	 BufferedImage player_left;
	 BufferedImage player_right;
	
	 ArrayList<String> tabItem = new ArrayList<String>();
	 ArrayList<Integer> tabnbItem = new ArrayList<Integer>();
	 
	public Player() {
		
	}
	
	public Player(GameState gs,KeyHandler keyH) {
		Player.gamestate = gs;
		this.keyhandler = keyH;
		initVariables();
		initImage();
	}
	private void initVariables() {
		x = 100;
		y = 100;
		speed = 1;
		vie = 10000;
		atq = 2;
		vie_max = 10000;
		
		durability_stoneaxe = 200;
		durability_stoneaxe_total = 200;
		
		durability_stonesword = 150;
		durability_stonesword_total = 150;
		
		durability_goldensword = 40;
		durability_goldensword_total = 40;
		
		durability_ironsword = 250;
		durability_ironsword_total = 250;
		
		
		enemy_index = 0;
		pnj_index = 0;
		
		attack_time = 500;//en ms//
		invinsibilitytime = 1000;
		
		
		atq_stone_sword=1000;
		atq_stone_axe=3;
		atq_iron_sword = 4;
		atq_golden_sword = 6;
		
		animation_invinsible = false;
		initimage = false;
	}
	
	private void initImage(){
		try{
			String basePath = new File("").getAbsolutePath();
			
			
			player_up = ImageIO.read(new File(basePath+"/res/img/player/perso_up.png"));
			player_left = ImageIO.read(new File(basePath+"/res/img/player/perso_left.png"));
			player_right = ImageIO.read(new File(basePath+"/res/img/player/perso_right.png"));
			player_down = ImageIO.read(new File(basePath+"/res/img/player/perso_down.png"));
			
			image_player = player_up;
		}
		catch(IOException e) {
			e.printStackTrace();		
			}
		
		initimage = true;
	}
	
	
	public void update() {
		
		move();
		if(!gamestate.getMap().fin) {
		isAttacking();
		 enemyinattackrange();
		 manger();	
		 HPMax();
		}
		 
	}
	
	
	private void HPMax() {
		if(vie>vie_max)
			vie = vie_max;
	}
	
	public int getVieMax() {
		return vie_max;
	}
	
	public void render(Graphics2D g2) {
		if(!isInvinsible())
		 render_normal(g2);
		else
			render_invinsible(g2);
		
		
		rectangle(g2);
		
	}
	
	
	private void render_normal(Graphics2D g2) {
		

		BufferedImage image = null;
		image = image_player;
		g2.drawImage(image,x, y, gamestate.TileSize, gamestate.TileSize,null);
		
		
		
	}

	private void render_invinsible(Graphics2D g2) {
		BufferedImage image = null;
		if(gamestate.getTimer().time_sec_Player_invinsible_anim>10) {
			image = image_player;
			gamestate.getTimer().time_sec_Player_invinsible_anim = 0;
		}
		
		g2.drawImage(image,x, y, gamestate.TileSize, gamestate.TileSize,null);
		
		
	}
	
	
	public Boolean getinitimage() {
		return initimage;
	}
	
	private void move() {
		if(keyhandler.upPressed ||keyhandler.downPressed ||keyhandler.leftPressed ||keyhandler.rightPressed ) {
		if(keyhandler.upPressed == true) {
			y-=speed;
			 up = true;
				down = false;
				left = false;
				right = false;
				
				image_player = player_up;
		}
		else if(keyhandler.downPressed == true) {
			y+=speed;
			down = true;
			
			up = false;
			
			left = false;
			right = false;
			
			image_player = player_down;
		}
		else if(keyhandler.leftPressed == true) {
			x-=speed;
			left = true;
			
			up = false;
			down = false;

			right = false;
			image_player = player_left;
		}
		else if(keyhandler.rightPressed == true) {
			x+=speed;
			right = true;
			up = false;
			down = false;
			left = false;
			image_player = player_right;
		}
	}
		
	
	
	
	
	
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public void setX(int value) {
		x = value;
	}
	
	public void setY(int value) {
		y = value;
	}
	
	public float getSpeed() {
		return speed;
	}
	public Boolean getUp() {
		return up;
	}
	public Boolean getDown() {
		return down;
	}
	public Boolean getLeft() {
		return left;
	}
	public Boolean getRight() {
		return right;
	}
	
	
	public void setVie(int v) {
		vie = v;
	}
	public int getVie() {
		return vie;
	}
	public void setAtq(int a) {
		atq = a;
	}
	
	public float getdurability(String s) {
		if(s == "stone_axe")
			return durability_stoneaxe;
		if(s=="stone_sword")
			return durability_stonesword;
		if(s=="iron_sword")
			return durability_ironsword;
		if(s=="golden_sword")
			return durability_goldensword;
		
		return 0;
	}
	
	public float getdurabilitymax(String s) {
		if(s == "stone_axe")
			return durability_stoneaxe_total;
		if(s == "stone_sword")
			return durability_stonesword_total;
		if(s=="iron_sword")
			return durability_ironsword_total;
		if(s=="golden_sword")
			return durability_goldensword_total;
		return 0;
	}
	
	
	public int getAtq(String s) {
		
		if(s == "stone_sword")
			return atq_stone_sword;
		else if (s == "stone_axe")
			return atq_stone_axe;
		else if (s == "iron_sword")
			return atq_iron_sword;
		else if (s == "golden_sword")
			return atq_golden_sword;
		return 0;
		
	}
	
	public int getAtqAuto() {
		
		int attaque = 1;
		if(hasStoneSword())
			attaque = atq_stone_sword;
		if(hasStoneAxe())
			attaque = atq_stone_axe;
		if(hasIronSword())
			attaque= atq_iron_sword;
		if(hasGoldenSword()) 
			attaque= atq_golden_sword;
		
		return attaque;
	}
	
	public Boolean isAlive() {
		if(vie<=0)
			return false;
		return true;
	}
	
public void addItem(String s,int value) {
		
		if(tabItem.contains(s)==false) {
		tabItem.add(s);
		tabnbItem.add(value);
	}
		else 
			setItemnbAdd(s,1);
		
		
		System.out.println("tabItem : " + tabItem + '\n' + "nb : " + tabnbItem);
	}
	
	
public Boolean enoughitem(String s,int value) {
	if(value!=-1) {
		if((getItemnbauto(s) - value )>= 0) {
		return true;
		}}
	return false;
}
	
	public void ifitem0() {
		//Permet de retirer l'item s'il est <=0 du tableau(et donc de l'inventaire)//
		for(int i = 0; i < tabItem.size();i++) {
			if(tabnbItem.get(i) <=0) {
				
				System.out.println("tabitemnb : " + tabItem.get(i) + " " + tabnbItem.get(i));
				
				tabItem.remove(i);
				tabnbItem.remove(i);
				i--;
					
				}
			}
		}
	
	
	public String getItemString(int i) {
		
		
		
		if(tabItem.size()!=0) {
			if(tabnbItem.get(i)>=1)
			return tabItem.get(i);
		}
		return "null";
	}
	
	
	
	public int getItemnb(int i) {
		return tabnbItem.get(i);
	}
	//test
	public int getItemnbSize() {
		return tabnbItem.size();
	}
	
	public int getItemnbauto(String s) {
		for(int i = 0;i<tabItem.size();i++) {
			
			if(tabItem.get(i).compareTo(s) == 0) {
				return tabnbItem.get(i);
			}
		}
		return 0;
	}
	
public int findpositionelement(String s) {
	if(isininventory(s)) {
		int nb;
		for(int i = 0;i < tabItem.size();i++) {
			if(tabItem.get(i).compareTo(s) == 0) {
				nb = i;
				return nb;
			}
		}
	}
	return -1;
}
	
	public void removeWeaponDurability() {
		
		String s = "null";
		
			if(durability_stonesword<=0) {
				s = "stone_sword";
				durability_stonesword = durability_stonesword_total;
			}
			else if (durability_stoneaxe<=0) {
				s = "stone_axe";
				durability_stoneaxe = durability_stoneaxe_total;
			}
			else if (durability_ironsword<=0) {
				s = "iron_sword";
				durability_ironsword = durability_ironsword_total;
				
			}
			
			else if (durability_goldensword<=0) {
				s = "golden_sword";
				durability_goldensword = durability_goldensword_total;
			}
			
			
			if(!(s.compareTo("null") == 0)) {
				
				tabnbItem.set(findpositionelement(s), tabnbItem.get(findpositionelement(s))-1);
				ifitem0();
			}
				
	}

	public Boolean isininventory(String s) {
		for(int i = 0;i < tabItem.size();i++) {
			if(tabItem.get(i).compareTo(s) == 0) 
				return true;
			}
		return false;
	}
	
	public void setItemnbAdd(String s,int value) {
		int i = findpositionelement(s);
		int total_value = getItemnbauto(s)+value;
		tabnbItem.set(i, total_value);
	}
	
	
	
	public Boolean supazero(String s) {
		int i = findpositionelement(s);
		if(i != -1) {
		if(tabnbItem.get(i)>=0)
			return true;
		}
		return false;
	}
	
	public int getItemTabSize() {
		return tabItem.size();
	}
	
	public void resetinventory() {
		tabItem.clear();
		tabnbItem.clear();
		clearDurability();		
	}
	
	public void manger() {
		if(gamestate.getSouris().rightPressed && hasFood()) {
			
			gamestate.getSouris().setRightMousetofalse();
			
			setVie(getVie()+nbPVfood(gamestate.getSelection().getItemselected()));
			setItemnbAdd(gamestate.getSelection().getItemselected(),-1);
			ifitem0();
		}
	}
	
	
	
	public int nbPVfood(String s) {
		if(s == "golden_apple") {
			return 20;
		}
		if(s=="apple")
			return 5;
		
		return 0;
	}
	
	
	public static Boolean hasFood() {
		if(gamestate.getSelection().getItemselected().contains("apple"))
			return true;
		return false;
	}
	
	public static Boolean canAttack() {
		int attack_time_value = 0;
		
		//diviser par 3 pour avoir la bonne valeur en ms//
		
		if(hasStoneSword()) 
			attack_time_value = 1000/3;
		else if (hasStoneAxe())
			attack_time_value = 1100/3;
			
		else if(hasIronSword()) 
			attack_time_value = 800/3;

		else if(hasGoldenSword()) 
			attack_time_value = 200/3;
		
		if(!(gamestate.getTimer().time_sec_Player_attack > attack_time_value)) 
			return false;
		
		
		return true;
	}
	
	
	private Boolean hasArme() {
		if(gamestate.getSelection().getItemselected().contains("sword") ||gamestate.getSelection().getItemselected().contains("axe"))
			return true;
		return false;
	}
	
	private static Boolean hasStoneSword() {
		if(gamestate.getSelection().getItemselected().contains("stone_sword"))
			return true;
		return false;
	}
	
	private static Boolean hasIronSword() {
		if(gamestate.getSelection().getItemselected().contains("iron_sword"))
			return true;
		return false;
	}
	
	private static Boolean hasGoldenSword() {
		if(gamestate.getSelection().getItemselected().contains("golden_sword"))
			return true;
		return false;
	}
	
	private static Boolean hasStoneAxe() {
		if(gamestate.getSelection().getItemselected().contains("stone_axe"))
			return true;
		return false;
	}
	
	
	
	public Boolean isAttacking() {
		
		if(gamestate.getSouris().leftPressed && canAttack() && hasArme()) {
			
			gamestate.getSouris().leftPressed = false;
			
			if(enemyinattackrange()) {
				
				
				int vieenemy = Enemy.tabenemy_vie.get(enemy_index);
				
				Enemy.tabenemy_vie.set(enemy_index,vieenemy - getAtqAuto());
				
				
				if(gamestate.getSelection().getItemselected() == "stone_sword") 
						durability_stonesword-=10;
				
				else if (gamestate.getSelection().getItemselected() == "stone_axe") 
						durability_stoneaxe-=10;
				else if (gamestate.getSelection().getItemselected() == "iron_sword")
						durability_ironsword-=10;
				else if(gamestate.getSelection().getItemselected() == "golden_sword")
						durability_goldensword-=10;
				
				removeWeaponDurability();
				
				//gamestate.getGui().setDurability( false);
				gamestate.getGui().setDurability( true);
				gamestate.getTimer().resettime_sec_Player_attack();
				
			return true;
				}
			else 
				gamestate.getGui().setDurability( false);
		}
		
	
	
		return false;
	}
	
	private void clearDurability() {
		durability_stoneaxe = durability_stoneaxe_total;
		durability_stonesword = durability_stonesword_total;
		durability_ironsword = durability_ironsword_total;
		durability_goldensword = durability_goldensword_total;
	}
	
	public float ratio(String s) {
		if(s == "stone_axe") 
			return durability_stoneaxe/durability_stoneaxe_total;
		if(s=="stone_sword")
			return durability_stonesword/durability_stonesword_total;
		if(s=="iron_sword")
			return durability_ironsword/durability_ironsword_total;
		if(s=="golden_sword")
			return durability_goldensword/durability_goldensword_total;
		return 1;
	}
	
	private Boolean enemyinattackrange() {
			
		for(int i = 0;i<gamestate.getenemy().getTabsize();i++) {
		if(gamestate.getenemy().getX(i) > whereisAttackRange_X() -gamestate.TileSize && gamestate.getenemy().getX(i) <whereisAttackRange_X() +gamestate.TileSize &&
				gamestate.getenemy().getY(i) > whereisAttackRange_Y()-gamestate.TileSize && gamestate.getenemy().getY(i) < whereisAttackRange_Y() +gamestate.TileSize ){
			
			enemy_index = i;
			
			return true;
		}
		}
			return false;
		
	}
	
	public Boolean pnjinattackrange() {
		for(int i = 0;i<gamestate.getPnj().getTabNbPNJ();i++) {
			if(gamestate.getPnj().getX(i) > whereisAttackRange_X() -gamestate.TileSize && gamestate.getPnj().getX(i) <whereisAttackRange_X() +gamestate.TileSize &&
					gamestate.getPnj().getY(i) > whereisAttackRange_Y()-gamestate.TileSize && gamestate.getPnj().getY(i) < whereisAttackRange_Y() +gamestate.TileSize ){
				
				pnj_index = i;
				
				return true;
			}
			}
				return false;
		}
		
		
	
	
	private void rectangle(Graphics2D g) {
		if(!enemyinattackrange() && !pnjinattackrange())
		g.setColor(Color.BLACK);
		
		else if(enemyinattackrange()){
			
			if(canAttack())
				g.setColor(Color.GREEN);
			else
			g.setColor(Color.RED);
		}
		else if (pnjinattackrange()) {
			g.setColor(Color.BLUE);
		}
		
		g.fillRect(whereisAttackRange_X(), whereisAttackRange_Y(), 32, 32);
		
	}
	
	
	
	
	private int whereisAttackRange_X() {
		
		
			
		//EN HAUT ET EN BAS
		if(up || down) 
		{
			
			return x;
		}
		//A DROITE
		else if(right) {
			
			return x+gamestate.TileSize;
		}
			
		
		//A GAUCHE
		else if (left)
			return x-gamestate.TileSize;
		
		return 0;
		}
	
	private int whereisAttackRange_Y() {
		//EN HAUT
		if(up) 
			return y-gamestate.TileSize;
		//EN BAS
		if(down)
			return y + gamestate.TileSize;
		if(left||right)
			return y;
		return 0;
		}
	
	
	
	public Boolean isInvinsible() {
	
		if(gamestate.getTimer().time_sec_Player_invinsible > invinsibilitytime) {
			//System.out.println("qshdkhsqkjdsqd");
			return false;
		}
		return true;
	}
	
	
	
  }





