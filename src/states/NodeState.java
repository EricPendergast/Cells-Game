package states;

import input.*;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import nodes.*;


import dataStructures.ArrayList;

public class NodeState extends State{
	private boolean prevClick = false;
	
	private ArrayList<Node> nodes;
	public NodeState(){
		nodes = new ArrayList<Node>();
	}
	public void render(Graphics2D g2){
		for(int i = 0; i < nodes.size(); i++){
			nodes.get(i).drawOn(g2);
		}
	}
	public void update(){
		//false if release, true if press, null otherwise
		Boolean onClick = Mouse.button1Pressed;
		if(prevClick == Mouse.button1Pressed)
			onClick = null;
		prevClick = Mouse.button1Pressed;
		
		for(int i = 0; i < nodes.size(); i++){
			nodes.get(i).update(onClick);
		}
	}
	public void addNode(Node n){
		nodes.add(n);
	}
}
