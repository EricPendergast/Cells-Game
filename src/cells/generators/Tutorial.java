package cells.generators;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import cells.*;
import cells.enemyCells.*;
public class Tutorial implements Generator{
	
	public Point generate(CellRegion[][] cellParts) {
		VirusCluster v;
		CellPart.putInGrid(new TextCell(4400,6000,"Welcome to CELLS", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(4400,6100,"Use right click to pan the view", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(4400,6200,"--------->", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(5100,5800,"Press \"2\" to zoom out", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(5100,5500,"Press \"1\" to zoom out more", new Font("Impact",Font.PLAIN,80)), cellParts);
		CellPart.putInGrid(new TextCell(6600,6000,"Press \"3\" to zoom back in", new Font("Impact",Font.PLAIN,160)), cellParts);
		CellPart.putInGrid(new TextCell(6800,6200,"Go in this direction ---->", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(6600,6000,"Press \"3\" to zoom back in", new Font("Impact",Font.PLAIN,160)), cellParts);

		CellPart.putInGrid(new TextCell(9000,5900,"These are your viruses", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(9000,5950,"Click on it and press \"Q\"", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(8700,6100,"It will move toward the spot you click", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(8650,6150,"Extra note: it will move faster if it is smaller", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(8900,6200,"Press \"2\"", new Font("Impact",Font.PLAIN,45)), cellParts);
		
		CellPart.putInGrid(new TextCell(9800,5800,"This is a white blood cell", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(9800,5850,"It wants to kill you", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(9710,5900,"If you get far away,", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(9710,5950,"it will stop chasing you", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(9710,6100,"and die after 30 seconds", new Font("Impact",Font.PLAIN,45)), cellParts);

		CellPart.putInGrid(new TextCell(9900,6350,"------->", new Font("Impact",Font.PLAIN,80)), cellParts);
		
		CellPart.putInGrid(new TextCell(11000,5900,"To merge two virus groups,", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(11000,5950,"select one, press \"E\" and click on the other", new Font("Impact",Font.PLAIN,45)), cellParts);
		
		CellPart.putInGrid(new TextCell(11000,6300,"To split apart a virus group, press \"w\"", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(11500,6400,"-------->", new Font("Impact",Font.PLAIN,60)), cellParts);

		
		v = new VirusCluster(11200,6100);
		for(int i = 0; i < 25; i++){
			v.add(new Virus(0,0));
		}
		CellPart.putInGrid(v, cellParts);
		
		v = new VirusCluster(11500,6100);
		for(int i = 0; i < 25; i++){
			v.add(new Virus(0,0));
		}
		CellPart.putInGrid(v, cellParts);
		
		CellPart.putInGrid(new TextCell(12300,6000,"This is a patrol cell", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(12300,6350,"If you get too close,", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(12300,6400,"it will run away and", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(12300,6450,"send white blood cells", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(12300,6500,"You can also kill it if you ", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(12300,6550,"touch it with a group larger than 5", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(12300,6600,"There will only be 20 of these,", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(12300,6650,"so if you kill all of them, you", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(12300,6700,"won't have to deal with white", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(12300,6750,"blood cells", new Font("Impact",Font.PLAIN,45)), cellParts);
		
		CellPart.putInGrid(new PatrolCell(12450,6100,null), cellParts);
		
		CellPart.putInGrid(new WhiteBloodCell(10000,6000,19), cellParts);
		
		
		CellPart.putInGrid(new TextCell(13000,6000,"This is a gold cell", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(13000,6350,"You can infect it by making", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(13000,6400,"the virus touch it", new Font("Impact",Font.PLAIN,45)), cellParts);
		
		CellPart.putInGrid(new GoldCell(13200,6100,null,30), cellParts);

		
		CellPart.putInGrid(new TextCell(13700,6000,"This is a red blood cell", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(13700,6350,"It will give you 5 viruses", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(13700,6400,"if you touch it", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new RedBloodCell(13900,6100,null), cellParts);

		CellPart.putInGrid(new TextCell(14400,6000,"This is the master cell", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(14400,6650,"You need to kill it to win", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(14400,6700,"If you touch it, it will", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(14400,6750,"absorb your viruses and shrink", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new TextCell(14400,6800,"Once it has absorbed 250 viruses, it will die", new Font("Impact",Font.PLAIN,45)), cellParts);
		CellPart.putInGrid(new MasterCell(new VeinMarker(14600,6300, null, null)), cellParts);
		
		CellPart.putInGrid(new TextCell(15000,6300,"Hit ESC to go back to the main menu", new Font("Impact",Font.PLAIN,80)), cellParts);

		v = new VirusCluster(9300,6000);
		for(int i = 0; i < 25; i++){
			v.add(new Virus(0,0));
		}
		CellPart.putInGrid(v, cellParts);
		//CellPart.putInGrid(new WallCellFast(6000,6000,100),cellParts);
		
		double x = 10000;
		double y = 6000;
		double m = 20;
		double radius = Math.PI*2/m;
		double dist = (150*m)/(2*Math.PI);
		for(int i = 0; i < m; i++){
			double r = radius * i;
			double xc = -Math.sin(r)*dist + x;
			double yc = Math.cos(r)*dist + y;
			//membrane.add(new MembranePart(xc,yc,membrane,new Point(xc-x,yc-y),this));
			CellPart.putInGrid(new WallCellFast(xc,yc,100),cellParts);
		}
//		int shift = 140;
//		int space = 270;
//		int downSpace = 230;
//		for(int i = 1 ; i < 40; i++){
//			WallCellFast p = new WallCellFast(3800 + i*space,3800 + downSpace, 150);
//			p.activate(false);
//			cellParts[(int)(p.x/1000)][(int)(p.y/1000)].add(p);
//			WallCellFast p2 = new WallCellFast(3800 + i*space+(39%2==0?shift:0),3800 + 39*downSpace, 150);
//			p2.activate(false);
//			cellParts[(int)(p2.x/1000)][(int)(p2.y/1000)].add(p2);
//			
//			p = new WallCellFast(3800 + space*39, 3800 + i*downSpace, 150);
//			p.activate(false);
//			cellParts[(int)(p.x/1000)][(int)(p.y/1000)].add(p);
//		}
		return new Point(4500,6000);
	}
	
}

class TextCell extends CellPart{
	String text;
	Font font;
	public TextCell(double x, double y, String text, Font f){
		super(x,y);
		this.text = text;
		this.font  = f;
	}
	
	public void drawSelfOn(Graphics2D g2){
		g2.setColor(Color.BLACK);
		g2.setFont(font);
		g2.drawString(text, (int)x, (int)y);
	}
}