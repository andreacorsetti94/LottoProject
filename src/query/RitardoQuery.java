package query;

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
import estrazioni.Estrazione;

public class RitardoQuery extends AbstractOrderedQuery{

	public RitardoQuery(List<Estrazione> estrazioni) {
		super(estrazioni);
	}
	
	public int ritardoMassimoCombinazioneRuota(RuotaID id, Combinazione comb){
		int max = 0;
		int tmp = 0;
		
		for ( Estrazione estrazione: super.getEstrazioni()){
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
		
		for ( Estrazione estrazione: super.getEstrazioni()){
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
				
		for ( int i = 0; i < super.getEstrazioni().size(); i++ ){
			Estrazione e = super.getEstrazioni().get(i);
			if ( e.getRuota(id).containsCombinazione(comb) ){
				countEstrazioneDaCuiPartire = i + 1;
				break;
			}
		}
		
		int countRitardo = 0;
		for ( int j = countEstrazioneDaCuiPartire; j < super.getEstrazioni().size(); j++ ){
			Estrazione e = super.getEstrazioni().get(j);
			if ( e.getRuota(id).containsCombinazione(comb) ){
				return countRitardo;
			}
			countRitardo++;
		}
		return countRitardo;
	}
	
	public int ritardoPrecedenteCombinazioneTutte(Combinazione comb){
		int countEstrazioneDaCuiPartire = 0;
		
		outer: for ( int i = 0; i < super.getEstrazioni().size(); i++ ){
			Estrazione e = super.getEstrazioni().get(i);
			for ( Ruota ruota: e.getRuote() ){
				if (ruota.containsCombinazione(comb) && ruota.getRuota() != RuotaID.NAZIONALE ){
					countEstrazioneDaCuiPartire = i + 1;
					break outer;
				}
			}
		}
		
		int countRitardo = 0;
		for ( int j = countEstrazioneDaCuiPartire; j < super.getEstrazioni().size(); j++ ){
			Estrazione e = super.getEstrazioni().get(j);
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
		
		for ( Estrazione e: super.getEstrazioni() ){
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
		
		for ( Estrazione e: super.getEstrazioni() ){
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

		for ( Estrazione estrazione: super.getEstrazioni()){
			if (estrazione.getRuota(id).containsCombinazione(comb)) break;
			count++;
		}
		return count;
	}

	public int ritardoCombinazioneTutte(Combinazione comb){
		int count = 0;

		outer_loop: for (Estrazione estrazione: super.getEstrazioni()){
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

		return CollectionHelper.sortHashMapByIntegerValue(map, limit, true);

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

		return CollectionHelper.sortHashMapByIntegerValue(map, limit, true);

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

		return CollectionHelper.sortHashMapByIntegerValue(map, limit, true);

	}

	@SuppressWarnings("unchecked")
	public LinkedHashMap<Ambo, Integer> ambiPiuRitardatari(RuotaID id, int limit){
		
		Map<Ambo,Integer> amboMap = new HashMap<>();
		int i = 0;
		
		while(amboMap.size() <= 4005 && i < super.getEstrazioni().size() ){
			Estrazione estrazione = super.getEstrazioni().get(i);
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
				amboMap.put(ambo, super.getEstrazioni().size());
			}
		}

		return CollectionHelper.sortHashMapByIntegerValue(amboMap, limit, true);

	}
	
	public LinkedHashMap<Ambo, Integer> ambiPiuRitardatariBis(RuotaID id, int limit){
		Map<Ambo,Integer> amboMap = new HashMap<>();
		for ( Ambo ambo: NumeroController.generaAmbi() ){
			int rit = ritardoCombinazioneRuota(id, ambo);
			amboMap.put(ambo,rit);
		}
		return CollectionHelper.sortHashMapByIntegerValue(amboMap, limit, true);

	}
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap<Ambo, Integer> ambiPiuRitardatariTutte(int limit){
		
		Map<Ambo,Integer> amboMap = new HashMap<>();
		int i = 0;
		
		while(amboMap.size() <= 4005 && i < super.getEstrazioni().size() ){
			Estrazione estrazione = super.getEstrazioni().get(i);
			for(Ruota ruota: estrazione.getRuote()){
				if ( ruota.getRuota() == RuotaID.NAZIONALE) continue;
				List<Ambo> ambiInRuota = (List<Ambo>) RuotaController.getPermutazioneCombinazione(ruota, 2);

				for ( Ambo ambo: ambiInRuota ){
					Integer ritardo = amboMap.get(ambo);
					if ( ritardo == null ){
						amboMap.put(ambo, i);
					}
				}
			}
			i++;

		}
		
		for ( Ambo ambo: NumeroController.generaAmbi() ){
			if ( amboMap.get(ambo) == null ){
				amboMap.put(ambo, super.getEstrazioni().size());
			}
		}

		return CollectionHelper.sortHashMapByIntegerValue(amboMap, limit, true);

	}
	
	public LinkedHashMap<Ambo, Integer> ambiPiuRitardatariTutteBis(int limit){
		Map<Ambo,Integer> amboMap = new HashMap<>();
		for ( Ambo ambo: NumeroController.generaAmbi() ){
			int rit = ritardoCombinazioneTutte(ambo);
			amboMap.put(ambo,rit);
		}
		return CollectionHelper.sortHashMapByIntegerValue(amboMap, limit, true);

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
		return CollectionHelper.sortHashMapByIntegerValue(amboMap, limit, true);
	}
	
	public int ritardoAmboDaLista(RuotaID id, List<Numero> numeri){
		List<Numero[]> ambiColl = RuotaController.permute(2, CollectionHelper.listToArray(numeri));
		int ritardoMinimo = Integer.MAX_VALUE;
		
		for(Numero[] ambi: ambiColl){
			Ambo a = new Ambo(ambi[0], ambi[1]);
			int ritardoAmbo = this.ritardoCombinazioneRuota(id, a);
			if ( ritardoAmbo < ritardoMinimo ){
				ritardoMinimo = ritardoAmbo;
			}
		}
		return ritardoMinimo;
	}
	
	public int ritardoAmboDaListaTutte(List<Numero> numeri){
		List<Numero[]> ambiColl = RuotaController.permute(2, CollectionHelper.listToArray(numeri));
		int ritardoMinimo = Integer.MAX_VALUE;
		
		for(Numero[] ambi: ambiColl){
			Ambo a = new Ambo(ambi[0], ambi[1]);
			int ritardoAmbo = this.ritardoCombinazioneTutte(a);
			if ( ritardoAmbo < ritardoMinimo ){
				ritardoMinimo = ritardoAmbo;
			}
		}
		return ritardoMinimo;
	}

}
