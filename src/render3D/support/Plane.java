package render3D.support;

public class Plane {
	public double k, mx, my, mz;
	public Plane(){}
	public Plane(double[] normal, double[] point){
		reset(normal, point);
//		k = (normal.getX()*a.getX() + normal.getY()*a.getY() + normal.getZ()*a.getZ());
//		if(k==0){
//			mx = normal.getX();
//			my = normal.getY();
//			mz = normal.getZ();
//		}
//		else{
//			mx = normal.getX()/k;
//			my = normal.getY()/k;
//			mz = normal.getZ()/k;
//			k = 1;
//		}
	}
	public void reset(double[] normal, double[] point){
		k = (normal[0]*point[0] + normal[1]*point[1] + normal[2]*point[2]);
		if(k == 0){
			mx = normal[0];
			my = normal[1];
			mz = normal[2];
		}
		else{
			mx = normal[0]/k;
			my = normal[1]/k;
			mz = normal[2]/k;
			k = 1;
		}
	}
	public double[] findIntersect(Line l){
		double top = k-(l.xk*mx + l.yk*my + l.zk*mz);
		double bottom = l.xt*mx + l.yt*my + l.zt*mz;
		double t = top/bottom;
		//System.out.println(l);
		double[] ret = new double[3];
		
		ret[0] = l.xt*t + l.xk;
		ret[1] = l.yt*t + l.yk;
		ret[2] = l.zt*t + l.zk;
		
		return ret;
//		double top = k-(l.getXK()*mx + l.getYK()*my + l.getZK()*mz);
//		double bottom = l.getXT()*mx + l.getYT()*my + l.getZT()*mz;
//		double t = top/bottom;
//		
//		double x = l.getXT()*t + l.getXK();
//		double y = l.getYT()*t + l.getYK();
//		double z = l.getZT()*t + l.getZK();
//		return new Point3D(x,y,z);
	}
	public String toString(){
		return mx + "x + " + my + "y + " + mz + "z = " + k;
	}
}
