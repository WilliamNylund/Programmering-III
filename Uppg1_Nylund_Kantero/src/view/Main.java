package view;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args){
		
		//problem uppstår när det inte finns lika många aktievärden i alphavantage för symbol 1 och 2.
		//i DataManager klassen skapar vi nu strängen som skall utskrivas genom att sätta båda symbolernas värden
		//i en lista och sedan dela den på mitten. Om ena aktien innehåller mera värden än
		//den andra blir värdena fel och grafen kan inte skrivas ut pga x och y axelns
		//antal värden är olika.
		//t.ex när Output Size är full kan problem uppstå.
		Window window = new Window();
		//lag en trycatch som catchar om 2 symbols har olika värden lulu XD drunk lmao
	}

}
