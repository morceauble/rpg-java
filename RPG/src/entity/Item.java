package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GameState;

public class Item {
	
	GameState gamestate;
	
	//items//
	
		public BufferedImage image_apple;
		public BufferedImage image_cobblestone;
		public BufferedImage image_stick;
		public BufferedImage image_stone_axe;
		public BufferedImage image_stone_sword;
		public BufferedImage image_golden_apple;
		public BufferedImage image_gold;
		public BufferedImage image_stone_pickaxe;
		public BufferedImage image_goldensword;
		public BufferedImage image_ironsword;
		public BufferedImage image_iron;
		
		
		
	
	 Graphics2D g2;
	
	static ArrayList<BufferedImage> tabItem = new ArrayList<BufferedImage>();
	static ArrayList<Integer> tabItem_x = new ArrayList<Integer>();
	static ArrayList<Integer> tabItem_y = new ArrayList<Integer>();
	static ArrayList<String> tabItem_String = new ArrayList<String>();
	static ArrayList<String> tabItem_String_Craftable = new ArrayList<String>();
	
	static ArrayList<String> tabItem_craft1_nom = new ArrayList<String>();
	static ArrayList<String> tabItem_craft2_nom = new ArrayList<String>();
	static ArrayList<Integer> tabItem_craft1_nb = new ArrayList<Integer>();
	static ArrayList<Integer> tabItem_craft2_nb = new ArrayList<Integer>();
	
	
	public int nb_stick_axe,nb_stick_sword,
	nb_cobble_stonesword,nb_cobble_stoneaxe,
	nb_apple_goldenapple,nb_gold_goldenapple,
	nb_stick_pickaxe,nb_cobble_stonepickaxe,
	nb_gold_goldensword,nb_iron_ironsword;
	
	public Boolean success_stonesword,success_stoneaxe,success_goldenapple,success_stonepickaxe,success_goldensword,success_ironsword;
	public Boolean success_stonesword_craftable,success_stoneaxe_craftable,success_goldenapple_craftable,success_stonepickaxe_craftable,success_goldensword_craftable,success_ironsword_craftable;
	
	
	public Item() {
		
	}
	
	public Item(GameState gs){
		this.gamestate = gs;
		initVariables();
		try {
			initImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initVariables() {
		nb_stick_axe = 2;
		nb_cobble_stoneaxe = 3;
		
		nb_stick_sword = 2;
		nb_cobble_stonesword = 2;
		
		
		nb_apple_goldenapple = 1;
		nb_gold_goldenapple = 1;
		
		nb_stick_pickaxe = 2;
		nb_cobble_stonepickaxe = 4;
		
		nb_gold_goldensword = 3;
		nb_iron_ironsword = 2;
		
		success_stonesword = false;
		success_stoneaxe = false;
		success_goldenapple = false;
		success_stonepickaxe = false;
		success_goldensword = false;
		success_ironsword = false;
		
		success_stonesword_craftable = false;
		success_stoneaxe_craftable = false;
		success_goldenapple_craftable = false;
		success_stonepickaxe_craftable = false;
		success_goldensword_craftable = false;
		success_ironsword_craftable = false;
		
		
	}
	
	private void initImage() throws IOException {
			String basePath = new File("").getAbsolutePath();
			String itemtexture = "/res/img/item/";
			
			
			image_apple = ImageIO.read(new File(basePath+itemtexture+"food/apple.png"));
			image_golden_apple= ImageIO.read(new File(basePath+itemtexture+"food/golden_apple.png"));
			
			image_cobblestone=ImageIO.read(new File(basePath+itemtexture+"materiaux/cobblestone.png"));
			image_stick=ImageIO.read(new File(basePath+itemtexture+"materiaux/stick.png"));
			image_gold = ImageIO.read(new File(basePath+itemtexture+"materiaux/gold_ingot.png"));
			image_iron = ImageIO.read(new File(basePath+itemtexture+"materiaux/iron_ingot.png"));
			
			
			image_stone_axe = ImageIO.read(new File(basePath+itemtexture+"outil/stone_axe.png"));
			image_stone_sword = ImageIO.read(new File(basePath+itemtexture+"outil/stone_sword.png"));
			image_stone_pickaxe = ImageIO.read(new File(basePath+itemtexture+"outil/stone_pickaxe.png"));
			image_goldensword = ImageIO.read(new File(basePath+itemtexture+"outil/golden_sword.png"));
			image_ironsword =ImageIO.read(new File(basePath+itemtexture+"outil/iron_sword.png"));
		}
	//Item spawn//
	public void addItem(String s,int x,int y) {
		if(s == "apple") {
			tabItem.add(image_apple);
		}
		else if (s == "cobblestone") {
			tabItem.add(image_cobblestone);
		}
		else if(s == "stick") {
			tabItem.add(image_stick);
		}
		else if (s == "stone_axe") {
			tabItem.add(image_stone_axe);
		}
		else if (s == "stone_sword") {
			tabItem.add(image_stone_sword);
		}
		else if (s == "golden_apple") {
			tabItem.add(image_golden_apple);
		}
		else if (s=="gold") {
			tabItem.add(image_gold);
		}
		else if (s == "stone_pickaxe") {
			tabItem.add(image_stone_pickaxe);
		}
		else if (s == "iron_sword")
			tabItem.add(image_ironsword);
		else if (s == "golden_sword")
			tabItem.add(image_goldensword);
		
		tabItem_x.add(x);
		tabItem_y.add(y);

			tabItem_String.add(s);
		
			System.out.println("item : " + tabItem_String);
			System.out.println("x : " + tabItem_x + "y: " + tabItem_y);
		
		
	}
	
	

	
	
	private Boolean itemnecessaire(String s) {
		if(s == "stone_sword") {
			
			if(gamestate.getplayer().getItemnbauto("stick") >=nb_stick_sword && gamestate.getplayer().getItemnbauto("cobblestone") >=nb_cobble_stonesword)
				return true;
			}
		
		else if (s == "stone_axe") {
			if(gamestate.getplayer().getItemnbauto("stick") >=nb_stick_axe && gamestate.getplayer().getItemnbauto("cobblestone") >=nb_cobble_stoneaxe)
				return true;
		}
		
		else if (s == "stone_pickaxe") {
			if(gamestate.getplayer().getItemnbauto("stick") >=nb_stick_pickaxe && gamestate.getplayer().getItemnbauto("cobblestone") >=nb_cobble_stonepickaxe)
				return true;
		}
		else if (s == "golden_sword") {
			
			
			if(gamestate.getplayer().getItemnbauto("stick") >=nb_stick_sword && gamestate.getplayer().getItemnbauto("gold") >=nb_gold_goldensword) 
				return true;
			}
		
		
		else if (s == "golden_apple") {
				if(gamestate.getplayer().getItemnbauto("apple") >=nb_apple_goldenapple && gamestate.getplayer().getItemnbauto("gold") >=nb_gold_goldenapple)
				return true;
		
		}
		
		
		
		else if (s == "iron_sword") {
			if(gamestate.getplayer().getItemnbauto("stick") >=nb_stick_sword && gamestate.getplayer().getItemnbauto("iron") >=nb_iron_ironsword) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public Boolean ifSuccess() {
		if(success_stonepickaxe_craftable || success_stoneaxe_craftable || success_goldenapple_craftable || success_stonesword_craftable || success_ironsword_craftable || success_goldensword_craftable)
			return true;
		return false;
	}
	
	private void TriCraftableItem() {
		if(!(tabItem_String_Craftable.contains("stone_sword"))) {
			if(itemnecessaire("stone_sword")) {
				
				tabItem_String_Craftable.add("stone_sword");
				tabItem_craft1_nom.add("stick");
				tabItem_craft1_nb.add(nb_stick_sword);
				tabItem_craft2_nb.add(nb_cobble_stonesword);
				tabItem_craft2_nom.add("cobblestone");
				
				success_stonesword = true;
				success_stonesword_craftable = true;
			}}
		if(!(tabItem_String_Craftable.contains("stone_axe"))){
			if(itemnecessaire("stone_axe")) {
				
				tabItem_String_Craftable.add("stone_axe");
				
				tabItem_craft1_nom.add("stick");
				tabItem_craft1_nb.add(nb_stick_axe);
				tabItem_craft2_nb.add(nb_cobble_stoneaxe);
				tabItem_craft2_nom.add("cobblestone");
				
				
				
				success_stoneaxe = true;
				success_stoneaxe_craftable = true;
				}}
		
		
		if(!(tabItem_String_Craftable.contains("golden_apple")))
		{
			if(itemnecessaire("golden_apple")) {
				
				tabItem_String_Craftable.add("golden_apple");
				
				tabItem_craft1_nom.add("gold");
				tabItem_craft1_nb.add(nb_gold_goldenapple);
				tabItem_craft2_nb.add(nb_apple_goldenapple);
				tabItem_craft2_nom.add("apple");
				
				
				success_goldenapple = true;
				success_goldenapple_craftable = true;
				}}
		if(!(tabItem_String_Craftable.contains("stone_pickaxe"))) {
			if(itemnecessaire("stone_pickaxe")) {
				
				tabItem_String_Craftable.add("stone_pickaxe");
				
				tabItem_craft1_nom.add("stick");
				tabItem_craft1_nb.add(nb_stick_pickaxe);
				tabItem_craft2_nb.add(nb_cobble_stonepickaxe);
				tabItem_craft2_nom.add("cobblestone");
				success_stonepickaxe = true;
				success_stonepickaxe_craftable = true;
			}
			
			
			if(!(tabItem_String_Craftable.contains("golden_sword"))) {
				if(itemnecessaire("golden_sword")) {
					tabItem_String_Craftable.add("golden_sword");
					
					tabItem_craft1_nom.add("gold");
					tabItem_craft1_nb.add(nb_gold_goldensword);
					tabItem_craft2_nb.add(nb_stick_sword);
					tabItem_craft2_nom.add("stick");
					success_goldensword = true;
					success_goldensword_craftable = true;
				}
				}
			if(!(tabItem_String_Craftable.contains("iron_sword"))) {
				if(itemnecessaire("iron_sword")) {
					tabItem_String_Craftable.add("iron_sword");
					
					tabItem_craft1_nom.add("stick");
					tabItem_craft1_nb.add(nb_stick_sword);
					tabItem_craft2_nb.add(nb_iron_ironsword);
					tabItem_craft2_nom.add("iron");
					success_ironsword = true;
					success_ironsword_craftable = true;
				}
				}
			}
			
			
			
			
		//System.out.println("craftable item: " + tabItem_String_Craftable);
		
		
		
	}
	
	public int getTabSizeShop() {
		return tabItem_craft1_nom.size();
	}
	
	public String gettabItem_craft1_nom(int i) {
		if(tabItem_craft1_nom.size()!=0)
		return tabItem_craft1_nom.get(i);
		return "null";
	}
	
	public String gettabItem_craft2_nom(int i) {
		if(tabItem_craft2_nom.size()!=0)
		return tabItem_craft2_nom.get(i);
		return "null";
	}
	public int gettabItem_craft2_nb(int i) {
		if(tabItem_craft2_nb.size()!=0)
		return tabItem_craft2_nb.get(i);
		return -1;
	}
	public int gettabItem_craft1_nb(int i) {
		if(tabItem_craft1_nb.size()!=0)
		return tabItem_craft1_nb.get(i);
		return -1;
	}
	
	public String getItemCrafttab(int i) {
		
		return tabItem_String_Craftable.get(i);
	}
	
	public int getItemCrafttab_size() {
		return tabItem_String_Craftable.size();
	}
	
	//remove l'item de la map(et actualise pour le craft d'item et autre)//
	public void removeItem(int i,int x,int y) {
		
			if(tabItem_x.get(i) == x 
					&& tabItem_y.get(i) == y) {
				
				tabItem.remove(i);
				tabItem_x.remove(i);
				tabItem_y.remove(i);
				tabItem_String.remove(i);	
			}
			//detection si un item est craftable//
			TriCraftableItem();
	}
	
	
	
	
	public void render(Graphics2D g2) {
		for(int i = 0;i<tabItem.size();i++) {
			
			BufferedImage image_item = null;
			image_item = tabItem.get(i);
			
			g2.drawImage(image_item,tabItem_x.get(i),tabItem_y.get(i),gamestate);
		}
	}
	
	
	public void update() {
		
	}
	
	public int getX(int i) {
		if(tabItem_x.size()!=0 && tabItem_x.size()>i)
		return tabItem_x.get(i);
		return -1;
	}
	
	public int getY(int i) {
		if(tabItem_y.size()!=0 && tabItem_y.size()>i)
		return tabItem_y.get(i);
		return -1;
	}
	public int getTabsize() {
		return tabItem.size();
	}
	
	public int getTabsizeString() {
		return tabItem_String.size();
	}
	
	public String getTabString(int i) {
		return tabItem_String.get(i);
	}
	
	public BufferedImage getimage(String s) {
		
		BufferedImage image = null;
		
		if(s == "apple") {
			image = image_apple;
		}
		else if (s == "golden_apple") {
			 image=image_golden_apple;
		}
		else if (s == "cobblestone") {
			image= image_cobblestone;
		}
		else if(s == "stick") {
		image= image_stick;
		}
		else if (s == "stone_axe") {
			image=  image_stone_axe;
		}
		else if (s == "stone_sword") {
			image=  image_stone_sword;
		}
		else if (s == "stone_pickaxe") {
			image=  image_stone_pickaxe;
		}
		else if (s == "gold") {
			image=  image_gold;
		}
		else if (s == "iron_sword")
			image = image_ironsword;
		else if (s == "golden_sword")
			image = image_goldensword;
		else if( s == "iron")
			image = image_iron;
		return image;
	}
	
	
	public void resetItem() {
		tabItem.clear();
		tabItem_x.clear();
		tabItem_y.clear();
		tabItem_String.clear();
	}
}


	

