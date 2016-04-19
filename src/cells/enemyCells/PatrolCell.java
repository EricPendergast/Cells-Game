package cells.enemyCells;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import cells.*;

public class PatrolCell extends CellPart implements HasRadius{
	double radius = 15;
	ArrayList<Integer> path;
	VeinMarker next;
	boolean goingBack = false;
	
	boolean found = false;
	public PatrolCell(double x, double y, VeinMarker n){
		super(x,y);
		next = n;
		path = new ArrayList<Integer>();
	}
	
	boolean initial = false;
	public void update(){
		if(next == null){
			limitSpeed(5);
			super.update();
			return;
		}
		moveToward(next.getPoint(), .2, 0);
		if(!found)
			limitSpeed(5);
		else
			limitSpeed(4.6);
		super.update();
		
		if(initial){
			initial = false;
			
		}
		if(found)
			goingBack = true;
		
		if(distance(next) < 50){
			
			if(goingBack){
				
				if(next.getParent() == null){
					
				}
				else{
					next = next.getParent();
					if(!found && path.size() > 0)
						path.remove(path.size()-1);
				}
			}else{
				
				if(next.numBranches() == 0){
					goingBack = true;
				}else{
					int index = (int)(Math.random()*next.numBranches());
					
					next = next.getBranch(index);
					path.add(index);
				}
			}
		}
		
	}
	public void effect(CellPart p){
		if(!(p instanceof MasterCell))
			super.effect(p);
		if(p instanceof HasRadius && !(p instanceof MasterCell)){
			repel(p.getPoint(), 100, radius + ((HasRadius)p).getRadius() + 20);
		}
		if(p instanceof VirusCluster){
			if(!found && distance(p) < 300 + ((VirusCluster)p).getRadius()){
				found = true;
				initial = true;
				if(next != null && next.getParent() != null)
					next = next.getParent();
			}
			
			if(((VirusCluster)p).getSize() > 5 && distance(p) < ((HasRadius)p).getRadius() + getRadius())
				die();
		}
		
	}
	public void drawSelfOn(Graphics2D g2){
		if(found)
			g2.setColor(new Color(0x9E1B95));
		else
			g2.setColor(new Color(0xB5,0x66,0xFF));
		g2.fillOval((int)(x-radius), (int)(y-radius), (int)(radius*2), (int)(radius*2));
		
		
		g2.setStroke(new BasicStroke(10));
		g2.setColor(new Color(0xB9,0xB7,0xE8,0x30));
		g2.drawOval((int)(x-300),(int)(y-300),600,600);
		g2.setStroke(new BasicStroke(1));
//		if(next != null)
//			g2.drawLine((int)x, (int)y, (int)next.x, (int)next.y);
	}
	public double getRadius() {
		return radius;
	}
	public ArrayList<Integer> getPath(){
		return path;
	}
}
