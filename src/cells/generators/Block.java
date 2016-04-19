package cells.generators;

import java.awt.Rectangle;

import cells.*;

public class Block implements Generator {
	public Point generate(CellRegion[][] cellParts) {
		int shift = 140;//80;
		int space = 270;//140;
		int downSpace = 230;//120;
		for(int i = 1; i < 40; i++){
			for(int j = 1; j < 40; j++){
				CellPart p = new WallCell(i*space+(j%2==0?shift:0),j*downSpace,80);
				cellParts[(int)(p.x/1000)][(int)(p.y/1000)].add(p);
			}
		}
		
		for(int i = 0; i < 22; i++){
			Rectangle rect = new Rectangle(-5000,-5000,0,0);
			for(CellRegion[] arr : cellParts)
				for(CellRegion r : arr)
					r.update(rect);
			System.out.println(i/22.0*100 + "%");
		}
		
		return new Point(0,0);
	}

}

