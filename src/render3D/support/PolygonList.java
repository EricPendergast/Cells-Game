package render3D.support;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import render3D.RenderNode;
import render3D.Viewer;

public class PolygonList extends Polygon3D{
	//Used for sorting
	double distance;
	
	ArrayList<Polygon3D> polys = new ArrayList<Polygon3D>();
	public PolygonList(ArrayList<Polygon3D> polys){
		this.polys = polys;
	}
	public void drawSelfOn(Graphics2D g2, Viewer view){
		for(Polygon3D p : polys)
			p.drawSelfOn(g2, view);
		
	}
	public void putSelfInGrid(PolygonChunk[][] container){
		for(Polygon3D p : polys)
			p.putSelfInGrid(container);
	}
	public void updateChunk(){
		for(Polygon3D p : polys)
			p.updateChunk();
	}
	public void update(){
		for(Polygon3D p : polys)
			p.update();
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double dist) {
		distance = dist;
	}
	public double[] getAveragePoint() {
		double[] p = {0,0,0,0};
		for(Polygon3D poly : polys)
			p = Opp.add(p, poly.getAveragePoint());
		if(polys.size() == 0)
			return p;
		else
			return Opp.multiply(p,1.0/polys.size());
	}
}
