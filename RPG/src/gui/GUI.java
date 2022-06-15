package gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import main.GameState;
import main.map;

public class GUI {

	
	Font  font  = new Font(Font.SERIF, Font.PLAIN,  20);
	
	ArrayList<String> select_image_String_tab = new ArrayList<String>();
	ArrayList<String> select_image_String_tab_pnj = new ArrayList<String>();
	
	ArrayList<Boolean> success_nonanimated = new ArrayList<Boolean>();
	
	ArrayList<String> tab_description = new ArrayList<String>();
	
	ArrayList<Float> tab_ratio = new ArrayList<Float>();
	
	GameState gamestate;
	map carte;
	
	Boolean mainmenu;
	
	
	Boolean interact,onpause,inventory,shop,interactPNJ;
	int pos_pause_x,pos_pause_y;
	public int background_size_x,background_size_y,background_x,background_y,background_y_souris;
	public boolean isBlocked;
	
	String selected_finalcraft_item;
	String selected_final_item_shop;
	int select_image;
	
	int success_x,success_y;
	
	Boolean isAnimation_success_finished;
	
	Color RED = Color.red;
	Color WHITE = Color.white;
	
	int time_animation_move;
	float opacity;
	
	
	
	int inventory_x,inventory_y;
	

	
	String success_icon = null;
	
	
	public Boolean durability_bool;
	
	public GUI(GameState gs,map m) {
		gamestate = gs;
		carte = m;
		initVariables();
		initgD();
		
	}
	
	
	private void initVariables() {
		pos_pause_x = 1920/4;
		pos_pause_y = 1080/4;
		background_size_x = 300;
		background_size_y = 500;
		background_x = 40;
		background_y = 40;
		background_y_souris = background_y + 20;
		selected_finalcraft_item = "init";
		isBlocked = false;
		time_animation_move = 0;
		opacity = 0;
		
		mainmenu = false;
		
		inventory_x = 600;
		inventory_y = 10;
		
		success_x = 32*35;
		
		success_y = 50;
		isAnimation_success_finished = true;
		durability_bool = false;
		
		select_image = 0;
		interact = false;
		onpause = false;
		interactPNJ = false;
		inventory = false;
	}
	
	private void initgD() {
		
	}
	
	private String InttoString(int value) {
		return Integer.toString(value);
	}
	
	
	
	private void interaction(String m, Graphics2D g) {
		 g.setFont ( font );
		 g.setColor (Color.WHITE);
		 g.drawString ( m, gamestate.getplayer().getX() + 32,gamestate.getplayer().getY());
	}
	
	private void pausetitle(String m,Graphics2D g) {
		 g.setFont (new Font("TimesRoman", Font.PLAIN, 50));
		 g.setColor (Color.RED);
		 g.drawString("PAUSE", pos_pause_x, pos_pause_y);
	}
	
	private void infoniveaux(String m,Graphics2D g) {
		String ordre = InttoString(carte.getOrdre());
		 g.setFont (new Font("TimesRoman", Font.PLAIN, 30));
		 g.setColor (Color.WHITE);
		 g.drawString ( m + ordre, pos_pause_x,pos_pause_y + 40);
	}
	
	private void vie(String m,Graphics2D g) {
		String vie = InttoString(gamestate.getplayer().getVie());
		String viemax = InttoString(gamestate.getplayer().getVieMax());
		 g.setFont ( font );
		 g.setColor (Color.RED);
		 g.drawString ( m + vie + "/" + viemax , 400,20);
	}
	
	
	private void item_information_background(Graphics2D g,int i) {
		if(SourissurItem(i)) {
		g.setColor(Color.BLACK);
		g.fillRect(inventory_x + i*50,inventory_y*6 , 160, 100);
		}
	}
	
	
	
	private void durability(Graphics2D g) {

		String selectedweapon = gamestate.getSelection().getItemselected();
		
		//System.out.println(selectedweapon);
		
		if(selectedweapon!="null") {
		
		float durability = gamestate.getplayer().getdurability(selectedweapon);
		float ratio = gamestate.getplayer().ratio(selectedweapon);
		float durabilitymax = gamestate.getplayer().getdurabilitymax(selectedweapon);
	
		int place_weapon = gamestate.getplayer().findpositionelement(selectedweapon);
		
		int red = 0,green = 255,blue = 0;
		
		 if(durability_bool) {
			 if(ratio>0.5) {	 
			green = 255;
			red = (int) (255*(1-ratio) + 120);
			 }
			 else {
				 green = (int) (255*ratio);
				 red = 255;
			 }
			blue = 10;
		
		 }
		 
		 	g.setColor(Color.BLACK);
			g.fillRect(inventory_x + place_weapon*50, inventory_y*5, (int)(durabilitymax/6), 5);
			
			g.setColor(new Color(red,green,blue));
			g.fillRect(inventory_x + place_weapon*50, inventory_y*5, (int)durability/6, 5);
		
		
		}
		
		
	}
	
	public void setDurability(Boolean b) {
		durability_bool = b;
	}
	
	
		private void item_information(Graphics2D g,int i,String s) {
		
		if(SourissurItem(i)) {
			
			int compteur = 0;
			
			String text_ = "";
		g.setColor(WHITE);
		
		String text = null;
		
		if(s == "apple" || s == "golden_apple")
			text = "Nourriture:    Soigne "+ gamestate.getplayer().nbPVfood(s)+"PV";
		if(s == "cobblestone" || s == "stick" || s == "gold" || s == "iron")
			text = "Matériaux:		  Sert à créer 	des armes   et autres...";	
		if(s == "stone_sword" || s == "stone_axe" ||s == "iron_sword" ||s == "golden_sword" )
			text = "Arme :        Inflige " + gamestate.getplayer().getAtq(s) + "    points de    degats";
		if(s == "stone_pickaxe")
			text = "Outil : 		Permet de casser différents matériaux";
		
		g.setFont(font);
		
		//text_ permet de faire la distinction entre chaque ligne//
			for(int j = 0;j < text.length();j++) {
				char c = text.charAt(j);
				
				text_+=c;
				
				if(text_.length()%13==0) {
					tab_description.add(text_);
					text_ = "";
					compteur++;
				}
				}
			tab_description.add(text_);
			compteur++;
			
			for(int k = 0;k<compteur;k++)
				g.drawString(tab_description.get(k),inventory_x + 20 +i*50 , inventory_y*8 + k*20);
			}
		else tab_description.clear();
		}
	
	
	private void item(String m,Graphics2D g) {
		g.setFont(font);
		g.setColor(Color.WHITE);
		for(int i = 0;i<gamestate.getplayer().getItemTabSize();i++) {
		String nb_item = InttoString(gamestate.getplayer().getItemnb(i));
		String nom_item = gamestate.getplayer().getItemString(i);
		
		g.drawImage(gamestate.getitem().getimage(nom_item),inventory_x + i*50, inventory_y,gamestate);
		g.drawString(nb_item, inventory_x + 30 + i*50, inventory_y*5);
		
		item_information_background(g,i);
		item_information( g, i, nom_item);
		
		}
	}
	
	private void inv_background(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setBackground(Color.darkGray);
		g.fillRoundRect(background_x, background_y, background_size_x, background_size_y,20,20);
		
		g.setColor(Color.darkGray);
		g.setBackground(Color.LIGHT_GRAY);
		g.fillRoundRect(background_x * 2, background_y * 2, background_size_x - 80, background_size_y - 80,20,20);
	}
	
	private void selectionUIInventory(Graphics2D g) {
		
		for(int i = 0;i < gamestate.getitem().getItemCrafttab_size();i++) {	
			 if (SourisdansRectangle(i)) {
				g.setColor(WHITE);
				setselected_finalcraft_item(i);
			 }
			 else g.setColor(RED);
			 g.fillRoundRect(background_x*2, background_y+ 40*i, background_size_x - 80, 40 ,20,20);
			
			}
	}
	
	private void selectionUISHOP(Graphics2D g) {
		for(int i = 0;i<gamestate.getPnj().getTabSize();i++) {
			if(SourisdansRectangle(i)) {
				
				
				g.setColor(Color.YELLOW);
				setselected_finalitem_pnj(i);
			}
			else g.setColor(Color.gray);
			 g.fillRoundRect(background_x*2, background_y+ 40*i, background_size_x - 80, 40 ,20,20);
				
			
			
		}
		
		
	}
	
	private void selectioninventory(Graphics2D g) {
		g.setColor(RED);
		g.drawRect(inventory_x + gamestate.getSouris().nb_molette*50, inventory_y, 32, 32);
	}
	
	
	private void success_Text(int i,String s,Graphics2D g) {
		g.setFont(font);
		g.setColor(Color.YELLOW);
		g.drawString(s, success_x  , success_y - i);
	}
	
	private void success_Background(int i,Graphics2D g) {

		g.setColor(Color.BLACK);
		g.fillRect(success_x , success_y - i, 100, 40);
	}
	
	private void successInformation(String s,Graphics2D g) {
		if(gamestate.getitem().ifSuccess()) {
			
			//Si l'animation est finie,choisi la prochaine icone pour afficher//
			if(isAnimation_success_finished) {
			

			if(gamestate.getitem().success_stonesword_craftable) {
				success_icon = "stone_sword";
				isAnimation_success_finished = false;
				}
			else if(gamestate.getitem().success_stoneaxe_craftable) {
				success_icon = "stone_axe";
				isAnimation_success_finished = false;
				}
			else if (gamestate.getitem().success_goldenapple_craftable) {
				success_icon = "golden_apple";
				isAnimation_success_finished = false;
				}
			
			else if (gamestate.getitem().success_ironsword_craftable) {
				success_icon = "iron_sword";
				isAnimation_success_finished = false;
			}
			
			else if (gamestate.getitem().success_goldensword_craftable) {
				success_icon = "golden_sword";
				isAnimation_success_finished = false;
			}
			else if (gamestate.getitem().success_stonepickaxe_craftable) {
				success_icon = "stone_pickaxe";
				isAnimation_success_finished = false;
			}
			}
			
			
			//Si animation terminée, ne refait plus l'animation et réinitialise les valeurs//
			if(time_animation_move>100) {
				isAnimation_success_finished = true;
				
				
				if(gamestate.getitem().success_stonesword_craftable)
					gamestate.getitem().success_stonesword_craftable = false;
				else if (gamestate.getitem().success_stoneaxe_craftable)
					gamestate.getitem().success_stoneaxe_craftable = false;
				else if (gamestate.getitem().success_goldenapple_craftable)
					gamestate.getitem().success_goldenapple_craftable = false;
				else if (gamestate.getitem().success_goldensword_craftable)
					gamestate.getitem().success_goldensword_craftable = false;
				else if (gamestate.getitem().success_ironsword_craftable)
					gamestate.getitem().success_ironsword_craftable = false;
				else if (gamestate.getitem().success_stonepickaxe_craftable)
					gamestate.getitem().success_stonepickaxe_craftable  = false;
				
				time_animation_move = 0;
				opacity = 0;
			}
			
			//Si animation pas fini, alors draw.../
			if(isAnimation_success_finished == false) {
				//augmente l'opacité, jusquau maximum(1)//
				if(gamestate.getTimer().time_sec_GUI_Successopacity > 2) {
					opacity+=0.01;
					 if(opacity>1)
						 opacity = 1;
				}
				//Applique l'opacité//
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)opacity));
		
				success_Text(time_animation_move,s,g);
				success_Background(time_animation_move,g);
				g.drawImage(gamestate.getitem().getimage(success_icon),success_x , success_y - time_animation_move,gamestate);
			
			
			if(gamestate.getTimer().time_sec_GUI_Successtimerate > 10) {
				
				time_animation_move++;
				gamestate.getTimer().resettime_sec_GUI_Successtimerate();
		}}
			g.dispose();
				}
			}
		
	
	

	private void setselected_finalcraft_item(int i) {
		selected_finalcraft_item = select_image_String_tab.get(i);
		}
	private void setselected_finalitem_pnj(int i) {
		selected_final_item_shop = select_image_String_tab_pnj.get(i);
	}
	
	public String getselected_finalcraft_item() {
		return selected_finalcraft_item;
	}
	public String getselected_finalitem_pnj() {
		return selected_final_item_shop;
	}
	
	
	
	
	private Boolean SourisdansRectangle(int i) {
		if(gamestate.getSouris().getXMouse() > background_x*2 &&
				gamestate.getSouris().getYMouse()> (background_y_souris) + 40*i  &&
				gamestate.getSouris().getXMouse()<background_size_x &&
				gamestate.getSouris().getYMouse()< background_y_souris  + background_y+ 40*i) {
			
			select_image = i;
			
			
		return true;
		}
		return false;
	}
	
	public int getSelect_image() {
		return select_image;
	}
	
	private Boolean SourissurItem(int i) {
		if(gamestate.getSouris().getXMouse() > inventory_x + i*50 &&
				gamestate.getSouris().getYMouse()> inventory_y  &&
				gamestate.getSouris().getXMouse()<inventory_x + i*50 + 50 &&
				gamestate.getSouris().getYMouse()< 10*inventory_y) {
		return true;
		}
		return false;
	}
	
	private void inv_itemlist(Graphics2D g) {
		
		//initialisation//
		g.setFont(font);
		g.setColor(Color.BLACK);

		for(int i = 0;i < gamestate.getitem().getItemCrafttab_size();i++) {
			
			//POUR LES IMAGES DES ITEMS//
		String nom_item_craftable = gamestate.getitem().getItemCrafttab(i);	
		 String image_1 = gamestate.getitem().gettabItem_craft1_nom(i);
		 String image_2 = gamestate.getitem().gettabItem_craft2_nom(i);
		 String nb1 = InttoString(gamestate.getitem().gettabItem_craft1_nb(i));
		 String nb2 = InttoString(gamestate.getitem().gettabItem_craft2_nb(i));
		 
		 
		
		
		
		if(select_image_String_tab.contains(nom_item_craftable) == false) {
			select_image_String_tab.add(nom_item_craftable);
		}
		
		g.drawImage(gamestate.getitem().getimage(image_1),background_x+40,background_y+ i*40,gamestate);
		
		
		g.drawString("+",background_x + 100, background_y+ i*40 + 32);
		
		g.drawImage(gamestate.getitem().getimage(image_2),background_x + 120,background_y+ i*40,gamestate);
		
		
		g.drawString("->",background_x + 180, background_y+ i*40 + 32);
		
		g.drawImage(gamestate.getitem().getimage(nom_item_craftable),background_x + 200,background_y + i*40,gamestate);
		
		g.drawString(nb1, background_x+40 + 32, background_y+ 32+i*40);
		g.drawString(nb2,background_x + 120 + 32, background_y+ 32+i*40);
		
	
		}
	}
	
	
	 private void enemyVie(Graphics2D g) {
		
		 for(int i = 0;i < gamestate.getenemy().getTabsize();i++) {
			
			 
			 int vie = gamestate.getenemy().getVie(i);
			 int x = gamestate.getenemy().getX(i);
			 int y = gamestate.getenemy().getY(i);
			 
			 float ratio = gamestate.getenemy().ratio(i);
			 int red = 0,blue = 0,green = 255;
			 
			 
			 if(durability_bool) {
				 
				 if(ratio>=0) {
				 if(ratio>0.5) {	 
				green = 255;
				red = (int) (255*(1-ratio) + 150);
				 }
				 else {
					 green = (int) (255*ratio);
					 red = 255;
				 }
				blue = 40;
			 }
				 else {
					 green = 0;
					 blue = 40;
					 red = 0;
				 }
			 
			 }
			 g.setColor(new Color(red,green,blue));
				g.fillRect(x , y - 5, (int)vie*2, 5);
		 }
	}
	
	 
	private void enemy_number(Graphics2D g) {
		int nb_enemy = gamestate.getenemy().getnombre_enemy();
		String s = InttoString(nb_enemy);
		g.setColor(Color.red);
		g.setFont(font);
		g.drawString("Reste : "+s, 1000, 15);
	}
	 
	private void pnj_background(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.setBackground(Color.YELLOW);
		g.fillRoundRect(background_x, background_y, background_size_x, background_size_y,20,20);
		
		g.setColor(Color.darkGray);
		g.setBackground(Color.LIGHT_GRAY);
		g.fillRoundRect(background_x * 2, background_y * 2, background_size_x - 80, background_size_y - 80,20,20);
	}
	
	private void pnjInventory(Graphics2D g) {
		
		g.setFont(font);
		g.setColor(Color.BLACK);
		
		for(int i = 0;i < gamestate.getPnj().getTabSize();i++) {
			
			 String nom_Item= gamestate.getPnj().gettabpnj_itemfinal(i);
			 String image_1 = gamestate.getPnj().gettabpnj_item1_nom(i);
			 String image_2 = gamestate.getPnj().gettabpnj_item2_nom(i);
			 String nb1 = InttoString(gamestate.getPnj().gettabpnj_item1_nb(i));
			 String nb2 = InttoString(gamestate.getPnj().gettabpnj_item2_nb(i));
			 
			 
			 		
			if(select_image_String_tab_pnj.size()<gamestate.getPnj().getTabSize())
				select_image_String_tab_pnj.add(nom_Item);
			
			
			g.drawImage(gamestate.getitem().getimage(image_1),background_x+40,background_y+ i*40,gamestate);
			
			
			g.drawString("+",background_x + 100, background_y+ i*40 + 32);
			
			g.drawImage(gamestate.getitem().getimage(image_2),background_x + 120,background_y+ i*40,gamestate);
			
			
			g.drawString("->",background_x + 180, background_y+ i*40 + 32);
			
			g.drawImage(gamestate.getitem().getimage(nom_Item),background_x + 200,background_y + i*40,gamestate);
			
			
			g.drawString(nb1, background_x+40 + 32, background_y+ 32+i*40);
			g.drawString(nb2,background_x + 120 + 32, background_y+ 32+i*40);
		
			
			
			
			
		}
	}
	
	private void pnjInterface(Graphics2D g) {
		
		pnj_background(g);
		selectionUISHOP(g);
		pnjInventory(g);
	}
	 
	public void render(Graphics2D g2) {
		
		
		vie("vie : ",g2);
		item("Item : ",g2);
		enemyVie(g2);
		enemy_number(g2);
		selectioninventory(g2);
		
		
		if(interact)
		interaction("interaction..",g2);
		
		if(onpause) {
			pausetitle("",g2);
			infoniveaux("niveau : ",g2);
		}
		
		
		if(inventory) {
			inv_background(g2);
			
			selectionUIInventory(g2);
			inv_itemlist(g2);
		}
		
		if(interactPNJ) {
			pnjInterface(g2);
		}
		
		durability(g2);
		successInformation("succès : ",g2);
		
		
	}

	public void setinteract(Boolean value) {
		interact = value;
	}
	public void setonpause(Boolean value) {
		onpause = value;
	}
	public void setinventory(Boolean value) {
		inventory = value;
	}
	
	public void setinteractPNJ(Boolean value) {
		interactPNJ = value;
	}
	
	
	
}
