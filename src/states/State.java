package states;

import input.Mouse;

import java.awt.Graphics2D;

public abstract class State {
	public abstract void render(Graphics2D g2);
	public abstract void update();
}
