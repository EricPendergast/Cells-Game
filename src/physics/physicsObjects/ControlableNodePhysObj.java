package physics.physicsObjects;
import java.awt.event.KeyEvent;

import input.Keyboard;
import input.Mouse;
public class ControlableNodePhysObj extends NodePhysObj{
	int speed = 2;
	public ControlableNodePhysObj(double x, double y, PhysicsObj target){
		super(x,y,target);
	}
	public void update(){
		super.update();
//		if(Keyboard.keys[KeyEvent.VK_W])
//			y-=speed;
//		if(Keyboard.keys[KeyEvent.VK_S])
//			y+=speed;
//		if(Keyboard.keys[KeyEvent.VK_D])
//			x+=speed;
//		if(Keyboard.keys[KeyEvent.VK_A])
//			x-=speed;
		x = Mouse.button1At.x;
		y = Mouse.button1At.y;
		ensureDist(target,5);
	}
}
