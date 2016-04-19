package physics.physicsObjects;

import java.awt.Color;
import java.awt.Graphics2D;

public class PhysicsObj {
	static double g = .0001;
	//It's location
	double x, y;
	//Where it's moving
	double dx, dy;
	double mass;
	public PhysicsObj(){
		x = y = dx = dy = 0;
	}
	public PhysicsObj(double x, double y){
		this.x = x;
		this.y = y;
		dx = dy = 0;
		mass = 1;
	}
	public PhysicsObj(double x, double y, double dx, double dy){
		this(x,y);
		this.dx = dx;
		this.dy = dy;
	}
	public PhysicsObj(double x, double y, double dx, double dy, double mass){
		this(x,y,dx,dy);
		this.mass = mass;
	}
	
	public void update(){
		x += dx;
		y += dy;
	}
	public void effect(PhysicsObj p){
		if(this == p)
			return;
		gravity(p);
	}
	//Applies the gravity of this to the direction of p
	public void gravity(PhysicsObj p){
		//cv is the vector connecting p and this
		double cvx = this.x-p.x;
		double cvy = this.y-p.y;
		//The distance between this and p
		double dist = Math.sqrt(cvx*cvx + cvy*cvy);
		
		double massProduct = this.mass * p.mass;
		
		cvx = cvx*(g*massProduct/dist);
		cvy = cvy*(g*massProduct/dist);
		
		p.dx += Double.isInfinite(cvx) ? 0 : cvx;
		p.dy += Double.isInfinite(cvy) ? 0 : cvy;
	}
	
	public void drawSelfOn(Graphics2D g2){
		g2.setColor(Color.black);
		g2.drawOval((int)x-3, (int)y-3, 6, 6);
	}
	public String toString(){
		return "[x:"+x+", y:"+y+", dx:" + dx + ", dy:" + dy + "]";
	}
	public void setLocation(int x, int y){this.x = x; this.y = y;};
	public void setDirection(int dx, int dy){this.dx = dx; this.dy = dy;}
	public void onClick(Boolean b){};
}
