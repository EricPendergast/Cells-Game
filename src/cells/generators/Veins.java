package cells.generators;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import cells.CellPart;
import cells.CellRegion;
import cells.Generator;
import cells.Point;
import cells.Vector;
import cells.WallCell;

public class Veins implements Generator {
	
	public Point generate(CellRegion[][] cellParts){
		
		VeinMaker origin = new VeinMaker(270*40/2, 230*40/2, 0, 0, /*radius*/300, null, 0);
		cellParts[(int)(origin.x/1000)][(int)(origin.y/1000)].add(origin);
		for(int i = 0; i < 50; i++){
			origin.construct(10);
			Rectangle rect = new Rectangle(-5000,-5000,0,0);
			for(CellRegion[] arr : cellParts)
				for(CellRegion r : arr)
					r.update(rect);
		}
		
		int shift = 140;
		int space = 270;
		int downSpace = 230;
		for(int i = 1; i < 40; i++){
			for(int j = 1; j < 40; j++){
				WallCell p = new WallCell(i*space+(j%2==0?shift:0),j*downSpace,80);
				p.activate(false);
				cellParts[(int)(p.x/1000)][(int)(p.y/1000)].add(p);
			}
		}
//		for(CellRegion[] arr : cellParts)
//			for(CellRegion r : arr)
//				r.update(rect);
		
		for(int i = 0; i < 22; i++){
			Rectangle rect = new Rectangle(-5000,-5000,0,0);
			for(CellRegion[] arr : cellParts)
				for(CellRegion r : arr)
					r.update(rect);
			System.out.println(i/22.0*100 + "%");
		}
		
		return new Point(0,0);
	}
}

class VeinMaker extends CellPart{
	boolean isDead = false;
	
	int depth;
	double radius;
	int iterations;
	VeinMaker parent;
	
	VeinMaker[] branches;
	
	ArrayList<WallCell> killList = new ArrayList<WallCell>();
	public VeinMaker(double x, double y, double dx, double dy, double radius, VeinMaker parent, int depth){
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
			for(WallCell w : killList)
				w.die();
		}
	}
	public void effect(CellPart p){
		if(p == this)
			return;
		
		if(p instanceof VeinMaker && p != parent){
			VeinMaker v = (VeinMaker)p;
			if(distance(v)*.75 < this.radius + v.radius && this.depth >= v.depth){
				die();
				System.out.println("asdf" + depth + " " + v.depth);
			}
			
		}
		
		if(p instanceof WallCell){
			WallCell w = (WallCell)p;
			if(distance(w) < this.radius/2 + w.getRadius()){
				killList.add(w);
				//w.limitDist(this.getPoint(), this.radius/2+w.getRadius());
			}
			
		}
	}
	public void drawSelfOn(Graphics2D g2){
		g2.setColor(Color.RED);
		radius /= 2;
		g2.drawOval((int)(x-radius), (int)(y-radius), (int)radius*2, (int)radius*2);
		radius *= 2;
	}
	public void construct(int maxDepth){
		if(depth >= maxDepth)
			return;
		if(isDead)
			return;
		else if (branches == null){
			branches = new VeinMaker[3];
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
		double rad = this.radius*( i==0 ? 1 : .9);
		dir.multiply(this.radius+rad);
		branches[i] = new VeinMaker(x+dir.dx,y+dir.dy, 0,0, rad, this, this.depth+1);
		super.chunk.add(branches[i]);
	}
	public void die(){
		super.die();
		isDead = true;
		if(branches != null)
			for(VeinMaker v : branches)
				if(v != null)
					v.die();
	}
}