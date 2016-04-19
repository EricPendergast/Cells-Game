package render3D.support;

//Contains different operations on vectors
//Note: points have an extra spot that is 1
//vectors have an extra spot that is 0
public class Opp {
	public static double[] x = {1,0,0};
	public static double[] y = {0,1,0};
	public static double[] z = {0,0,1};
	public static double[] add(double[] a, double[] b){
		//double[] arr = {a[0]+b[0], a[1]+b[1], a[2]+b[2]};
		double[] arr = new double[a.length];
		for(int i = 0; i < arr.length; i++){
			arr[i] = a[i]+b[i];
		}
		arr[arr.length-1] = Math.max(a[a.length-1],b[b.length-1]);
		return arr;
	}
	public static int[] add(int[] a, int[] b){
		//double[] arr = {a[0]+b[0], a[1]+b[1], a[2]+b[2]};
		int[] arr = new int[a.length];
		for(int i = 0; i < arr.length; i++){
			arr[i] =a[i]+b[i];
		}
		arr[arr.length-1] = Math.max(a[a.length-1],b[b.length-1]);
		return arr;
	}
	public static double[] subtract(double[] a, double[] b){
		//double[] arr = {a[0]-b[0], a[1]-b[1], a[2]-b[2]};
		double[] arr = new double[a.length];
		for(int i = 0; i < arr.length; i++){
			arr[i] =a[i]-b[i];
		}
		return arr;
	}
	public static int[] subtract(int[] a, int[] b){
		//int[] arr = {a[0]-b[0], a[1]-b[1], a[2]-b[2]};
		int[] arr = new int[a.length];
		for(int i = 0; i < arr.length; i++){
			arr[i] =a[i]-b[i];
		}
		return arr;
	}
	public static double[] multiply(double[] a, double scalar){
		//double[] arr = {a[0] * scalar, a[1] * scalar, a[2] * scalar};
		double[] arr = new double[a.length];
		for(int i = 0; i < arr.length; i++){
			arr[i] =a[i]*scalar;
		}
		arr[arr.length-1] = a[a.length-1];
		return arr;
	}
	public static int[] multiply(int[] a, int scalar){
		//int[] arr = {a[0] * scalar, a[1] * scalar, a[2] * scalar};
		int[] arr = new int[a.length];
		for(int i = 0; i < arr.length; i++){
			arr[i] =a[i]*scalar;
		}
		arr[arr.length-1] = a[a.length-1];
		return arr;
	}
	public static double dotProduct(double[] a, double[] b){
		return a[0]*b[0] + a[1]*b[1] + a[2]*b[2];
	}
	public static double[] crossProduct(double[] a, double[] b){
		double[] cross = new double[4];
		cross[0] = a[1]*b[2]-a[2]*b[1];
		cross[1] = a[2]*b[0]-a[0]*b[2];
		cross[2] = a[0]*b[1]-a[1]*b[0];
		cross[3] = 0;
		
		return cross;
		
//		a.getY()*b.getZ()-a.getZ()*b.getY(),
//		a.getZ()*b.getX()-a.getX()*b.getZ(),
//		a.getX()*b.getY()-a.getY()*b.getX()
	}
	//Rotates a around b 
	public static double[] rotate(double[] a, double[] b, double angle){
		if(angle == 0){
			double[] r = {a[0],a[1],a[2],a[3]};
			return r;
		}
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		
		double[] cross = crossProduct(b,a);
		double dot = dotProduct(b,a);
		
		double[] ret = new double[4];
		ret[0] = a[0]*cos + cross[0]*sin + b[0]*dot*(1-cos);
		ret[1] = a[1]*cos + cross[1]*sin + b[1]*dot*(1-cos);
		ret[2] = a[2]*cos + cross[2]*sin + b[2]*dot*(1-cos);
		ret[3] = 1;
		return ret;
		
//		Point3D v = this;
//		double cos = Math.cos(rad);
//		double sin = Math.sin(rad);
//		Point3D cross = Vec3D.crossProd(k,v);
//		double dot = Vec3D.dotProd(k,v);
//		double x = v.getX()*cos + cross.getX()*sin +k.getX()*dot*(1-cos);
//		double y = v.getY()*cos + cross.getY()*sin +k.getY()*dot*(1-cos);
//		double z = v.getZ()*cos + cross.getZ()*sin +k.getZ()*dot*(1-cos);
//		xCoord = x;
//		yCoord = y;
//		zCoord = z;
	}
	
	public static double[] newArr(double ... a){
		return a;
	}
	public static int[] newIntArr(int ... a){
		return a;
	}
	public static double[][] newPolygon(double a1, double b1, double c1, double a2, double b2, double c2,double a3, double b3, double c3){
		double[] arr1 = {a1,b1,c1,1};
		double[] arr2 = {a2,b2,c2,1};
		double[] arr3 = {a3,b3,c3,1};
		
		double[][] ret = {arr1,arr2,arr3};
		return ret;
	}
	public static double distanceBetween(double[] a, double[] b){
		double sum = 0;
		for(int i = 0; i < a.length-1; i++){
			double temp = a[i]-b[i];
			sum += temp*temp;
		}
		
		return Math.sqrt(sum);
	}
	public static double comparitiveDistance(double[] a, double[] b){
		double sum = 0;
		for(int i = 0; i < a.length-1; i++){
			double temp = a[i]-b[i];
			sum += temp*temp;
		}
		return sum;
	}
	public static String arrToString(double[] arr){
		String ret = "[";
		for(int i = 0; i < arr.length; i++){
			ret+=arr[i];
			if(i!=arr.length-1)
				ret+=", ";
		}
		ret+="]";
		return ret;
	}
	public static String arrToString(int[] arr){
		String ret = "[";
		for(int i = 0; i < arr.length; i++){
			ret+=arr[i];
			if(i!=arr.length-1)
				ret+=", ";
		}
		ret+="]";
		return ret;
	}
	public static double[] vecMatrixMult(double[] vec, double[][] mat){
		double[] ret = new double[vec.length];
		for(int i = 0; i < vec.length; i++){
			ret[i] = 0;
			for(int j = 0; j < mat[0].length; j++)
				ret[i] += mat[i][j]*vec[j];
			
		}
		return ret;
	}
	public static double[][] matrixMult(double[][] a, double[][] b){
		double[][] ret = new double[a.length][b[0].length];
		for(int i = 0; i < ret.length; i++){
			for(int j = 0; j < ret[i].length; j++){
				for(int k = 0; k < a[0].length; k++){
					ret[i][j] += a[i][k]*b[k][j];
				}
			}
		}
		return ret;
	}
	public static double[] averagePoint(double[][] points){
		double[] ret = {0,0,0,0};
		
		for(double[] p : points)
			ret = Opp.add(ret, p);
		return Opp.multiply(ret, 1.0/points.length);
	}
	
	public static double[][] newRotationMatrix(double[] axis, double[] point, double rad){
		double u = axis[0];
		double v = axis[1];
		double w = axis[2];
		double L = u*u + v*v + w*w;
		double Ls = Math.sqrt(L);
		double a = point[0];
		double b = point[1];
		double c = point[2];
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		
		double[][] ret = new double[4][4];
		
		ret[0][0] = (u*u+(v*v+w*w)*cos)/L;
		ret[1][0] = (u*v*(1-cos)+w*Ls*sin)/L;
		ret[2][0] = (u*w*(1-cos)-v*Ls*sin)/L;
		
		ret[0][1] = (u*v*(1-cos)-w*Ls*sin)/L;
		ret[1][1] = (v*v+(u*u+w*w)*cos)/L;
		ret[2][1] = (v*w*(1-cos)+u*Ls*sin)/L;
		
		ret[0][2] = (u*w*(1-cos)+v*Ls*sin)/L;
		ret[1][2] = (v*w*(1-cos)-u*Ls*sin)/L;
		ret[2][2] = (w*w+(u*u+v*v)*cos)/L;
		
		
		ret[0][3] = ((a*(v*v+w*w)-u*(b*v+c*w))*(1-cos)+(b*w-c*v)*Ls*sin);
		ret[1][3] = ((b*(u*u+w*w)-v*(a*u+c*w))*(1-cos)+(c*u-a*w)*Ls*sin);
		ret[2][3] = ((c*(u*u+v*v)-w*(a*u+b*v))*(1-cos)+(a*v-b*u)*Ls*sin);
		ret[3][3] = 1;
		return ret;
	}
}
