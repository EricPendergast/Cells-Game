package cells;

import java.awt.Color;
import java.awt.Graphics2D;

public class WallCell extends Nucleus implements StaticCell, HasRadius{
	private Point prefLoc;
	private boolean activated;
	public WallCell(double x, double y, int m) {
		super(x, y, m);
		prefLoc = new Point(x,y);
		System.out.println(radius);
	}
	public WallCell(double x, double y, int m, int dx, int dy) {
		super(x, y, m, dx, dy);
		
	}
	int timer = 0;
	int stopUpdate = 22;
	public void update(){
		dx*=.5;
		dy*=.5;
		//x += dx;
		//y += dy;
		timer++;
		
		if(timer < stopUpdate){
			for(MembranePart p : membrane){
				p.update();
			}
		}
		
	}
	public void drawSelfOn(Graphics2D g2){
		g2.setColor(Color.BLACK);
		g2.drawOval((int)x-(int)(subRadius/2), (int)y-(int)(subRadius/2), (int)subRadius, (int)subRadius);
		//if(timer >= stopUpdate)
		if(!isOptimized)
			for(int i = 0; i < membrane.size(); i++)
				membrane.get(i).drawSelfOn(g2);
		else
			g2.drawOval((int)x-(int)(radius), (int)y-(int)(radius), (int)radius*2, (int)radius*2);
		
		//MembranePart first = membrane.get(0);
	}
	public void effect(CellPart p){
//		if(!activated)
//			return;
		if(this == p)
			return;
		
		if(p instanceof Nucleus && (timer < stopUpdate)){
			//System.out.println("asdf" + (timer < stopUpdate));
			double dist = distance(p);
			if(dist < .7*(radius + ((Nucleus)p).radius)){
				p.limitDist(getPoint(), .7*(radius + ((Nucleus)p).radius));
				for(MembranePart memp : ((Nucleus)p).membrane){
					memp.limitDist(getPoint(), radius);
				}
			}
//			if(dist < 1.5*(radius + ((Nucleus)p).radius)){
//				for(MembranePart part : membrane){
//					for(MembranePart b : ((Nucleus)p).membrane){
//						part.effect(b);
//					}
//				}
//			}
			repel(p.getPoint(),200,1*(radius + ((Nucleus)p).radius));
		}
		if(p instanceof WallCell && (timer < stopUpdate)){
			double dist = distance(p);
			if(dist < 1.5*(radius + ((Nucleus)p).radius)){
				for(MembranePart part : membrane){
					for(MembranePart b : ((Nucleus)p).membrane){
						part.effect(b);
					}
				}
			}
		}
	}
	public void setOptimized(boolean b){
		isOptimized = b;
	}
	public void activate(boolean b){
		activated = b;
	}
}
