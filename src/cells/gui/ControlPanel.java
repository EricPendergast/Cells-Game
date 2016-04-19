package cells.gui;

import input.Keyboard;
import input.Mouse;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import cells.VirusCluster;
import nodes.Node;
public class ControlPanel extends Node{
	String[] buttons;
	int buttonWidth = 200;
	int buttonHeight = 50;
	
	Font font = new Font("Impact", Font.BOLD, 30);
	
	boolean ignoreReselect = false;
	boolean isVirusSelected = false;
	public ControlPanel(Rectangle hitbox){
		super.hitbox = hitbox;
		buttons = new String[5];
		buttons[0] = "Move-Q";
		buttons[1] = "Split-W";	
		buttons[2] = "Combine-E";
		buttons[3] = "Show All-Z";
		buttons[4] = "Pause-P";
	}
	public void drawOn(Graphics2D g2) {
		g2.setFont(font);
		g2.setColor(Color.decode("0x36BF32"));
		g2.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		for(int i = 0; i < buttons.length; i++){
			g2.setColor(Color.decode(("0xBB32BF")));
			g2.fillRoundRect(hitbox.x, hitbox.y+i*buttonHeight + 10*i, buttonWidth, buttonHeight,25,25);
			g2.setColor(Color.WHITE);
			if(buttons[i] != null)
				g2.drawString(buttons[i], hitbox.x+10, i*(10 + buttonHeight)+36);
		}
	}
	//Whether or not the user was holding a key the last tick
	boolean prevKey = false;
	boolean prevKey2 = false;
	public void update(Boolean onClick) {
		if(!prevKey2 & (prevKey2 = Keyboard.keys[KeyEvent.VK_P])){
			boolean start = true;
			boolean last = true;
			boolean searching = false;
			while(true){
				start = Keyboard.keys[KeyEvent.VK_P];
				if(!searching){
					if(start != last)
						searching = true;
				}else{
					if(!last && start)
						break;
				}
				
				last = start;
				try{Thread.sleep(1);}catch(Exception e){}
			}
		}
		
		if(Keyboard.keys[KeyEvent.VK_Z])
			VirusCluster.highlight = true;
		else
			VirusCluster.highlight = false;
		if(!VirusCluster.isVirusSelected)
			return;
		VirusCluster.split = false;
		ignoreReselect = false;
		if(onClick != null){
			if(VirusCluster.move || VirusCluster.combine){
				ignoreReselect = true;
			}
		}
		if(!isVirusSelected){
			VirusCluster.move = false;
			VirusCluster.combine = false;
			VirusCluster.split = false;
			ignoreReselect = false;
		}
		if(onClick != null && onClick && contains(Mouse.button1At)){
			
			for(int i = 0; i < buttons.length; i++){
				Rectangle hit = new Rectangle(hitbox.x, hitbox.y+i*buttonHeight + 10*i, buttonWidth, buttonHeight);
				if(hit.contains(Mouse.button1At)){
//					if(i == 0){
//						VirusCluster.move = true;
//					}
//					if(i == 1){
//						VirusCluster.split = true;
//					}
//					if(i == 2){
//						VirusCluster.combine = true;
//					}
					
//					if(i == 9){
//						VirusCluster.increment = true;
//					}
				}
			}
		}
		if(Keyboard.keys[KeyEvent.VK_Q])
			VirusCluster.move = true;
		//The single & is so that both sides are executed
		//the prevKey variable makes sure that the user can't hold down the key and repeatedly split
		if(!prevKey & (prevKey = Keyboard.keys[KeyEvent.VK_W])){
			VirusCluster.split = true;
		}
		
		if(Keyboard.keys[KeyEvent.VK_E])
			VirusCluster.combine = true;
		
		VirusCluster.isVirusSelected = false;
	}
	public boolean contains(Point p){
		return hitbox.contains(p);
	}
	public boolean ignoreReselect(){
		return ignoreReselect;
	}
	public void setVirusSelected(boolean b){
		isVirusSelected = b;
	}
}
