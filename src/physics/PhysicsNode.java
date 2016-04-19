package physics;

import input.Mouse;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import nodes.Node;
//Has an array of PhysicsObjs and makes them interact with each other, also renders them
public class PhysicsNode extends Node{
	ArrayList<PhysicsObj> physObjs;
	public PhysicsNode(Rectangle hitbox){
		super.hitbox = hitbox;
		physObjs = new ArrayList<PhysicsObj>();
		
//		physObjs.add(new PhysicsObj(100,100,0,0));
//		physObjs.add(new PhysicsObj(100 ,50,0,0));
		
		//physObjs.add(new PhysicsObj(500,300,2,0));
		//physObjs.add(new StaticPhysObj(500,375,100));
		
		//physObjs.add(new ESPhysicsObj(500,300,0,0,5,1));
		//physObjs.add(new ESPhysicsObj(525,350,0,0,5,1));
		
		for(int i = 0; i < 1000; i++){
			//physObjs.add(new ESPhysicsObj(Math.random()*1000,Math.random()*750,Math.random() < .5 ? Math.random()*10 : Math.random()*-10));
		//	physObjs.add(new ESPhysicsObj(Math.random()*1000,Math.random()*750,(int)(Math.random()*3)-1));
		}
//		for(int i = 0; i < 1000; i += 20){
//			physObjs.add(new StaticESPhysObj(i,1,1));
//			physObjs.add(new StaticESPhysObj(i+10,1,-1));
//		}
//		for(int i = 0; i < 1000; i += 20){
//			physObjs.add(new StaticESPhysObj(i,744,1));
//			physObjs.add(new StaticESPhysObj(i+10,744,-1));
//		}
//		for(int i = 0; i < 750; i += 20){
//			physObjs.add(new StaticESPhysObj(994,i,1));
//			physObjs.add(new StaticESPhysObj(994,i+10,-1));
//		}
//		for(int i = 0; i < 750; i += 20){
//			physObjs.add(new StaticESPhysObj(0,i,1));
//			physObjs.add(new StaticESPhysObj(0,i+10,-1));
//		}
		
		//for(int i = 0; i < 50; i++){
		//	physObjs.add(new StaticESPhysObj(Math.random()*100+400,Math.random()*10+500,10));
		//}
		//for(int i = 0; i < 50; i++){
		//	physObjs.add(new StaticESPhysObj(Math.random()*100+520,Math.random()*10+500,-10));
	//	}
	}
	public void drawOn(Graphics2D g2) {
		for(PhysicsObj p : physObjs)
			p.drawSelfOn(g2);
	}
	//Where the user originally pressed
	Point origin = new Point(0,0);
	
	public void update(Boolean onClick) {
		for(PhysicsObj p : physObjs)
			for(PhysicsObj p2 :physObjs)
				p2.effect(p);
		
		for(PhysicsObj p : physObjs)
			p.update();
		
		if(onClick != null){
			if(onClick){
				origin = new Point(Mouse.button1At);
				
				//physObjs.add(new PhysicsObj(origin.x,origin.y));
			}
			else{
				double scale = .1;
				Point p = Mouse.button1At;
//				if(Math.random() < .5){
//					physObjs.add(new ESPhysicsObj(origin.x,origin.y,(p.x-origin.x)*scale,(p.y-origin.y)*scale,-1));
//					physObjs.add(new ESPhysicsObj(origin.x,origin.y,(p.x-origin.x)*scale,(p.y-origin.y)*scale,-1));
//				}
//				else{
//					physObjs.add(new ESPhysicsObj(origin.x,origin.y,(p.x-origin.x)*scale,(p.y-origin.y)*scale,1));
//				}
				physObjs.add(new ESPhysicsObj(origin.x,origin.y,(p.x-origin.x)*scale,(p.y-origin.y)*scale,1,1));
				physObjs.add(new ESPhysicsObj(origin.x-10,origin.y,(p.x-origin.x)*scale,(p.y-origin.y)*scale,1,-1));
				//physObjs.add(new Electron(origin.x+10,origin.y,(p.x-origin.x)*scale,(p.y-origin.y)*scale+2));
//				for(int i = 0; i < 5; i++){
//					physObjs.add(new Electron(origin.x+(Math.random()*10-5),origin.y+(Math.random()*10-5),(p.x-origin.x)*scale,(p.y-origin.y)*scale));
//				}
				//physObjs.add(new ESPhysicsObj(origin.x-10,origin.y,(p.x-origin.x)*scale,(p.y-origin.y)*scale-.35,1));
			}
		}
	}

}
