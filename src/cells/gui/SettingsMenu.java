package cells.gui;

import input.Mouse;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import nodes.Button;
import states.NodeState;

public class SettingsMenu extends NodeState{
	BufferedReader r;
	BufferedWriter w;
	
	Rectangle buttonA;
	String strA;
	boolean selectedA;
	public SettingsMenu(BufferedReader reader, BufferedWriter writer){
		w = writer;
		r = reader;
		
		buttonA = new Rectangle(500,500,500,100);
		strA = "Screen Resolution: ";
		selectedA = false;
	}
	
	public void drawOn(Graphics2D g2){
		g2.setColor(Color.decode("0xE86464"));
		if(buttonA.contains(Mouse.button1At))
			g2.setColor(Color.decode("0xC90202"));
		g2.fillRect((int)buttonA.x, (int)buttonA.y, (int)buttonA.width, (int)buttonA.height);
		
		g2.setColor(Color.BLACK);
		g2.drawString(strA + (selectedA ? "Large" : "Small"),buttonA.x,buttonA.y);
	}
	public void update(Boolean onClick){
		if(onClick != null && onClick){
			if(buttonA.contains(Mouse.button1At)){
				selectedA = !selectedA;
				try {
					w.write(selectedA ? 2 : 1);
				} catch (IOException e) {}
			}
		}
	}
}
