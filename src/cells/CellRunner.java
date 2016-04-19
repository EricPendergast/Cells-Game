package cells;

import input.Keyboard;
import input.Mouse;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import cells.gui.ControlPanel;
import nodes.Node;
//Has an array of CellParts and makes them interact with each other, also renders them
public class CellRunner extends Node{
	public static boolean win = false;
	public static boolean lose = false;
	public static cells.Point mousePosInWorld = new cells.Point();
	//Screen position
	private Rectangle screen;
	private double dx, dy;
	CellRegion[][] cellParts;
	//0 if close, 1 if far
	int renderMode = 0;
	double zoomOutScale = 5;
	double zoomInScale = 2;
	double zoomInSuperScale = 1;
	double scale = zoomInSuperScale;
//	Rectangle selectBox;
//	Rectangle sbAnimate;
	
	ControlPanel controler;
	
	boolean debug = false;
	public CellRunner(Rectangle hitbox, Generator generator, ControlPanel controler){
		super.hitbox = hitbox;
		screen = new Rectangle(0,0, hitbox.width, hitbox.height);
		cellParts = new CellRegion[20][20];
		int chunksize = 1000;
		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 20; j++){
				cellParts[i][j] = new CellRegion(i,j,chunksize);
			}
		}
		for(CellRegion[] arr : cellParts)
			for(CellRegion p : arr)
				p.findPartners(cellParts);
		
		cells.Point p = generator.generate(cellParts);
		
		screen.x = (int)p.x;
		screen.y = (int)p.y;
		
		this.controler = controler;
	}
	long curr;
	public void drawOn(Graphics2D g2) {
		//Makes the screen limited to a certain area
		if(screen.x-screen.width/2*scale < 4000)
			screen.x = 4000 + (int)(screen.width/2*scale);
		if(screen.y-screen.height/2*scale < 4000)
			screen.y = 4000 + (int)(screen.height/2*scale);
		if(screen.x+(screen.width)/2*scale > 18340)
			screen.x = 18340 - (int)((screen.width)/2*scale);
		if(screen.y+screen.height/2*scale > 16331)
			screen.y = 16331 - (int)(screen.height/2*scale);
		////
		
		g2.setColor(Color.decode("0xF54747"));
		g2.fillRect(0,0,hitbox.width,hitbox.height);
		//////For the purpose of displaying framerate and number of total cells
		if(debug){
			int siz = 0;
			for(CellRegion[] arr : cellParts)
				for(CellRegion r : arr)
					siz += r.size();
			g2.drawString((System.currentTimeMillis()-curr) + "", 0, 12);
			g2.drawString(siz + "", 0, 24);
			curr = System.currentTimeMillis();
		}
		//////
		
		//////Moves the graphics object before drawing cells onto it
		g2.scale(1/scale, 1/scale);
		g2.translate(-screen.x+screen.width/2*scale,-screen.y+screen.height/2*scale);
		//////
		
		//////Draws all the cells
		for(CellRegion[] arr : cellParts)
			for(CellRegion r : arr)
				r.drawSelfOn(g2);
		//////
		//This is for the selection box 
//		if(selectBox != null){
//			Rectangle tempBox = new Rectangle(selectBox);
//			if(tempBox.width < 0){
//				tempBox.x += tempBox.width;
//				tempBox.width *= -1;
//			}
//			if(tempBox.height < 0){
//				tempBox.y += tempBox.height;
//				tempBox.height *= -1;
//			}
//			g2.drawRect(tempBox.x,tempBox.y,tempBox.width,tempBox.height);
//		}
		//moves graphics back
		g2.translate(screen.x-screen.width/2*scale,screen.y-screen.height/2*scale);
		g2.scale(scale, scale);
		
		controler.drawOn(g2);
		
		if(win){
			g2.setColor(new Color(0,0,0,0x88));
			g2.setFont(new Font("Impact",Font.PLAIN,160));
			g2.drawString("YOU WIN!!!", 240, 420);
		}else if(lose){
			g2.setColor(new Color(0,0,0));
			g2.setFont(new Font("Impact",Font.PLAIN,160));
			g2.drawString("YOU LOSE", 240, 420);
		}
	}
	
	
	//Where the user originally pressed
	Point origin = new Point(0,0);
	Point last = Mouse.button1At;
	
	public void update(Boolean onClick){
		VirusCluster.doesVirusExist = false;
		
		controler.update(onClick);
		if(onClick != null && controler.contains(Mouse.button1At))
			return;
		
		//update the mouse position for use by VirusClusters
		mousePosInWorld = getClickLocCellsPoint(Mouse.button1At);
		
		long start = System.currentTimeMillis();
		//////Updates the cells
		if(renderMode == 0){//fancy updates all the cells nearby if close up rendering
			//int scale = 2;
			Rectangle extendedScreen = new Rectangle((int)(screen.x-screen.width*scale),(int)(screen.y-screen.height*scale),(int)(screen.width*3*scale),(int)(screen.height*3*scale));
			for(CellRegion[] arr : cellParts)
				for(CellRegion r : arr)
					r.update(extendedScreen);
		}else if(renderMode == 1){//optimized updates all the cells if far away rendering
			Rectangle rect = new Rectangle(-5000,-5000,0,0);
			for(CellRegion[] arr : cellParts)
				for(CellRegion r : arr)
					r.update(rect);
		}
		//////
		
		//////Allows the user to move the screen with right click
		if(Mouse.button2Pressed){
			dx = (Mouse.button1At.x-last.x)*scale;
			dy = (Mouse.button1At.y-last.y)*scale;
		}
		screen.x -= (int)dx;
		screen.y -= (int)dy;
		dx*=.90;
		dy*=.90;
		last = new Point(Mouse.button1At);
		//////
		
		//////Changing zoom
		if(Keyboard.keys[KeyEvent.VK_1]){
			scale = zoomOutScale;
			renderMode = 1;
		}
		else if(Keyboard.keys[KeyEvent.VK_2]){
			scale = zoomInScale;
			renderMode = 0;
		}else if(Keyboard.keys[KeyEvent.VK_3]){
			scale = zoomInSuperScale;
			renderMode = 0;
		}
		//////
		
		//////placing cells, debugging purposes
		if(onClick != null && debug){
			if(Keyboard.keys[KeyEvent.VK_SHIFT]){
				if(onClick){
					Point pos = getClickLoc(Mouse.button1At);
					//WallCell w = new WallCell(pos.x-x,pos.y-y,60);
					Virus w = new Virus(pos.x,pos.y);
					for(CellRegion[] arr : cellParts){
						for(CellRegion r : arr)
							if(r.contains(w))
								r.add(w);
					}
				}
			}else 
			if(Keyboard.keys[KeyEvent.VK_CONTROL]){
				if(onClick){
					Point pos = getClickLoc(Mouse.button1At);
					//WallCell w = new WallCell(pos.x-x,pos.y-y,60);
					WallCell w = new WallCell(pos.x,pos.y,40);
					for(CellRegion[] arr : cellParts){
						for(CellRegion r : arr)
							if(r.contains(w))
								r.add(w);
					}
				}
			}
			else if(onClick){
				origin = getClickLoc(Mouse.button1At);
			}
			else{//////Selecting Cells
				double s = .1;
				Point p = getClickLoc(Mouse.button1At);
				Nucleus n = new Nucleus(origin.x,origin.y,20,(p.x-origin.x)*s,(p.y-origin.y)*s);
				cellParts[(int)(n.x/1000)][(int)(n.y/1000)].add(n);
			}//////
		}
		if(onClick != null && onClick && !controler.ignoreReselect()){
			ArrayList<VirusCluster> partsSelected = new ArrayList<VirusCluster>();
			for(CellRegion[] arr : cellParts){
				for(CellRegion r : arr){
					VirusCluster p = r.getNearest(getClickLocCellsPoint(Mouse.button1At));
					if(p != null)
						partsSelected.add(p);
				}
			}
			
			VirusCluster nearest = null;
			double smallestDist = 1000*1000;
			for(VirusCluster v : partsSelected){
				double dist = cells.Point.distanceCompare(getClickLocCellsPoint(Mouse.button1At), v.getPoint());
				if(dist < smallestDist){
					nearest = v;
					smallestDist = dist;
				}
			}
			
			if(nearest != null){
				nearest.isSelected = true;
				controler.setVirusSelected(true);
			}else{
				controler.setVirusSelected(false);
			}
			
		}
		//////
		
		if(!VirusCluster.doesVirusExist)
			lose = true;
		//////Update the selectBox
//		if(Mouse.button1Pressed && selectBox != null){
//			Point p = getClickLoc(Mouse.button1At);
//			//selectBox = new Rectangle(origin.x,origin.y,p.x-origin.x,p.y-origin.y);
//			selectBox.width = p.x-selectBox.x;
//			selectBox.height = p.y-selectBox.y;
//		}
		//////
	}
	public cells.Point getClickLocCellsPoint(Point p){
		return new cells.Point((int)(p.x*scale+screen.x-screen.width/2*scale),  (int)(p.y*scale+screen.y-screen.height/2*scale));
	}
	public Point getClickLoc(Point p){
		return new Point((int)(p.x*scale+screen.x-screen.width/2*scale),  (int)(p.y*scale+screen.y-screen.height/2*scale));
	}
	public void setDebug(boolean b){
		debug = b;
	}
	public Point getLocation(){
		return new Point(screen.x,screen.y);
	}
	public void setLocation(int x, int y){
		screen.x = x;
		screen.y = y;
	}
}
