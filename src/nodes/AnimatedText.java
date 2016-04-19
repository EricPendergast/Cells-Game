package nodes;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class AnimatedText extends Node {
	String text;
	String partText = "";
	boolean running = false;
	int speed = 1;
	int counter = 0;
	Font font;
	public AnimatedText(String text, int x, int y){
		this.text = text;
		hitbox = new Rectangle(x,y,0,0);
		font = new Font("Verdana",Font.BOLD,12);
	}
	public AnimatedText(String text, int x, int y, int speed){
		this.text = text;
		hitbox = new Rectangle(x,y,0,0);
		font = new Font("Verdana",Font.BOLD,12);

	}
	public AnimatedText(String text, int x, int y, int speed, Font font){
		this.text = text;
		hitbox = new Rectangle(x,y,0,0);
		this.font = font;
	}
	public void drawOn(Graphics2D g2) {
		g2.setFont(font);
		g2.drawString(partText, hitbox.x, hitbox.y);
	}

	public void update(Boolean onClick) {
		if(running){
			if(partText.length() == text.length())
				return;
			counter++;
			if(counter == speed){
				counter = 0;
				partText += text.charAt(partText.length());
			}
		}
	}
	
	public void start(){
		running = true;
	}
}
