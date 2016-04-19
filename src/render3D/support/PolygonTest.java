package render3D.support;

import java.awt.Graphics2D;
import java.util.ArrayList;

import render3D.Viewer;

public class PolygonTest extends PolygonControler{
		ArrayList<Polygon3D> polys = new ArrayList<Polygon3D>();
		public PolygonTest(ArrayList<Polygon3D> polys, PolygonChunk[][] container){
			this.polys = polys;
			super.container = container;
			for(Polygon3D p : polys)
				p.putSelfInGrid(container);
		}
		public void drawSelfOn(Graphics2D g2, Viewer view){}
		public void updateChunk(){
			for(Polygon3D p : polys)
				p.updateChunk();
		}
		public void update(){
//			for(Polygon3D p : polys){
//				p.rotateRelative(this.getAveragePoint(), 0, 0, .01);
//			}
			
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
