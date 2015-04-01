
public class Sausage extends Food {

	public Sausage(String name, int amount){
		super(name, amount);
	}

	public boolean eat(){
		return super.eat(10);
	}
	
	public String status(){
		String sausage = "Sausage " + name + " " + amount + " g.";
		return sausage;
	}
}

