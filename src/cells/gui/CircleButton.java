package cells.gui;

import input.Mouse;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import nodes.Node;

public class CircleButton extends Node{
	int radius;
	Point loc;
	Color color;
	
	String text;
	Point textLoc;
	Color textColor;
	Font font;
	
	boolean selected = false;
	
	boolean clicked = false;
	public CircleButton(Point l, int r, Color c){
		radius = r;
		loc = l;
		color = c;
	}
	public CircleButton(Point l, int r, Color c, String text, Point textLoc, Color textColor, Font font){
		this(l,r,c);
		this.text = text;
		this.textLoc = textLoc;
		this.textColor = textColor;
		this.font = font;
	}
	public void drawOn(Graphics2D g2) {
		g2.setColor(color);
		g2.fillOval(loc.x-radius,loc.y-radius,radius*2,radius*2);
		if(text != null){
			g2.setColor(textColor);
			g2.setFont(font);
			g2.drawString(text, textLoc.x, textLoc.y);
		}
	}
	
	public void update(Boolean onClick) {
		clicked = false;
		if(onClick != null && onClick){
			int xdiff = Mouse.button1At.x-loc.x;
			int ydiff = Mouse.button1At.y-loc.y;
			double dist = Math.sqrt(xdiff*xdiff+ydiff*ydiff);
			if(dist < radius){
				clicked = true;
			}
		}
	}
	public boolean isClicked(){
		return clicked;
	}
}
