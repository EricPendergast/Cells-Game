package cells;

public class Vector {
	public double dx, dy;
	public Vector(){}
	public Vector(double x, double y){
		dx=x;
		dy=y;
	}
	public void multiply(double s){
		dx *= s;
		dy *= s;
	}
	public void add(Vector v){
		dx += v.dx;
		dy += v.dy;
	}
	public void subtract(Vector v){
		dx -= v.dx;
		dy -= v.dy;
	}
	public void normalize(){
		double l = getLength();
		dx /= l;
		dy /= l;
	}
	public double getLength(){
		return Math.sqrt(dx*dx+dy*dy);
	}
	public String toString(){
		return "[" + dx + ", " + dy + "]";
	}
}
