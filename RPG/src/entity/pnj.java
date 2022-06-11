package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GameState;

public class pnj {

	
	GameState gamestate;
	
	ArrayList<Integer> tabpnj_x = new ArrayList<Integer>();
	ArrayList<Integer> tabpnj_y = new ArrayList<Integer>();
	ArrayList<BufferedImage> tabpnj_image = new ArrayList<BufferedImage>();
	
	ArrayList<String> tabpnj_itemfinal = new ArrayList<String>();
	ArrayList<String> tabpnj_item1_nom = new ArrayList<String>();
	ArrayList<String> tabpnj_item2_nom = new ArrayList<String>();
	
	ArrayList<Integer> tabpnj_item1_nb = new ArrayList<Integer>();
	ArrayList<Integer> tabpnj_item2_nb = new ArrayList<Integer>();
	
	BufferedImage image_wizard;
	BufferedImage image_pnjfin;
	
	public pnj(GameState gs) {
		this.gamestate = gs;
		initTextures();
		initVariables();
	}
	
	private void initVariables() {
	}
	
	
	private void initTextures() {
		try{
			String basePath = new File("").getAbsolutePath();
			image_wizard = ImageIO.read(new File(basePath+"/res/img/pnj/wizard.png"));
			image_pnjfin = ImageIO.read(new File(basePath+"/res/img/pnj/finpnj.png"));
			
			
			
			
		}catch(IOException e) {
			e.printStackTrace();		
			}
	}
	
	public void addPnj(String s,int x,int y) {
		if(s == "wizard") {
			tabpnj_image.add(image_wizard);
		}
		else if (s == "pnjfin")
			tabpnj_image.add(image_pnjfin);
		
		tabpnj_x.add(x);
		tabpnj_y.add(y);
		
		}
	
	public void addSlotShop(String item1,int nb1,String item2,int nb2,String itemfinal) {
		tabpnj_itemfinal.add(itemfinal);
		
		tabpnj_item1_nom.add(item1);
		tabpnj_item1_nb.add(nb1);
		
		tabpnj_item2_nom.add(item2);
		tabpnj_item2_nb.add(nb2);
		
	}
	
	public void addSlotShop_rand() {
		Random r1 = new Random();
		int nb1 = r1.nextInt(10+0) + 0;
		
		
		int nb2 = r1.nextInt(10+0) + 0;
		
		//Random r2 = new Random();
		
		tabpnj_itemfinal.add(getrandItem());
		
		tabpnj_item1_nom.add(getrandItem());
		tabpnj_item1_nb.add(nb1);
		
		tabpnj_item2_nom.add(getrandItem());
		tabpnj_item2_nb.add(nb2);
		
		}
	
	public int getX(int i) {
		if(tabpnj_x.size()!=0)
		return tabpnj_x.get(i);
		return 1;
	}
	public int getY(int i) {
		if(tabpnj_y.size()!=0)
		return tabpnj_y.get(i);
		return 1;
	}
	
	public int getTabSize() {
		return tabpnj_itemfinal.size();
	}
	
	public int getTabNbPNJ() {
		return tabpnj_x.size();
	}
	
	public String gettabpnj_itemfinal(int i) {
		return tabpnj_itemfinal.get(i);
	}
	public String gettabpnj_item1_nom(int i) {
		return tabpnj_item1_nom.get(i);
	}
	public String gettabpnj_item2_nom(int i) {
		return tabpnj_item2_nom.get(i);
	}
	
	public int gettabpnj_item1_nb(int i) {
		return tabpnj_item1_nb.get(i);
	}
	public int gettabpnj_item2_nb(int i) {
		return tabpnj_item2_nb.get(i);
	}
	
	public void render(Graphics2D g2) {
		for(int i = 0;i<tabpnj_image.size();i++) {
			BufferedImage image_pnj = null;
			image_pnj = tabpnj_image.get(i);
			g2.drawImage(image_pnj,tabpnj_x.get(i),tabpnj_y.get(i),gamestate);
		}
	}
	public void update() {
	
	}

	private String getrandItem() {
		Random random = new Random();
		int value = random.nextInt(5 + 0) + 0;
		
		switch(value) {
		case(0):
			return "apple";
		case(1):
			return "cobblestone";
		case(2):
			return "gold";
		case(3):
			return "stick";
		case(4):
			return "golden_apple";
		
		}
		return null;
		
		
	}
	
	public String getPnjtype(int i) {
		if(tabpnj_image.get(i) == image_wizard)
			return "wizard";
		if(tabpnj_image.get(i) == image_pnjfin)
			return "pnjfin";
		return "null";
	}
	
	
	
	public void clear() {
		tabpnj_image.clear();
		tabpnj_x.clear();
		tabpnj_y.clear();
	}
	
	public void clear_shop() {
		tabpnj_item1_nom.clear();
		tabpnj_item2_nom.clear();
		
		tabpnj_item1_nb.clear();
		tabpnj_item2_nb.clear();
		tabpnj_itemfinal.clear();
		
	}
}


