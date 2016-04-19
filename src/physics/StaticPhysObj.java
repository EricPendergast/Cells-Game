package physics;

public class StaticPhysObj extends PhysicsObj{
	public StaticPhysObj(double x, double y){
		super(x,y);
	}
	public StaticPhysObj(double x, double y, double mass){
		super(x,y,0,0,mass);
	}
	public void update(){
		
	}
}
