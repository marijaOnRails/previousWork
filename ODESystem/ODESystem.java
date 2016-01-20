/** 
 *  @author marija
 */
public class ODESystem {
	public static final double ERROR=Math.pow(10, -3);

	public static final double CALC_LIMIT= 2 * Math.PI;//end of an interval

	public static int funcCounter=0;

	public static double h(int n){
		return 2*Math.PI/Math.pow(2, n);
	}
	/**
	 * berechnet Funktion y1, y2,y3,y4 mit dem expliziten Eulerverfahren
	 * @param double h
	 * @return array solution mit y1,y2,y3,y4
	 */
	public static double [] euler(double h){
		//starting solution
		double [] solution = new double [4];
		double y1=0;
		double y2=0;
		double y3=0;
		double y4=0;

		double t=0;
		//Anfangswerte
		double y10=1;
		double y20=0;
		double y30=2;
		double y40=0;

		while(t <= CALC_LIMIT){
			//Jedes y wird berechnet
			y1 = y10 + h*funktion1(y10,y20,y30,y40);
			y2 = y20 + h*funktion2(y10,y20,y30,y40);
			y3 = y30 + h*funktion3(y10,y20,y30,y40);
			y4 = y40 + h*funktion4(y10,y20,y30,y40);

			t=h+t; 
			y10=y1; 
			y20=y2;
			y30=y3;
			y40=y4;
		}
		solution[0]=y1;
		solution[1]=y2;
		solution[2]=y3;
		solution[3]=y4;

		return solution;

	}
	/**
	 * berechnet Funktion y1, y2,y3,y4 mit dem Heunverfahren
	 * @param double h
	 * @return array solution mit y1,y2,y3,y4
	 */
	public static double [] heun(double h){
		//starting solution
		double [] solution = new double [4];
		double y1=0;
		double y2=0;
		double y3=0;
		double y4=0;
		//Anfangswerte
		double y10=1;
		double y20=0;
		double y30=2;
		double y40=0;

		double t=0; 

		while(t <= CALC_LIMIT){
			//jedes y wird berechnet
			y1 = y10 + (h/2) * ( funktion1(y10,y20,y30,y40) + funktion1(y10+h*funktion1(y10,y20,y30,40), y20+h*funktion2(y10,y20,y30,40), y30+h*funktion3(y10,y20,y30,40), y40+h*funktion4(y10,y20,y30,40)));
			y2 = y20 + (h/2) * ( funktion2(y10,y20,y30,y40) + funktion2(y10+h*funktion1(y10,y20,y30,40), y20+h*funktion2(y10,y20,y30,40), y30+h*funktion3(y10,y20,y30,40), y40+h*funktion4(y10,y20,y30,40)));
			y3 = y30 + (h/2) * ( funktion3(y10,y20,y30,y40) + funktion3(y10+h*funktion1(y10,y20,y30,40), y20+h*funktion2(y10,y20,y30,40), y30+h*funktion3(y10,y20,y30,40), y40+h*funktion4(y10,y20,y30,40)));
			y4 = y40 + (h/2) * ( funktion4(y10,y20,y30,y40) + funktion4(y10+h*funktion1(y10,y20,y30,40), y20+h*funktion2(y10,y20,y30,40), y30+h*funktion3(y10,y20,y30,40), y40+h*funktion4(y10,y20,y30,40)));

			t= t+ h; 
			y10=y1; 
			y20=y2;
			y30=y3;
			y40=y4;
		}
		solution[0]=y1;
		solution[1]=y2;
		solution[2]=y3;
		solution[3]=y4;

		return solution;

	}
	/**
	 * berechnet Funktion f(y1)
	 * @param y1,y2,y3,y4
	 * @return double y1
	 */
	public static double funktion1(double y1,double y2,double y3, double y4){
		funcCounter++;
		y1=-y2;
		return y1;
	}
	/**
	 * berechnet Funktion f(y2)
	 * @param y1,y2,y3,y4
	 * @return double y2
	 */
	public static double funktion2(double y1,double y2,double y3, double y4){
		funcCounter++;
		y2=y1;
		return y2;
	}
	/**
	 * berechnet Funktion f(y3)
	 * @param y1,y2,y3,y4
	 * @return double y3
	 */
	public static double funktion3(double y1,double y2,double y3, double y4){
		funcCounter++;
		y3=-(2*(y2*y2+1)*y2+(4*y1*y1*y2))/(Math.pow((y2*y2+1),2));
		return y3;
	}
	/**
	 * berechnet Funktion f(y4)
	 * @param y1,y2,y3,y4
	 * @return double y4
	 */
	public static double funktion4(double y1,double y2,double y3, double y4){
		funcCounter++;
		y4=(2*(y2*y2+1)*((y1*y1)-(y2*y2))-(4*y1*y1*y2*y2))/(Math.pow((y2*y2+1), 2));
		return y4;
	}
	/**
	 * print array
	 * @param array
	 */
	public static void print(double [] x){
		for(int i=0; i<x.length;i++){
			System.out.println("  " + x[i]);
		}
	}
	/**
	 * berechnet korrekte Loesung fuer y1
	 * @param double t
	 * @return double y1
	 */
	public static double correct1 (double t){
		double x;
		x=Math.cos(t);
		return x;
	}
	/**
	 * berechnet korrekte Loesung fuer y2
	 * @param double t
	 * @return double y2
	 */
	public static double correct2 (double t){
		double x;
		x=Math.sin(t);
		return x;
	}
	/**
	 * berechnet korrekte Loesung fuer y3
	 * @param double t
	 * @return double y3
	 */
	public static double correct3 (double t){
		double x;
		x=2*Math.cos(t)/(Math.sin(t)*Math.sin(t)+1);
		return x;		
	}
	/**
	 * berechnet korrekte Loesung fuer y4
	 * @param double t
	 * @return double y4
	 */
	public static double correct4 (double t){
		double x;
		x=2*Math.cos(t)*Math.sin(t)/(Math.sin(t)*Math.sin(t)+1);
		return x;

	}
	/**
	 * berechnet 2-Norm der Abweichung 2 arrays
	 * @param 2 arrays mit double Werten
	 * @return double norm
	 */
	public static double norm (double[] x, double[] y){
		double norm=0;
		for(int i=0; i<x.length;i++){
			norm=norm + Math.pow(x[i]-y[i], 2);
		}
		norm = Math.sqrt(norm);
		return norm;

	}

	public static void main(String[]args){
		double[] correctSolution=new double[4];
		correctSolution[0]=correct1(CALC_LIMIT);
		correctSolution[1]=correct2(CALC_LIMIT);
		correctSolution[2]=correct3(CALC_LIMIT);
		correctSolution[3]=correct4(CALC_LIMIT);

		for(int n=6; n<=16; n++) {
			System.out.println("n=" + n);

			double h=h(n);

			/**System.out.println("  Euler:");
			double[] approxSolution=euler(h);
			print(approxSolution);
			double error=norm(correctSolution, approxSolution);
			System.out.println("  Error: " + error);
			System.out.println();

			if( error < FEHLER) 
				break; */
			System.out.println("Heun:");
			double[] approxSolution=heun(h);
			print(approxSolution);
			double error=norm(correctSolution, approxSolution);
			System.out.println("  Error: " + error);

			if( error < FEHLER) 
				break; 
		}
		System.out.println("Function counter= " + funcCounter);

	}

}
