package input;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseWheel implements MouseWheelListener{
	public static int wheelMove = 0;
	public void mouseWheelMoved(MouseWheelEvent e) {
		wheelMove = e.getWheelRotation();
		System.out.println("HEHEHEH");
	}

}
