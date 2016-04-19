package render3D.support;

public class Line {
	//k is shift, t is slope
	public double xk,xt, yk,yt, zk,zt;
	public Line(double[] start, double[] end){
		xk = start[0];
		xt = end[0]-start[0];
		
		yk = start[1];
		yt = end[1]-start[1];
		
		zk = start[2];
		zt = end[2]-start[2];
	}
	public String toString(){
		return "x = " + xt + "t + " + xk + "\r" + 
				"y = " + yt + "t + " + yk + "\r" + 
				"z = " + zt + "t + " + zk + "\r"; 
	}
}
