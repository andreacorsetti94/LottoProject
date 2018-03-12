package estrazioni;

import helper.CollectionHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import numeri.Numero;
import numeri.NumeroController;
import ruote.Ruota;
import ruote.RuotaID;

public class SpiaController {

	private final List<Estrazione> storico;

	public SpiaController(List<Estrazione> estrazioni) {
		super();
		this.storico = estrazioni;
	}
	
	//parametri: Numero, Ruota, limite di quanti spiati mettere per ogni spia, quante estrazioni
	//considerare per una spia di spiare gli altri
	/**
	 * Torna una mappa numero spiato - numero di spie a buon fine, che rappresenta la collezione
	 * ordinata di numeri spiati a partire dalla ruota selezionata, il numero spia, il limite
	 * di lunghezza della mappa ritornata e il numero di estrazioni da prendere in considerazione
	 * a partire dall'uscita della spia.
	 * @param id
	 * @param numeroSpia
	 * @param limitSpie
	 * @param limitEstrazioni
	 * @return
	 */
	public LinkedHashMap<Numero,Integer> spiaPerNumeroRuota(RuotaID id, Numero numeroSpia, int limitSpie, int limitEstrazioni){
		Map<Numero, Integer> spieToNumeroSpieMap = new HashMap<>();
		
		for (int i = 0; i < storico.size(); i++){
			Estrazione e = storico.get(i);
			if ( e.getRuota(id).containsCombinazione(numeroSpia) ){
				for ( int j = i + 1; j < i + 1 + limitEstrazioni && j < storico.size(); j++ ){
					Estrazione succ = storico.get(j);
					Ruota ruota = succ.getRuota(id);
					for ( Numero spiato: ruota.getNumeri() ){
						Integer numeroVolteSpiato = spieToNumeroSpieMap.get(spiato);
						if ( numeroVolteSpiato == null){
							spieToNumeroSpieMap.put(spiato, 1);
						}
						else{
							spieToNumeroSpieMap.put(spiato, numeroVolteSpiato + 1);
						}
					}
				}
			}
		}
		return CollectionHelper.sortAmboHashMapByIntegerValue(spieToNumeroSpieMap, limitSpie, true);
	}
	
	/**
	 * Torna una mappa che associa a un ambetto, *limitSpie* spiati, dove la spia avviene entro
	 * *limitEstrazioni* per una ruota *id*. La mappa non è ordinata
	 * @param id
	 * @param limitSpie
	 * @param limitEstrazioni
	 * @return
	 */
	public Map<Numero, LinkedHashMap<Numero,Integer>> spieSuRuota(RuotaID id,int limitSpie, int limitEstrazioni ){
		Map<Numero, LinkedHashMap<Numero,Integer>> spieSuRuota = new HashMap<>();

		for ( Numero numero: NumeroController.getInsiemeNumeri() ){
			LinkedHashMap<Numero,Integer> spie = spiaPerNumeroRuota(id, numero, limitSpie, limitEstrazioni);
			spieSuRuota.put(numero, spie);
		}
		
		return spieSuRuota;
		
	}
	
}
