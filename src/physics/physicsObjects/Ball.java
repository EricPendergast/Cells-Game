package physics.physicsObjects;

import java.awt.Color;
import java.awt.Graphics2D;

public class Ball extends PhysicsObj{
	int radius = 80;
	public Ball(double x, double y){
		super(x,y);
	}
	public void effect(PhysicsObj p){
		if(this != p)
			push(p, radius);
	}
	
	public void push(PhysicsObj p, double distance){
		if(p == null)
			return;
		double diffx = this.x - p.x;
		double diffy = this.y - p.y;
		double length = Math.sqrt(diffx*diffx + diffy*diffy) + .000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001;
		if(length > distance)
			return;
		diffx = diffx/length*distance;
		diffy = diffy/length*distance;
		p.x = this.x - diffx;
		p.y = this.y - diffy;
	}
	
	public void drawSelfOn(Graphics2D g2){
		g2.setColor(Color.black);
		g2.drawOval((int)x-radius, (int)y-radius, radius*2, radius*2);
	}
}
