package nodes;

import input.Keyboard;
import input.Mouse;

import java.awt.Rectangle;

public class TestButton extends Button{

	public TestButton(Rectangle hitbox, String loc, String selectedImageLoc, String label) {
		super(hitbox, loc, selectedImageLoc, label);
	}
	public void update(Boolean onClick){
		if(onClick != null && hitbox.contains(Mouse.button1At)){
			System.out.println(onClick);
		}
	}
}
