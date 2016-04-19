package cells;

import java.awt.Color;
import java.awt.Graphics2D;

import cells.enemyCells.RedBloodCell;

public class GoldCell extends RedBloodCell implements HasRadius, Infectable{
	double radius;
	boolean infected;
	
	VirusCluster closest;
	public GoldCell(double x, double y, VeinMarker origin, double radius){
		super(x,y, origin);
		this.radius = radius;
		infected = false;
	}
	
	int counter = 0;
	public void update(){
		if(radius < 5)
			die();
		counter++;
		if(counter % 1 == 0 && isInfected()){
			if(closest != null && distance(closest) < 100+closest.getRadius() + getRadius()){
				closest.add(new Virus(0,0));
				double area = Math.PI*radius*radius;
				area -= 100;
				radius = Math.sqrt(area/Math.PI);
			}
		}
		if(!isInfected()){
			super.update();
		}else{
			limitSpeed(4);
			x += dx;
			y += dy;
		}
	}
	public void drawSelfOn(Graphics2D g2){
		if(!isInfected()){
			g2.setColor(new Color(0xE0C828));
			g2.fillOval((int)(x-radius),(int)(y-radius),(int)(radius*2),(int)(radius*2));
			g2.setColor(new Color(0xFCE33D));
			g2.fillOval((int)(x-radius*.7),(int)(y-radius*.7),(int)(radius*.7*2),(int)(radius*.7*2));
		}else{
			g2.setColor(new Color(0x17A320));
			g2.fillOval((int)(x-radius),(int)(y-radius),(int)(radius*2),(int)(radius*2));
			g2.setColor(new Color(0x66D16E));
			g2.fillOval((int)(x-radius*.7),(int)(y-radius*.7),(int)(radius*.7*2),(int)(radius*.7*2));
		}
	}
	public double getRadius(){
		return radius;
	}
	
	public void effect(CellPart p){
		super.effect(p);
		
		if(p instanceof VirusCluster && isInfected()){
			if(closest == null || distanceCompare(closest) > distanceCompare(p)){
				closest = (VirusCluster)p;
			}
		}
		
	}
	public void infect(VirusCluster v){
		if(isInfected() || distance(v) > v.getRadius() + getRadius()+100)
			return;
		if(v.getSize() > 0){
			infected = true;
		}
	}
	
	public boolean isInfected(){
		return infected;
	}
}
