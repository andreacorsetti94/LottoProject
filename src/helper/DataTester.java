package helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import numeri.Numero;
import ruote.Ruota;
import estrazioni.Estrazione;

public class DataTester {

public Estrazione generaEstrazione(){
		
		Estrazione estr = new Estrazione(generaData(),0);
		
		for ( Ruota ruota: estr.getRuote() ){
			ruota.popolaRuota(generatoreNumeri());
		}
		
		//STORICO_ESTRAZIONI.add(estr);
		return estr;
	}
	
	private Calendar generaData(){
		Calendar cal = Calendar.getInstance();
		
		int rndYear = new Random().nextInt(15) + 2003;
		int rndMonth = new Random().nextInt(12);
		int rndDay = new Random().nextInt(28) + 1;
		
		cal.set(rndYear, rndMonth, rndDay);
		return cal;
	}
	
	private Numero[] generatoreNumeri(){
		
		List<Integer> generated = new ArrayList<>();
		Numero primo = new Numero();
		generated.add(primo.value());
		
		Numero secondo = new Numero();
		while ( generated.contains(secondo.value() )){
			secondo = new Numero();
		}
		generated.add(secondo.value());
		
		Numero terzo = new Numero();
		while ( generated.contains(terzo.value() )){
			terzo = new Numero();
		}
		generated.add(terzo.value());
		
		Numero quarto = new Numero();
		while ( generated.contains(quarto.value() )){
			quarto = new Numero();
		}
		generated.add(quarto.value());
		
		Numero quinto = new Numero();
		while ( generated.contains(quinto.value() )){
			quinto = new Numero();
		}
		generated.add(quinto.value());
		return new Numero[]{primo, secondo, terzo, quarto, quinto};
	}
}
