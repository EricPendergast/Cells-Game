package cells;

import java.awt.Rectangle;


public class Virus extends CellPart{
	VirusCluster parent = null;
	Point prefLoc;
	public Virus(double x, double y) {
		super(x, y);
	}
	public Virus(double x, double y, double dx, double dy){
		super(x,y,dx,dy);
	}
	
	public void update(){
		
		dx *= .95;
		dy *= .95;
		super.update();
		if(parent != null)
			approach(new Point(prefLoc.x+parent.x,prefLoc.y+parent.y),40,1);
	}
	
	public void setCluster(VirusCluster v){
		parent = v;
	}
	public void setPrefLoc(Point p){
		prefLoc = p;
	}
	
//	public void effect(CellPart p){
//		if(p == this)
//			return;
//		
//		if(p.getClass().toString().equals("Nucleus")){
//			Nucleus n = (Nucleus)p;
//			if(distance(n) > 20 + n.radius)
//				return;
//			MembranePart closest = n.membrane.get(0);
//			double mindist = distance(closest);
//			for(MembranePart part : n.membrane){
//				double dist = distance(part);
//				if(dist < mindist){
//					mindist = dist;
//					closest = part;
//				}
//			}
//			
//			
//		}
//	}
	
}
