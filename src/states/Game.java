package states;

import input.Keyboard;
import input.Mouse;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

import nodes.Node;

public class Game extends Canvas implements Runnable{
	private int pixelsWidth = 600;
	private int pixelsHeight = pixelsWidth/16*11;
	private int scale = 1;
	private JFrame frame;
	private ArrayList<State> states = new ArrayList<State>();
	private Mouse mouse;
	private Keyboard keyboard;
	private int currentStateIndex = 0;
	public Game(){
		Dimension size = new Dimension(pixelsWidth*scale,pixelsHeight*scale);
		setPreferredSize(size);
		
		frame = new JFrame();

		keyboard = new Keyboard();
		addKeyListener(keyboard);
		mouse = new Mouse(scale,frame);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
//		wheel = new MouseWheel();
//		addMouseWheelListener(wheel);
		frame.setResizable(false);
		frame.setTitle("Simulation");
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	public Game(int w, int h, int s){
		pixelsWidth = w;
		pixelsHeight = h;
		scale = s;
		
		Dimension size = new Dimension(pixelsWidth*scale,pixelsHeight*scale);
		setPreferredSize(size);

		frame = new JFrame();

		
//		wheel = new MouseWheel();
//		addMouseWheelListener(wheel);
		frame.setResizable(false);
		frame.setTitle("Simulation");
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		keyboard = new Keyboard();
		addKeyListener(keyboard);
		mouse = new Mouse(scale,frame);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	public void start(){
		Thread t = new Thread(this,"Display");
		t.start();
	}
	public void run() {
		long start = System.currentTimeMillis();
		long frameTime = 1000/30;
		while(true){
			if(System.currentTimeMillis()-start >= frameTime){
				start = System.currentTimeMillis();
				if(states.size() != 0){
					render();
					update();
				}
			}
		}
	}
	private void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();
		g2.scale(scale,scale);
		g2.clearRect(0, 0, pixelsWidth, pixelsHeight);
		
		states.get(currentStateIndex).render(g2);
		
		g2.dispose();
		bs.show();
	}
	private void update(){
		states.get(currentStateIndex).update();
	}
	public void addState(State s){
		states.add(s);
	}
	public void setStateIndex(int i){
		currentStateIndex = i;
	}
}
