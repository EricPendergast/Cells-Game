package render3D;

import java.awt.Color;
import java.util.ArrayList;

import render3D.support.*;
import states.*;

public class _RenderTest {
	public static void main(String[] args){
		Game game = new Game(1000,700,1);
		NodeState n = new NodeState();
		game.addState(n);
		PolygonChunk[][] polygons = new PolygonChunk[100][100];
		
		RenderNode node = new RenderNode(polygons);
		ControllableViewer v = new ControllableViewer(polygons, node);
		n.addNode(node);
		//polygons[0][0] = new PolygonChunk();
		//polygons[0][0].add(new Polygon3D(Opp.newPolygon(10,0,50,10,0,0,10,-50,0),polygons, Color.RED));
		//polygons[0][1].add(new Polygon3D(Opp.newPolygon(50, 20, 40, 10, 100, 5, 12, 40, 23), polygons,Color.GREEN));
		//polygons[0][1] = new PolygonChunk(new Polygon3D(Opp.newPolygon(50, 20, 40, 10, 100, 5, 12, 40, 23), polygons,Color.GREEN));
		//polygons.add(new Polygon3D(Opp.newPolygon(10,0,50,10,0,0,10,-50,0),polygons, Color.RED));
		//polygons.add(new Polygon3D(Opp.newPolygon(50, 20, 40, 10, 100, 5, 12, 40, 23), polygons,Color.GREEN));
		
		//(new Polygon3D(Opp.newPolygon(10,0,50,10,0,0,10,-50,0), Color.RED)).putSelfInGrid(polygons);
		//(new Polygon3D(Opp.newPolygon(110,100,150,110,100,100,110,50,100),Color.GREEN)).putSelfInGrid(polygons);
		//(new Polygon3D(Opp.newPolygon(300,200,200,310,200,100,310,250,100),Color.YELLOW)).putSelfInGrid(polygons);
		ArrayList<Polygon3D> a = new ArrayList<Polygon3D>();
		a.add(new Polygon3D(Opp.newPolygon(10,50,0,10,-50,0,10,0,50), Color.RED));
		a.add(new Polygon3D(Opp.newPolygon(110,100,150,110,100,100,110,50,100),Color.GREEN));
		a.add(new Polygon3D(Opp.newPolygon(300,200,200,310,200,100,310,250,100),Color.YELLOW));
		//a.add(new Polygon3D(Opp.newPolygon(100,5,0,100,5,10,100,-5,0),Color.YELLOW));
		(new PolygonEditor(a, polygons, v)).putSelfInGrid(polygons);
		
		
		game.start();
	}
}