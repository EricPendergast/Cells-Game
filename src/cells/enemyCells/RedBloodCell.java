package cells.enemyCells;

import java.awt.Color;
import java.awt.Graphics2D;

import cells.*;

public class RedBloodCell extends CellPart implements HasRadius, Infectable{
	double radius = 50;
	VeinMarker next;
	boolean infected = false;
	public RedBloodCell(double x, double y, VeinMarker origin){
		super(x,y);
		next = origin;
	}
	public void update(){
		if(next != null)
			moveToward(next.getPoint(),.2,0);
		if(next != null && distance(next) < 50){
			if(next.numBranches() > 0){
			int index = (int)(Math.random()*next.numBranches());
			next = next.getBranch(index);
			}else{
				die();
			}
		}
		if(!infected)
			limitSpeed(5);
		else
			limitSpeed(4);
		super.update();
	}
	public void drawSelfOn(Graphics2D g2){
		g2.setColor(new Color(0xBD0000));
		g2.fillOval((int)(x-radius),(int)(y-radius),(int)(radius*2),(int)(radius*2));
		g2.setColor(new Color(0xD64040));
		g2.fillOval((int)(x-radius*.7),(int)(y-radius*.7),(int)(radius*.7*2),(int)(radius*.7*2));
		
		if(infected){
			g2.setColor(new Color(0x2EB337));
			g2.fillOval((int)(x-radius*.3),(int)(y-radius*.3),(int)(radius*.3*2),(int)(radius*.3*2));
		}
	}
	public double getRadius() {
		return radius;
	}
	
	public void infect(VirusCluster v) {
		if(!infected && distance(v) < getRadius() + v.getRadius() + 20){
			infected = true;
			for(int i = 0; i < 5; i++)
				v.add(new Virus(0,0));
		}
	}
	public boolean isInfected() {
		return infected;
	}
	
}
