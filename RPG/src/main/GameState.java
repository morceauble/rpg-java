package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.Timer;

import javax.swing.JPanel;

import entity.Player;
import entity.pnj;
import entity.Enemy;
import entity.Item;
import gui.GUI;
import gui.mouse;
import gui.selection;

@SuppressWarnings("serial")
public class GameState extends JPanel implements Runnable{
	
	public int TileSize;
	int scale;
	public int finalTileSize;
	int maxScreenHauteur;
	int maxScreenLongueur;
	static int screenHauteur;
	static int screenLongueur;
	int mapon;
	
	
	Boolean ingame;
	Boolean inmenu_principal;
	
	Boolean mptogs = false;
	
	
	int pos_x,pos_y;
	
	Boolean interactionporte;
	
	
	Boolean up,down,left,right;
	static Boolean ismap = true;
	

	
	
	 
	

	//Init
	Thread gameThread;
	
	KeyHandler kh = new KeyHandler();
	
	Player player = new Player(this,kh);
	
	Enemy enemy = new Enemy(this);
	
	Item item = new Item(this);
	
	mouse souris = new mouse(this);

	map Map = new map(this,"map0");
	
	GUI gui = new GUI(this,Map);
	
	Timeur TIMER = new Timeur(this);
	
	selection Selection = new selection(this);
	
	pnj PNJ = new pnj(this);
	
	menuprincipal mp = new menuprincipal(this);
	
	portal portail = new portal(this);
	
	int FPS = 60;
	
	
	
	
	
	public GameState() {
		
		inmenu_principal = true;
		ingame = false;
		
		initVariables();
		initWindow();
		initKey();
		initMouse();
	}
	
	
	private void initVariables() {
		 TileSize = 32;
		 scale = 3;
		 finalTileSize = TileSize * scale;//Taille final du perso(aggrandi en *3)
	
		 
		 maxScreenLongueur = 32;
		 maxScreenHauteur = 12;
		 screenHauteur = maxScreenHauteur * finalTileSize;
		 screenLongueur = maxScreenLongueur * finalTileSize;
		
		 interactionporte = false;
		 
		
		 
		
		
		

	}
	
	private void initWindow() {
		this.setPreferredSize(new Dimension(screenLongueur,screenHauteur));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
	}

	
	private void initKey() {
		this.addKeyListener(kh);
		this.setFocusable(true);
	}
	
	private void initMouse() {
		this.addMouseListener(souris);
		this.addMouseWheelListener(souris);
		this.setFocusable(true);
	}
	
	public void startGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	
	
	
	@Override
	public void run() {
		
		double drawInterval = 100000000/FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		int drawCount = 0;
		int timer = 0;
		while(gameThread!= null) {
			
			
			currentTime = System.nanoTime();
			
			delta +=(currentTime -lastTime)/drawInterval;
			lastTime = currentTime;
			
			if(delta >=1) {
				//game update//
				update();
				//render game//
				repaint();
				delta--;
				drawCount++;
			}
			
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				if(remainingTime<0)
					remainingTime = 0;
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
			
			if(timer>=1000000000) {
				drawCount = 0;
				timer = 0;
			}
			
			
		
		}
		
		
	}
	
	
	public void update() {
		
		
		if(ingame && mp.game_action) {
			
			
			
		if(!pause() && !isInventory()  && !isShopping())
		updateMap();
		if(isInventory())
			updateCrafting();
		if(isShopping()) 
			updateShopping();
		finjeu();
		
		
		
		
		}
		
		
		else if (inmenu_principal) {
			mp.update();
			
		}
		
		TIMER.updateTimer();
		souris.update();
	}
	
	
	
	private void updateMap() {
		
		Map.update();
		player.update();
		enemy.update();
		item.update();
		
		collisionEnemy();
		collisionMissile();
		collisionItem();
		interaction();
		loot_enemy();
		
		
		
		
		if(player.getUp() || player.getDown() || player.getLeft() || player.getRight())
		collision();
		
		changermap();
		}
	
	private void updateShopping() {
		if(souris.leftPressed) {
			souris.setLeftMousetofalse();
			
			
			
				int i = gui.getSelect_image();
				
				if(player.enoughitem(PNJ.gettabpnj_item1_nom(i),PNJ.gettabpnj_item1_nb(i)) && player.enoughitem(PNJ.gettabpnj_item2_nom(i),PNJ.gettabpnj_item2_nb(i))) {
					player.setItemnbAdd(PNJ.gettabpnj_item1_nom(i),-PNJ.gettabpnj_item1_nb(i));
					player.setItemnbAdd(PNJ.gettabpnj_item2_nom(i),-PNJ.gettabpnj_item2_nb(i));
					player.addItem(PNJ.gettabpnj_itemfinal(i), 1);
				
				
			}
			player.ifitem0();
		}
	}
	
	
	
	private void updateCrafting() {
		
		if(souris.leftPressed) {
			souris.setLeftMousetofalse();

			int i = gui.getSelect_image();
			
			if(player.enoughitem(item.gettabItem_craft1_nom(i),item.gettabItem_craft1_nb(i)) && player.enoughitem(item.gettabItem_craft2_nom(i),item.gettabItem_craft2_nb(i))) {
				player.setItemnbAdd(item.gettabItem_craft1_nom(i),-item.gettabItem_craft1_nb(i));
				player.setItemnbAdd(item.gettabItem_craft2_nom(i),-item.gettabItem_craft2_nb(i));
				player.addItem(item.getItemCrafttab(i), 1);
		}
			player.ifitem0();
			}
		}
		
	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		if(ingame) {
		Map.render(g2);
		player.render(g2);
		PNJ.render(g2);
		enemy.render(g2);
		item.render(g2);
		portail.render(g2);
		
		Map.rendertop(g2);
		gui.render(g2);
		
		}
		
		 if (inmenu_principal) {
			mp.render(g2);
		}
		
		g2.dispose();
	}
	
	
	private Boolean pause() {
		if(kh.pausePressed) {
			gui.setonpause(true);
			return true;
		}
		gui.setonpause(false);
		return false;
	}
	
	private Boolean isInventory() {
		if(kh.inventoryPressed) {
			gui.setinventory(true);
			return true;
		}
		gui.setinventory(false);
		return false;
	}
	
	
	
	
	private Boolean isShopping() {
		if(player.pnjinattackrange()&& kh.interactionPressed) {
			
			if(getPnj().getPnjtype(getplayer().pnj_index) == "wizard") {
				
			
			gui.setinteractPNJ(true);
			return true;
		}
		}
		gui.setinteractPNJ(false);
		return false;
	}
	
	private int whereisinteraction(String value) {
		//EN BAS
		
		//System.out.println("player Y +TS + 1 : " + (player.getY()+TileSize + 1)/TileSize + "player.getX()/TileSize : " + player.getX()/TileSize);
		
		if(Map.gettabString_mid_int((player.getY()+TileSize + 1)/TileSize,player.getX()/TileSize).contains(value)) 
			return 1;
		
		//EN HAUT//
		else if(Map.gettabString_mid_int((player.getY()-1)/TileSize,player.getX()/TileSize).contains(value)) 
			return 2;
		
		//A DROITE
		else if(Map.gettabString_mid_int((player.getY())/TileSize,(player.getX()+TileSize + 1)/TileSize).contains(value)) 
			return 3;
		
		//A GAUCHE
		else if (Map.gettabString_mid_int((player.getY())/TileSize,(player.getX() - 1)/TileSize).contains(value))
			return 4;
		
		return 0;
		}

	private Boolean isinportal() {
		if(player.getX() > portail.getX() && 
				player.getX() < portail.getX() + TileSize && 
				player.getY() > portail.getY() && 
				player.getY() < portail.getY() + 2*TileSize) {
			return true;
		}
		return false;
	}
	
	
	private void changermap() {
		if(enemy.nomoreEnemy && Map.fin == false)
			
			if(isinportal())
			chargermap("suivant");
		
	}
	
	//METTRE UNE FONCTION DANS INTEGER(AVEC UN SORTE DE PORTAIL) POUR POUVOIR ACTIVER changermap("")
	public void chargermap(String value) {
		System.out.println("CHARGER MAP " + value);
		
		
		
		if(value == "precedent") {
			if(Map.getOrdre()>0)
			Map.setOrdre(-1);
			mptogs = false;
		}
		
		else if (value == "suivant") {
			Map.setOrdre(1);
			mptogs = false;
		}
		else {
			Map.setOrdre(0);
			mptogs = true;
		}
		
		initGameState();
	}
	
	
	private void initGameState() {
		
		Map.ischangingmap(true);
		PNJ.clear();
		enemy.resetEnemy();
		if(mptogs == false) {
		//mettre la map dans un fichier .txt//
		Map.effacermap();
		
		}
		
		
		
		 int i = Map.getOrdre();
		
		String s = "map" + Integer.toString(i);
		System.out.println("ordre : " +  i);
		
		 Map = new map(this,s);
		
		 portail.setCoor(-100, -100);
		 
		if(!Map.fin) {
			
			spawnEnemy_level(i);
			PNJ.addPnj("wizard", 500, 500);

			 PNJ.clear_shop();
			 
			 Random r = new Random();
			 
			 int nb2 = r.nextInt(10 + 1) + 1;
			 int nb1 = r.nextInt(3 + 1) + 1;
			 
			 PNJ.addSlotShop("stick", nb1, "cobblestone", nb2, "golden_apple");
			 PNJ.addSlotShop("gold", nb1, "apple", nb2%2, "golden_sword");
			 
			 PNJ.addSlotShop_rand();
			 PNJ.addSlotShop_rand();
			 PNJ.addSlotShop_rand();
			 PNJ.addSlotShop_rand();

			
		}
		//Si dernière map//
		if(Map.fin) {
			 
			 player.resetinventory();
			
				PNJ.addPnj("pnjfin", 300, 300);
				
		}

		item.resetItem();
		
		player.setX(120);
		player.setY(120);
		Map.ischangingmap(false);
		ismap = true;
	}
	
	private void finjeu() {
		if(player.pnjinattackrange()&& kh.interactionPressed) {
			
			if(getPnj().getPnjtype(getplayer().pnj_index) == "pnjfin") {
				System.out.println();
				System.out.println("INTERACTION AVEC FINPNJ"+'\n' +'\n');
				setinmenu_principal(true);
				setingame(false);
				mp.game_action = false;
				Map.fin = false;
				Map.resetMapEdit();
				Map.setOrdre(0);
				Map.effacertout();
			}
		}
	}
	
	private void loot_enemy() {
		for(int i = 0;i<enemy.getTabsize();i++) {
		if(enemy.isAlive(i) == false) {
			item.addItem("golden_apple", enemy.getX(i), enemy.getY(i));
			
			
			enemy.suppEnemy();
			
			if(enemy.nomoreEnemy)
				portail.setCoor(15*TileSize, 10*TileSize);
		}
		
		}
	}
	
	private void spawnEnemy_level(int value_level) {
		switch(value_level) {
		
		case(0):
			
			/*enemy.addEnemy("zombie", 50, 50);
		 	enemy.addEnemy("zombie", 150, 50);
		 	enemy.addEnemy("zombie", 50, 200);*/
		 	enemy.addEnemy("gragouille", 500, 200);
		 	enemy.addEnemy("gragouille", 100, 50);
			break;
		case(1):
			enemy.addEnemy("zombie", 50, 50);
		enemy.addEnemy("gragouille", 500, 200);
	 	enemy.addEnemy("gragouille", 100, 50);
			break;
		
		case(2):
			enemy.addEnemy("zombie", 150, 150);
		enemy.addEnemy("gragouille", 500, 200);
	 	enemy.addEnemy("gragouille", 100, 50);
			break;
		case(3):
			enemy.addEnemy("gragouille", 200, 200);
			enemy.addEnemy("zombie", 150, 150);
			enemy.addEnemy("gragouille", 500, 200);
		 	enemy.addEnemy("gragouille", 100, 50);
			break;
		default:
			enemy.addEnemy("zombie", 50, 50);
		 	enemy.addEnemy("gragouille", 100, 50);
		
		
	}}
	
	private void interaction_bed() {
		gui.setinteract(true);
		if(souris.rightPressed) {
			souris.rightPressed = false;
			player.setVie(player.getVieMax());
			
		}
	}
	
	private void interaction_porte() {
		System.out.println("INTERACTION PORTE");
		Map.effacermap();
		
		if(ismap) {
			//maison
		ismap = false;
		
		pos_x = player.getX();
		pos_y = player.getY();
		Map = new map(this,"m1");
		System.out.println(Map.getx_door()* TileSize);
		System.out.println((Map.gety_door() - 1)* TileSize);
		
		player.setX(Map.getx_door()* TileSize);
		player.setY((Map.gety_door() - 1)* TileSize);
		
		 interactionporte = false;
		}
		
		
		else {
			//map
			int i = Map.getOrdre();
			
			
			String s = "map" + Integer.toString(i);
		Map = new map(this,s);
		ismap = true;
		
		
		
		
		player.setX(pos_x);
		player.setY(pos_y + TileSize); 
		 interactionporte = false;
	}
	}
	
	private void interaction_chest() {
		gui.setinteract(true);
		if(souris.rightPressed) {
			souris.setRightMousetofalse();
			int x_ = 0,y_ = 0;
			switch(whereisinteraction("C")) {
			
			case(1):
				y_ = (player.getY()+TileSize + 1)/TileSize;
				x_ = player.getX()/TileSize;
				break;
			case(2):
				
				y_ = (player.getY()-1)/TileSize;
				x_ = player.getX()/TileSize;
				
				break;
			case(3):
				
				y_ = player.getY()/TileSize;
				x_ = (player.getX()+TileSize + 1)/TileSize;
				break;
			case(4):
				
				y_ = player.getY()/TileSize;
				x_ = (player.getX() - 1)/TileSize;
				
				break;
			}
			
			
			Random random = new Random();
			int max = 100;
			int min = 0;

			int value = random.nextInt(max + min) + min;
			int rX = random.nextInt(max + min) + min;
			int rY = random.nextInt(max + min) + min;
			
			if(value>=2) {
				System.out.println("STICK ADD CHEST");
				item.addItem("stick", x_*TileSize - rX,y_*TileSize + rY);
			}
			//100-18 chances sur 100 que ce soit juste un buisson//
			if(value>=18) {
				Map.settabString_top(y_, x_, "F");
			}
			//90%//
			if(value>=10) {
				item.addItem("apple", x_*TileSize,y_*TileSize);
				Map.settabString_top(y_, x_, "-");
			}
			//95%//
			if(value>=5) {
				item.addItem("cobblestone", x_*TileSize + rX,y_*TileSize - rY);
				Map.settabString_top(y_, x_, "-");
			}
			
			if(value>=5) {
				item.addItem("gold", x_*TileSize - rX/2,y_*TileSize + rY+ rY/4);
				Map.settabString_top(y_, x_, "-");
			}
			
			//1 chance sur 100 que ce soit un ennemy
			else if(value == 0){
				enemy.addEnemy("chest", x_*TileSize,y_*TileSize);
				Map.settabString_top(y_, x_, "-");
			}
			
			
			Map.settabString_mid(y_,x_,"-");
	}}
	
	private void interaction_oaksapling() {
		if(Selection.getItemselected() =="stone_axe") {
			gui.setinteract(true);
			if(souris.rightPressed ) {
					souris.setRightMousetofalse();				
					int x_ = 0,y_ = 0;
				switch(whereisinteraction("O")) {
				
				case(1):
					y_ = (player.getY()+TileSize + 1)/TileSize;
					x_ = player.getX()/TileSize;
					break;
				case(2):
					
					y_ = (player.getY()-1)/TileSize;
					x_ = player.getX()/TileSize;
					
					break;
				case(3):
					
					y_ = player.getY()/TileSize;
					x_ = (player.getX()+TileSize + 1)/TileSize;
					break;
				case(4):
					
					y_ = player.getY()/TileSize;
					x_ = (player.getX() - 1)/TileSize;
					
					break;
				}
				
				Map.settabString_top(y_, x_, "F");
				Map.settabString_mid(y_,x_,"-");
				/*Map.settabString_mid_int(y_,x_,"-");
				Map.settabString_mid_coll(y_,x_,"-");*/
				
				
				
				Random random = new Random();
				int max = 80;
				int min = 40;
				int value = random.nextInt(max + min) + min;
				int rX = random.nextInt(max + min) + min;
				int rY = random.nextInt(max + min) + min;
				
				if(value>60)
				item.addItem("apple", x_*TileSize + rX,y_*TileSize + rY);
				}}
	}
	
	private void interaction_stone() {
		if(Selection.getItemselected() =="stone_pickaxe") {
			gui.setinteract(true);
			if(souris.rightPressed ) {
		
				souris.setRightMousetofalse();				
				int x_ = 0,y_ = 0;
			switch(whereisinteraction("S")) {
			
			case(1):
				y_ = (player.getY()+TileSize + 1)/TileSize;
				x_ = player.getX()/TileSize;
				break;
			case(2):
				
				y_ = (player.getY()-1)/TileSize;
				x_ = player.getX()/TileSize;
				
				break;
			case(3):
				
				y_ = player.getY()/TileSize;
				x_ = (player.getX()+TileSize + 1)/TileSize;
				break;
			case(4):
				
				y_ = player.getY()/TileSize;
				x_ = (player.getX() - 1)/TileSize;
				
				break;
			}
			Map.settabString_top(y_, x_, "-");
			Map.settabString_mid(y_,x_,"-");
			/*Map.settabString_mid_int(y_,x_,"T");
			Map.settabString_mid_coll(y_,x_,"T");*/
			
			
			Random random = new Random();
			int max = 80;
			int min = 30;
			int value = random.nextInt(max + min) + min;
			int rX = random.nextInt(max + min) + min;
			int rY = random.nextInt(max + min) + min;
			
			if(value>50)
			item.addItem("cobblestone", x_*TileSize + rX,y_*TileSize + rY);
			
			
			}}
	}
	
		
		
		
		
		
		
	
	
	private void interaction(){
		
		
		//System.out.println("interactionporte: " + interactionporte);
		
		//Si le joueur est sur une porte//
		if(Map.gettabString_mid_int((player.getY())/TileSize,player.getX()/TileSize).contains("2")||Map.gettabString_mid_int(player.getY()/TileSize,player.getX()/TileSize).contains("1")){
			interactionporte = true;
			 interaction_porte();
			
		}
	
		if(!interactionporte) {
		
		//Regarde autour si interaction avec C(Chest)//
	if(whereisinteraction("C")!=0){
		interaction_chest();
		}
		
		
	
		//Regarde autour si interaction avec O(pousse chene oak)//
	else if(whereisinteraction("O")!=0){	
		interaction_oaksapling();
		}
		
	
	
	else if(whereisinteraction("S")!=0){
		interaction_stone();
	}
	
	else if (whereisinteraction("3") !=0 || whereisinteraction("4") !=0) {
		interaction_bed();
	}

		else gui.setinteract(false);
	}
	}
	
	
	private void collisionMissile() {
		for(int i = 0;i<enemy.getTabMissileSize();i++) {
			
			if(player.getX()>=enemy.getMissileX(i) 
					&& player.getY()>=enemy.getMissileY(i)
					&& player.getX()<=enemy.getMissileX(i)+TileSize
					&& player.getY()<=enemy.getMissileY(i)+TileSize/2) {
			
				checkPlayerStatus();
			
			}
		}

	}
	
	
	
	private void collisionEnemy() {
		for(int i = 0;i<enemy.getTabsize();i++) {
		if(player.getX()>=enemy.getX(i) 
				&& player.getY()>=enemy.getY(i)
				&& player.getX()<=enemy.getX(i)+TileSize
				&& player.getY()<=enemy.getY(i)+TileSize) {
			
			checkPlayerStatus();
			
			if(enemy.getTypeenemy(i) == "zombie") {
				enemy.healZombie(2, i);
			}
			
			}
		}
	}
	
	
	private void checkPlayerStatus() {
		if(!player.isInvinsible()) {
			player.setVie(player.getVie()-1);
			getTimer().resettime_sec_Player_invinsible();
			}
			
			
			if(player.isAlive() == false) {
				player.resetinventory();
				player.setX(150);
				player.setY(150);
				player.setVie(20);
	}}
	
	
	
		
	
	private void collision() {
		
		int locx = player.getX();
		int locy = player.getY();
		
		
		
		//COLLISIONS(),up et left//
		if(!(Map.gettabString_mid_int((locy)/TileSize,locx/TileSize).contains("+")||Map.gettabString_mid_coll((locy)/TileSize,locx/TileSize).contains("-"))){
		
			
			if(player.getUp()) {
				
				locy+=1;
				
				player.setY(locy);
			}
			else if(player.getLeft()) {
				locx+=1;
				player.setX(locx);
			}
		}
		else if (!(Map.gettabString_mid_int((locy + TileSize)/TileSize,locx/TileSize).contains("+") ||Map.gettabString_mid_coll((locy + TileSize)/TileSize,locx/TileSize).contains("-") )) {
			 if(player.getDown()) {
			
				locy-=(1);
				player.setY(locy);
			}
		}
		
		else if (!(Map.gettabString_mid_int((locy)/TileSize,(locx +TileSize)/TileSize).contains("+")||Map.gettabString_mid_coll((locy)/TileSize,(locx +TileSize)/TileSize).contains("-"))) {
			if(player.getRight()) {
				//System.out.print("collision DROITE" + '\n' + "avant col : " + locx + '\n');
				locx-=(1);
				player.setX(locx);
			}
		}
	}
		
		
	private void collisionItem() {
		
		for(int i = 0;i<item.getTabsizeString();i++) {
			
		if(			player.getX()>=item.getX(i) 
				&& 	player.getY()>=item.getY(i)
				&& 	player.getX()<=item.getX(i)+TileSize
				&& 	player.getY()<=item.getY(i)+TileSize
				
				
				&&	player.getX()+TileSize>=item.getX(i)
				&&	player.getY()+TileSize>=item.getY(i)	
					) {
			
			player.addItem(item.getTabString(i), 1);
			item.removeItem(i,item.getX(i), item.getY(i));
			
		}
	}}
	
	
	
	
	
	public Player getplayer() {
		return player;
	}
	
	public Enemy getenemy() {
		return enemy;
	}
	
	public Item getitem() {
		return item;
	}
	
	public GUI getGui() {
		return gui;
	}
	
	public mouse getSouris() {
		return souris;
	}
	
	public Timeur getTimer() {
		return TIMER;
	}
	
	public selection getSelection() {
		return Selection;
	}
	
	public pnj getPnj() {
		return PNJ;
	}
	
	public void setingame(Boolean b) {
		ingame = b;
	}
	public void setinmenu_principal(Boolean b) {
		inmenu_principal = b;
	}
	
	public map getMap() {
		return Map;
	}
	
}
