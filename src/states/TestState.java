package states;

import java.awt.Rectangle;

import nodes.*;
public class TestState extends NodeState{
	public TestState(){
		//super.addNode(new ImageNode("TEST",new Rectangle(0,0,0,0)));
		super.addNode(new TestButton(new Rectangle(260,0,50,50), "/test.jpg", "", "BUTTON"));
	}
}
