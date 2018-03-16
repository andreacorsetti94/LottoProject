package estrazioni;

import iofetch.LocalConfigurationManager;

import java.util.ArrayList;
import java.util.List;

public class StoricoManager {
	
	/**
	 * Usate solo allo startup per caricare il file salvato
	 */
	private static List<String> storicoLines;
	
	/**
	 * Crea nuove estrazioni a partire da una lista di stringhe e le aggiunge
	 * alla classe Controller
	 * @param newLines
	 */
	public static void updateEstrazioni(List<String> newLines){
		List<Estrazione> nuoveEstrazioni = EstrazioneFetcher.retrieveEstrazioni(newLines);
		List<Estrazione> estrazioniNonAncoraInserite = new ArrayList<>();
		
		List<Estrazione> currentEstrazioni = EstrazioneController.getList();
		for( Estrazione nuova: nuoveEstrazioni ){
			if (!currentEstrazioni.contains(nuova) && !estrazioniNonAncoraInserite.contains(nuova) ){
				estrazioniNonAncoraInserite.add(nuova);
			}
		}
		EstrazioneController.appendEstrazioniToPresent(estrazioniNonAncoraInserite);
		
	}
	
	public static List<String> getStoricoLines(){
		return storicoLines;
	}
	
	public static void setStoricoLines(List<String> lines){
		storicoLines = lines;
	}
	
	static {
		storicoLines = LocalConfigurationManager.fetchStoricoStream();
	}
}
