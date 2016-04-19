package nodes;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class ImageNode extends Node{
	String fileLoc;
	public ImageNode(String loc, Rectangle h){
		fileLoc = loc;
		hitbox = h;
	}
	@Override
	public void drawOn(Graphics2D g2) {
		g2.drawImage(getImage(fileLoc),hitbox.x,hitbox.y,null);
	}

	@Override
	public void update(Boolean onClick) {
		// TODO Auto-generated method stub
		
	}

}
