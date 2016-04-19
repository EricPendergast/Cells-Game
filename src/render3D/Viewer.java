package render3D;

import input.Keyboard;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import nodes.Node;

import render3D.support.*;
import states.Game;

//public class Viewer {
//	double scale = 100;
//	double perspective = 6.5;
//	ArrayList<Polygon3D> polygons;
//	//Containing node
//	Node containingNode;
//	double[] viewerLocation = {-90,69,53};
//	double[] directionLook = {1,0,0};
//	double c = 1000000;
//	double height = c;
//	double width = c;
//	double[] vecTopLeft = {1, c/2, c/2};
//	double[] vecBottomLeft = {1, -c/2, c/2};
//	Plane planeOV;
//	public Viewer(ArrayList<Polygon3D> polygons, Node containingNode){
//		this.polygons = polygons;
//		this.containingNode = containingNode;
//		planeOV = new Plane();
//		calculatePlaneOV();
//	}
//	
//	public double[] get2DLocationOf3DPoint(double[] point){
//		
//		//finds where point intersects the plane
//		double[] tempPOV = Opp.subtract(viewerLocation, Opp.multiply(directionLook, perspective));
//		//System.out.println(Opp.arrToString(tempPOV));
//		Line l = new Line(tempPOV,point);
//		double[] intersection = planeOV.findIntersect(l);
//		
//		
//		double l2 = Opp.distanceBetween(intersection, Opp.add(viewerLocation, vecTopLeft));
//		double l3 = Opp.distanceBetween(intersection, Opp.add(viewerLocation, vecBottomLeft));
//		
//		double s = (height+l2+l3)/2;
//		double x = Math.sqrt(Math.abs(s*(s-height)*(s-l2)*(s-l3)))/(.5*height);
//		double y = Math.sqrt(l3*l3-x*x);
//		
//
//		Rectangle r = containingNode.getHitbox();
//		//System.out.println(l + " " + Opp.arrToString(intersection));
//
//		x = x-(width/2-r.width/2);
//		y = y-(height/2-r.height/2);
//		x = (x-r.width/2)*scale + r.width/2;
//		y = (y-r.height/2)*scale + r.height/2;
//		y = -y +r.width;
//		
//		double[] ret = {x,y};
//		
//		return ret;
//		
//		
////		//distance from i to POV+vec2
////		double l2 = Line3D.getLength(i, new Point3D(ViewerInfo.pointOV.getX()+ViewerInfo.vec2.getX(), ViewerInfo.pointOV.getY()+ViewerInfo.vec2.getY(), ViewerInfo.pointOV.getZ()+ViewerInfo.vec2.getZ()));
////		//distance from i to POV+vec3
////		double l3 = Line3D.getLength(i, new Point3D(ViewerInfo.pointOV.getX()+ViewerInfo.vec3.getX(), ViewerInfo.pointOV.getY()+ViewerInfo.vec3.getY(), ViewerInfo.pointOV.getZ()+ViewerInfo.vec3.getZ()));
////		
////		double s = (height+l2+l3)/2;
////		double x = Math.sqrt(Math.abs(s*(s-height)*(s-l2)*(s-l3)))/(.5*height);
////		double y = Math.sqrt(l3*l3-x*x);
////		
////		x = x-(width/2-Game.width/2);
////		y = y-(height/2-Game.height/2);
////		x = (x-Game.width/2)*ViewerInfo.scale + Game.width/2;
////		y = (y-Game.height/2)*ViewerInfo.scale + Game.height/2;
////		y = -y+Game.height;
////		
////		return new Point2D.Double(x,y);
//		
//	}
//	public void update(){
////		if(Keyboard.keys[KeyEvent.VK_W]){
////			viewerLocation[0]++;
////		}
////		if(Keyboard.keys[KeyEvent.VK_S]){
////			viewerLocation[0]--;
////		}
////		if(Keyboard.keys[KeyEvent.VK_A]){
////			viewerLocation[1]++;
////		}
//		if(Keyboard.keys[KeyEvent.VK_F]){
//			perspective*=1.01;
//			System.out.println(perspective);
//			//scale*=.99;
//		}
////		calculatePlaneOV();
//		sort(polygons);
//	}
//	public void calculatePlaneOV(){
//		planeOV.reset(directionLook, viewerLocation);
//	}
//	//Sorts the polygons in order of their distance away from the viewer
//	public void sort(ArrayList<Polygon3D> list){
//		for(Polygon3D p : list)
//			p.distance = Opp.comparitiveDistance(viewerLocation, p.getAveragePoint());
//		
//		Collections.sort(list);
//	}
//	public boolean isInFront(double[] point){
//		return Opp.comparitiveDistance(point,viewerLocation) > Opp.comparitiveDistance(point, Opp.add(viewerLocation, directionLook));
//	}
//}
public class Viewer {
	double scale = 123;
	double perspective = 6.5;
	PolygonChunk[][] polygons;
	//Containing node
	Node containingNode;
	double c = 1000000;
	double height = c;
	double width = c;
	
	double[][] coordSys;
	double[] location = {0,0,0,1};
	double[] direction = {1,0,0,0};
	public Viewer(PolygonChunk[][] polygons, Node containingNode){
		this.polygons = polygons;
		this.containingNode = containingNode;
		coordSys = new double[4][4];
		coordSys[0][0] = 1;
		coordSys[1][1] = 1;
		coordSys[2][2] = 1;
		coordSys[3][3] = 1;
		//coordSys[0][3] = 123;
	}
	
	public double[] project(double[] point){
		double[] newCoordSys = Opp.vecMatrixMult(point, coordSys);
		if(newCoordSys[0] <  0)
			return null;
		double[] p2D = new double[3];
		Rectangle r = containingNode.getHitbox();
		p2D[1] = newCoordSys[1]/(newCoordSys[0]/perspective)*scale + r.width/2;
		p2D[0] = r.height/2-newCoordSys[2]/(newCoordSys[0]/perspective)*scale;
		p2D[2] = 1;
//		System.out.println(Opp.arrToString(coordSys[0]));
//		System.out.println(Opp.arrToString(coordSys[1]));
//		System.out.println(Opp.arrToString(coordSys[2]));
//		System.out.println(Opp.arrToString(coordSys[3]));
		//System.out.println(coordSys[0][3] + " " + coordSys[1][3] + " " + coordSys[2][3]);
//		System.out.println();
		return p2D;
	}
	public void update(){
//		if(Keyboard.keys[KeyEvent.VK_W]){
//			viewerLocation[0]++;
//		}
//		if(Keyboard.keys[KeyEvent.VK_S]){
//			viewerLocation[0]--;
//		}
//		if(Keyboard.keys[KeyEvent.VK_A]){
//			viewerLocation[1]++;
//		}
		if(Keyboard.keys[KeyEvent.VK_F]){
			perspective*=1.01;
			//System.out.println(perspective);
			//scale*=.99;
		}
//		calculatePlaneOV();
		sort(polygons);
	}
	//Sorts the polygons in order of their distance away from the viewer
	public void sort(PolygonChunk[][] list){
		for(PolygonChunk[] a : list){
			for(PolygonChunk chunk : a){
				if(chunk != null){
					ArrayList<Polygon3D> polygonsInChunk = chunk.getPolygons();
					for(Polygon3D p : polygonsInChunk){
						p.distance = Opp.comparitiveDistance(location, p.getAveragePoint());
					}
					
					Collections.sort(polygonsInChunk);
				}
			}
		}
				//p.distance = Opp.comparitiveDistance(viewerLocation, p.getAveragePoint());
		
		//Collections.sort(list);
	}
	public boolean isInFront(double[] point){
		return Opp.comparitiveDistance(point,location) < Opp.comparitiveDistance(point, Opp.add(location, direction));
		//return true;
	}
	public int[] getChunk(){
		
		int[] ret = {(int)(location[0]/RenderNode.chunkSize),(int)(location[1]/RenderNode.chunkSize),1};
		return ret;
	}
	public double[] getLocation(){
		return location;
	}
}
