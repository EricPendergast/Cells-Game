package physics;

import java.awt.Color;
import java.awt.Graphics2D;

public class StaticESPhysObj extends ESPhysicsObj{

	public StaticESPhysObj(double x, double y, double charge) {
		super(x, y, charge);
	}
	public void update(){
		return;
	}
	
	public void drawSelfOn(Graphics2D g2){
		if(charge > 0)
			g2.setColor(Color.red);
		else if(charge == 0)
			g2.setColor(Color.blue);
		else
			g2.setColor(Color.green);
		g2.fillOval((int)x-3, (int)y-3, 6, 6);
	}
}
