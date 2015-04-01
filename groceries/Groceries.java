
public abstract class Groceries {
	protected String name;
	protected int amount; // in milliliter or gram
	
	public Groceries(String name, int amount) {
	this.name = name;
	this.amount = amount;
	}
	public abstract boolean eat();
	public abstract boolean drink();
	public abstract String status();
}
