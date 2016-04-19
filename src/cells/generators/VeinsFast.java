package cells.generators;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import cells.CellPart;
import cells.CellRegion;
import cells.Generator;
import cells.enemyCells.MasterCell;
import cells.GoldCell;
import cells.Point;
import cells.Vector;
import cells.VeinMarker;
import cells.Virus;
import cells.VirusCluster;
import cells.WallCellFast;

public class VeinsFast implements Generator {
	public static int shift = 5000;
	public static int xBound = 10000+shift;
	public static int yBound = 8500+shift;
	public static int xMin = 100+shift;
	public static int yMin = 100+shift;
	public Point generate(CellRegion[][] cellParts){
		
//		VeinMakerFast origin = new VeinMakerFast(270*40/2, 230*40/2, 0, 0, /*radius*/290, null, 0);
		VeinMakerFast origin = new VeinMakerFast(2000+shift, 2000+shift, 0, 0, /*radius*//*290*/250, null, 0);
		cellParts[(int)(origin.x/1000)][(int)(origin.y/1000)].add(origin);
		for(int i = 0; i < 500; i++){
			origin.construct(25);
			Rectangle rect = new Rectangle(-1,-1,0,0);
			for(CellRegion[] arr : cellParts)
				for(CellRegion r : arr)
					r.update(rect);
		}
		
		int shift = 140;
		int space = 270;
		int downSpace = 230;
		for(int i = -10; i < 50; i++){
			for(int j = -10; j < 50; j++){
				WallCellFast p = new WallCellFast(i*space+(j%2==0?shift:0) + this.shift,j*downSpace + this.shift, 150);
				//p.activate(false);
				cellParts[(int)(p.x/1000)][(int)(p.y/1000)].add(p);
			}
		}
		
//		for(CellRegion[] arr : cellParts)
//			for(CellRegion r : arr)
//				r.update(rect);
		
		for(int i = 0; i < 22; i++){
			Rectangle rect = new Rectangle(-1,-1,0,0);
			for(CellRegion[] arr : cellParts)
				for(CellRegion r : arr)
					r.update(rect);
			//System.out.println(i/22.0*100 + "%");
		}
//		VirusCluster v2 = new VirusCluster(origin.x+500,origin.y+500);
//		v2.add(new Virus(v2.x+5,v2.y+5));
//		CellPart.putInGrid(v2, cellParts);
		
		CellPart.putInGrid(new MasterCell(origin.constructMarkers()),cellParts);
		Point furthest = origin.getFurthest().getPoint();
		origin.die();
		VirusCluster virus = new VirusCluster(furthest.x,furthest.y);
		//CellPart.putInGrid(new GoldCell(virus.x-50,virus.y,null,15), cellParts);
		for(int i = 0; i < 5; i++)
			virus.add(new Virus(virus.x,virus.y));
		
		CellPart.putInGrid(virus, cellParts);
		
		return furthest;
		//Generate border
//		for(int i = 1 ; i < 40; i++){
//			WallCellFast p = new WallCellFast(i*space,downSpace, 150);
//			p.activate(false);
//			cellParts[(int)(p.x/1000)][(int)(p.y/1000)].add(p);
//			WallCellFast p2 = new WallCellFast(i*space+(39%2==0?shift:0),39*downSpace, 150);
//			p2.activate(false);
//			cellParts[(int)(p2.x/1000)][(int)(p2.y/1000)].add(p2);
//			
//			p = new WallCellFast(space*39, i*downSpace, 150);
//			p.activate(false);
//			cellParts[(int)(p.x/1000)][(int)(p.y/1000)].add(p);
//		}
	}
}

class VeinMakerFast extends CellPart{
	boolean isDead = false;
	
	int depth;
	double radius;
	int iterations;
	VeinMakerFast parent;
	
	VeinMakerFast[] branches;
	
	ArrayList<WallCellFast> killList = new ArrayList<WallCellFast>();
	public VeinMakerFast(double x, double y, double dx, double dy, double radius, VeinMakerFast parent, int depth){
		super(x,y,dx,dy);
		this.radius = radius;
		this.parent = parent;
		this.depth = depth;
	}
	
	public void update(){
		super.update();
		if(branches != null)
			for(int i = 0; i < branches.length; i++){
				if(branches[i] != null && branches[i].isDead)
					branches[i] = null;
			}
		if(!isDead){
			for(WallCellFast w : killList)
				w.die();
		}
		
//		if(branches != null){
//			boolean found = false;
//			for(VeinMakerFast v : branches)
//				if(v != null)
//					found = true;
//			if(!found)
//				branches = null;
//		}
	}
	public void effect(CellPart p){
		if(p == this)
			return;
		
		if(p instanceof VeinMakerFast && p != parent){
			VeinMakerFast v = (VeinMakerFast)p;
			if(distance(v)*.65 < this.radius + v.radius && this.depth >= v.depth){
				die();
			}
			
		}
		
		if(p instanceof WallCellFast){
			WallCellFast w = (WallCellFast)p;
			double dist = distance(w);
			if(parent != null){
				if(dist < this.radius/2 + w.getRadius()){
					killList.add(w);
				}else{
					w.limitDist(this.getPoint(), this.radius/2+w.getRadius());
				}
			}else{
				if(dist < this.radius*2 + w.getRadius()){
					killList.add(w);
				}else{
					w.limitDist(this.getPoint(), this.radius/2+w.getRadius());
				}
			}
		}
	}
	public void drawSelfOn(Graphics2D g2){
		
		g2.setColor(Color.RED);
		if(depth == 0)
			g2.setColor(Color.BLUE);
		radius /= 2;
		g2.drawOval((int)(x-radius), (int)(y-radius), (int)radius*2, (int)radius*2);
		radius *= 2;
		if(branches != null)
			for(VeinMakerFast v : branches){
				if(v != null)
					g2.drawLine((int)x, (int)y, (int)v.x, (int)v.y);
			}
	}
	public void construct(int maxDepth){
		if(depth >= maxDepth)
			return;
		if(isDead)
			return;
		else if (branches == null){
			branches = new VeinMakerFast[3];
			for(int i = 0 ; i < branches.length; i++){
				genBranch(i);
			}
		}else{
			for(int i = 0; i < branches.length; i++){
				if(branches[i] != null)
					branches[i].construct(maxDepth);
				else{
					genBranch(i);
				}
			}
		}
	}
	private void genBranch(int i){
		Vector dir = new Vector(Math.random()-.5,Math.random()-.5);
		dir.normalize();
		double rad = this.radius*( i==0 ? 1 : 1);
		dir.multiply(this.radius+rad);
		VeinMakerFast vein = new VeinMakerFast(x+dir.dx,y+dir.dy, 0,0, rad, this, this.depth+1);
		if(vein.x < VeinsFast.xBound && vein.y < VeinsFast.yBound && vein.x > VeinsFast.xMin && vein.y > VeinsFast.yMin){
			branches[i] = vein;
			super.chunk.add(branches[i]);
		}
	}
	public void die(){
		super.die();
		isDead = true;
		if(branches != null)
			for(VeinMakerFast v : branches)
				if(v != null)
					v.die();
	}
	public VeinMarker constructMarkers(){
		ArrayList<VeinMarker> markers = new ArrayList<VeinMarker>();
		if(branches != null)
			for(VeinMakerFast vein : branches)
				if(vein != null)
					markers.add(vein.constructMarkers());
		VeinMarker[] markersArr = new VeinMarker[markers.size()];
		for(int i = 0; i < markersArr.length; i++){
			markersArr[i] = (VeinMarker)(markers.get(i));
		}
		
		
		VeinMarker v = new VeinMarker(x,y,null,markersArr);
		for(VeinMarker m : markersArr)
			m.setParent(v);
		super.chunk.add(v);
		return v;
	}
	
	public VeinMakerFast getFurthest(){
		VeinMakerFast furthest = this;
		if(branches != null)
			for(VeinMakerFast v : branches){
				if(v != null){
					VeinMakerFast subFurthest = v.getFurthest();
					if(subFurthest == null)
						continue;
					
					if(furthest == null || furthest.depth < subFurthest.depth){
						furthest = subFurthest;
						
					}
				}
			}
		return furthest;
	}
	
	
}