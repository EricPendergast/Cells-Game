package cells.enemyCells;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import cells.*;

public class MasterCell extends CellPart implements HasRadius{
	VeinMarker origin;
	double radius = 250;
	public MasterCell(VeinMarker o){
		origin = o;
		this.x = o.x;
		this.y = o.y;
	}
	int counter = 0;
	public void update(){
		counter++;
//		if(counter % 60 == 0)
//			super.chunk.addFromCell(new Nucleus(x+5,y+5, 15));
		
		if(radius < 25){
			die();
			CellRunner.win = true;
		}
		
		if(counter <= 60 * 20 && counter % 60 == 0)
			chunk.addFromCell(new PatrolCell(x+5,y+5,origin));
		if(counter % 30 == 0)
			chunk.addFromCell(new RedBloodCell(x+5,y+5,origin));
		if(counter % 180 == 0)
			chunk.addFromCell(new GoldCell(x+5,y+5,origin,40));
	}
	
	public void drawSelfOn(Graphics2D g2){
		g2.setColor(new Color(0x5F,0x1B,0x9E));
		g2.fillOval((int)(x-radius), (int)(y-radius), (int)(radius*2), (int)(radius*2));
	}
	
	public void effect(CellPart p){
		super.effect(p);
		if(p instanceof PatrolCell){
			PatrolCell pat = (PatrolCell)p;
			if(distance(pat) < pat.getRadius() + radius){
				if(pat.found){
					if(counter % 60 == 0 && pat.goingBack){
						counter++;
						chunk.addFromCell(new WhiteBloodCell(x,y,15, (ArrayList<Integer>)(pat.path.clone()), origin));
						pat.found = false;
						pat.goingBack = false;
						pat.path = new ArrayList<Integer>();
					}
				}else{
					if(pat.goingBack){
						pat.goingBack = false;
						pat.path = new ArrayList<Integer>();
					}
				}
			}
		}
		
		if(p instanceof VirusCluster){
			if(distance(p) < ((VirusCluster)p).getRadius()+getRadius()+20 && counter % 3 == 0){
				((VirusCluster)p).remove();
				radius--;
			}
		}
	}
	public double getRadius() {
		return radius;
	}
}
