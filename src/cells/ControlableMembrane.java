package cells;

import input.Keyboard;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ControlableMembrane extends MembranePart{

	public ControlableMembrane(double x, double y, ArrayList<MembranePart> others, Point p, Nucleus n) {
		super(x, y, others,p,n);
	}
	double speed = 2;
	public void update(){
		//dx = dy = 0;
		super.update();
		if(Keyboard.keys[KeyEvent.VK_W])
			y -= speed;
		if(Keyboard.keys[KeyEvent.VK_A])
			x -= speed;
		if(Keyboard.keys[KeyEvent.VK_S])
			y += speed;
		if(Keyboard.keys[KeyEvent.VK_D])
			x += speed;
	}
}
