/*
 *  @author marija
 */


public class Jacobi {

	public static double[][] A= { {4, -1, 0, -1, 0, 0, 0, 0, 0}, 
		                          {-1, 4, -1, 0, -1, 0, 0, 0, 0},
		                          {0, -1, 4, 0, 0, -1, 0, 0, 0}, 
		                          {-1, 0, 0, 4, -1, 0, -1, 0, 0},
		                          {0, -1, 0, -1, 4, -1, 0, -1, 0},
		                          {0, 0, -1, 0, -1, 4, 0, 0, -1},
		                          {0, 0, 0, -1, 0, 0, 4, -1, 0},
		                          {0, 0, 0, 0, -1, 0, -1, 4, -1},
		                          {0, 0, 0, 0, 0, -1, 0, -1, 4} };

    public static final double MAXITERATION=1000;

	/**
	 * calculates identity matrix
	 * @param matrix
	 * @return identity matrix
	 */
	public static double [][] eigenmatrix(double [][] matrix){

		for(int i=0; i<matrix.length;i++){
			for(int j=0; j<matrix[0].length;j++){
				if(i==j){
					matrix[i][j]=1;
				}
				else{
					matrix[i][j]=0;
				}
			}
		}
		return matrix;
	}

	/**
	 * print Matrix
	 * @param matrix
	 */
	public static void print (double [][]matrix){

		for(int i=0; i<matrix.length;i++){
			for(int j=0; j<matrix[0].length;j++){
				System.out.print(matrix[i][j]);
				System.out.print(" ");				
			}
			System.out.println();
		}
	}

	/**
	 * addiert Eigenmatrix, multipliziert mit gama, dem AMatrix
	 * @param I
	 * @param gama
	 * @return InputMatrix
	 */
	public static double[][] add (double[][]I, double gama){
		double [][] InputMatrix=new double [9][9];
		for (int i=0;i<InputMatrix.length;i++){
			for(int j=0;j<InputMatrix[0].length;j++){
				if( i==j )
					InputMatrix[i][j]=gama+A[i][j];
				else
					InputMatrix[i][j]=A[i][j];
			}
		}
		return InputMatrix;
	}

    /**
     * Jacobi Verfahren
     * @param b 
     * @param inputMatrix
     * @return Vektor x
     */
	public static double[] loesung (double[]b, double[][]inputMatrix){		
		double[] x = new double [9];
		double tol=Math.pow(10, -5);
		double itteration=0;
		double X0[]={1,1,1,1,1,1,1,1,1}; //Startwert nach dem 1. Itteration

		double sum;
		double[] errorVector=new double[9];
		double norm=0;


		while(itteration<MAXITERATION){
			itteration++;

			//berechnet die Summe aller Elemente ausserhalb der Diagonale
			for(int i=0;i<inputMatrix.length;i++){
				sum=0;
				for(int j=0;j<inputMatrix[0].length;j++){
					if(i!=j){
						sum+=inputMatrix[i][j]*X0[j];
					}	
				}
				sum= b[i] - sum;
				x[i] = sum / inputMatrix [i][i];
			}

			//berechnet die Norm des Vektors x
			norm=0;
			for(int m=0; m<x.length; m++){
				errorVector[m]=x[m]-X0[m];
				norm+=Math.pow(errorVector[m],2);
			}
			norm=Math.sqrt(norm);
			if(norm<tol){
				break;
			}
			else{
				for(int k=0; k<x.length;k++){
					X0[k]=x[k];
				}
			}
		}
		System.out.println("Itterations= " + itteration);
		return x;
	}

	/**
	 * print Array
	 * @param array
	 */
	public static void print (double [] array){
		for(int i=0; i<array.length;i++){
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}


	public static void main (String[] args){
		if (args.length != 10 ) {
			System.out.println("Uebergebene Inputs sind anders als erwartet. "
					+ "Programm wird nicht ausgefuert. "
					+ "Als Argument sollen gama und Vektor b, der Laenge 9 "
					+ "uebergeben werden.");
			System.exit(1);
		}
		double gama = Double.parseDouble(args[0]);//Argument 1
		double[] b = new double[9]; 

		for (int i=1; i<10; i++) {
			b[i-1] = Double.parseDouble(args[i]);//Argumente 2-10
		}

		double [][] I=new double [9][9];

		System.out.println("Loesung:");
		print(loesung(b,(add(eigenmatrix(I),gama))));

	}

}
