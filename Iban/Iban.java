/**
* @author marija
*/
import java.math.BigInteger;
/**
* The class calculates the IBAN from the given german bank identification code and 
* bank account number
*/

public class Iban {

	public static void main(String[] args) {
		
		String blz = args[0]; // 1. Argument : german bank identification code
		String kto = args[1];	// 2. Argument : bank account number
	
		String statenum = "131400";
		String account =blz+kto+statenum;
		String state = "DE";
		
		BigInteger acc = new BigInteger(account);
		BigInteger rest = acc.mod(new BigInteger("97"));
		
		int pp  = 98-rest.intValue();
		String check="";
		if (pp<10)

		{ 
			check = "0"+pp;
			
		}
		else
		{
		    check = String.valueOf(pp); 
		}
		System.out.println(state+check+blz+kto);
	}
}
	
	


