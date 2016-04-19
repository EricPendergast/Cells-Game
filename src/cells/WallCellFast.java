package cells;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class WallCellFast extends CellPart implements StaticCell, HasRadius{
	private Point prefLoc;
	private boolean activated;
	double subRadius = 10;
	double radius;
	
	public WallCellFast(double x, double y, double r) {
		super(x, y);
		radius = r;
		prefLoc = new Point(x,y);
	}
	public WallCellFast(double x, double y, double r, int dx, int dy) {
		super(x, y, dx, dy);
		radius = r;
	}
	public void update(){
	}
	public void drawSelfOn(Graphics2D g2){
		g2.setColor(Color.decode("0xF57C6C"));
		//g2.setStroke(new BasicStroke(20));
		radius += 10;
		g2.fillOval((int)x-(int)(radius), (int)y-(int)(radius), (int)radius*2, (int)radius*2);
		radius -= 10;
		//g2.setStroke(new BasicStroke(1));
		
		g2.setColor(Color.decode("0xF59689"));
		g2.fillOval((int)x-(int)(radius), (int)y-(int)(radius), (int)radius*2, (int)radius*2);
		
		
		g2.setColor(Color.BLACK);
		if(isSelected)
			g2.setColor(Color.WHITE);
		g2.drawOval((int)x-(int)(subRadius/2), (int)y-(int)(subRadius/2), (int)subRadius, (int)subRadius);
		
	}
	public void effect(CellPart p){
//		if(!activated)
//			return;
		if(this == p)
			return;
		
		if(p instanceof Nucleus){
			for(MembranePart memp : ((Nucleus)p).membrane)
				memp.limitDist(getPoint(), radius+20);
		}
//		if(p instanceof VirusCluster){
//			p.limitDist(getPoint(), ((VirusCluster)p).getRadius()+getRadius());
//		}
		
		if(p instanceof HasRadius && !(p instanceof WallCellFast)){
			p.limitDist(getPoint(), ((HasRadius)p).getRadius()+getRadius());
		}
//		if(p instanceof Nucleus && (timer < stopUpdate)){
//			//System.out.println("asdf" + (timer < stopUpdate));
//			double dist = distance(p);
//			if(dist < .7*(radius + ((Nucleus)p).radius)){
//				p.limitDist(getPoint(), .7*(radius + ((Nucleus)p).radius));
//				for(MembranePart memp : ((Nucleus)p).membrane){
//					memp.limitDist(getPoint(), radius);
//				}
//			}
////			if(dist < 1.5*(radius + ((Nucleus)p).radius)){
////				for(MembranePart part : membrane){
////					for(MembranePart b : ((Nucleus)p).membrane){
////						part.effect(b);
////					}
////				}
////			}
//			repel(p.getPoint(),200,1*(radius + ((Nucleus)p).radius));
//		}
//		if(p instanceof WallCell && (timer < stopUpdate)){
//			double dist = distance(p);
//			if(dist < 1.5*(radius + ((Nucleus)p).radius)){
//				for(MembranePart part : membrane){
//					for(MembranePart b : ((Nucleus)p).membrane){
//						part.effect(b);
//					}
//				}
//			}
//		}
	}
	public void setOptimized(boolean b){
		isOptimized = b;
	}
	public void activate(boolean b){
		activated = b;
	}
	public double getRadius(){
		return radius;
	}
}
