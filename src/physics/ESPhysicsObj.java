package physics;

import java.awt.Color;
import java.awt.Graphics2D;

public class ESPhysicsObj extends PhysicsObj{
	
	//private static final double electricConst = 8.854E-12;
	protected static final double electricConst = 8.854;
	protected double charge = 1;
	
	public ESPhysicsObj(double x, double y, double charge){
		super(x,y);
		this.charge = charge;
	}
	public ESPhysicsObj(double x, double y, double dx, double dy, double charge){
		super(x,y,dx,dy);
		this.charge = charge;
	}
	public ESPhysicsObj(double x, double y, double dx, double dy, double mass, double charge){
		super(x,y,dx,dy,mass);
		this.charge = charge;
	}
	public void effect(PhysicsObj p){
		if(this == p)
			return;
		super.effect(p);
		electroStatic(p);
	}
	public void electroStatic(PhysicsObj p){
		//cv is the vector connecting p and this
		double cvx = this.x-p.x;
		double cvy = this.y-p.y;
		
		double pCharge = !(p instanceof ESPhysicsObj) ? 1 : ((ESPhysicsObj)p).charge;
		double chargeProd = pCharge*this.charge;
		
		double xDiff = this.x-p.x;
		double yDiff = this.y-p.y;
		
		double dist = Math.sqrt(cvx*cvx + cvy*cvy);
		
		double constProd = electricConst * 4.0 * Math.PI;
		
		double massProd = this.mass / p.mass;
		cvx = cvx*(-chargeProd*massProd)/(constProd*(dist));
		cvy = cvy*(-chargeProd*massProd)/(constProd*(dist));
		
		p.dx += Double.isInfinite(cvx) ? 0 : cvx;
		p.dy += Double.isInfinite(cvy) ? 0 : cvy;
	}
	//This gives the electrostatic force a range
//	public void electroStatic(PhysicsObj p){
//		//cv is the vector connecting p and this
//		double cvx = this.x-p.x;
//		double cvy = this.y-p.y;
//		
//		double pCharge = !(p instanceof ESPhysicsObj) ? 1 : ((ESPhysicsObj)p).charge;
//		double chargeProd = pCharge*this.charge;
//		
//		double xDiff = this.x-p.x;
//		double yDiff = this.y-p.y;
//		
//		double dist = Math.sqrt(cvx*cvx + cvy*cvy);
//		
////		if(dist > 100 && !(p instanceof Electron) && !(this instanceof Electron))
////			return;
//		
//			
//		double constProd = electricConst * 4.0 * Math.PI;
//		
//		double massProd = this.mass / p.mass;
//		cvx = cvx*(-chargeProd*massProd)/(constProd*(dist));
//		cvy = cvy*(-chargeProd*massProd)/(constProd*(dist));
//		
//		if(!(p instanceof Electron) && !(this instanceof Electron)){
//			cvx *= .1;
//			cvy *= .1;
//		}
//		
//		p.dx += Double.isInfinite(cvx) ? 0 : cvx;
//		p.dy += Double.isInfinite(cvy) ? 0 : cvy;
//	}
	//public void gravity(PhysicsObj p){}
	public void drawSelfOn(Graphics2D g2){
		if(charge > 0)
			g2.setColor(Color.red);
		else if(charge == 0)
			g2.setColor(Color.blue);
		else
			g2.setColor(Color.green);
		g2.fillOval((int)x-3, (int)y-3, 6, 6);
	//	g2.drawLine((int)x,(int)y,(int)x+(int)dx*5,(int)y+(int)dy*5);
		//System.out.println(this);
	}
}
