package render3D;

import input.Mouse;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import render3D.support.*;

import nodes.*;

//public class RenderNode extends Node{
//	Viewer view;
//	ArrayList<Polygon3D> polygons;
//	public RenderNode(ArrayList<Polygon3D> polygons){
//		this.polygons = polygons;
//		view = new ControllableViewer(polygons,this);
//		hitbox = new Rectangle(0,0,Mouse.getFrame().getWidth(), Mouse.getFrame().getHeight());
//	}
//	public void drawOn(Graphics2D g2) {
//		//g2.drawString("asdf", 0, 20);
//		for(Polygon3D p : polygons){
//			p.drawSelfOn(g2, view);
//		}
//	}
//
//	public void update(Boolean onClick) {
//		for(Polygon3D p : polygons){
//			p.update();
//		}
//		view.update();
//	}
//}
public class RenderNode extends Node{
	Viewer view;
	PolygonChunk[][] polygons;
	public static final double chunkSize = 100;
	public RenderNode(PolygonChunk[][] polygons){
		this.polygons = polygons;
		view = new ControllableViewer(polygons,this);
		hitbox = new Rectangle(0,0,Mouse.getFrame().getWidth(), Mouse.getFrame().getHeight());
	}
	public RenderNode(PolygonChunk[][] polygons, Viewer v){
		this.polygons = polygons;
		view = v;
		hitbox = new Rectangle(0,0,Mouse.getFrame().getWidth(), Mouse.getFrame().getHeight());
	}
	public void drawOn(Graphics2D g2) {
		//Determines the order in which to render the chunks
		int[] chunkLoc = view.getChunk();
		//System.out.println();
		for(int i = 10; i > 0; i--){
			for(int x = 0; x < i; x++){
				renderChunk(Opp.add( chunkLoc, Opp.newIntArr(x,(i-x),0)   ), g2);
				renderChunk(Opp.add( chunkLoc, Opp.newIntArr((i-x),-x,0)  ), g2);
				renderChunk(Opp.add( chunkLoc, Opp.newIntArr(-x,-(i-x),0) ), g2);
				renderChunk(Opp.add( chunkLoc, Opp.newIntArr(-(i-x),x,0)  ), g2);
			}
		}
//		for(int i = 0; i < 100; i++){
//			for(int j = 0; j < 100; j++){
//				renderChunk(Opp.newIntArr(i,j),g2);
//			}
//		}
		renderChunk(chunkLoc, g2);
	}
	public void renderChunk(int[] chunkLoc, Graphics2D g2){
		if(chunkLoc[0] < 0 || chunkLoc[0] > polygons.length || chunkLoc[1] < 0 || chunkLoc[1] > polygons[0].length)
			return;
		PolygonChunk chunk = polygons[chunkLoc[0]][chunkLoc[1]];
		if(chunk == null)
			return;
		for(Polygon3D p : chunk.getPolygons())
			p.drawSelfOn(g2,view);
		
	}
	public void update(Boolean onClick) {
		for(PolygonChunk[] p : polygons)
			for(PolygonChunk chunk : p)
				if(chunk != null){
					ArrayList<Polygon3D> list = chunk.getPolygons();
					for(int i = list.size()-1; i >= 0; i--){
						list.get(i).update();
					}
				}
		view.update();
	}
}

