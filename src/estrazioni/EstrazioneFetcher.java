package estrazioni;

import helper.DateHelper;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Stack;

import numeri.Numero;
import ruote.RuotaHelper;
import ruote.RuotaID;

/**
 * @author andy
 *
 */
public class EstrazioneFetcher {

	private static final String DATE_FORMAT = "yyyy/MM/dd";

	/**
	 * A partire dalle linee del file.
	 * NOTA: Si assume che ciò preso in input sia ORDINATO PER DATA

	 * @return
	 */
	public List<Estrazione> fetchCompleteList(){

		List<String> lines = StoricoManager.getStoricoLines();
		return this.retrieveEstrazioni(lines);
		
	}
	
	private List<Estrazione> retrieveEstrazioni(List<String> inputLines){
		Stack<Estrazione> stack = new Stack<>();

		Estrazione estrazioneCorrente = null;
		int current_year = 0;		//necessary for setting annual counter
		int annualCounter = 1;		//necessary for setting annual counter
		for ( String line: inputLines ){
			
			String[] linefields = line.split("\\s+");

			Calendar date = DateHelper.fetchCalendarDate(DATE_FORMAT, linefields[0]);
			
			if ( this.nuovaEstrazioneNecessaria(stack, date) ){
				if ( date.get(Calendar.YEAR) == current_year ){
					annualCounter++;			//necessary for setting annual counter
				}
				else{
					current_year = date.get(Calendar.YEAR);	//necessary for setting annual counter
					annualCounter = 1;			//necessary for setting annual counter
				}
				
				estrazioneCorrente = new Estrazione(date, annualCounter);
				stack.push(estrazioneCorrente);
			}
			else{
				estrazioneCorrente = stack.peek();
			}

			RuotaID id = RuotaHelper.fetchIdFromString(linefields[1]);
			
			estrazioneCorrente.getRuota(id).popolaRuota(this.fetchNumeriRuota(linefields[2], 
					linefields[3], linefields[4], linefields[5], linefields[6]));
		}
		

		return stack;
	}

	/**
	 * A partire da un array di stringhe, queste vengono convertite in Numero
	 * @param line
	 * @return
	 */
	private List<Numero> fetchNumeriRuota(String... line){

		int len = 5;
		Numero[] numeri = new Numero[len];

		for ( int i = 0; i < len; i++){
			try{
				numeri[i] = new Numero(Integer.parseInt(line[i]));
			}
			catch( Exception e){
				System.err.println("Error extracting values from input line"  + e.getMessage());
			}
		}

		return Arrays.asList(numeri);
	}

	/**
	 * A partire da una data e dallo stack, verifica se la data fa riferimento
	 * a quella della estrazione precedente nello stack oppure no 
	 * (e quindi verrà generata una nuova estrazione)
	 * @param prec
	 * @param data
	 * @return
	 */
	private boolean nuovaEstrazioneNecessaria(Stack<Estrazione> stack, Calendar data){
		if ( stack.isEmpty() ) return true;
		
		Estrazione prec = stack.peek();
		Calendar prec_date = prec.getDate();
		
		boolean necessario = ! (data.equals(prec_date) );

		return necessario;
	}

}
