package entity;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import main.GameState;

public class Enemy extends Entity {

	static GameState gamestate;
	static int time_move;
	static int start_moving;
	static int vie;
	static int vie_max;
	
	static int nombre_enemy;

	
	 protected Timer Timer_range;
	 
	 int i_var;
	
	 
	 BufferedImage image_missile;
	 
	 
	static ArrayList<BufferedImage> tabenemy = new ArrayList<BufferedImage>();
	static ArrayList<Integer> tabenemy_x = new ArrayList<Integer>();
	static ArrayList<Integer> tabenemy_y = new ArrayList<Integer>();
	static ArrayList<Integer> tabenemy_vie = new ArrayList<Integer>();
	static ArrayList<Integer> tabenemy_viemax = new ArrayList<Integer>();
	
	static ArrayList<BufferedImage> tabmissile_image = new ArrayList<BufferedImage>();
	static ArrayList<Integer> tabmissile_x = new ArrayList<Integer>();
	static ArrayList<Integer> tabmissile_y = new ArrayList<Integer>();
	
	static ArrayList<Integer> tabSpeed = new ArrayList<Integer>();
	
	int missile_nombre;
	
	public Enemy() {
		
	}
	
	public Enemy(GameState gs) {
		Enemy.gamestate = gs;
		initVariables();
		initImage();
	}
	
	private void initVariables() {
		vie = 10;
		vie_max = 10;
		i_var = 0;
		speed = 2;
		start_moving = 100;
		time_move = 1;
		missile_nombre = 0;
		nombre_enemy = 0;
		
		
	}
	private void initImage() {
	try{
		String basePath = new File("").getAbsolutePath();
		image_chest = ImageIO.read(new File(basePath+"/res/img/enemy/chest.png"));
		image_gragouille = ImageIO.read(new File(basePath+"/res/img/enemy/gragouille.png"));
		image_gragouille_mirror = ImageIO.read(new File(basePath+"/res/img/enemy/gragouille_mirror.png"));
		image_zombie =  ImageIO.read(new File(basePath+"/res/img/enemy/zombie.png"));
		
		image_missile = ImageIO.read(new File(basePath+"/res/img/enemy/missile.png"));
	}
	catch(IOException e) {
		e.printStackTrace();		
		}
	}
	
	public void addEnemy(String s,int x,int y) {
		if(s == "chest") {
			tabenemy.add(image_chest);
			tabenemy_vie.add(1);
			tabenemy_viemax.add(1);
		}
		else if(s == "gragouille") {
			tabenemy.add(image_gragouille);
			tabenemy_vie.add(vie);
			tabenemy_viemax.add(vie_max);
			}
		else if (s == "zombie") {
			tabenemy.add(image_zombie);
			tabenemy_vie.add(40);
			tabenemy_viemax.add(40);
		}
		tabenemy_x.add(x);
		tabenemy_y.add(y);
		nombre_enemy++;
		
		System.out.println(" Enemy add " + s + "x :" + tabenemy_x+ "y: " + tabenemy_y+ "vie : " + tabenemy_vie );
		
	}

	
	
	private void addmissile(int i) {
	
		tabmissile_x.add(tabenemy_x.get(i));
		tabmissile_y.add(tabenemy_y.get(i));
		tabmissile_image.add(image_missile);
		}
	
	
	private void spawn_missile() {
		if(gamestate.getTimer().time_sec_Enemy_missiletimerate>10) {
			
			int speed_ = speed;
			for(int i = 0;i < tabenemy.size();i++ ) {
				
				if(tabenemy.get(i) == image_gragouille) {
				if(isPlayerinRange(i)!=-1) {
					
					
					missile_nombre++;
					
			switch(isPlayerinRange(i)) {
			
			case(0):
				speed_ = 5*speed;
				addmissile(i);
				break;
			case(1):
				speed_ = -5*speed;
				addmissile(i);
				break;
			default:
				speed_ = 0;
				break;
			}
			tabSpeed.add(speed_);
			gamestate.getTimer().resettime_sec_Enemy_missiletimerate();
			}
		}
		}
	}}
	
	
	private void move_missile() {
		if(gamestate.getTimer().time_sec_Enemy_missilespeedrate>10) {
		for(int i =0;i<tabmissile_image.size();i++) {
			tabmissile_x.set(i, tabmissile_x.get(i) - tabSpeed.get(i));
			tabmissile_y.set(i, tabmissile_y.get(i));
			gamestate.getTimer().resettime_sec_Enemy_missilespeedrate();
		}
		}
		
	}
	
	private void missile_outofmap() {

		if((missile_nombre>0)) {
		for(int i =0;i<missile_nombre;i++) {
			if(tabmissile_x.get(i)<0 || tabmissile_y.get(i)<0 || tabmissile_x.get(i)>32*39 || tabmissile_y.get(i)>1080) {
				tabmissile_x.remove(i);
				tabmissile_y.remove(i);
				tabmissile_image.remove(i);
				tabSpeed.remove(i);
				missile_nombre--;
			}
		}
		}
	}
	
	
	
	public int getTabMissileSize() {
		return tabmissile_image.size();
	}
	
	public int getMissileX(int i) {
		return tabmissile_x.get(i);
	}
	public int getMissileY(int i) {
		return tabmissile_y.get(i);
	}
	
	private void move_zombie() {

		if(gamestate.getTimer().time_sec_Enemy_zombie_canmove>10) {
		
			for(int i = 0;i < tabenemy.size();i++) {
				
				if(tabenemy.get(i) == image_zombie) {
					
					
					
					int speed_x = 0;
					int speed_y = 0;
					
					int x = gamestate.getplayer().getX();
					int y = gamestate.getplayer().getY();
				
					
					
					if(tabenemy_x.get(i) <= x && tabenemy_y.get(i)<=y){
						speed_x = 1;
						speed_y = 1;
					}
					else if (tabenemy_x.get(i) <= x && tabenemy_y.get(i)>y) {
						speed_x = 1;
						speed_y = -1;
					}
					else if (tabenemy_x.get(i) > x && tabenemy_y.get(i)>y) {
						speed_x = -1;
						speed_y = -1;
					}
					else {
						speed_x = -1;
						speed_y = 1;
					}
					
					
					tabenemy_x.set(i, tabenemy_x.get(i) + speed_x);
					tabenemy_y.set(i, tabenemy_y.get(i) + speed_y);
					
					gamestate.getTimer().time_sec_Enemy_zombie_canmove = 0;
					
					}
				
			}
				
		
	}}
	
	
	private void move_gragouille() {
			if(clock_move()) {
				int speed_x = speed;
				int speed_y = 0;
				for(int i = 0; i < tabenemy.size();i++) {
					
					
					if(tabenemy.get(i) == image_gragouille) {
				switch(isPlayerinRange(i)){ 
				
				
				case(0):
					speed_x = -3*speed;
					tabenemy.set(i,image_gragouille_mirror);
					break;
				case(1):
					speed_x = 2*speed;
					tabenemy.set(i,image_gragouille);
					break;
				default:
					speed_x = 0;
					//tabenemy.set(i,image_gragouille);
					break;
				}
				tabenemy_x.set(i, tabenemy_x.get(i) - speed_x);
				tabenemy_y.set(i, tabenemy_y.get(i) - speed_y);
				
				gamestate.getTimer().resettime_sec_Enemy_move();
				}
				
			}
		}
	}
	
	private int isPlayerinRange(int i) {
		//à droite
		if(gamestate.getplayer().getY() > tabenemy_y.get(i) && gamestate.getplayer().getY() < tabenemy_y.get(i) + 32 &&
				gamestate.getplayer().getX() < tabenemy_x.get(i) && gamestate.getplayer().getX() > tabenemy_x.get(i) - 64*3) 
			return 0;
		//à gauche
		else if(gamestate.getplayer().getY() > tabenemy_y.get(i) && gamestate.getplayer().getY() < tabenemy_y.get(i) + 32 &&
				gamestate.getplayer().getX() > tabenemy_x.get(i) && gamestate.getplayer().getX() < tabenemy_x.get(i) + 64*3) 
			return 1;
	
		return -1;
	}
	
	public void render(Graphics2D g2) {
		
		
		for(int i=0;i<tabenemy.size();i++) {
			BufferedImage image_enemy = null;
			image_enemy = tabenemy.get(i);
			g2.drawImage(image_enemy,tabenemy_x.get(i),tabenemy_y.get(i),gamestate);
		}
		
		
		for(int j = 0;j<tabmissile_image.size();j++) {
			BufferedImage image_missile = null;
			image_missile = tabmissile_image.get(j);
			g2.drawImage(image_missile,tabmissile_x.get(j),tabmissile_y.get(j),gamestate);
		}
	}
	
	public void update() {
		update_enemy();
		
		update_missile();
	}

	
	private void update_enemy() {
		move_gragouille();
		move_zombie();
	}
	
	private void update_missile() {
		spawn_missile();
		move_missile();
		missile_outofmap();
	}
	
	public int getX(int i) {
		if(tabenemy_x.size()!=0)
		return tabenemy_x.get(i);
		return -1;
	}
	
	public int getY(int i) {
		if(tabenemy_y.size()!=0)
		return tabenemy_y.get(i);
		return -1;
	}
	public int getTabsize() {
		return tabenemy.size();
	}
	
	public void resetEnemy() {
		tabenemy.clear();
		tabenemy_x.clear();
		tabenemy_y.clear();
		tabenemy_vie.clear();
		tabenemy_viemax.clear();
		nombre_enemy = 0;
	}
	
	
	  

	public static Boolean canMove() {
		
		if(gamestate.getTimer().time_sec_Enemy_CanMOVE > start_moving) 
			return true;

		return false;
	}
	
	private static Boolean clock_move() {
		if(gamestate.getTimer().time_sec_Enemy_move > time_move)
			return true;
		return false;
	}
	
	
	public int getVie(int i) {
		if(tabenemy_vie.size()!=0)
		return tabenemy_vie.get(i);
		return -1;
	}
	
	public void suppEnemy() {
		
		for(int i = 0;i<tabenemy_vie.size();i++) {
			if(isAlive(i)==false) {
			
					
					tabenemy.remove(i);
					tabenemy_x.remove(i);
					tabenemy_y.remove(i);
					tabenemy_vie.remove(i);
					tabenemy_viemax.remove(i);
					nombre_enemy--;
					i--;
			}
		}
		
	}
	
	public Boolean isAlive(int i) {
		
		if(tabenemy_vie.get(i) <=0) {
				return false;
			}
		return true;
		}
	

	public float ratio(int i) {
		
		int vie = tabenemy_vie.get(i);
		int vie_max = tabenemy_viemax.get(i);
		return vie/vie_max;
	}
	
	public int getnombre_enemy() {
		return nombre_enemy;
	}
	public void setnombre_enemy(int i) {
		nombre_enemy = i;
	}
	
}
	

	
