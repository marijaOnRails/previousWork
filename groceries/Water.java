
public class Wasser extends Getraenk {

	public Wasser (String name, int menge){
		super(name, menge);
	}

	public boolean trinken(){
		return super.trinken(200);
	}
	
	public String status(){
		String wasser="Wasser " + name + " " + menge  +" ml.";
		return wasser;
	}

}