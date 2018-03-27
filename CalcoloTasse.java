import java.io.*;
import java.math.*;
import java.util.Scanner;
import java.util.ArrayList;
public class CalcoloTasse {
    //array statico per arrotondamento a 0,05 più vicino
	static double [] arr = {0.0,0.4,0.3,0.2,0.1,0.0,0.4,0.3,0.2,0.1};
	//funzione per far stampare solo 2 decimali
	public static double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
	//funzione che arrotonda allo 0,05 più vicino
	public static double arrotondamento(double valore) {
		int tmp = (int) valore * 100;
		tmp = tmp % 10;
		valore += arr[tmp];
		return valore;
	}
	//main
	public static void main(String[] args) {
		//utilizzo la scanner per acquisire da tastiera
		Scanner input = new Scanner(System.in);
		//utilizzo arraylist per creare un array dinamico di oggetti(Prodotto)
		ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>(1);
		double tassatot = 0;
		double prezzotot = 0;
		int i = 0;
		System.out.println("Inserisci quantita' prodotto oppure inserisci 0 per uscire dall'acquisizione ");
		int check = input.nextInt(); //utilizzo 0 per uscire dal ciclo
		while(check != 0) {
			//utilizzo nuovo come un oggetto "di appoggio" temporaneo
			Prodotto nuovo = new Prodotto();
			nuovo.quantita=check;
			//acquisisco da tastiera i vari dati
			System.out.println("Inserisci nome prodotto: ");
			nuovo.nome=input.next();
			System.out.println("E' un libro/cibo/prodotto medico? SI/NO ");
			nuovo.tipo=input.next();
			System.out.println("E' stato importato? SI/NO ");
			nuovo.prov=input.next();
			System.out.println("Inserisci prezzo: ");
			nuovo.prezzo=input.nextDouble();
			double tmp = 0;
			//controllo tipi di prodotto per la tassazione
			if(nuovo.tipo.equals("no") || nuovo.tipo.equals("NO")) {
				tmp += nuovo.quantita * nuovo.prezzo * 0.1;
			}
			if(nuovo.prov.equals("si") || nuovo.prov.equals("SI")) {
				tmp += nuovo.quantita * nuovo.prezzo * 0.05;
			}
			//aggiungo all'array il prodotto
			tmp = arrotondamento(tmp);
			tassatot += tmp;
			nuovo.prezzo += tmp;
			prodotti.add(i,nuovo);
			i++;
			System.out.println("Inserisci quantita' prodotto oppure inserisci 0 per uscire dall'acquisizione ");
			check = input.nextInt();
		}
		//stampo output
		for(int j=0; j<prodotti.size(); j++) {
			System.out.println(prodotti.get(j).quantita+" "+prodotti.get(j).nome+": "+round(prodotti.get(j).prezzo,2) +"\n");
			prezzotot=prezzotot+prodotti.get(j).prezzo;
		}
		System.out.println("Sales taxes: " + round(arrotondamento(tassatot),2) + "\n");
		System.out.println("Total: "+ round(prezzotot,2)+"\n");
	}

}