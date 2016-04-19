package physics;

public class Electron extends ESPhysicsObj{
	public static double electronMass = .01;
	public Electron(double x, double y){
		super(x,y,-1);
		mass = electronMass;
	}
	public Electron(double x, double y, double dx, double dy){
		super(x,y,dx,dy,-1);
		mass = electronMass;
	}
//	public void update(){
//		x += dx*(Math.random()+.5);
//		y += dy*(Math.random()+.5);
//	}
}
