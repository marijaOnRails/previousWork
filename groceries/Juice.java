
public class Juice extends Beverage {
	
	public Juice (String name){
		super(name,500);
	}

	public boolean drink(){
		return super.drink(100);
	}
	
	public String status(){
		String juice="Juice " + name + " " + amount + " ml.";
		return juice;
	}
       

}
