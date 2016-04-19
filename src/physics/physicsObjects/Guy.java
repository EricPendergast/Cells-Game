package physics.physicsObjects;

import input.Mouse;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Guy {
	Head head;
	BodyPart torso;
	BodyPart lArm;
	BodyPart rArm;
	
	BodyPart bottomTorso;
	BodyPart rLeg;
	BodyPart lLeg;
	
	public void putSelfInArrayList(ArrayList<PhysicsObj> array){
		head = new Head(0,0,null);
		torso = new BodyPart(0,0,null,20);
		head.setTarget(torso);
		
		lArm = new BodyPart(0,1,null,40);
		rArm = new BodyPart(500,500,null,40);
		torso.addTarget(lArm);
		torso.addTarget(rArm);
		
		bottomTorso = new BodyPart(44,53,null,20);
		torso.addTarget(bottomTorso);
		
		rLeg = new BodyPart(123,145,null,40);
		lLeg = new BodyPart(123,144,null,40);
		bottomTorso.addTarget(rLeg);
		bottomTorso.addTarget(lLeg);
		
		array.add(head);
		array.add(torso);
		array.add(rArm);
		array.add(lArm);
		array.add(bottomTorso);
		array.add(rLeg);
		array.add(lLeg);
	}
}

class Head extends ControlableNodePhysObj{
	Color c = Color.RED;
	public Head(double x, double y, PhysicsObj target) {
		super(x, y, target);
		super.distFromTarget = 10;
	}
	public void update(){
		dy += .5;
		if(!c.equals(Color.red)){
			dx = (Mouse.button1At.x - x)*.5;		
			dy = (Mouse.button1At.y - y)*.5;
			System.out.println(dy + " " + dx);
			x = Mouse.button1At.x;
			y = Mouse.button1At.y;
			ensureDist(target,5);
		}
		else{
			x += dx;
			y += dy;
			super.ensureDist(target, distFromTarget);
		}
	}
	public void drawSelfOn(Graphics2D g2){
		g2.setColor(Color.BLACK);
		if(target != null)
			g2.drawLine((int)this.x,(int)this.y,(int)target.x,(int)target.y);
		
		g2.setColor(c);
		g2.fillOval((int)x-10, (int)y-10, 20, 20);
		
	}
	public void onClick(Boolean onClick){
		if(onClick)
			c = Color.BLUE;
		else
			c = Color.RED;
	}
}
class BodyPart extends NodePhysObj{
	ArrayList<PhysicsObj> targets;
	public BodyPart(double x, double y, PhysicsObj target, double length) {
		super(x, y, target);
		targets = new ArrayList<PhysicsObj>();
		super.distFromTarget = length;
	}
	public void update(){
		super.update();
		y+=5;
	}
	public void effect(PhysicsObj p){
		if(p instanceof BodyPart && !((BodyPart)p).contains(this)){
			double xDiff = this.x - p.x;
			double yDiff = this.y - p.y;
			double length = 1.0/(Math.sqrt(xDiff*xDiff + yDiff*yDiff)+.0001)*3;
			xDiff *= length;
			yDiff *= length;
			p.x -= xDiff;
			p.y -= yDiff;
			this.x += xDiff;
			this.y += yDiff;
		}
	}

	public void ensureDist(PhysicsObj p, double distance){
		for(PhysicsObj phys : targets)
			super.ensureDist(phys,distFromTarget);
	}
	public void drawSelfOn(Graphics2D g2){
		g2.setColor(Color.BLACK);
		for(PhysicsObj p : targets){
			g2.drawLine((int)this.x,(int)this.y,(int)p.x,(int)p.y);
		}
	}
	public void addTarget(PhysicsObj p){
		targets.add(p);
	}
	public boolean contains(PhysicsObj p){
		for(PhysicsObj p2 : targets)
			if(p == p2)
				return true;
		return false;
	}
}