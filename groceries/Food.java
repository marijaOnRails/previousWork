
public abstract class Food extends Groceries {

	public Food (String name, int amount){
		super(name, amount);
	}

	public abstract boolean eat (); 

	protected boolean eat(int amount){ 
		if (this.amount>=amount){
			this.amount -= amount;
			return true;
		} else 
			return false;
	}

	public boolean drink(){
		return false;	
	}

	public abstract String status(); 
}
