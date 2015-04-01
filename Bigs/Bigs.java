/**
* author @marija
*/
/**
* class that does different operations with arrays
* each array holds an arbitrary large integer: each cell of the array
* holds a digit of the integer
*/
public class Bigs {

	// adds two of such integers
	public static int[ ] add (int[ ] a, int[ ] b)
	{ 
		int len = Math.max(a.length, b.length);
		int[] c = new int[len + 1];
		int rest=0;
		int commonLen=Math.min(a.length, b.length); 
		for (int i = 0; i < commonLen; i++) {
			c[i] =(a[i] + b[i]+ rest)%10;
			rest = (a[i] + b[i] + rest) /10; 
		}
		int[] longer;
		if( a.length > b.length )
			longer=a;
		else
			longer=b;

		for (int i = commonLen; i < longer.length; i++) {
			c[i] =(longer[i]+ rest)%10;
			rest = (longer[i]+ rest) /10;
		}
		c[c.length-1]=rest;
		if (c[c.length-1]==0){
			int [] n = new int [len];
			for (int i = 0; i < c.length-1; i++) { 
				n[i] = c[i];
			}
			return n;
		}

		else {
			return c;
		}
	}

	// prints such an integer (without Newline)
	static void print (int[ ] n)
	{ 
		for (int i = n.length-1; i>=0; i--) {

			System.out.print(n[i]);
		}
	}


	// prints such an integer (with Newline)
	static void println (int[ ] n)
	{ 
		for (int i = n.length-1; i>=0; i--) {
			System.out.print(n[i]);
		}
		System.out.println();
	}

	// creates an array from an integer d

	static int[ ] digit(int d)	
	{ 
		int[] c = new int[1];
		if (d>=0 && d <=9){
			c [0]=d;}
		return c;
	}


	// creates an array, wth the value zero
	static int[ ] Null()
	{ 
		int[] c = new int[1];
		c [0]=0;
		return c;
	}

	//  creates an array with the value one
	static int[ ] One()
	{ 
		int[] c = new int[1];
		c [0]=1;
		return c;
	}


	// rest of the division by 10(integer)
	static int mod10(int[ ] n)
	{ 
		int rest = n[0];
		return rest;
	}


	// integer quotient when divided by 10
	static int[ ] div10(int[ ] n)
	{ 
		int[] c = new int[n.length-1];
		for (int i = 0; i < n.length-1; i++) {
			c[i] = n[i+1]; }

		return c; 
	}


	// conversion of any integer into an array	
	static int[ ] fromInt(int n)
	{ 
		int len = (int)Math.ceil(Math.log10(n));
		int[ ] c = new int[len];
		for (int i=0; i<len;i++){
			int ziffer = n % 10;
			c[i]=ziffer;
			n = n/10;}

		return c;
	}


	// copy of an array
	static int[ ] copy(int[ ] n)
	{ 
		int[] c=new int[n.length];;
		for(int i =0;i < n.length;i++){
			c[i] = n[i];}
		return c;
	}


	// multiplies an array with one-digit integer
	static int[ ] times(int[ ] n, int d)
	{ 
		int[] c = new int[n.length+1];
		int rest=0;
		for (int i = 0; i <n.length; i++) {
			c[i] =(n[i]*d + rest)%10;
			rest = (n[i] *d+rest) /10;}
		c[c.length-1]=rest;
		if (c[c.length-1]==0){
			int [] m = new int [n.length];
			for (int i = 0; i < n.length; i++) {
				m[i] = c[i];}
			return m;
		}
		else {
			return c;
		}
	}


	// multiplies an array with 10
	static int[ ] times10(int[ ] n)
	{  
		int[] c = new int[n.length + 1];

		for (int i = 0; i< n.length; i++){
			c[i+1]=n[i];
		}
		c[0] = 0;
		return c;
	}


	// multiples two arrays 	
	static int[ ] times(int[ ]a, int[ ] b)
	{  
		int[] result = Null();	
		int[] temp;
		for(int i=0; i<b.length; i++) {
			temp=times(a, b[i]);
			for(int j=0; j<i; j++) {
				temp=times10(temp);
			}
			result=add(result, temp);
		}
		return result;
	}




	// Test (arrays a and b) : a < b ?
	static boolean less (int[ ] a, int[ ] b)
	{
		if (a.length < b.length){
			return true;}
		else if (a.length == b.length){
			for (int i=0; i<a.length; i++){
				if (a[i]< b[i]){
					return true;
				}
			}
		}
		return false; 
	}




	// Test (arrays a and b) : a==b?
	static boolean equal (int[ ] a, int[ ] b)
	{
		if (a.length == b.length){
			for (int i=0; i<a.length; i++){
				if (a[i]== b[i]){
					return true;
				}
			}
		}
		return false; 
	}





	// Test correctnes of an array: array exists and contains at least on digit 
	// all positions are 0-9; no leading zeros (except zero itself)
	static boolean ok (int[ ] n)
	{ 
		if (n==null) {
			return false;}
		if (n.length==0) {
			return false;}
		for (int i=0; i<n.length; i++){
			if (n[i]<0 && n[i]>9){
				return false;}
		}
		if (n[n.length-1]==0){
			return false;}
		else{
			return true;}

	}



	public static void main (String[ ] s) {
		int[ ] a = One();
		for (int i=0; i<9989; ++i) {
			a = times(a, 2);
		}
		System.out.println("has " + a.length + " cells");
		println(a); 

		int[ ] b = fromInt(12345679);
		int[ ] c = copy(b);

		for (int i=1; i<424; ++i) {
			c=times(c, b);
			
		}



		System.out.println("has " + c.length + " cells");
		println(c);  

		System.out.println(less(a, c));

	}
}
