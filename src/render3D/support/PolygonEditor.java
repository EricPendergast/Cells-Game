package render3D.support;

import input.Keyboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import render3D.Viewer;

public class PolygonEditor extends PolygonControler{
	ArrayList<Polygon3D> polys = new ArrayList<Polygon3D>();
	Viewer view;
	int selected = 0;
	int action = 1;

	public PolygonEditor(ArrayList<Polygon3D> polys, PolygonChunk[][] container, Viewer v){
		this.polys = polys;
		super.container = container;
		for(Polygon3D p : polys)
			p.putSelfInGrid(container);
		view = v;
		for(int i = 0; i < 10000; i++){
			Polygon3D p = new Polygon3D(Opp.newPolygon(100,0,0,0,0,100,0,-100,0),Color.DARK_GRAY);
			polys.add(p);
			p.putSelfInGrid(container);
		}
	}
	public void drawSelfOn(Graphics2D g2, Viewer view){
		switch(action){
		case 0:
			g2.drawString("Action: Rotate",0,50);
			break;
		case 1:
			g2.drawString("Action: Move",0,50);
		}
		//draw axis
//			g2.setStroke(new BasicStroke(3));
//			//double[] mid = Opp.add(view.getLocation(), view.)
//			double[] mid2D = view.project(mid);
//			double[] xVec = view.project(Opp.add(mid,Opp.multiply(Opp.x,50)));
//			double[] yVec = view.project(Opp.add(mid,Opp.multiply(Opp.y,50)));
//			double[] zVec = view.project(Opp.add(mid,Opp.multiply(Opp.z,50)));
//			g2.setColor(Color.RED);
//			g2.drawLine((int)mid2D[1], (int)mid2D[0], (int)xVec[1], (int)xVec[0]);
//			g2.setColor(Color.GREEN);
//			g2.drawLine((int)mid2D[1], (int)mid2D[0], (int)yVec[1], (int)yVec[0]);
//			g2.setColor(Color.BLUE);
//			g2.drawLine((int)mid2D[1], (int)mid2D[0], (int)zVec[1], (int)zVec[0]);
		
	}
	public void updateChunk(){
		for(Polygon3D p : polys)
			p.updateChunk();
	}
	boolean last = false;
	boolean t = false;
	boolean n = false;
	public void update(){
		if(Keyboard.keys[KeyEvent.VK_R]){
			for(Polygon3D p : polys){
				p.rotateRelative(p.getAveragePoint(), (Math.random()-.5)*.2, (Math.random()-.5)*.2, (Math.random()-.5)*.2);
			}
		}
		if(t != (t = Keyboard.keys[KeyEvent.VK_T]) && t)
			action = (action+1)%2;
		if(n != (n = Keyboard.keys[KeyEvent.VK_N]) && n){
			Polygon3D p = new Polygon3D(Opp.newPolygon(100,0,0,0,0,100,0,-100,0),Color.DARK_GRAY);
			polys.add(p);
			p.putSelfInGrid(container);
		}
		if(polys.size() > 0 && last != (last = Keyboard.keys[KeyEvent.VK_CONTROL]) && last == true){
			//System.out.println("asdf");
			polys.get(selected).setSelected(false);
			selected++;
			selected = selected%polys.size();
			polys.get(selected).setSelected(true);
		}
		if(polys.size() > 0){
			double rot = .01;
			double m = 1;
			Polygon3D p = polys.get(selected);
			double[] center = p.getAveragePoint();
			if(Keyboard.keys[KeyEvent.VK_R]){
				
			}else if(Keyboard.keys[KeyEvent.VK_NUMPAD7]){//7
				if(action == 0)
					p.rotateRelative(center,rot,0,0);
				if(action == 1)
					p.move(m, 0, 0);
			}else if(Keyboard.keys[KeyEvent.VK_NUMPAD9]){//9
				if(action == 0)
					p.rotateRelative(center,-rot,0,0);
				if(action == 1)
					p.move(-m, 0, 0);
			}else if(Keyboard.keys[KeyEvent.VK_NUMPAD1]){//8
				if(action == 0)
					p.rotateRelative(center,0,rot,0);
				if(action == 1)
					p.move(0, m, 0);
			}else if(Keyboard.keys[KeyEvent.VK_NUMPAD3]){//2
				if(action == 0)
					p.rotateRelative(center,0,-rot,0);
				if(action == 1)
					p.move(0, -m, 0);
			}else if(Keyboard.keys[KeyEvent.VK_NUMPAD8]){//1
				if(action == 0)
					p.rotateRelative(center,0,0,rot);
				if(action == 1)
					p.move(0, 0, m);
			}else if(Keyboard.keys[KeyEvent.VK_NUMPAD2]){//3
				if(action == 0)
					p.rotateRelative(center,0,0,-rot);
				if(action == 1)
					p.move(0, 0, -m);
			}
		}
		if(Keyboard.keys[KeyEvent.VK_O]){
			System.out.println("asdf");
			for(int i = -0; i < 20; i++){
				for(int j = -0; j < 20; j++){
					double scale = 100;
					double w = 90;
					Polygon3D p = new Polygon3D(Opp.newPolygon(i*scale,j*scale,-5,  i*scale+w,j*scale,-5,  i*scale,j*scale+w,-5),Color.DARK_GRAY);
					Polygon3D p2 = new Polygon3D(Opp.newPolygon(i*scale+w,j*scale+w,-5,  i*scale+w,j*scale,-5,  i*scale,j*scale+w,-5),Color.DARK_GRAY);
					polys.add(p);
					p.putSelfInGrid(container);
					polys.add(p2);
					p2.putSelfInGrid(container);
				}
			}
		}
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
