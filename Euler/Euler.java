/** 
 *  @author marija
 */

/**
* The class solves a system of differential equations:
* y' = A * y_i - c0 
* where A is the given matrix and c0 the given vector 
*/
public class Euler {

	public static double [][] A = {{-800.2,-399.6},{-399.6,-200.8}};
	public static double[] c0={2,4};

	public static final double CALC_LIMIT= 5;//The end of an interval

	/**
	 * solves the system using the explicit Euler Method
	 * @param double h, array y0 with starting values
	 * @return array y with y1,y2
	 */
	public static double [] explicit(double h, double[] y0){
		double[] y = new double[2];
		double t=0;
		while(t<=CALC_LIMIT){
			
			
			y[0] = y0[0] + h*function1(y0,c0);
			y[1] = y0[1] + h*function2(y0,c0);

			t = t+ h; 
			
			y0[0]=y[0];
			y0[1]=y[1];

		}

		return y;
	}
	/**
	 * solves the system using the implicit Euler Method
	 * @param double h, array y0 with starting values
	 * @return array y with y1,y2
	 */
	public static double [] implicit (double h, double[] y0){
		double t=0;
		double [] y = new double [2];

		while(t <= CALC_LIMIT){
			y[0] = y0[0] + h*function1(y,c0);
			y[1] = y0[1] + h*function2(y,c0);

			t = t+ h; 

			y0[0]=y[0];
			y0[1]=y[1];

		}

		return y;


	}
	/**
	 * calculates f(y1)
	 * @param 2 arrays: y0-starting value for y1; c-constant
	 * @return double solution y1
	 */
	public static double function1(double []y0, double []c0){
		double solution=A[0][0]*y0[0] + A[0][1]*y0[1] -c0[0];

		return solution;
	}
	/**
	 * calculates f(y2)
	 * @param 2 arrays: y0-starting value for y2; c-constant
	 * @return double solution y2
	 */
	public static double function2(double []y0, double []c0){
		double solution=A[1][0]*y0[0] + A[1][1]*y0[1] -c0[1];

		return solution;
	}
	/**
	 * print array
	 * @param array
	 */
	public static void print(double []x){
		for(int i=0; i<x.length;i++){
			System.out.println(x[i]);
		}
	}

	public static void main(String []args){
		double h=0.00001;
		double[] y0={5,5};

		System.out.println("Explicit: ");
		print(explicit(h,y0));

		System.out.println("Implicit: ");
		print(implicit(h,y0));

	}


}
