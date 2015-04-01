
public abstract class Beverage extends Groceries { 

	public Beverage (String name, int amount){
		super(name, amount);
	}

	public boolean eat (){
		return false;
	}
	
	public boolean drink(int amount){
		if (this.amount>=amount){
			this.amount -= amount;
			return true;
		} else
			return false;	
	}
	
	public abstract boolean drink(); 
	
	public abstract String status(); 
}
