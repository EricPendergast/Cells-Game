package physics;

import java.awt.Rectangle;

import states.NodeState;
//Encapsulates the PhysicsNode
public class PhysicsState extends NodeState{
	private PhysicsNode phys;
	public PhysicsState(){

		phys = new PhysicsNode(new Rectangle(0,0,100,100));
		super.addNode(phys);
	}
	public void update(){
		super.update();
	}
}
