/** 
  @author marija
 */
import java.util.LinkedList;

/**
* The class calculates the roots (zeroes) of the function f_n(x)=(x-1)^n
* using Newton Method: x_n+1=x_n - f(x)/f'(x)
*/

public class NewtonMethod {

	public static final double TOL= Math.pow(10, -6);
	public static final int MAX_ITERATIONS=1000;

	/**
	 * calculate function f(x)
	 * @param x, n
	 * @return double - Result of the function f(x)
	 */
	public static double function (double x, double n){	
		return Math.pow((x-1),n);
	}

	/**
	 * calculate derivate f'(x)
	 * @param x, n
	 * @return double - Result of the derivation f'(x)
	 */
	public static double derivate (double x, double n){
		return n*Math.pow(x-1, n-1);
	}
	/**
	 * calculate the roots (or zeroes) of a real-valued function using Newton Method
	 * @return LinkedList with Results 
	 */
	public static LinkedList<Double> newton (double n, double x0){
		LinkedList<Double> solutions=new LinkedList<Double>();
		solutions.addLast(x0);
		double x=0;
		double count=0;

		while(count<MAX_ITERATIONS){
			x=x0-(function(x0,n)/derivate(x0,n));
			count ++;
			if(Math.abs(x-x0)<TOL){
				break;
			}
			x0=x;
			solutions.addLast(x);
		}
		return solutions;
	}
	/**
	 * calculates the average of all the values - analytically calculated solution : 
         * average = x_k+1-correctSolution/x_k-correctSolution
	 * @param LinkedList with values , double analytically calculated solution
	 * @return double -average
	 */
	public static double average (LinkedList<Double>values, double correctSolution){
		
		double sum=0;
		int count=0;
		for(int i=0; i<values.size()-1;i++){
			sum += Math.abs(values.get(i+1)- correctSolution)/Math.abs(values.get(i)- correctSolution);
			count++;
		}
		return sum/count;		
	}

	public static void main (String [] args){

		double n = Double.parseDouble(args[0]);//Argument n
		LinkedList<Double> solutions=newton(n, 3);
		
		System.out.println("Solution: " + solutions.getLast());
		System.out.println("Average: " + average(solutions, 1));
		
	}

}
