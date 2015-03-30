/** 
 *  Marija Pipercevic
 *  Matrikelnummer : 519168
 *  Email: pipercem@informatik.hu-berlin.de
 *  @author marija
 */
import java.util.LinkedList;

public class Aufgabe22 {

	public static final double TOL= Math.pow(10, -6);
	public static final int MAX_ITERATIONS=1000;

	/**
	 * berechnet Funktion f(X)
	 * @param x, n
	 * @return double - Ergebnis der Abbildung
	 */
	public static double funktion (double x, double n){	
		return Math.pow((x-1),n);
	}

	/**
	 * berechnet Ableitung f(X)
	 * @param x, n
	 * @return double - Ergebnis der Ableitung
	 */
	public static double ableitung (double x, double n){
		return n*Math.pow(x-1, n-1);
	}
	/**
	 * berechnet Nullstelle der Funktion mittels Newton Verfahren
	 * @param Startwert x0, n
	 * @return Liste mit Ergebnissen 
	 */
	public static LinkedList<Double> newton (double n, double x0){
		LinkedList<Double> solutions=new LinkedList<Double>();
		solutions.addLast(x0);
		double x=0;
		double count=0;

		while(count<MAX_ITERATIONS){
			x=x0-(funktion(x0,n)/ableitung(x0,n));
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
	 * berechnet Durchschnitt der Werte 
	 * @param Liste mit den Werten, double analytische Loesung
	 * @return double -Durchschnitt
	 */
	public static double durchschnitt (LinkedList<Double>werte, double korrekteLoesung){
		
		double sum=0;
		int count=0;
		for(int i=0; i<werte.size()-1;i++){
			sum += Math.abs(werte.get(i+1)- korrekteLoesung)/Math.abs(werte.get(i)- korrekteLoesung);
			count++;
		}
		return sum/count;		
	}

	public static void main (String [] args){

		double n = Double.parseDouble(args[0]);//Argument n
		LinkedList<Double> solutions=newton(n, 3);
		
		System.out.println("Loesung: " + solutions.getLast());
		System.out.println("Durchschnitt: " + durchschnitt(solutions, 1));
		
	}

}
