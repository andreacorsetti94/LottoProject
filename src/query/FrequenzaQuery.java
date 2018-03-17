package query;

import helper.CollectionHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import estrazioni.Estrazione;
import numeri.Combinazione;
import numeri.TipoCombinazione;
import ruote.Ruota;
import ruote.RuotaController;
import ruote.RuotaID;

public class FrequenzaQuery extends AbstractQuery{

	public FrequenzaQuery(List<Estrazione> estrazioni){
		super(estrazioni);
	}

	public int getFreqCombinazioneRuota(RuotaID id, Combinazione comb){
		int count = 0;

		for ( Estrazione estrazione: super.getEstrazioni() ){
			Ruota ruota = estrazione.getRuota(id);

			if ( ruota.containsCombinazione(comb) ) count++;
		}
		return count;
	}

	public int getFreqCombinazioneTutte(Combinazione comb){
		int count = 0;

		for ( Estrazione estrazione: super.getEstrazioni() ){
			for ( Ruota ruota: estrazione.getRuote() ){
				if ( ruota.containsCombinazione(comb) && ruota.getRuota() != RuotaID.NAZIONALE) 
					count++;
			}
		}
		return count;
	}

	public LinkedHashMap<Combinazione,Integer> combinazioniPiuFrequenti(RuotaID id, int limit, TipoCombinazione tipo){
		Map<Combinazione, Integer> combMap = new HashMap<>();

		for ( Estrazione e: super.getEstrazioni() ){
			Ruota r = e.getRuota(id);
			List<? extends Combinazione> combInRuota = RuotaController.getPermutazioneCombinazione(r, tipo.len());
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

		return CollectionHelper.sortHashMapByIntegerValue(combMap, limit, true);
	}

	public LinkedHashMap<Combinazione,Integer> combinazioniPiuFrequentiTutte(int limit, TipoCombinazione tipo){
		Map<Combinazione, Integer> combMap = new HashMap<>();

		for ( Estrazione e: super.getEstrazioni() ){
			for( Ruota r: e.getRuote() ){
				if ( r.getRuota() == RuotaID.NAZIONALE ) continue;
				List<? extends Combinazione> combInRuota = RuotaController.getPermutazioneCombinazione(r, tipo.len());
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
		}

		return CollectionHelper.sortHashMapByIntegerValue(combMap, limit, true);
	}

}

