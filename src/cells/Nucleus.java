package cells;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Nucleus extends CellPart implements HasRadius{
	protected ArrayList<MembranePart> membrane;
	protected double radius;
	protected double subRadius = 13;
	protected double speedLimit = 3;
	public Nucleus(double x, double y, int m){
		super(x,y);
		membrane = new ArrayList<MembranePart>();
		radius = Math.PI*2/m;
		double dist = (10*m)/(2*Math.PI);
		for(int i = 0; i < m; i++){
			double r = radius * i;
			double xc = -Math.sin(r)*dist + x;
			double yc = Math.cos(r)*dist + y;
			membrane.add(new MembranePart(xc,yc,membrane,new Point(xc-x,yc-y),this));
			if(i != 0){
				membrane.get(i).left = membrane.get(i-1);
				membrane.get(i-1).right = membrane.get(i);
			}
		}
		membrane.get(0).left = membrane.get(membrane.size()-1);
		membrane.get(membrane.size()-1).right = membrane.get(0);
		//membrane.add(new ControlableMembrane(x+100,y+60,membrane,new Point(), this));
		radius = dist;
//		for(MembranePart part : membrane)
//			part.clearPartners();
//		for(MembranePart part : membrane)
//			part.updatePartners();
//		for(MembranePart p : membrane)
//			p.reset();
	}
	public Nucleus(double x, double y, int m, double dx, double dy){
		this(x,y,m);
		this.dx = dx;
		this.dy = dy;
		for(MembranePart part : membrane){
			part.dx = dx;
			part.dy = dy;
		}
	}
	public void drawSelfOn(Graphics2D g2){
		g2.setColor(Color.BLACK);
		g2.drawOval((int)x-(int)(subRadius/2), (int)y-(int)(subRadius/2), (int)subRadius, (int)subRadius);
		drawMembrane(g2);
	}
	protected void drawMembrane(Graphics2D g2){
		if(!isOptimized)
			for(int i = 0; i < membrane.size(); i++)
				membrane.get(i).drawSelfOn(g2);
		else
			g2.fillOval((int)x-(int)(radius), (int)y-(int)(radius), (int)radius*2, (int)radius*2);
	}
	int counter = 0;
	double speed = 2;
	public void update(){
		dx*=.999;
		dy*=.999;
		super.update();
		if(!isOptimized)
			for(MembranePart p : membrane)
				p.update();
//		if(Keyboard.keys[KeyEvent.VK_UP]){dx = 0;
//			dy = -speed;}
//		if(Keyboard.keys[KeyEvent.VK_LEFT]){dy = 0;
//			dx = -speed;}
//		if(Keyboard.keys[KeyEvent.VK_DOWN]){dx = 0;
//			dy = speed;}
//		if(Keyboard.keys[KeyEvent.VK_RIGHT]){dy = 0;
//			dx = speed;}
		//if(Keyboard.keys[KeyEvent.VK_Q]){
		//	for(MembranePart p : membrane)
		//		p.solidify();
		//}
		limitSpeed(speedLimit);
		
//		if((counter = counter+1)%10 == 0){
//			for(MembranePart part : membrane)
//				part.clearPartners();
//			for(MembranePart part : membrane)
//				part.updatePartners();
//		}
		//System.out.println(isOptimized);
	}
	
	public void setOptimized(boolean b){
		//if switch from optimized to fancy
		if(!b && isOptimized){
			for(MembranePart p : membrane)
				p.reset();
		}
			
		super.setOptimized(b);
	}
	public int membraneSize(){return membrane.size();}
	public double getSubRadius(){return subRadius;}
	public double getRadius(){return radius;}
	public void effect(CellPart p){
		if(this == p)
			return;
		super.effect(p);
		if(p instanceof Nucleus){
			double dist = distance(p);
			//System.out.println(dist + " " + (1.5*(radius + ((Nucleus)p).radius)));
			if(dist < 1.5*(radius + ((Nucleus)p).radius)){
				if(!isOptimized)
					for(MembranePart part : membrane){
						for(MembranePart b : ((Nucleus)p).membrane){
							part.effect(b);
						}
					}
			}
			
		}
//		if(p instanceof HasRadius)
//			repel(p.getPoint(),200,1*(radius + ((HasRadius)p).getRadius()));
	}
}
