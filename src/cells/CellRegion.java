package cells;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;

public class CellRegion extends CellPart{
	private int LU=0,U=1,RU=2,L=3,R=4,LD=5,D=6,RD=7;
	private CellRegion[] regions = new CellRegion[8];
	private LinkedList<CellPart> cellParts;
	private LinkedList<CellPart> temporaryCells;
	Color rand = new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
	private double dim;
	public CellRegion(int x, int y, double d){
		cellParts = new LinkedList<CellPart>();
		temporaryCells = new LinkedList<CellPart>();
		this.x = x;
		this.y = y;
		dim = d;
	}
	public void findPartners(CellRegion[][] c){
		for(CellRegion[] arr : c)
			for(CellRegion r : arr){
				if(r.x == x-1 && r.y == y+1)
					regions[LU] = r;
				else if(r.x == x && r.y == y+1)
					regions[U] = r;
				else if(r.x == x+1 && r.y == y+1)
					regions[RU] = r;
				else if(r.x == x-1 && r.y == y)
					regions[L] = r;
				else if(r.x == x+1 && r.y == y)
					regions[R] = r;
				else if(r.x == x-1 && r.y == y-1)
					regions[LD] = r;
				else if(r.x == x && r.y == y-1)
					regions[D] = r;
				else if(r.x == x+1 && r.y == y-1)
					regions[RD] = r;
			
		}
	}
	public void drawSelfOn(Graphics2D g2){
		for(CellPart p : cellParts)
			p.drawSelfOn(g2);
		g2.setColor(Color.BLACK);
//		for(CellRegion r : regions){
//			if(r != null)
//				g2.drawLine((int)(x*dim), (int)(y*dim), (int)(r.x*dim), (int)(r.y*dim));
//		}
		
	}
	public void add(CellPart p){
		p.chunk = this;
		cellParts.add(0,p);
	}
	//this is used for adding cells to this chunk from a cell in this chunk 
	public void addFromCell(CellPart p){
		p.chunk = this;
		temporaryCells.add(0,p);
	}
	public void update(Rectangle hitbox){
		cellParts.addAll(temporaryCells);
		temporaryCells.clear();
		//determines whether to do optimized or fancy collisions
		for(CellPart p : cellParts)
			p.setOptimized(!p.isContained(hitbox));
		
		
		for(CellPart p : cellParts){
			for(CellPart p2 : cellParts)
				p.effect(p2);
			for(CellRegion nextTo : regions){
				if(nextTo != null)
					for(CellPart p2 : nextTo.cellParts)
						p.effect(p2);
			}
		}
		//iterates through celParts and moves cells to other regions if outside of this region
		Iterator<CellPart> it = cellParts.iterator();
		while(it.hasNext()){
			CellPart p = (CellPart)(it.next());
			p.update();
			if(!contains(p)){
				it.remove();
				for(CellRegion r : regions){
					if(r != null && r.contains(p)){
						r.add(p);
					}
				}
			}
		}
	}
	public boolean contains(CellPart p){
		if(	p.x > x*dim && p.x <= (x+1)*dim && 
			p.y > y*dim && p.y <= (y+1)*dim){
			return true;
		}
		return false;
	}
	public int size(){
		return cellParts.size();
	}
	public void select(Rectangle area){
		for(CellPart p : cellParts)
			p.select(area);
	}
	public boolean selectVirus(Point p){
		boolean found = false;
		for(CellPart part : cellParts)
			if(part instanceof VirusCluster){
				if(found){
					((VirusCluster)part).isSelected = false;
					continue;
				}
				
				if(((VirusCluster)part).select(p))
					found = true;
			}
		return found;
	}
	public VirusCluster getNearest(Point p){
		VirusCluster nearest = null;
		double smallestDist = 1000;
		for(CellPart part : cellParts)
			if(part instanceof VirusCluster){
				part.isSelected = false;
				double dist = Point.distance(p, part.getPoint());
				if(dist < smallestDist){
					nearest = (VirusCluster)part;
					smallestDist = dist;
				}
			}
		return nearest;
	}
}
