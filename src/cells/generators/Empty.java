package cells.generators;

import cells.CellRegion;
import cells.Generator;
import cells.Point;

public class Empty implements Generator {
	
	public Point generate(CellRegion[][] cells) {
		return new Point(0,0);
	}

}
