package cells.enemyCells;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import cells.*;
public class WhiteBloodCell extends Nucleus{
	int counter = 0;
	
	int absorbRate = 3;
	//Amt of time since absorbed last virus
	int lastVirus = absorbRate;
	VirusCluster closest = null;
	double accell = .3;
	{
		speedLimit = 5;
	}
	
	VeinMarker next;
	ArrayList<Integer> path;
	int pathIteration = 0;
	public WhiteBloodCell(double x, double y, int m) {
		super(x, y, m);
		path = new ArrayList<Integer>();
	}
	public WhiteBloodCell(double x, double y, int m, ArrayList<Integer> p, VeinMarker o) {
		super(x, y, m);
		path = p;
		next = o;
	}
	public WhiteBloodCell(double x, double y, int m, double dx, double dy) {
		super(x, y, m, dx, dy);
		path = new ArrayList<Integer>();
	}
	//Amount of time since last targeting
	int lastTarget = 0;
	public void update(){
//		lastVirus++;
//		counter++;
//		//System.out.println(origin);
//		if(origin != null)
//			System.out.println(origin.getParent());
//		if(closest != null && distanceCompare(closest) < 250*250)
//			origin = null;
//		if(closest != null && !closest.isDead() && distanceCompare(closest) < 1000*1000){
//			Vector dir = Point.getDifference(closest.getPoint(), getPoint());
//			dir.normalize();
//			dir.multiply(accell);
//			this.dx -= dir.dx;
//			this.dy -= dir.dy;
//		}else{
//			if(origin != null)
//				if(distance(origin) < 100){
//					if(origin.numBranches() == 0 || pathIteration >= path.size())
//						origin = null;
//					else{
//						origin = origin.getBranch(path.get(pathIteration));
//						pathIteration++;
//					}
//				}
//		}
//		
////		if(origin != null){
////			if(distance(origin) < 100){
////				if(origin.numBranches() == 0 || pathIteration >= path.size())
////					origin = null;
////				else{
////					origin = origin.getBranch(path.get(pathIteration));
////					pathIteration++;
////				}
////			}
////		}else if(closest != null && !closest.isDead() && distanceCompare(closest) < 1000*1000){
////			Vector dir = Point.getDifference(closest.getPoint(), getPoint());
////			dir.normalize();
////			dir.multiply(accell);
////			this.dx -= dir.dx;
////			this.dy -= dir.dy;
////		}
//		
//		if((closest == null || closest.isDead() || distanceCompare(closest) > 1000*1000) && origin == null)
//			lastTarget++;
//		else
//			lastTarget = 0;
//		if(lastTarget > 5*30)
//			die();
//		if(origin != null)
//			moveToward(origin.getPoint(),.1,0);
//		//dx *= .9;
//		//dy *= .9;
//		//limitSpeed(1);
//		super.update();
		if(closest != null && (closest.isDead() ||  distance(closest) > 1000))
			closest = null;
//		if(next != null)
//			System.out.println( (pathIteration == path.size()) + " " + pathIteration + " " + path.size());
		if((closest != null && distance(closest) < 500) || pathIteration == path.size())
			next = null;
		
		if(next != null)
			moveToward(next.getPoint(),accell,0);
		else if(closest != null && distance(closest) < 1000)
			moveToward(closest.getPoint(),accell,0);
		
		if(next != null && distance(next) < 100){
			next = next.getBranch(path.get(pathIteration));
			pathIteration++;
		}
		
		
		if(closest == null && next == null)
			lastTarget++;
		else
			lastTarget = 0;
		
		if(lastTarget > 30*30)
			die();
		limitSpeed(speedLimit);
		super.update();
		
		lastVirus++;
	}
	public void drawSelfOn(Graphics2D g2){
		//if(next != null)
		g2.setColor(new Color(0xFA,0xF9,0xF2));
		//else
		//	g2.setColor(Color.BLACK);
		drawMembrane(g2);
	}
	public void effect(CellPart p){
		super.effect(p);
		
//		if(p instanceof Virus){
//			if(lastVirus < absorbRate)
//				return;
//			if(closest == null || closest.isDead())
//				closest = (Virus)p;
//			else if(distanceCompare(p) < distanceCompare(closest))
//				closest = (Virus)p;
//			
//			if(distance(p) < radius){
//				p.die();
//				lastVirus = 0;
//			}
//		}
		if(p instanceof VirusCluster){
			
			if(lastVirus < absorbRate)
				return;
			if(closest == null || closest.isDead())
				closest = (VirusCluster)p;
			else if(distanceCompare(p) < distanceCompare(closest))
				closest = (VirusCluster)p;
			if(distance(p) < (radius + ((HasRadius)p).getRadius())*1.2 && ((VirusCluster)p).getSize() > 0){
				
				((VirusCluster)p).remove();
				lastVirus = 0;
			}
		}
	}
}
