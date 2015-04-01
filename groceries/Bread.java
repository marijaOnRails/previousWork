
public class Bread extends Food {
	public Bread (int type, int amount){
		super(""+type, amount);
		
		if(type==0)
			name="WhiteBroead";
		else if (type==1)
			name="BlackBread";
		else if (type==2)
			name="BrownBread";
		else
			name="SpecialBread";
	}

	public boolean eat(){
		return super.eat(50);
	}
	
	public String status(){
		String bread="Bread " + name + " " + amount + "g.";
		return bread;
	}
}
