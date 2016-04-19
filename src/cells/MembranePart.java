package cells;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class MembranePart extends CellPart{
	
	//Prefered distance from left and right
	private double preferedDist = 10;
	private double roughness = 5;
	private double stiffness = 40;
	double speedLimit = 10;
	//Left and right partners
	MembranePart left, right;
	//The whole membrane
	ArrayList<MembranePart> others;
	//Location to approach relative to nucleus
	Point prefLoc;
	Nucleus nucleus;
	
	//Used for counting chains
	private boolean marked = false;
	
	public MembranePart(double x, double y, ArrayList<MembranePart> others, Point prefLoc, Nucleus n){
		super(x,y);
		this.others = others;
		this.prefLoc = prefLoc;
		nucleus = n;
	}
	public MembranePart(double x, double y, ArrayList<MembranePart> others, Point prefLoc, Nucleus n, double prefDist, double rough, double stiff){
		this(x,y,others,prefLoc,n);
		preferedDist = prefDist;
		roughness = rough;
		stiffness = stiff;
	}
	public void drawSelfOn(Graphics2D g2){
		//super.drawSelfOn(g2);
//		if(right != null)
//			g2.drawLine((int)x, (int)y, (int)right.x, (int)right.y);
//		if(left != null)
//			g2.drawLine((int)x, (int)y, (int)left.x, (int)left.y);
		
		int[] xs = {(int)this.x,(int)left.x,(int)nucleus.x};
		int[] ys = {(int)this.y,(int)left.y,(int)nucleus.y};
		g2.fillPolygon(xs,ys,3);
	}
	
	
	public void clearPartners(){
		left = null;
		right = null;
	}
	
	public void update(){
		if(isDead()){
			x = prefLoc.x;
			y = prefLoc.y;
		}
		limitSpeed(speedLimit);
		dy *= .95;
		dx *= .95;
		super.update();
		Vector direction = new Vector(dx,dy);
		move();
		
	}
	
	private void move(){
		//Move toward its left and right partners
		super.approach(getAveragePos(),stiffness,preferedDist);
		//Approach default position
		if(prefLoc != null && nucleus != null)
			super.approach(new Point(nucleus.x+prefLoc.x,nucleus.y+prefLoc.y),stiffness,roughness);
		if(nucleus != null)
			limitDist(nucleus.getPoint(),nucleus.getSubRadius());
	}
	
	//Finds the average of left and right, accounting for if either is null
	protected Point getAveragePos(){
		Point avgLoc = new Point();
		if(left == null && right == null)
			return avgLoc;
		if(right == null)
			avgLoc = left.getPoint();
		else if(left == null)
			avgLoc = right.getPoint(); 
		else
			avgLoc = Point.average(right.getPoint(),left.getPoint()); 
		return avgLoc;
	}
	
	public int getLeftChainLength(){
		if(marked || left == null)
			return 0;
		marked = true;
		int ret = 1 + left.getLeftChainLength();
		marked = false;
		
		return ret;
	}
	
	public int getRightChainLength(){
		if(marked || right == null)
			return 0;
		marked = true;
		int ret = 1 + right.getRightChainLength();
		marked = false;
		
		return ret;
	}
	
	public void effect(CellPart p){
		if(p instanceof MembranePart){
			//repel(p.getPoint(), 20,20);
			limitDist(p.getPoint(),preferedDist*2);
		}
	}
	
	public void setPrefLoc(Point p){
		prefLoc = p;
	}
	
	public void solidify(){
		if(nucleus == null)
			return;
		prefLoc.x = x-nucleus.x;
		prefLoc.y = y-nucleus.y;
	}
	public void updatePartners(){
		double min = 100;
		for(MembranePart p : others){
			if(p.right == this){
				left = p;
				break;
			}
			if(p == this)
				continue;
			double dist = distance(p);
			if(dist < min){
				left = p;
				min = dist;
			}
		}
		if(left != null)
			left.right = this;
		min = 100;
		for(MembranePart p : others){
			if(p.left == this){
				right = p;
				break;
			}
			if(p == this || p == left)
				continue;
			double dist = distance(p);
			if(dist < min){
				right = p;
				min = dist;
			}
		}
		if(right != null)
			right.left = this;
	}
	public void reset(){
		Point newLoc = Point.average(nucleus.getPoint(), new Point(prefLoc.x+nucleus.x,prefLoc.y+nucleus.y));
		dx = 0;
		dy = 0;
		x = newLoc.x;
		y = newLoc.y;
	}
}






//public void updatePartners(){
//double min = 100;
//for(MembranePart p : others){
//	if(p.right == this){
//		left = p;
//		break;
//	}
//	if(p.right != null || p == this)
//		continue;
//	double dist = distance(p);
//	if(dist < min){
//		left = p;
//		p.right = this;
//		min = dist;
//	}
//}
//min = 100;
//double minDot = 0;
//for(MembranePart p : others){
//	if(p.left == this){
//		right = p;
//		break;
//	}
//	if(p.left != null || p == this || p == left)
//		continue;
//	if(left == null){
//		double dist = distance(p);
//		if(dist < min){
//			left = p;
//			p.right = this;
//			min = dist;
//		}
//	}else if(distance(p) < min){
//		Point vecA = new Point(p.x-this.x, p.y-this.y);
//		Point vecB = new Point(left.x-this.x, left.y-this.y);
//		double dot = vecA.x*vecB.x + vecA.y*vecB.y;
//		if(dot < minDot){
//			minDot = dot;
//			right = p;
//		}
//	}
//}
//}

//public void updatePartners(){
//	double min = 50;
//	int maxChain = 0;
//	for(MembranePart p : others){
//		if(p.right == this){
//			left = p;
//			break;
//		}
//		if(p.right != null || p == this || (p.left != null && p.left.left == this))
//			continue;
//		double dist = distance(p);
//		int c = p.getLeftChainLength();
//		if(dist < min && c >= maxChain){
//			left = p;
//			p.right = this;
//			maxChain = c;
//		}
//	}
//	min = 50;
//	maxChain = 0;
//	for(MembranePart p : others){
//		if(p.left == this){
//			right = p;
//			break;
//		}
//		if(p.left != null || p == this || p == left || (p.right != null && p.right.right == this))
//			continue;
//		double dist = distance(p);
//		int c = p.getRightChainLength();
//		if(dist < min && c >= maxChain){
//			right = p;
//			p.left = this;
//			maxChain = c;
//		}
//	}
//}