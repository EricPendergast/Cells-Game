package cells;

import input.Keyboard;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import cells.generators.Tutorial;
import cells.generators.VeinsFast;
import cells.gui.ControlPanel;
import states.Game;
import states.NodeState;

public class CellState extends NodeState{
	private CellRunner cells;
	Game game;
	Generator g;
	public CellState(Game game){
		this.game = game;
		g = new VeinsFast();
	}
	public CellState(Game game, Generator g){
		this(game);
		this.g = g;
	}
	boolean updated = false;
	public void update(){
		if(g instanceof Tutorial){
			if(Keyboard.keys[KeyEvent.VK_ESCAPE])
				game.setStateIndex(1);
		}
		super.update();
		if(!updated){
			updated = true;
			cells = new CellRunner(new Rectangle(0,0,1200,750), g, new ControlPanel(new Rectangle(1000,0,200,750)));
			super.addNode(cells);
		}
	}
	public CellRunner getRunner(){
		return cells;
	}
}
