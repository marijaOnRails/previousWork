
public class Aufgabe3 {

	private static Knoten feldZuListe(int[] eingabe) {
		// zu implementieren
		Knoten list=null;
		for(int i=eingabe.length-1; i>=0; i--){
			Knoten temp=new Knoten();

			temp.schluessel=eingabe[i];
			temp.rechts=list;

			list=temp;
		}
		return list;
	}


	private static boolean existiertSchluessel(Knoten start, int schluessel) {
		// zu implementieren
		boolean key=false;
		while (start!=null) {
			if(start.schluessel==schluessel){
				key=true;
				break;
			}
			start=start.rechts;
		}
		return key;

	}
	private static void gibListeAus(Knoten start) {
		while (start != null) {
			System.out.println(start.schluessel);
			start = start.rechts;
		}
	}

	public static void main (String[] args) {
		int[] eingabe = {2, 4, 5, 7, 8};
		Knoten start = feldZuListe(eingabe);
		gibListeAus(start);
		System.out.println(existiertSchluessel(start, 7));
		System.out.println(existiertSchluessel(start, 0));
	}
}

