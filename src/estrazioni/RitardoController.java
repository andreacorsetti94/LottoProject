package estrazioni;

import helper.CollectionHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import numeri.Ambo;
import numeri.Combinazione;
import numeri.Numero;
import numeri.NumeroController;
import numeri.NumeroRuota;
import ruote.Ruota;
import ruote.RuotaController;
import ruote.RuotaID;

public class RitardoController {

	private final List<Estrazione> storico;

	public RitardoController(List<Estrazione> estrazioni) {
		super();
		this.storico = EstrazioneController.sortFromRecent(estrazioni);
	}

	public int getRitardoCombinazioneSuRuota(RuotaID id, Combinazione comb){
		int count = 0;

		for ( Estrazione estrazione: storico){
			if (estrazione.getRuota(id).containsCombinazione(comb)) break;
			count++;
		}
		return count;
	}

	public int getRitardoCombinazioneSuTutte(Combinazione comb){
		int count = 0;

		outer_loop: for (Estrazione estrazione: storico){
			for ( Ruota ruota: estrazione.getRuote() ){
				if ( ruota.containsCombinazione(comb) ) break outer_loop;
			}
			count++;
		}

		return count;
	}

	/**
	 * Torna una lista di *len* coppie numero-ritardo, dove al primo posto
	 * c'è il numero più ritardatario sulla ruota in input, al secondo posto il secondo, ecc.
	 * @param id
	 * @param len
	 * @return
	 */
	public LinkedHashMap<Numero, Integer> piuRitardatariPerRuota(RuotaID id, int len){
		if ( len < 1 ) return new LinkedHashMap<>();

		Map<Numero,Integer> map = new HashMap<>();

		for ( Numero numero: NumeroController.getInsiemeNumeri() ){
			int ritardo = getRitardoCombinazioneSuRuota(id, numero);
			map.put(numero,ritardo);
		}

		return CollectionHelper.sortAmboHashMapByIntegerValue(map, len, true);

	}

	/**
	 * Torna una lista di *len* coppie numero-ritardo, dove al primo posto
	 * c'è il numero più ritardatario su tutte, al secondo posto il secondo, ecc.
	 * @param id
	 * @param len
	 * @return
	 */
	public LinkedHashMap<Numero, Integer> piuRitardatariPerTutte(int len){
		if ( len < 1 ) return new LinkedHashMap<>();

		Map<Numero,Integer> map = new HashMap<>();

		for ( Numero numero: NumeroController.getInsiemeNumeri() ){
			int ritardo = getRitardoCombinazioneSuTutte(numero);
			map.put(numero,ritardo);
		}

		return CollectionHelper.sortAmboHashMapByIntegerValue(map, len, true);

	}


	/**
	 * Torna una lista di *len* triple Ruota, Numero, ritardo ordinate in base al ritardo
	 * @param id
	 * @param len
	 * @return
	 */
	public LinkedHashMap<NumeroRuota, Integer> piuRitardatariAssoluti(int len){
		if ( len < 1 ) return new LinkedHashMap<>();

		Map<NumeroRuota,Integer> map = new HashMap<>();

		for ( Numero numero: NumeroController.getInsiemeNumeri() ){
			for ( RuotaID id: RuotaID.values() ){
				NumeroRuota nr = new NumeroRuota(numero, id);
				int ritardo = getRitardoCombinazioneSuRuota(id,numero);
				map.put(nr,ritardo);
			}

		}

		return CollectionHelper.sortAmboHashMapByIntegerValue(map, len, true);

	}

	@SuppressWarnings("unchecked")
	public LinkedHashMap<Ambo, Integer> ambiPiuRitardatari(RuotaID id, int len){
		
		Map<Ambo,Integer> amboMap = new HashMap<>();
		int i = 0;
		
		while(amboMap.size() <= 4005 && i < storico.size() ){
			Estrazione estrazione = storico.get(i);
			Ruota ruota = estrazione.getRuota(id);
			List<Ambo> ambiInRuota = (List<Ambo>) RuotaController.getPermutazioneCombinazione(ruota, 2);

			for ( Ambo ambo: ambiInRuota ){
				Integer ritardo = amboMap.get(ambo);
				if ( ritardo == null ){
					amboMap.put(ambo, i);
				}
			}
			i++;
		}
		
		for ( Ambo ambo: NumeroController.generaAmbi() ){
			if ( amboMap.get(ambo) == null ){
				amboMap.put(ambo, storico.size());
			}
		}

		return CollectionHelper.sortAmboHashMapByIntegerValue(amboMap, len, true);

	}
	
	public LinkedHashMap<Combinazione, Integer> combPiuRitardatarie(RuotaID id, int limit, int combLen){
		
		Map<Combinazione,Integer> combMap = new HashMap<>();
		int i = 0;
		
		while(combMap.size() <= 4005 && i < storico.size() ){
			Estrazione estrazione = storico.get(i);
			Ruota ruota = estrazione.getRuota(id);
			List<? extends Combinazione> combInRuota = RuotaController.getPermutazioneCombinazione(ruota, 2);

			for ( Combinazione ambo: combInRuota ){
				Integer ritardo = combMap.get(ambo);
				if ( ritardo == null ){
					combMap.put(ambo, i);
				}
			}
			i++;
		}
		
		//controlla se di default puoi assegnare qualcosa
		for ( Ambo ambo: NumeroController.generaAmbi() ){
			if ( combMap.get(ambo) == null ){
				combMap.put(ambo, storico.size());
			}
		}

		return CollectionHelper.sortAmboHashMapByIntegerValue(combMap, limit, true);

	}
	
	public LinkedHashMap<Ambo, Integer> ambiPiuRitardatariBis(RuotaID id, int len){
		Map<Ambo,Integer> amboMap = new HashMap<>();
		for ( Ambo ambo: NumeroController.generaAmbi() ){
			int rit = getRitardoCombinazioneSuRuota(id, ambo);
			amboMap.put(ambo,rit);
		}
		return CollectionHelper.sortAmboHashMapByIntegerValue(amboMap, len, true);

	}
	
	public LinkedHashMap<Ambo, Integer> ambiPiuRitardatariConCapogioco(RuotaID id, int len){
		//get capogioco, cioè massimo ritardatario per una ruota
		List<Numero> keyList = new ArrayList<>(this.piuRitardatariPerRuota(id, 1).keySet());
		Numero n = keyList.get(0);
		
		List<Ambo> ambi = NumeroController.generaAmbiConCapogioco(n);
		Map<Ambo,Integer> amboMap = new HashMap<>();
		for ( Ambo ambo: ambi ){
			int rit = getRitardoCombinazioneSuRuota(id, ambo);
			amboMap.put(ambo,rit);
		}
		return CollectionHelper.sortAmboHashMapByIntegerValue(amboMap, len, true);
	}

}
