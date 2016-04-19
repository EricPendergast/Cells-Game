package nodes;

import input.FileRead;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dataStructures.HashMap;

public abstract class Node {
	private static HashMap<String,BufferedImage> nodeImages = new HashMap<String,BufferedImage>();
	private static BufferedImage[][] text = new BufferedImage[16][6];

	protected Rectangle hitbox;
	
	protected static final int CENTER = 1;
	protected static final int LEFT = 0;
	
	public Node(){
		hitbox = new Rectangle(0,0,0,0);
	}
	public abstract void drawOn(Graphics2D g2);
	
	public abstract void update(Boolean onClick);
	//Retrieves a BufferedImage from buttonImages, or puts it in the hashmap if it doesn't exist yet
	protected BufferedImage getImage(String img){

		BufferedImage image = nodeImages.get(img);
		if(image == null){
			try{
				image = FileRead.getImage(img);
				
				image = image.getSubimage(0,0,hitbox.width,hitbox.height);
				nodeImages.put(img,image);
			}catch(java.awt.image.RasterFormatException e){System.out.println("Raster format exception");return null;}
			catch(NullPointerException e){e.printStackTrace();System.out.println("NullPointer");return null;}
		}
		return image;
	}
	
	//Draws the given string relative to the button location (in the center, or on the left)
	protected void drawString(Graphics2D g2, String string, int position){
		Point textDrawLoc;
		if(position == Button.CENTER)
			textDrawLoc = new Point(	(int)(hitbox.getCenterX()-(string.length()/2.0)*6),
										(int)hitbox.getCenterY()-4);
		else
			textDrawLoc = new Point(	(int)(hitbox.x),
										(int)hitbox.getCenterY()-4);
		
		for(int i = 0; i < string.length(); i++){
			char c = string.charAt(i);
			
			int textureIndex = (int)c - 32;
			Point tileSheetLoc = new Point(textureIndex%16,textureIndex/16);
			BufferedImage image = text[tileSheetLoc.x][tileSheetLoc.y];
			
			if(image == null){
				try{
					image = FileRead.getImage("/font.png");
					image = image.getSubimage(	(int)(tileSheetLoc.x*8), 
												(int)(tileSheetLoc.y*8), 8, 8);
					//text.put(tileSheetLoc, image);
					text[tileSheetLoc.x][tileSheetLoc.y] = image;
				}catch(java.awt.image.RasterFormatException e){
					return;
				}
				catch(NullPointerException e){return;}
			}
			g2.drawImage(image, textDrawLoc.x, textDrawLoc.y, null);
			textDrawLoc.x += 6;
			if(c == 'l'){
				textDrawLoc.x -= 3;
			}else if(c == 'i' || c == '!' || c == ',' || c == '.' || c == '|'){
				textDrawLoc.x -= 4;
			}else if(c == 't'){
				textDrawLoc.x -= 2;
			}else if(c == '@'){
				textDrawLoc.x += 1;
			}
		}
	}
	protected void drawString(Graphics2D g2, String string, int x, int y){
		Point textDrawLoc;
		textDrawLoc = new Point(hitbox.x+x,hitbox.y+y);
		
		for(int i = 0; i < string.length(); i++){
			char c = string.charAt(i);
			
			int textureIndex = (int)c - 32;
			Point tileSheetLoc = new Point(textureIndex%16,textureIndex/16);
			
			BufferedImage image = text[tileSheetLoc.x][tileSheetLoc.y];
			
			if(image == null){
				try{
					image = FileRead.getImage("/font.png");
					image = image.getSubimage(	(int)(tileSheetLoc.x*8), 
												(int)(tileSheetLoc.y*8), 8, 8);
					text[tileSheetLoc.x][tileSheetLoc.y] = image;
				}catch(java.awt.image.RasterFormatException e){
					return;
				}
				catch(NullPointerException e){return;}
			}
			g2.drawImage(image, textDrawLoc.x, textDrawLoc.y, null);
			textDrawLoc.x += 6;
			if(c == 'l'){
				textDrawLoc.x -= 3;
			}else if(c == 'i' || c == '!' || c == ',' || c == '.' || c == '|'){
				textDrawLoc.x -= 4;
			}else if(c == 't'){
				textDrawLoc.x -= 2;
			}else if(c == '@'){
				textDrawLoc.x += 1;
			}
		}
	}
	public static void addImageOverride(String imageName, String fileLoc, int x, int y, int width, int height){
		try{
			BufferedImage image = FileRead.getImage(fileLoc);
			image = image.getSubimage(x,y,width,height);
			nodeImages.put(imageName,image);
		}catch(java.awt.image.RasterFormatException e){}
		catch(NullPointerException e){}
	}
	public static void addImageOverride(String imageName, String fileLoc, Rectangle r){
		addImageOverride(imageName, fileLoc, r.x, r.y, r.width, r.height);
	}
	public static void addImage(String imageName, String fileLoc, int x, int y, int width, int height){
		if(nodeImages.get(imageName) != null)
			return;
		addImageOverride(imageName, fileLoc, x, y, width, height);
	}
	public static void addImage(String imageName, String fileLoc, Rectangle r){
		if(nodeImages.get(imageName) != null)
			return;
		addImageOverride(imageName, fileLoc, r);
	}
	public Rectangle getHitbox(){
		return hitbox;
	}
}
