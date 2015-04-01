/** 
 *  @author marija
 */

/**
* The class calculates the polar coordinates from the given cartesian coordinates
* using the NewtonMethod with inverse Jacobi Matrix : x_n+1=x_n - J^(-1) * f*(x_n)
*/
public class JacobiMatrix {

	public static final int ITTERATIONS=1000;
	public static final double TOLERANCE=Math.pow(10, -6);

	/**
	 * calculates the inverse matrix of the given jacobi matrix 
	 * @param r0, fi0 : starting polar coordinates 
	 * @return matrix 
	 */
	public static double [][] invertJacobi (double[] rFi){
		double[][] jacobi =new double [2][2];
		jacobi[0][0]=Math.cos(rFi[1]);
		jacobi[0][1]=Math.sin(rFi[1]);
		jacobi[1][0]=(-Math.sin(rFi[1]))/rFi[0];
		jacobi[1][1]=Math.cos(rFi[1])/rFi[0];
		return jacobi;
	}
	/**
	 * calculates the zeroes of the function f(r, fi) 
	 * @param array with r0, fi0 and constant values x and y
	 * @return array with zero values
	 */
	public static double[] function (double[] rFi, double[] startXY){		
		double [] result = new double [2];
		result[0]= rFi[0]*Math.cos(rFi[1])-startXY[0];
		result[1]= rFi[0]*Math.sin(rFi[1])-startXY[1];
		return result;
	}
	/**
	 * calculates r and fi using Newton Method with inverse jacobi matrix
	 * @param cartesian coordinates x and y
	 * @return array with r and fi 
	 */
	public static double[] newton(double[] startXY){
		double[] currentRFi = {1,1};
		double[] newRFi= new double[2];
		int count=0;

		while (count<ITTERATIONS){
			double[][] jacobi=invertJacobi(currentRFi);
			double[] fx=function(currentRFi, startXY);

			newRFi[0]=currentRFi[0]-( jacobi[0][0]*fx[0] + jacobi[0][1]*fx[1]);
			newRFi[1]=currentRFi[1]-(jacobi[1][0]*fx[0] + jacobi[1][1]*fx[1]);

			if(Math.abs(newRFi[1]-currentRFi[1])<TOLERANCE && 
					Math.abs(newRFi[0]-currentRFi[0])< TOLERANCE){
				break;
			}
			count++;
			currentRFi[0]=newRFi[0];
			currentRFi[1]=newRFi[1];
		}

		return newRFi;		
	}
	/**
	 * print array
	 * @param array
	 */
	public static void print (double [] array){

		System.out.println("r= " + array[0]);
		System.out.println("fi= " + array[1]);
	}

	public static void main(String[] args) {
		double x = Double.parseDouble(args[0]);//1. Argument
		double y = Double.parseDouble(args[1]);//2. Argument
		double[] XY=new double [2];
		XY[0]=x;
		XY[1]=y;
		print(newton(XY));
	}
	
}
