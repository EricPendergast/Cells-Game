package cells.gui;

import input.Mouse;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import cells.CellRunner;
import cells.generators.Empty;
import states.Game;
import states.NodeState;

public class StartMenu extends NodeState{
	CellRunner runner;
	CircleButton[] buttons = new CircleButton[10];
	Game game;
	public StartMenu(CellRunner r, Game g){
		game = g;
		runner = r;
		buttons[0] = new CircleButton(new Point(500+100,375), 150, new Color(0x48,0x61,0x78), "START", new Point(420+100,383), Color.decode("0x6EB1EB"), new Font("Times New Roman", Font.BOLD,50));
		buttons[1] = new CircleButton(new Point(500+100,650), 75, new Color(0x48,0x61,0x78), "INSTRUCTIONS", new Point(420+120,655), Color.decode("0x6EB1EB"), new Font("Times New Roman", Font.BOLD,16));
		
		this.addNode(buttons[0]);
		this.addNode(buttons[1]);
//		CellRunner c = new CellRunner(new Rectangle(0,0,1000,750), new Empty(), null);
//		c.setDebug(true);
//		c.setLocation(1000, 750);
//		this.addNode(c);
	}
	public void update(){
		Mouse.button2Pressed = false;
		super.update();
		if(buttons[0].isClicked()){
			//System.out.println()
			game.setStateIndex(0);
		}
		if(buttons[1].isClicked()){
			game.setStateIndex(2);
		}
	}
	public void render(Graphics2D g2){
		g2.setColor(new Color(0xFC9090));
		g2.fillRect(0,0,1200,1000);
		
		g2.setColor(new Color(0x0));
		g2.setFont(new Font("Impact",Font.PLAIN,180));
		g2.drawString("CELLS",390,200);
		g2.setFont(new Font("this is not a font",Font.PLAIN,40));
		g2.drawString("By Eric Pendergast",830,720);
		super.render(g2);
	}
}