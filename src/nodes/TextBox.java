package nodes;

import input.Keyboard;
import input.Mouse;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class TextBox extends Button{
	private int length = 17;
	private String text;
	private boolean lastClicked = false;
	public TextBox(Rectangle hitbox, String imageLoc, String selectedImageLoc) {
		super(hitbox, imageLoc, selectedImageLoc, "");
		text = "";
	}
	public TextBox(Rectangle hitbox, String imageLoc, String selectedImageLoc, String label) {
		super(hitbox, imageLoc, selectedImageLoc, label);
		text = "";
	}
	public void update(Boolean onClick){
		if(onClick != null && onClick == true)
			lastClicked = hitbox.contains(Mouse.button1At);
		if(lastClicked){
			for(int i = 32; i < 32+96; i++){
				if(Keyboard.keys[i]){
					if(Keyboard.keys[KeyEvent.VK_SHIFT]){
						if(i >= KeyEvent.VK_A && i <= KeyEvent.VK_Z){
							text += (char)i;
						}else{
							switch(i){
							case KeyEvent.VK_0:
								text += ')';
								break;
							case KeyEvent.VK_1:
								text += '!';
								break;
							case KeyEvent.VK_2:
								text += '@';
								break;
							case KeyEvent.VK_3:
								text += '#';
								break;
							case KeyEvent.VK_4:
								text += '$';
								break;
							case KeyEvent.VK_5:
								text += '%';
								break;
							case KeyEvent.VK_6:
								text += '^';
								break;
							case KeyEvent.VK_7:
								text += '&';
								break;
							case KeyEvent.VK_8:
								text += '*';
								break;
							case KeyEvent.VK_9:
								text += '(';
								break;
							case 92:
								text += '|';
								break;
							case 91:
								text += '{';
								break;
							case 93:
								text += '}';
								break;
							}
						}
					}else{
						if(i >= KeyEvent.VK_A && i <= KeyEvent.VK_Z){
							text += (char)(i+32);
						}else if(i >= KeyEvent.VK_0 && i <= KeyEvent.VK_9){
							text += (char)i;
						}else{
							text += (char)i;
						}
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
		int sub = text.length()-17;
		if(sub < 0)
			sub = 0;
		super.drawString(g2," " + textString, 0,-8);
		super.drawString(g2," " + text.substring(sub), Button.LEFT);
	}
	public String getText(){
		return text;
	}
	public int getLength(){
		return length;
	}
	public void setLength(int l){
		length = l;
	}
}
