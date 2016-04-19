package physics.physicsObjects;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import input.Keyboard;

public class NodePhysObj extends PhysicsObj{
	PhysicsObj target;
	double distFromTarget = 5;
	public NodePhysObj(double x, double y, PhysicsObj target){
		this.x = x;
		this.y = y;
		this.target = target;
	}
	public void effect(PhysicsObj p){
//		if(p == target){
////			double diffx = this.x - p.x;
////			double diffy = this.y - p.y;
////			double length = Math.sqrt(diffx*diffx + diffy*diffy);
////			diffx = diffx/length*distFromTarget;
////			diffy = diffy/length*distFromTarget;
////			p.x = this.x - diffx;
////			p.y = this.y - diffy;
//			ensureDist(p,5);
//		}
		
		//ensureDist(p,distFromTarget);
	}
	
	
	public void update(){
		super.update();
//		y+=20;
	}
	public void ensureDist(PhysicsObj p, double distance){
		if(p == null)
			return;
		double diffx = this.x - p.x;
		double diffy = this.y - p.y;
		double length = Math.sqrt(diffx*diffx + diffy*diffy);
		diffx = diffx/length*distFromTarget;
		diffy = diffy/length*distFromTarget;
		p.x = this.x - diffx;
		p.y = this.y - diffy;
		
		if(p instanceof NodePhysObj)
			((NodePhysObj) p).ensureDist(((NodePhysObj) p).target, distFromTarget);
	}
	public void drawSelfOn(Graphics2D g2){
		super.drawSelfOn(g2);
		if(target != null)
			g2.drawLine((int)this.x,(int)this.y,(int)target.x,(int)target.y);
	}
	
	public void setTarget(PhysicsObj p){
		target = p;
	}
	public PhysicsObj getTarget(){
		return target;
	}
}
