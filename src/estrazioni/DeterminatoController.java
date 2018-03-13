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

public class DeterminatoController {

	private List<Estrazione> storico;

	public DeterminatoController(List<Estrazione> estrazioni){
		super();
		this.storico = EstrazioneController.sortFromRecent(estrazioni);
	}

	public int ritardoMassimoDetRuota(Numero numero, int pos, RuotaID id){
		int max = 0;
		int tmp = 0;

		for ( Estrazione estrazione: storico){
			Ruota ruota = estrazione.getRuota(id);
			if ( ruota.isEmpty() ) continue;
			if (ruota.getDeterminato(pos).equals(numero) ){
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

	public int ritardoMassimoDetTutte(Numero numero, int pos){
		int max = 0;
		int tmp = 0;

		for ( Estrazione estrazione: storico){
			boolean estrazioneContieneDetInPosizione = false;
			for ( Ruota ruota: estrazione.getRuote() ){
				if ( ruota.isEmpty() ) continue;
				if ( ruota.getDeterminato(pos).equals(numero) && ruota.getRuota() != RuotaID.NAZIONALE){
					if ( tmp > max ){
						max = tmp;
					}
					tmp = 0;
					estrazioneContieneDetInPosizione = true;
					break;
				}
			}
			if ( !estrazioneContieneDetInPosizione ){
				tmp++;
			}

		}
		return max;
	}

	public int ritardoPrecedenteDetRuota(Numero numero, int pos, RuotaID id){
		int countEstrazioneDaCuiPartire = 0;

		for ( int i = 0; i < storico.size(); i++ ){
			Estrazione e = storico.get(i);
			if ( e.getRuota(id).getDeterminato(pos).equals(numero) ){
				countEstrazioneDaCuiPartire = i + 1;
				break;
			}
		}

		int countRitardo = 0;
		for ( int j = countEstrazioneDaCuiPartire; j < storico.size(); j++ ){
			Estrazione e = storico.get(j);
			if ( e.getRuota(id).getDeterminato(pos).equals(numero) ){
				return countRitardo;
			}
			countRitardo++;
		}
		return countRitardo;
	}

	public int ritardoPrecedenteDetTutte(Numero numero, int pos){
		int countEstrazioneDaCuiPartire = 0;

		outer: for ( int i = 0; i < storico.size(); i++ ){
			Estrazione e = storico.get(i);
			for ( Ruota ruota: e.getRuote() ){
				if (ruota.getDeterminato(pos).equals(numero) && ruota.getRuota() != RuotaID.NAZIONALE ){
					countEstrazioneDaCuiPartire = i + 1;
					break outer;
				}
			}
		}

		int countRitardo = 0;
		for ( int j = countEstrazioneDaCuiPartire; j < storico.size(); j++ ){
			Estrazione e = storico.get(j);
			for ( Ruota ruota: e.getRuote() ){
				if (ruota.getDeterminato(pos).equals(numero) ){
					return countRitardo;
				}
			}
			countRitardo++;
		}
		return countRitardo;
	}

	public int ritardoMedioRuota(RuotaID id, Numero numero, int pos){

		//somma ritardi temporanei (compreso attuale) / (numero volte estratto + 1)
		int numeroEstrazioni = 0;
		int sommaRitardiTemporanei = 0;
		int tmpRitardo = 0;

		for ( Estrazione e: storico ){
			if ( e.getRuota(id).getDeterminato(pos).equals(numero) ){
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

	public int ritardoMedioTutte(Numero numero, int pos){

		//somma ritardi temporanei (compreso attuale) / (numero volte estratto + 1)
		int numeroEstrazioni = 0;
		int sommaRitardiTemporanei = 0;
		int tmpRitardo = 0;

		for ( Estrazione e: storico ){
			boolean flag = false;
			for ( Ruota ruota: e.getRuote() ){
				if ( ruota.getDeterminato(pos).equals(numero) && ruota.getRuota() != RuotaID.NAZIONALE){
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

	public int ritardoDetRuota(RuotaID id, Numero numero, int pos){
		int count = 0;

		for ( Estrazione estrazione: storico){
			if (estrazione.getRuota(id).getDeterminato(pos).equals(numero) ) break;
			count++;
		}
		return count;
	}

	public int ritardoDetTutte(Numero numero, int pos){
		int count = 0;

		outer: for (Estrazione estrazione: storico){
			for ( Ruota ruota: estrazione.getRuote() ){
				if ( ruota.isEmpty() ) continue;
				if ( ruota.getDeterminato(pos).equals(numero) && ruota.getRuota() != RuotaID.NAZIONALE) break outer;
			}
			count++;
		}

		return count;
	}

	public LinkedHashMap<Numero, Integer> ritardiDetRuota(RuotaID id, int pos, int limit){
		Map<Numero,Integer> numeriMap = new HashMap<>();

		for( Numero n: NumeroController.getInsiemeNumeri() ){
			numeriMap.put(n,this.ritardoDetRuota(id, n, pos));
		}

		return CollectionHelper.sortHashMapByIntegerValue(numeriMap, limit, true);
	}

	public LinkedHashMap<Numero, Integer> ritardiDetTutte(int pos, int limit){
		Map<Numero,Integer> numeriMap = new HashMap<>();

		for( Numero n: NumeroController.getInsiemeNumeri() ){
			numeriMap.put(n,this.ritardoDetTutte(n, pos));
		}

		return CollectionHelper.sortHashMapByIntegerValue(numeriMap, limit, true);
	}

	public int frequenzaDetRuota(RuotaID id, Numero numero, int pos){
		int count = 0;

		for ( Estrazione estrazione: storico ){
			Ruota ruota = estrazione.getRuota(id);
			if ( ruota.isEmpty() ) continue;
			if ( ruota.getDeterminato(pos).equals(numero) ) count++;
		}
		return count;
	}

	public int freqDetTutte(Numero numero, int pos){
		int count = 0;

		for ( Estrazione estrazione: storico ){
			for ( Ruota ruota: estrazione.getRuote() ){
				if ( ruota.isEmpty() ) continue;
				if ( ruota.getDeterminato(pos).equals(numero) && ruota.getRuota() != RuotaID.NAZIONALE) 
					count++;
			}
		}
		return count;
	}

	public LinkedHashMap<Numero,Integer> piuFrequentiRuota(RuotaID id, int pos, int limit){
		Map<Numero,Integer> numeriMap = new HashMap<>();

		for( Estrazione e: storico){
			Numero numInPosizione = e.getRuota(id).getDeterminato(pos);
			if ( numInPosizione == null )  continue;
			Integer frequenza = numeriMap.get(numInPosizione);
			if ( frequenza == null ){
				numeriMap.put(numInPosizione, 1);
			}
			else{
				numeriMap.put(numInPosizione, frequenza + 1);
			}
		}

		return CollectionHelper.sortHashMapByIntegerValue(numeriMap, limit, true);
	}

	public LinkedHashMap<Numero,Integer> piuFrequentiTutte(int pos, int limit){
		Map<Numero,Integer> numeriMap = new HashMap<>();

		for( Estrazione e: storico){
			for ( Ruota r: e.getRuote() ){
				if ( r.getRuota() == RuotaID.NAZIONALE ) continue;
				Numero numInPosizione = r.getDeterminato(pos);
				if ( numInPosizione == null )  continue;
				Integer frequenza = numeriMap.get(numInPosizione);
				if ( frequenza == null ){
					numeriMap.put(numInPosizione, 1);
				}
				else{
					numeriMap.put(numInPosizione, frequenza + 1);
				}	
			}

		}

		return CollectionHelper.sortHashMapByIntegerValue(numeriMap, limit, true);
	}
}
