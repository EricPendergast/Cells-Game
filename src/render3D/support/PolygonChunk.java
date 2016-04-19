package render3D.support;

import java.util.ArrayList;

public class PolygonChunk {
	private ArrayList<Polygon3D> polygons;
	public PolygonChunk(){
		polygons = new ArrayList<Polygon3D>();
	}
	public PolygonChunk(Polygon3D ... polygons){
		this();
		for(Polygon3D p : polygons)
			add(p);
	}
	public void add(Polygon3D p){
		polygons.add(p);
	}
	public void remove(Polygon3D p){
		polygons.remove(p);
	}
	public ArrayList<Polygon3D> getPolygons(){
		return polygons;
	}
	public boolean isEmpty(){
		return polygons.isEmpty();
	}
	public int size(){
		return polygons.size();
	}
}
