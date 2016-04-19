package render3D;

import input.Keyboard;
import input.Mouse;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import nodes.Node;
import render3D.support.*;

public class ControllableViewer extends Viewer{
	Robot mouseControl;
	boolean running = false;
	
	double[] rightVector = {0,-1,0,0};
	double[] upVector = {0,0,1,0};
	
	double tracking = .005;
	double speed = 5;
	public ControllableViewer(PolygonChunk[][] polygons, Node containingNode) {
		super(polygons, containingNode);
		
		try {mouseControl = new Robot();} catch (AWTException e) {e.printStackTrace();}
		start();
	}
	int count = 0;
	public void update(){
		super.update();
		if(Keyboard.keys[KeyEvent.VK_A]){
			move(rightVector);
			count = 0;
		}
		if(Keyboard.keys[KeyEvent.VK_D]){
			move(Opp.multiply(rightVector,-1));
			count = 0;
		}
		if(Keyboard.keys[KeyEvent.VK_W]){
			//viewerLocation[0]++;
			move(direction);		
			count = 0;
		}
		if(Keyboard.keys[KeyEvent.VK_S]){
			//viewerLocation[0]--;
			move(Opp.multiply(direction,-1));
			count = 0;
		}
		count++;
		
		if(Keyboard.keys[KeyEvent.VK_ESCAPE])
			System.exit(0);
		if(count < 200 && running){
			Point p = MouseInfo.getPointerInfo().getLocation();
			Point center = Mouse.getFrame().getLocation();
			center.x += Mouse.getFrame().getWidth()/2;
			center.y += Mouse.getFrame().getHeight()/2;
			mouseControl.mouseMove(center.x,center.y);
			
			//System.out.println(center + " " + p);
			Point p2 = MouseInfo.getPointerInfo().getLocation();
			lookRight(tracking*(p2.x-p.x));
			lookUp(tracking*(p2.y-p.y));
		}
		//calculatePlaneOV();
	}
	public void lookRight(double rad){
		double[][] rotate = Opp.newRotationMatrix(upVector,super.location,rad);
		rightVector = Opp.rotate(rightVector, upVector, -rad);
		super.direction =  Opp.rotate(super.direction, upVector, -rad);
		super.coordSys = Opp.matrixMult(coordSys, rotate);
	}
	double degreesTurned = 0;
	public void lookUp(double rad){
		if(degreesTurned+rad <= Math.PI/2 && degreesTurned+rad >= -Math.PI/2){
			double[][] rot = Opp.newRotationMatrix(rightVector,super.location,-rad);
			super.direction = Opp.rotate(super.direction, rightVector, rad);
			super.coordSys = Opp.matrixMult(super.coordSys, rot);
			degreesTurned += rad;
		}
	}
	private void move(double[] dir){
		dir = Opp.multiply(dir, speed);
		double[][] mult = {	{1,0,0,-dir[0]},
							{0,1,0,-dir[1]},
							{0,0,1,-dir[2]},
							{0,0,0,1}};
		super.coordSys = Opp.matrixMult(super.coordSys, mult);
		//System.out.println("LOC " + Opp.arrToString(location));
		//System.out.println("arr " + coordSys[0][3] + " " + coordSys[1][3] + " " +coordSys[2][3] + " ");
		super.location = Opp.add(super.location, dir);
	}
	public void stop(){
		//Make mouse visible
		running = false;
	}
	public void start(){
		///Make mouse invisible
		running = true;
	}
}
