package nodes;

import input.Keyboard;
import input.Mouse;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class NumBox extends Button{
	private int length = 17;
	private String text;
	private boolean lastClicked = false;
	private int def = 0;
	public NumBox(Rectangle hitbox, String imageLoc, String selectedImageLoc) {
		super(hitbox, imageLoc, selectedImageLoc, "");
		text = "";
	}
	public NumBox(Rectangle hitbox, String imageLoc, String selectedImageLoc, String label) {
		super(hitbox, imageLoc, selectedImageLoc, label);
		text = "";
	}
	public void update(Boolean onClick){
		if(onClick != null && onClick == true)
			lastClicked = hitbox.contains(Mouse.button1At);
		if(lastClicked){
			for(int i = KeyEvent.VK_0; i <= KeyEvent.VK_9; i++){
				if(Keyboard.keys[i]){
					{
						//System.out.println(i);
						text += (char)i;
					}
					Keyboard.keys[i] = false;
				}
			}
			
			if(Keyboard.keys[8]){
				if(text.length() > 0)
					text = text.substring(0,text.length()-1);
				Keyboard.keys[8] = false;
			}
		}
	}
	public void drawOn(Graphics2D g2){
		String img = imageLoc;
		if(lastClicked)
			img = selectedImageLoc;
		if(img == null || img.isEmpty())
			return;
		BufferedImage image = getImage(img);
		g2.drawImage(image, hitbox.x,hitbox.y, null);
		int sub = text.length()-length;
		if(sub < 0)
			sub = 0;
		super.drawString(g2," " + textString, 0,-8);
		super.drawString(g2," " + text.substring(sub), Button.LEFT);
	}
	public int getNumber(){
		try{
			if(text.isEmpty())
				return def;
			return Integer.valueOf(text);
		}catch(NullPointerException e){return def;}
	}
	public void setDefault(int d){
		def = d;
	}
	public int getLength(){
		return length;
	}
	public void setLength(int l){
		length = l;
	}
}
