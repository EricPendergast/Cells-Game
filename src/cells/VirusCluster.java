package cells;

import input.Mouse;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

public class VirusCluster extends CellPart implements HasRadius{
	public static boolean move = false;
	public static boolean split = false;
	public static boolean increment = false;
	public static boolean combine = false;
	public static boolean highlight = false;
	public static boolean isVirusSelected = false;
	public static boolean doesVirusExist = false;
	LinkedList<Virus> viruses;
	//The average amount of space each virus takes up
	double virusSpace = 50;
	double radius;
	
	Point moveToward = null;
	//Whether or not it is searching for another virus cluster to combine with
	boolean searching = false;
	VirusCluster closest = null;
	public VirusCluster(double x, double y){
		super(x,y);
		viruses = new LinkedList<Virus>();
	}
	public VirusCluster(double x, double y, double dx, double dy){
		this(x,y);
		super.dx = dx;
		super.dy = dy;
	}
	
	public void update(){
		doesVirusExist = true;
		if(isSelected)
			isVirusSelected = true;
//		if(split)
//			moveToward = null;
		if(moveToward != null){
			if(Point.distance(moveToward, getPoint()) < 50)
				moveToward = null;
			else{
				Vector vec = Point.getDifference(getPoint(),moveToward);
				vec.normalize();
				vec.multiply(.3);
				dx += vec.dx;
				dy += vec.dy;
			}
		}
		if(closest == this){System.out.println("asdf");
			closest = null;
			combine = false;
			searching = false;
		}
		if(closest != null && (closest.isDead() || moveToward != null || closest.moveToward != null))
			closest = null;
		if(closest != null){
			searching = false;
			Vector vec = new Vector(this.x-closest.x,this.y-closest.y);
			vec.normalize();
			vec.multiply(-3);
			this.x += vec.dx;
			this.y += vec.dy;
			closest.x -= vec.dx;
			closest.y -= vec.dy;
			if(distance(closest) < (this.radius + closest.radius)*1.2){
				for(Virus v : closest.viruses)
					this.add(v);
				closest.die();
				closest = null;
				isSelected = true;
			}
		}
		if(closest == null && moveToward == null){
			dx *= .9;
			dy *= .9;
		}
		if(viruses.size() > 20)
			limitSpeed(3);
		else if(viruses.size() > 3)
			limitSpeed(4.7);
		else
			limitSpeed(7);
		super.update();
		for(Virus v : viruses)
			v.update();
		
		if(isSelected){
			if(increment){
				for(int i = 0; i < 50; i++)
					add(new Virus(x,y));
				increment = false;
			}else if(move){
				if(Mouse.button1Pressed && Mouse.button1At.x < 1000){
					moveToward = new Point(CellRunner.mousePosInWorld.x,CellRunner.mousePosInWorld.y);
					isSelected = true;
					move = false;
				}
				
			}else if(split){
				split = false;
				VirusCluster a = new VirusCluster(x,y,dx,dy);
				a.isSelected = true;
				a.moveToward = moveToward;
				VirusCluster b = new VirusCluster(x+Math.random()*100-50,y+Math.random()*100-50);
				int i = 0;
				//goes through this and adds half of its viruses to 2 different viruses
				for(Virus virus : viruses){
					if(i < viruses.size()/2)
						a.add(virus);
					else
						b.add(virus);
					i++;
				}
				chunk.addFromCell(a);
				chunk.addFromCell(b);
				this.die();
				
			}else if(combine){
				searching = true;
				isSelected = true;
				combine = false;
			}
		}else{
			searching = false;
		}
		
		if(viruses.size() == 0)
			die();
	}
	
	public void add(Virus virus){
		viruses.add(0,virus);
		virus.setCluster(this);
		Vector v = new Vector(Math.random()-.5,Math.random()-.5);
		v.normalize();
		v.multiply(radius);
		virus.setPrefLoc(new Point(v.dx,v.dy));
		virus.x = x + v.dx;
		virus.y = y + v.dy;
		radius = Math.sqrt((viruses.size()*virusSpace)/Math.PI);
	}
	
	public void effect(CellPart p){
		if(searching && p instanceof VirusCluster && Mouse.button1Pressed){
			Point mousePos = new Point(CellRunner.mousePosInWorld.x,CellRunner.mousePosInWorld.y);
			//if the mouse click is closer to p than closest
			if(closest == null || Point.distanceCompare(mousePos,closest.getPoint()) > Point.distanceCompare(mousePos,p.getPoint())){
				closest = (VirusCluster)p;
			}
		}
		
		if(p == this)
			return;
		if(p instanceof HasRadius)
			repel(p.getPoint(),200,1*(radius + ((HasRadius)p).getRadius()));
		
		if(p instanceof HasRadius){
			//repel(p.getPoint(),200,1*(radius + ((HasRadius)p).getRadius()));
			int i = 0;
			for(Virus v : viruses){
				i++;
				v.repel(p.getPoint(), 50, ((HasRadius)p).getRadius()+20);
				if(i > 500)
					break;
			}
		}
		
		if(p instanceof Infectable)
			((Infectable)p).infect(this);
		
		
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void drawSelfOn(Graphics2D g2){
		if(isSelected && move){
			g2.setColor(Color.WHITE);
			g2.setStroke(new BasicStroke(10));
			g2.drawLine((int)x,(int)y, (int)CellRunner.mousePosInWorld.x, (int)CellRunner.mousePosInWorld.y);
			g2.setStroke(new BasicStroke(1));
		}else if(moveToward != null){
			g2.setColor(Color.WHITE);
			g2.setStroke(new BasicStroke(10));
			g2.drawLine((int)x,(int)y,(int)moveToward.x,(int)moveToward.y);
			g2.setStroke(new BasicStroke(1));
		}
		if(isSelected && searching){
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(10));
			g2.drawLine((int)x,(int)y, (int)CellRunner.mousePosInWorld.x, (int)CellRunner.mousePosInWorld.y);
			g2.setStroke(new BasicStroke(1));
		}else if(closest != null){
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(10));
			g2.drawLine((int)x,(int)y,(int)closest.x,(int)closest.y);
			g2.setStroke(new BasicStroke(1));
		}
		g2.setColor(new Color(0x25,0x49,0xB8));
		
//		if(isSelected){
//			g2.setColor(new Color(0x25,0x49,0xB8,0x88));
//			radius += 10;
//			g2.fillOval((int)(x-radius),(int)(y-radius),(int)radius*2,(int)radius*2);
//			radius -= 10;
//		}
		
		if(isSelected || highlight)
			g2.setColor(new Color(0x31,0xFF,0x1F));
		
		for(Virus vir : viruses)
			vir.drawSelfOn(g2);
	}
	
	public boolean select(Point p){
		super.isSelected = Point.distance(getPoint(), p) <= radius+10;
		return isSelected;
	}
	
	public int getSize(){
		return viruses.size();
	}
	
	public void remove(){
		viruses.removeFirst();
		radius = Math.sqrt((viruses.size()*virusSpace)/Math.PI);
	}
}
