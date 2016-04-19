package cells;

public class Point {
	public double x, y;
	public Point(){}
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	public Point intersect(Point a, Point b, Point c, Point d){
		double constA = (a.x*b.y-a.y*b.x);
		double constB = (c.x*d.y-c.y*d.x);
		double constC = (a.x-b.x)*(c.y-d.y)-(a.y-b.y)*(c.x-d.x);
		Point p = new Point();
		p.x = ( (constA)*(c.x-d.x) - (a.x-b.x)*(constB) )/constC;
		p.y = ( (constA)*(c.y-d.y) - (a.y-b.y)*(constB) )/constC;
		return p;
	}
	public static Point average(Point ... parray){
		Point ret = new Point();
		for(Point p : parray){
			ret.x += p.x;
			ret.y += p.y;
		}
		ret.x /= parray.length;
		ret.y /= parray.length;
		return ret;
	}
	public static double distance(Point a, Point b){
		return Math.sqrt(distanceCompare(a,b));
	}
	public static double distanceCompare(Point a, Point b){
		return Math.pow(a.x-b.x, 2) + Math.pow(a.y-b.y,2);
	}
	public static Vector getDifference(Point a, Point b){
		return new Vector(b.x-a.x,b.y-a.y);
	}
	public void add(double a, double b){
		x+=a;
		y+=b;
	}
	public void add(Vector v){
		x+=v.dx;
		y+=v.dy;
	}
	public void subtract(double a, double b){
		x-=a;
		y-=b;
	}
	public void subtract(Vector v){
		x-=v.dx;
		y-=v.dy;
	}
	public String toString(){
		return "[" + x + ", " + y + "]";
	}
}
