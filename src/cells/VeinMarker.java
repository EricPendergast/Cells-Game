package cells;

import java.awt.Color;
import java.awt.Graphics2D;

public class VeinMarker extends CellPart {
	VeinMarker parent;
	
	VeinMarker[] branches;
	
	int depth;
	
	public VeinMarker(double x, double y, VeinMarker parent, VeinMarker[] branches){
		this.x = x;
		this.y = y;
		dx = dy = 0;
		this.parent = parent;
		this. branches = branches;
	}
	public void update(){
	}
	public VeinMarker getParent(){
		return parent;
	}
	public VeinMarker getBranch(int index){
		try{
			if(branches != null)
				return branches[index];
		}catch(Exception e){
			return null;
		}
		return null;
	}
	public int numBranches(){
		if(branches == null)
			return 0;
		else
			return branches.length;
	}
	public int getDepth(){
		return depth;
	}
	
	public void drawSelfOn(Graphics2D g2){
		
//		g2.setColor(Color.RED);
//		if(depth == 0)
//			g2.setColor(Color.BLUE);
//		int radius = 10;
//		g2.drawOval((int)(x-radius), (int)(y-radius), (int)radius*2, (int)radius*2);
//
//		if(branches != null)
//			for(VeinMarker v : branches){
//				if(v != null)
//					g2.drawLine((int)x, (int)y, (int)v.x, (int)v.y);
//			}
//		
//		for(int i = 0; i < branches.length; i++)
//			g2.drawString("" + i, (int)branches[i].x, (int)branches[i].y);
	}
	
	public void setParent(VeinMarker v){
		parent = v;
	}
}
