package estrazioni;

import helper.CollectionHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import numeri.Combinazione;
import ruote.Ruota;
import ruote.RuotaController;
import ruote.RuotaID;

public class FrequenzaController {

	private List<Estrazione> storico;
	
	public FrequenzaController(List<Estrazione> estrazioni){
		super();
		this.storico = estrazioni;
	}
	
	public int getFreqCombinazioneRuota(RuotaID id, Combinazione comb){
		int count = 0;
		
		for ( Estrazione estrazione: storico ){
			Ruota ruota = estrazione.getRuota(id);
			
			if ( ruota.containsCombinazione(comb) ) count++;
		}
		return count;
	}
	
	public int getFreqCombinazioneTutte(Combinazione comb){
		int count = 0;
		
		for ( Estrazione estrazione: storico ){
			for ( Ruota ruota: estrazione.getRuote() ){
				if ( ruota.containsCombinazione(comb) ) count++;
			}
		}
		return count;
	}
	
	public LinkedHashMap<Combinazione,Integer> combinazioniPiuFrequenti(RuotaID id, int limit, int combLen){
		Map<Combinazione, Integer> combMap = new HashMap<>();
		
		for ( Estrazione e: storico ){
			Ruota r = e.getRuota(id);
			List<? extends Combinazione> combInRuota = RuotaController.getPermutazioneCombinazione(r, combLen);
			for ( Combinazione combinazione: combInRuota ){
				Integer freq = combMap.get(combinazione);
				if ( freq == null ){
					combMap.put(combinazione, 1);
				}
				else{
					combMap.put(combinazione,freq + 1);
				}
			}
		}
		
		return CollectionHelper.sortAmboHashMapByIntegerValue(combMap, limit, true);
	}
	
}

