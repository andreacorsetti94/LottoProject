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
	
	public int ritardoMassimoCombinazioneRuota(RuotaID id, Combinazione comb){
		int max = 0;
		int tmp = 0;
		
		for ( Estrazione estrazione: storico){
			if (estrazione.getRuota(id).containsCombinazione(comb)){
				if ( tmp > max ){
					max = tmp;
				}
				tmp = 0;
			}
			else{
				tmp++;
			}
			
		}
		return max;
	}

	public int ritardoMassimoCombinazioneTutte(Combinazione comb){
		int max = 0;
		int tmp = 0;
		
		for ( Estrazione estrazione: storico){
			boolean estrazioneContieneComb = false;
			for ( Ruota ruota: estrazione.getRuote() ){
				if ( ruota.containsCombinazione(comb) && ruota.getRuota() != RuotaID.NAZIONALE){
					if ( tmp > max ){
						max = tmp;
					}
					tmp = 0;
					estrazioneContieneComb = true;
					break;
				}
			}
			if ( !estrazioneContieneComb ){
				tmp++;
			}
			
		}
		return max;
	}
	
	public int ritardoPrecedenteCombinazioneRuota(RuotaID id, Combinazione comb){
		int countEstrazioneDaCuiPartire = 0;
				
		for ( int i = 0; i < storico.size(); i++ ){
			Estrazione e = storico.get(i);
			if ( e.getRuota(id).containsCombinazione(comb) ){
				countEstrazioneDaCuiPartire = i + 1;
				break;
			}
		}
		
		int countRitardo = 0;
		for ( int j = countEstrazioneDaCuiPartire; j < storico.size(); j++ ){
			Estrazione e = storico.get(j);
			if ( e.getRuota(id).containsCombinazione(comb) ){
				return countRitardo;
			}
			countRitardo++;
		}
		return countRitardo;
	}
	
	public int ritardoPrecedenteCombinazioneTutte(Combinazione comb){
		int countEstrazioneDaCuiPartire = 0;
		
		outer: for ( int i = 0; i < storico.size(); i++ ){
			Estrazione e = storico.get(i);
			for ( Ruota ruota: e.getRuote() ){
				if (ruota.containsCombinazione(comb) && ruota.getRuota() != RuotaID.NAZIONALE ){
					countEstrazioneDaCuiPartire = i + 1;
					break outer;
				}
			}
		}
		
		int countRitardo = 0;
		for ( int j = countEstrazioneDaCuiPartire; j < storico.size(); j++ ){
			Estrazione e = storico.get(j);
			for ( Ruota ruota: e.getRuote() ){
				if (ruota.containsCombinazione(comb) ){
					return countRitardo;
				}
			}
			countRitardo++;
		}
		return countRitardo;
	}
	
	public int ritardoMedioRuota(RuotaID id, Combinazione comb){
		
		//somma ritardi temporanei (compreso attuale) / (numero volte estratto + 1)
		int numeroEstrazioni = 0;
		int sommaRitardiTemporanei = 0;
		int tmpRitardo = 0;
		
		for ( Estrazione e: storico ){
			if ( e.getRuota(id).containsCombinazione(comb) ){
				sommaRitardiTemporanei += tmpRitardo;
				tmpRitardo = 0;
				numeroEstrazioni++;
			}
			else{
				tmpRitardo++;
			}
		}
		
		//includere ritardo attuale
		sommaRitardiTemporanei += tmpRitardo;
		return (int) sommaRitardiTemporanei/(numeroEstrazioni + 1);
	}
	
	public int ritardoMedioTutte(Combinazione comb){
		
		//somma ritardi temporanei (compreso attuale) / (numero volte estratto + 1)
		int numeroEstrazioni = 0;
		int sommaRitardiTemporanei = 0;
		int tmpRitardo = 0;
		
		for ( Estrazione e: storico ){
			boolean flag = false;
			for ( Ruota ruota: e.getRuote() ){
				if ( ruota.containsCombinazione(comb) && ruota.getRuota() != RuotaID.NAZIONALE){
					sommaRitardiTemporanei += tmpRitardo;
					tmpRitardo = 0;
					numeroEstrazioni++;
					flag = true;
					break;
				}
			}
			if ( !flag ){
				tmpRitardo++;
			}
		}
		
		//includere ritardo attuale
		sommaRitardiTemporanei += tmpRitardo;
		return (int) sommaRitardiTemporanei/(numeroEstrazioni + 1);
	}
	
	
	public int ritardoCombinazioneRuota(RuotaID id, Combinazione comb){
		int count = 0;

		for ( Estrazione estrazione: storico){
			if (estrazione.getRuota(id).containsCombinazione(comb)) break;
			count++;
		}
		return count;
	}

	public int ritardoCombinazioneTutte(Combinazione comb){
		int count = 0;

		outer_loop: for (Estrazione estrazione: storico){
			for ( Ruota ruota: estrazione.getRuote() ){
				if ( ruota.containsCombinazione(comb) && ruota.getRuota() != RuotaID.NAZIONALE) break outer_loop;
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
	public LinkedHashMap<Numero, Integer> piuRitardatariPerRuota(RuotaID id, int limit){
		if ( limit < 1 ) return new LinkedHashMap<>();

		Map<Numero,Integer> map = new HashMap<>();

		for ( Numero numero: NumeroController.getInsiemeNumeri() ){
			int ritardo = ritardoCombinazioneRuota(id, numero);
			map.put(numero,ritardo);
		}

		return CollectionHelper.sortAmboHashMapByIntegerValue(map, limit, true);

	}

	/**
	 * Torna una lista di *len* coppie numero-ritardo, dove al primo posto
	 * c'è il numero più ritardatario su tutte, al secondo posto il secondo, ecc.
	 * @param id
	 * @param len
	 * @return
	 */
	public LinkedHashMap<Numero, Integer> piuRitardatariPerTutte(int limit){
		if ( limit < 1 ) return new LinkedHashMap<>();

		Map<Numero,Integer> map = new HashMap<>();

		for ( Numero numero: NumeroController.getInsiemeNumeri() ){
			int ritardo = ritardoCombinazioneTutte(numero);
			map.put(numero,ritardo);
		}

		return CollectionHelper.sortAmboHashMapByIntegerValue(map, limit, true);

	}


	/**
	 * Torna una lista di *len* triple Ruota, Numero, ritardo ordinate in base al ritardo
	 * @param id
	 * @param len
	 * @return
	 */
	public LinkedHashMap<NumeroRuota, Integer> piuRitardatariAssoluti(int limit){
		if ( limit < 1 ) return new LinkedHashMap<>();

		Map<NumeroRuota,Integer> map = new HashMap<>();

		for ( Numero numero: NumeroController.getInsiemeNumeri() ){
			for ( RuotaID id: RuotaID.values() ){
				NumeroRuota nr = new NumeroRuota(numero, id);
				int ritardo = ritardoCombinazioneRuota(id,numero);
				map.put(nr,ritardo);
			}

		}

		return CollectionHelper.sortAmboHashMapByIntegerValue(map, limit, true);

	}

	@SuppressWarnings("unchecked")
	public LinkedHashMap<Ambo, Integer> ambiPiuRitardatari(RuotaID id, int limit){
		
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

		return CollectionHelper.sortAmboHashMapByIntegerValue(amboMap, limit, true);

	}
	
	public LinkedHashMap<Ambo, Integer> ambiPiuRitardatariBis(RuotaID id, int limit){
		Map<Ambo,Integer> amboMap = new HashMap<>();
		for ( Ambo ambo: NumeroController.generaAmbi() ){
			int rit = ritardoCombinazioneRuota(id, ambo);
			amboMap.put(ambo,rit);
		}
		return CollectionHelper.sortAmboHashMapByIntegerValue(amboMap, limit, true);

	}
	
	public LinkedHashMap<Ambo, Integer> ambiPiuRitardatariConCapogioco(RuotaID id, int limit){
		//get capogioco, cioè massimo ritardatario per una ruota
		List<Numero> keyList = new ArrayList<>(this.piuRitardatariPerRuota(id, 1).keySet());
		Numero n = keyList.get(0);
		
		List<Ambo> ambi = NumeroController.generaAmbiAPartireDaNumero(n);
		Map<Ambo,Integer> amboMap = new HashMap<>();
		for ( Ambo ambo: ambi ){
			int rit = ritardoCombinazioneRuota(id, ambo);
			amboMap.put(ambo,rit);
		}
		return CollectionHelper.sortAmboHashMapByIntegerValue(amboMap, limit, true);
	}

}
