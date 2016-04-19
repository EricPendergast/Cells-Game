package render3D.support;

import input.Keyboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import render3D.RenderNode;
import render3D.Viewer;

public class Polygon3D implements Comparable{
	//Should be 3x3
	//Each row is a different point
	private double[][] locations;
	private int[] chunkLoc = null;
	protected Color color;
	protected PolygonChunk[][] container;
	//This is where it's distance away from the viewer will be temporarily stored
	public double distance = 0;
	boolean isSelected = false;
	public Polygon3D(){
		this(Opp.newPolygon(0, 0, 0, 0, 0, 0, 0, 0, 0), Color.BLACK);
	}
	public Polygon3D(double[][] locations, Color color){
		this.locations = locations;
		this.color = color;
	}
	
	public void update(){
//		if(Keyboard.keys[KeyEvent.VK_W]){
//			locations[0][1]++;
//			locations[1][1]++;
//			locations[2][1]++;
//		}
		if(Keyboard.keys[KeyEvent.VK_Q])
			asdf();
	}
	//Changes which chunk it's in depending on it's location
	public void updateChunk(){
		if(container == null)
			return;
		int[] newChunkLoc = {(int)(locations[0][0]/RenderNode.chunkSize), (int)(locations[0][1]/RenderNode.chunkSize)};
		//If the chunk changed, it will move it to the new chunk
		if(!(chunkLoc[0] == newChunkLoc[0] && chunkLoc[1] == newChunkLoc[1])){
			container[chunkLoc[0]][chunkLoc[1]].remove(this);
			if(container[newChunkLoc[0]][newChunkLoc[1]] == null)
				container[newChunkLoc[0]][newChunkLoc[1]] = new PolygonChunk();
			container[newChunkLoc[0]][newChunkLoc[1]].add(this);
			chunkLoc[0] = newChunkLoc[0];
			chunkLoc[1] = newChunkLoc[1];
		}
	}
	public void drawSelfOn(Graphics2D g2, Viewer view){
//		if(!(view.isInFront(locations[0]) && view.isInFront(locations[1]) && view.isInFront(locations[2])))
//			return;
		double[][] arr = {view.project(locations[0]), view.project(locations[1]), view.project(locations[2])};
		if(arr[0] == null || arr[1] == null || arr[2] == null)
			return;
		int[] x = {(int)arr[0][0], (int)arr[1][0], (int)arr[2][0]};
		int[] y = {(int)arr[0][1], (int)arr[1][1], (int)arr[2][1]};
		g2.setColor(color);
		//g2.fillPolygon(x,y,3);
		fillPolygon(g2,x,y);
		if(isSelected)
			g2.setStroke(new BasicStroke(5));
		else
			g2.setStroke(new BasicStroke(1));
		
		g2.setColor(Color.BLACK);
		drawPolygon(g2,x,y);
		
		//g2.drawString(Opp.arrToString(chunkLoc), y[0],x[0]);
		//g2.drawString("("+locations[1][0]+", "+locations[1][1]+", " +locations[1][2]+")", y[1],x[1]);
		//g2.drawString("("+locations[2][0]+", "+locations[2][1]+", " +locations[2][2]+")", y[2],x[2]);
		
		////
		//g2.drawString(Opp.arrToString(view.getChunk()), 0, 12);
		//g2.drawString(Opp.arrToString(view.getLocation()), 0, 24);
		////
		
//		if(color.equals(Color.RED))
//			System.out.println("Polygon3d" + " red");
	}
	
	public double[] getAveragePoint(){
		return Opp.averagePoint(locations);
	}

	public int compareTo(Object polygon) {
		if(!(polygon instanceof Polygon3D))
			return 0;
		
		if(distance > ((Polygon3D)polygon).distance)
			return -1;
		if(distance < ((Polygon3D)polygon).distance)
			return 1;
		
		return 0;
	}
	public void drawPolygon(Graphics2D g2, int[] x, int[] y){
		//Polygon sd = new Polygon(y,x,x.length);
		g2.draw(new Polygon(y,x,x.length));
	}
	public void fillPolygon(Graphics2D g2, int[] x, int[] y){
		g2.fill(new Polygon(y,x,x.length));
	}
	public void putSelfInGrid(PolygonChunk[][] container){
		this.container = container;
		chunkLoc = new int[2];
		chunkLoc[0] = (int)(locations[0][0]/RenderNode.chunkSize);
		chunkLoc[1] = (int)(locations[0][1]/RenderNode.chunkSize);
		PolygonChunk c = container[chunkLoc[0]][chunkLoc[1]];
		if(c == null){
			container[chunkLoc[0]][chunkLoc[1]] = new PolygonChunk(this);
		}else{
			c.add(this);
		}
		//updateChunk();
	}
	public void rotateRelative(double[] p, double x, double y, double z){
		for( int i = 0; i < locations.length; i++){
			double[] vec = Opp.subtract(locations[i], p);
			vec = Opp.rotate(vec, Opp.x, x);
			vec = Opp.rotate(vec, Opp.y, y);
			vec = Opp.rotate(vec, Opp.z, z);
			locations[i] = Opp.add(p, vec);
		}
		updateChunk();
	}
	public void move(double x, double y, double z){
		for(int i = 0; i < locations.length; i++)
			locations[i] = Opp.add(locations[i], Opp.newArr(x,y,z,0));
	}
	public void setSelected(boolean s){
		isSelected = s;
	}
	public void asdf(){
		locations[0][0]++;
		locations[1][0]++;
		locations[2][0]++;
		updateChunk();
	}
	public double getDistance() {return distance;}
	public void setDistance(double dist) {distance = dist;}
}
