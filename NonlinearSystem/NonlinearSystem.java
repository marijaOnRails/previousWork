/** 
   @author marija
 */

/**
* The class solves a nonlinear system defined by the iterative approach
* X_{i+1} = A * X_i +B
* where X_k is k-th iteration of the nonlinear system
* A and B define the iterative steps
*/
public class NonlinearSystem {
	/**
	* definition of iterative steps, matrix A and vector B
	*/
	public static double A [][]={{0.2 , -0.7} , {0.9 , 0}};
	public static double B [] = {1.3 , 0.3};

	/**
	 * solves system of nonlinear equations
	 * @param starting value X01, X02
	 * @return A with all Values after 1-10 iterations
	 */
	public static double [][] iteration (double X01, double X02){
		double [][] x=new double[10][2];
		
		for (int i=0; i<x.length; i++){
			x[i][0]= (A[0][0]*X01 + A[0][1]*X02)+B[0];
			X01=x[i][0];			

			x[i][1]= (A[1][0]*X01 + A[1][1]*X02)+B[1];
			X02=x[i][1];
		}
		return x;
	}

	/**
	 * print A
	 * @param A
	 */
	public static void print (double [][] x){
		for(int i=0; i<x.length; i++){
			System.out.println(i+1 +". X1=" + x[i][0] + "  X2=" + x[i][1]);
		}

	}
	/**
	 * print Array
	 * @param Array
	 */
	static void print(double[] x, String endsWith){
		for(int i=0; i<x.length; i++){
			System.out.print(" " + x[i] +endsWith);
		}
		System.out.println();
	}

	/**
	 * calculate Logarithm of the max norm of a A
	 * @param A
	 * @return Array with Log values
	 */
	public static double [] logarithm (double [][]x){
		double [] log=new double [x.length-1];
		
		for(int i=0; i<x.length-1;i++){
			double[] xk1=x[i+1];
			double[] xk=x[i];
			log[i]=Math.log10(Math.max( Math.abs(xk1[0] - xk[0]), Math.abs(xk1[1] - xk[1]) ));
			
		}
		
		return log;
	}

	public static void main(String [] args){
		double a=1;
		double b=3;

		print(iteration(a,b));
		System.out.println();
		double[] logs=logarithm(iteration(a,b));
		System.out.println("LOGARITHMS:");
		print(logs, "\n");
	}

}
