package cells;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class CellPart {
	//Its location
	public double x, y;
	//Where it's moving
	public double dx, dy;
	//Whether it will do optimized collisions
	boolean isOptimized;
	
	protected CellRegion chunk = null;
	
	protected boolean isSelected = false;
	
	public CellPart(){
		x = y = dx = dy = 0;
	}
	public CellPart(double x, double y){
		this.x = x;
		this.y = y;
		dx = dy = 0;
	}
	public CellPart(double x, double y, double dx, double dy){
		this(x,y);
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update(){
		x += dx;
		y += dy;
	}
	public void effect(CellPart p){
		if(this == p)
			return;
		if(p instanceof StaticCell){
			p.effect(this);
		}
		if(p instanceof HasRadius && this instanceof HasRadius){
			repel(p.getPoint(),100,((HasRadius)p).getRadius()+((HasRadius)this).getRadius());
		}
	}
	
	public void drawSelfOn(Graphics2D g2){
		//g2.setColor(Color.black);
		if(isSelected)
			g2.setColor(Color.red);
		g2.drawOval((int)x, (int)y, 5, 5);
	}
	public String toString(){
		return "[x:"+x+", y:"+y+", dx:" + dx + ", dy:" + dy + "]";
	}
	public void setLocation(int x, int y){this.x = x; this.y = y;};
	public void setDirection(int dx, int dy){this.dx = dx; this.dy = dy;}
	public Point getPoint(){
		return new Point(x,y);
	}
	public double distance(CellPart a){
		return Math.sqrt(distanceCompare(a));
	}
	public double distanceCompare(CellPart a){
		return Math.pow(x-a.x, 2) + Math.pow(y-a.y,2);
	}
	
	
	public void approach(Point p, double speed, double dist){
//		Point dToward = new Point(p.x - this.x, p.y - this.y);
//		double tdx = dToward.x/scale;
//		double tdy = dToward.y/scale;
//		if(dist > preferedDist-n && dist < preferedDist){
//			tdx = tdy = 0;
//		}else if(dist < preferedDist-n){
//			tdx = -tdx;
//			tdy = -tdy;
//		}
//		dx += tdx;
//		dy += tdy;
		
		Vector d = new Vector(p.x - this.x, p.y - this.y);
		//d.normalize();
		d.multiply(1/speed);
		if(Point.distanceCompare(this.getPoint(), p) > dist*dist){
			dx += d.dx/2;
			dy += d.dy/2;
		}
	}
	
	public void moveToward(Point p, double speed, double dist){
		Vector d = new Vector(p.x - this.x, p.y - this.y);
		d.normalize();
		d.multiply(speed);
		if(Point.distanceCompare(this.getPoint(), p) > dist*dist){
			dx += d.dx;
			dy += d.dy;
		}
	}
	public void repel(Point p, double speed, double dist){
		Vector d = new Vector(p.x - this.x, p.y - this.y);
		//d.normalize();
		d.multiply(1/-speed);
		//if(dist < preferedDist){
		if(Point.distance(this.getPoint(), p) < dist){
			dx += d.dx/2;
			dy += d.dy/2;
		}
		
	}
	public void limitDist(Point p, double distance){
		double distCheck = Point.distanceCompare(this.getPoint(), p);
		if(distCheck < distance*distance){
			Vector dToward = Point.getDifference(this.getPoint(),p);
			dToward.normalize();
			dToward.multiply(distance);
			x = p.x - dToward.dx;
			y = p.y - dToward.dy;
		}
	}
	public void limitSpeed(double speedLimit){
		Vector v = new Vector(dx,dy);
		if(v.getLength() > speedLimit){
			v.normalize();
			v.multiply(speedLimit);
			dx = v.dx;
			dy = v.dy;
		}
	}
	public void die(){
		x = -1;
	}
	public boolean isDead(){
		return x < 0 || y < 0;
	}
	public boolean isContained(Rectangle area){
		return area.contains(x,y);
	}
	public void setOptimized(boolean b){
		isOptimized = b;
	}
	public void select(Rectangle area){
		if(isContained(area))
			isSelected = true;
		else
			isSelected = false;
	}
	public boolean isSelected(){return isSelected;}
	
	public static void putInGrid(CellPart p, CellRegion[][] grid){
		grid[(int)(p.x/1000)][(int)(p.y/1000)].add(p);
	}
}
