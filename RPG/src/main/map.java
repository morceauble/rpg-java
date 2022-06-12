package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class map extends JPanel {
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3798509234370641338L;
	GameState gs;
	

	private String nomfichier;
	private String pathmap;
	private static int Y = 0;
	static int compteurinit = 0;
	
	private static int x_door = 0;
	private static int y_door = 0;
	public static int ordre = 0;
	
	public Boolean fin;
	
	Boolean ischangingmap = false;

	static ArrayList<ArrayList<String>> tabString_all = new ArrayList<ArrayList<String>>();
    static ArrayList<ArrayList<String>> tabString_back = new ArrayList<ArrayList<String>>();
    static ArrayList<ArrayList<String>> tabString_mid = new ArrayList<ArrayList<String>>();
    static ArrayList<ArrayList<String>> tabString_mid_coll = new ArrayList<ArrayList<String>>();
    static ArrayList<ArrayList<String>> tabString_mid_inte = new ArrayList<ArrayList<String>>();
    static ArrayList<ArrayList<String>> tabString_top = new ArrayList<ArrayList<String>>();
    
    static ArrayList<Integer> tabTaille = new ArrayList<Integer>();
    static ArrayList<String> inittab = new ArrayList<String>();
    static ArrayList<Boolean> tabfirstime = new ArrayList<Boolean>();
    
    BufferedImage dirt;
    BufferedImage oak_sapling;
    BufferedImage stone;
    BufferedImage wall;
    BufferedImage grass_flowerpink;
    BufferedImage oak_door_top;
    BufferedImage oak_door_bottom;
    BufferedImage oak_planks;
    BufferedImage acacia_sapling;
    BufferedImage acacia_log;
    BufferedImage azalea_leaves;
    BufferedImage chest;
    BufferedImage bed_head;
    BufferedImage bed_tail;
	
   
    
    public void setOrdre(int value) {
    	if(value == 0)
    		ordre = 0;
    	else
    	ordre+=value;
    }
    
    public int getOrdre() {
    	return ordre;
    }
    
    
   
    public map(GameState gamestate) {
    	this.gs = gamestate;
    }
//test
	public map(GameState gamestate,String nf){
		if(firstime(nf)){
		inittab.add(nf);
		
		nomfichier = nf;
		tabfirstime.add(true);
		
		
		}
		else { 
			nomfichier = nf + "edit";
		}
		fin = false;
		
		
		this.gs = gamestate;
		
		
		pathmap = "/res/txt/"+nomfichier+".txt";
		System.out.print("pathmap : " + pathmap + '\n');
		
		
		for(int i = 0;i<inittab.size();i++)
		System.out.println(" inittab : " + inittab.get(i));
		
		for(int i = 0;i<tabfirstime.size();i++)
			System.out.print(" tabfirsttime : " + tabfirstime.get(i) + "/ ");
		System.out.println("tabfirstimesize : " + tabfirstime.size() + '\n');

		try {
			
			initTextures();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			initTextMap(pathmap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initMap();
		
	}
	//regarde si c'est la premiere fois qu'on lit le fichier correspondant, si c'est la première fois, alors copié dans le fichier edit//	
	//Sinon fichier edit colle dans fichier edit(fichier init est une mémoire et edit une lecture des modifs liés au niveau).
	 private Boolean firstime(String value) {
	    	System.out.print("initabsize: " + inittab.size());
	    	if(inittab.size()!=0) {
	    	for(int i = 0;i<inittab.size();i++) {
	    		if(value.compareTo(inittab.get(i)) == 0) {

	    			return false;
	    		}
	    	}
	    	}
	    	return true;
	    }
	
	private void initTextMap(String path) throws IOException {
		String basePath = new File("").getAbsolutePath();
		File file = new File(basePath + path);
		
		System.out.println(file);
		
		 //permet de voir si c'est la derniere map dispo ou pas, si c'est la
		//derniere, aller à la map de fin//
		try {
		 InputStream in = new FileInputStream(file);
		}
		catch(FileNotFoundException error) {
			file = new File(basePath +  "/res/txt/mapFINALE.txt");
			fin = true;
			System.out.println("FIN : " + fin);
		}
		 
		InputStream in = new FileInputStream(file);
         Reader reader = new InputStreamReader(in);

         int c;
         ArrayList<String> v2 = new ArrayList<String>();
         while ((c = reader.read()) != -1) {
        	 if(c == '\n') {
        		 
        		 tabString_all.add(v2);
        		 tabTaille.add(v2.size());
        		 v2 = new ArrayList<String>(v2);
        		 System.out.print(" Y :" + Y + "tabString_all" + tabString_all.get(Y) + '\n');
        		 Y++;
        		 
        		 v2.clear();
        	 }
        	 else {
        	 String s = Character.toString(c);
        	 v2.add(s);
        	 
        	 
         }
     }

         
         tabTaille.add(v2.size());
         tabString_all.add(v2);
         
         reader.close();
         for(int i = 0;i<tabTaille.size();i++) {
        	 System.out.print(" i : " + i + " tabtaille " + tabTaille.get(i) + " / ");
         }
         System.out.print('\n');
}

	private void initTextures() throws IOException {
		
		System.out.print("initTextures" + '\n');
		String maptexture = "/res/img/maptexture/";
		String basePath = new File("").getAbsolutePath();
		
		dirt = ImageIO.read(new File(basePath + maptexture + "dirt.png"));
			grass_flowerpink = ImageIO.read(new File(basePath + maptexture + "grass_flowerpink.png"));
		oak_sapling = ImageIO.read(new File(basePath + maptexture + "oak_sapling.png"));
		acacia_sapling = ImageIO.read(new File(basePath + maptexture + "acacia_sapling.png"));
		stone = ImageIO.read(new File(basePath + maptexture + "stone.png"));
		wall = ImageIO.read(new File(basePath + maptexture + "wall.png"));
		oak_door_top =ImageIO.read(new File(basePath + maptexture + "oak_door_top.png"));
		oak_door_bottom =ImageIO.read(new File(basePath + maptexture + "oak_door_bottom.png"));
		oak_planks = ImageIO.read(new File(basePath + maptexture + "oak_planks.png"));
		acacia_log = ImageIO.read(new File(basePath + maptexture + "acacia_log.png"));	
		azalea_leaves = ImageIO.read(new File(basePath + maptexture + "azalea_leaves.png"));	
		chest = ImageIO.read(new File(basePath + maptexture + "chest.png"));	
		bed_head = ImageIO.read(new File(basePath + maptexture + "bed_head.png"));	
		bed_tail = ImageIO.read(new File(basePath + maptexture + "bed_tail.png"));	
		}
	

	
	private void initMap() {
		    	
		    	//Créer 3 Arraylist, car si en on change un, ça le change aussi dans les autres tableaux(back, mid et top)//
		    	ArrayList<String> v1 = new ArrayList<String>();
		    	ArrayList<String> v2 = new ArrayList<String>();
		    	ArrayList<String> v3 = new ArrayList<String>();
		    	ArrayList<String> v4 = new ArrayList<String>();
		    	ArrayList<String> v5 = new ArrayList<String>();
		    	
		    	//remplir les arraylist//
		    	for(int y = 0;y<=Y;y++) {
		          	for(int x = 0;x<tabTaille.get(y);x++) {
		          	
		          		v1.add("-");
		          		v2.add("-");
		          		v3.add("-");
		          		v4.add("-");
		          		v5.add("-");
		          		
		          	}
		          	tabString_back.add(v1);
		      		tabString_mid.add(v2);
		      		tabString_top.add(v3);
		      		tabString_mid_coll.add(v4);
		      		tabString_mid_inte.add(v5);
		      		v1 = new ArrayList<String>(v1);
		      		v2 = new ArrayList<String>(v2);
		      		v3 = new ArrayList<String>(v3);
		      		v4 = new ArrayList<String>(v4);
		      		v5 = new ArrayList<String>(v5);
		      		
			
		    	v1.clear();
		    	v2.clear();
		    	v3.clear();
		    	v4.clear();
		    	v5.clear();
		    	}
		    	
		    	//REMPLISSAGE//
		    	for(int y = 0;y<=Y;y++) {
		          	for(int x = 0;x<tabTaille.get(y);x++) {
		          		//back//		//Terre,GRASSFLOWERPINK,oakplanks//
		          		if(tabString_all.get(y).get(x).compareTo("T") == 0 
		          				|| tabString_all.get(y).get(x).compareTo("g") == 0 || tabString_all.get(y).get(x).compareTo("p") == 0) {
		          		tabString_back.get(y).set(x, tabString_all.get(y).get(x));
		          		}
		          		else tabString_back.get(y).set(x, " ");
		          	}}
		    	
		    	for(int j = 0;j<=Y;j++) {
		          	for(int i = 0;i<tabTaille.get(j);i++) {
		          		//mid//			
		          		//OakSapling,AccaciSapling,Wall,topporte,bottomporte,accacia_log,chest//
		          		if(tabString_all.get(j).get(i).compareTo("O") == 0 
		          				|| tabString_all.get(j).get(i).compareTo("A") == 0||tabString_all.get(j).get(i).compareTo("W") == 0
		          				|| tabString_all.get(j).get(i).compareTo("1") == 0 || tabString_all.get(j).get(i).compareTo("2") == 0 
		          				|| tabString_all.get(j).get(i).compareTo("L") == 0 ||tabString_all.get(j).get(i).compareTo("C") == 0
		          				||tabString_all.get(j).get(i).compareTo("S") == 0  || tabString_all.get(j).get(i).compareTo("3") == 0	
		          				|| tabString_all.get(j).get(i).compareTo("4") == 0) {
		          			
		              		tabString_mid.get(j).set(i,tabString_all.get(j).get(i));
		              		
		              		
		              		if(tabString_all.get(j).get(i).compareTo("1") == 0) {
		              		
		          			x_door = i;
		          			y_door = j;
		              		}
		          		}
		          		else tabString_mid.get(j).set(i, " ");
		          	}
		    	 }
		    	
		    	//System.out.println(tabString_mid);
		    	
		    	tritabMid();
		    	
		    	//System.out.println(tabString_mid_inte);
		    	for(int j = 0;j<=Y;j++) {
		          	for(int i = 0;i<tabTaille.get(j);i++) {
		          		//TOP//
		          		if(tabString_all.get(j).get(i).compareTo("F") == 0) 
		          			tabString_top.get(j).set(i,tabString_all.get(j).get(i));
		          		
		          		else tabString_top.get(j).set(i, " ");
		          	}
		          }
		    	
		    
		    	System.out.println("FILTRE FINI");
		    	
}
	private void tritabMid() {
		for(int j = 0;j<=Y;j++) {
          	for(int i = 0;i<tabTaille.get(j);i++) {
          		//INTE ET COLL//
          		if(tabString_mid.get(j).get(i).compareTo("O") == 0 ||tabString_mid.get(j).get(i).compareTo("A") == 0
          				||tabString_mid.get(j).get(i).compareTo("C") == 0 || tabString_mid.get(j).get(i).compareTo("-") == 0
          				|| tabString_mid.get(j).get(i).compareTo("3") == 0 ||tabString_mid.get(j).get(i).compareTo("4") == 0 )
          		{
          			tabString_mid_inte.get(j).set(i, tabString_mid.get(j).get(i));	
          			tabString_mid_coll.get(j).set(i, tabString_mid.get(j).get(i));	
          		}
          		//INTE//
          		else if (tabString_mid.get(j).get(i).compareTo("1") == 0 || tabString_mid.get(j).get(i).compareTo("2") == 0
          				|| tabString_mid.get(j).get(i).compareTo("S") == 0) 
          			tabString_mid_inte.get(j).set(i, tabString_mid.get(j).get(i));	
          		
          		//COLL//
          		else if (tabString_mid.get(j).get(i).compareTo("W") == 0 || tabString_mid.get(j).get(i).compareTo("L") == 0) 
          				tabString_mid_coll.get(j).set(i, tabString_mid.get(j).get(i));	
          	}
          } 	
	}

	
	public String gettabString_mid(int y,int x) {
		return tabString_mid.get(y).get(x);
	}
	
	public String gettabString_mid_int(int y,int x){
		
		return tabString_mid_inte.get(y).get(x);
	}
	public String gettabString_mid_coll(int y,int x) {
		return tabString_mid_coll.get(y).get(x);
	}
	
	public String gettabString_top(int y,int x) {
		return tabString_top.get(y).get(x);
	}
	public void settabString_mid(int y,int x,String value) {
		tabString_mid.get(y).set(x, value);
		tritabMid();
	}
	
	public void settabString_mid_int(int y,int x,String value) {
		tabString_mid_inte.get(y).set(x, value);
	}
	public void settabString_mid_coll(int y,int x,String value) {
		tabString_mid_coll.get(y).set(x, value);
	}
	
	public void settabString_top(int y,int x,String value) {
		tabString_top.get(y).set(x, value);
	}
	
	public int gettabTaille(int y) {
		return tabTaille.get(y);
	}
	public int getY() {
		return Y;
	}
	
	public String getPathFile() {
		return pathmap;
	}
	
	public void render(Graphics2D g2) {
		
		
		if(!ischangingmap) {
		
		for(int y = 0;y<=Y;y++) {
          	for(int x = 0;x<tabTaille.get(y);x++) {
          		BufferedImage image = null;
          		
          		if(tabString_back.get(y).get(x).compareTo("T") == 0) 
          			image = dirt;
          		
          		else if (tabString_back.get(y).get(x).compareTo("g") == 0)
          			image = grass_flowerpink;
          		else if (tabString_back.get(y).get(x).compareTo("p") == 0)
          			image = oak_planks;
          		else image = dirt;
          		
          		g2.drawImage(image,x*gs.TileSize, y*gs.TileSize, gs.TileSize, gs.TileSize,this);
          		
          		
          		if(tabString_mid.get(y).get(x).compareTo("W") == 0)
          			image = wall;
          		else if (tabString_mid.get(y).get(x).compareTo("O") == 0)
          			image = oak_sapling;
          		else if (tabString_mid.get(y).get(x).compareTo("A") == 0)
          			image = acacia_sapling;
          		else if (tabString_mid.get(y).get(x).compareTo("1") == 0) 
          			image = oak_door_top;
          		else if (tabString_mid.get(y).get(x).compareTo("2") == 0)
          			image = oak_door_bottom;
          		else if (tabString_mid.get(y).get(x).compareTo("L") == 0)
          			image = acacia_log;
          		else if (tabString_mid.get(y).get(x).compareTo("C") == 0)
          			image = chest;
          		else if (tabString_mid.get(y).get(x).compareTo("S") == 0)
          			image = stone;
          		else if (tabString_mid.get(y).get(x).compareTo("3") == 0) 
          			image = bed_head;
          		else if (tabString_mid.get(y).get(x).compareTo("4") == 0) 
          			image = bed_tail;
          		g2.drawImage(image,x*gs.TileSize, y*gs.TileSize, gs.TileSize, gs.TileSize,this);
		
          		
          	}
          	}}
	}
	
	public void rendertop(Graphics2D g2) {
		if(!ischangingmap) {
		for(int y = 0;y<=Y;y++) {
          	for(int x = 0;x<tabTaille.get(y);x++) {
          		BufferedImage image = null;
		
		
		if (tabString_top.get(y).get(x).compareTo("F") == 0)
  			image = azalea_leaves;
  		
  		g2.drawImage(image,x*gs.TileSize, y*gs.TileSize, gs.TileSize, gs.TileSize,this);
	}}}
	}
	public void update() {
		
	}
	
	private void writefile(String value) throws IOException {
		
		System.out.println("WriteFILE : " + value + "// ");
		String basePath = new File("").getAbsolutePath();
		 FileWriter myWriter = new FileWriter(basePath + value);
		 String v = null;
		 
		 for(int i = 0;i<tabfirstime.size();i++) {
			 if(tabfirstime.get(i)) {
				 tabfirstime.set(i, false);
			 }
			 
		 }
		 
		 
		 ArrayList<ArrayList<String>> taball = new ArrayList<ArrayList<String>>();
		 taball = tabString_all;
		 
		 for(int j = 0;j<=Y;j++) {
			 for(int i=0;i<tabTaille.get(j);i++) {
				 if(tabString_mid.get(j).get(i).compareTo("+") == 0)
					 taball.get(j).set(i,"T");
				 else if((tabString_mid.get(j).get(i).compareTo(" ") == 0) ||(tabString_mid.get(j).get(i).compareTo(Character.toString('\n')) == 0) ) {
					 taball.get(j).set(i,tabString_all.get(j).get(i));
				 }
				 else taball.get(j).set(i, tabString_mid.get(j).get(i));
			 }
		 }
		 
		 for(int j = 0;j<=Y;j++) {
			 for(int i=0;i<tabTaille.get(j);i++) {
				if(tabString_top.get(j).get(i).compareTo("F") == 0) {
					taball.get(j).set(i, "F");
			 }}
			 }
		 
		 
		 System.out.println("tab qui sera ecrit dans edit :" + '\n' + taball);
		 
		 
		 
		 for(int y = 0;y<=Y;y++) {
			 if(y!=0) {
					myWriter.write('\n');
					}
	          	for(int x = 0;x<tabTaille.get(y);x++) {
	          		v = taball.get(y).get(x);
	          			myWriter.write(v);
	          	}
		 }
		 myWriter.close();
	}
	
	//écriture de la map où le joueur est dans un fichier "edit", puis chargement de la map suivante choisie//
	public void effacermap() {
		
		//Si il y a un nouveau fichier//
		if(tabfirstime.size()>compteurinit) {
		if(tabfirstime.get(compteurinit) && !nomfichier.contains("edit")) {
		try {
			System.out.print("EFFACER MAP FIRST TIME : " + nomfichier + '\n');
			
			writefile("/res/txt/"+nomfichier + "edit.txt");
			compteurinit++;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		}
		else {
			try {
				System.out.print(" EFFACER MAP PAS FIRST TIME : " + nomfichier + '\n');
				
				writefile("/res/txt/"+nomfichier + ".txt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		
		tabString_all.clear();
		tabString_back.clear();
		tabString_mid.clear();
		tabString_mid_inte.clear();
		tabString_mid_coll.clear();
		tabString_top.clear();
		tabTaille.clear();
		Y = 0;
	}
	
	public void effacertout() {
		tabString_all.clear();
		tabString_back.clear();
		tabString_mid.clear();
		tabString_mid_inte.clear();
		tabString_mid_coll.clear();
		tabString_top.clear();
		tabTaille.clear();
		Y = 0;
	}
	
	public int getx_door() {
		return x_door;
	}
	public int gety_door() {
		return y_door;
	}
	
	public void ischangingmap(Boolean b) {
		ischangingmap = b;
	}
	
	public void resetMapEdit() {
		inittab.clear();
		compteurinit = 0;
		tabfirstime.clear();
		Y = 0;
	}
}
