package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	public static boolean[] keys = new boolean[1000];
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	public void keyTyped(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

}
