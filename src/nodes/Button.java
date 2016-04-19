package nodes;

import input.FileRead;
import input.Mouse;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import dataStructures.HashMap;
import java.awt.image.BufferedImage;

public class Button extends Node{
	protected String imageLoc;
	protected String selectedImageLoc;
	
	protected boolean selected = false;
	public boolean clicked;
	public String textString = "";

	
	public Button(Rectangle hitbox, String loc, String selectedImageLoc, String label){
		this.hitbox = hitbox;
		this.imageLoc = loc;
		this.selectedImageLoc = selectedImageLoc;
		clicked = false;
		textString = label;
	}
	//Called every tick, onClick indicates whether there was an initial click or release
	public void update(Boolean onClick){
		if(hitbox.contains(new Point(Mouse.button1At.x,Mouse.button1At.y))){
			selected = true;
		}else{
			selected = false;
		}
		if(onClick != null && onClick == true)
			clicked = true;
	}
	//Draws itself on a graphics object
	public void drawOn(Graphics2D g2){

		String img;
		if(selected && !selectedImageLoc.equals("")){
			img = selectedImageLoc;
		}else{
			img = imageLoc;
		}

		BufferedImage image = getImage(img);
		g2.drawImage(image,hitbox.x,hitbox.y,null);
		drawString(g2,textString,Node.CENTER);
	}
	
}
