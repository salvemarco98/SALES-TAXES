import java.io.*;
import java.math.*;
import java.util.Scanner;
import java.util.ArrayList;
public class CalcoloTasse {
    //array statico per arrotondamento a 0,05 più vicino
	static int [] roundArr = {0,4,3,2,1,0,4,3,2,1};
	//funzione che arrotonda allo 0,05 più vicino
	public static void arrotondamento(int [] price) {
		if(price == null)
			return;
		int digit = countDigit(price[1]);
		boolean flag = false;
		if(digit > 2)
		{
			price[1] /= Math.pow(10, digit - 2);
			flag = true;
		}
		int val = price[1] % 10;
		if((val == 0 || val == 5) && flag)
			price[1] += 5;
		else
			price[1] += roundArr[val];

		digit = countDigit(price[1]);
		if(digit > 2)
		{
			price[0] += 1;
			price[1] %= (int) Math.pow(10, digit - 1);
		}
	}
	//funzione che conta le cifre
	public static int countDigit(int n){
		int digit = 0;
		while(n != 0)
		{
			n /= 10;
			digit++;
		}
		return digit;
	}
//funzione che calcola la tassa con l'approssimazione a 0,05
	public static int [] calTax(int [] price, int tasso){
		int [] tax = new int [2];
		tax[0] = price[0];
		tax[1] = price[1];
		if(tasso == 10)
		{
			tax[1] += ((tax[0] % 10) * 100);
			tax[0] /= 10;
		}
		else
		{
			int digit = countDigit(tax[1]);
			int tmp = (tax[0] * (int) Math.pow(10,digit)) + tax[1];
			tmp = tmp * 5;
			digit += 2;
			tax[0] = tmp / (int) Math.pow(10,digit);
			tax[1] = tmp % (int) Math.pow(10,digit);
		}
		return tax;
	}
	//funzione che somma gli interi della tassa nell'array
	public static int [] sommaArr(int [] a1, int [] b1){
		if(a1 == null)
			return b1;
		if(b1 == null)
			return a1;
		int [] a = new int [2];
		int [] b = new int [2];
		a[0] = a1[0];
		a[1] = a1[1];
		b[0] = b1[0];
		b[1] = b1[1];
		int [] arr = new int[2];
		int diga = countDigit(a[1]);
		int digb = countDigit(b[1]);
		if(diga == 1)
			diga = 2;
		if(digb == 1)
			digb = 2;
		if(diga > digb)
		{
			int tmp = diga - digb;
			b[1] *= (int) Math.pow(10,tmp);
		}
		else if(digb > diga)
		{
			int tmp = digb - diga;
			diga = digb;
			a[1] *= (int) Math.pow(10,tmp);
		}
		arr[1] = a[1] + b[1];
		int digarr = countDigit(arr[1]);
		arr[0] = a[0] + b[0];
		if(digarr > diga)
		{
			arr[0] += 1;
			arr[1] %= (int) Math.pow(10,digarr-1);
		}
		return arr;
	}

	//main
	public static void main(String[] args) {
		//utilizzo la scanner per acquisire da tastiera
		Scanner input = new Scanner(System.in);
		//utilizzo arraylist per creare un array dinamico di oggetti(Prodotto)
		ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>(1);
		int [] tassatot = null;
		int [] prezzotot = null;
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
			String tmp = input.next();
			String [] arr = tmp.split("\\.");
			nuovo.prezzo[0]=Integer.valueOf(arr[0]);
			nuovo.prezzo[1] = Integer.valueOf(arr[1]);;
			int [] tmp1 = null;
			int [] tmp2 = null;
			//controllo tipi di prodotto per la tassazione
			if(nuovo.tipo.equals("no") || nuovo.tipo.equals("NO")) {
				tmp1 = calTax(nuovo.prezzo, 10);
			}
			if(nuovo.prov.equals("si") || nuovo.prov.equals("SI")) {
				tmp2 = calTax(nuovo.prezzo,5);
			}
			//aggiungo all'array il prodotto
			int [] tax = sommaArr(tmp1,tmp2);
			arrotondamento(tax);
			tassatot = sommaArr(tax,tassatot);
			nuovo.prezzo = sommaArr(tax,nuovo.prezzo);
			prodotti.add(i,nuovo);
			i++;
			System.out.println("Inserisci quantita' prodotto oppure inserisci 0 per uscire dall'acquisizione ");
			check = input.nextInt();
		}
		//stampo output
		for(int j=0; j<prodotti.size(); j++) {
			System.out.println(prodotti.get(j).quantita+" "+prodotti.get(j).nome+": "+prodotti.get(j).prezzo[0]+"."+prodotti.get(j).prezzo[1] +"\n");
			prezzotot= sommaArr(prodotti.get(j).prezzo,prezzotot);
		}
		System.out.println("Sales taxes: " + tassatot[0]+"."+tassatot[1] + "\n");
		System.out.println("Total: "+ prezzotot[0] +"."+prezzotot[1]+"\n");
	}

}
