package ruote;

import java.util.ArrayList;
import java.util.List;

import numeri.Ambo;
import numeri.Cinquina;
import numeri.Combinazione;
import numeri.CombinazioneFactory;
import numeri.Numero;
import numeri.Quaterna;
import numeri.Terno;

public class RuotaController {
	
	public boolean containsAmbo(Ruota ruota, Numero... numeri){
		if ( numeri == null || numeri.length < 2 ) return false;
		
		List<Numero[]> possibiliAmbi = permute(2, numeri);
		for( Numero[] possibileAmbo: possibiliAmbi){
			Ambo ambo = new Ambo(possibileAmbo[0], possibileAmbo[1]);
			if ( ruota.containsCombinazione(ambo) ) return true;
		}
		return false;
	}

	public boolean containsTerno(Ruota ruota, Numero... numeri){
		if ( numeri == null || numeri.length < 3 ) return false;
				
		List<Numero[]> possibiliTerni = permute(3, numeri);
		for( Numero[] possibileTerno: possibiliTerni){
			Terno terno = new Terno(possibileTerno[0], possibileTerno[1], possibileTerno[2]);
			if ( ruota.containsCombinazione(terno) ) return true;
		}
		return false;
		
	}
	
	public boolean containsQuaterna(Ruota ruota, Numero... numeri){
		if ( numeri == null || numeri.length < 4 ) return false;
		
		List<Numero[]> possibiliQuats = permute(4, numeri);
		for( Numero[] possibileQuat: possibiliQuats){
			Quaterna quat = new Quaterna(possibileQuat[0], possibileQuat[1], 
					possibileQuat[2], possibileQuat[3]);
			if ( ruota.containsCombinazione(quat) ) return true;
		}
		return false;
	}
	
	public boolean containsCinquina(Ruota ruota, Numero... numeri){
		if ( numeri == null || numeri.length < 5 ) return false;
		
		List<Numero[]> possibiliCinqs = permute(5, numeri);
		for( Numero[] possibileCinq: possibiliCinqs){
			Cinquina cinq = new Cinquina(possibileCinq[0], possibileCinq[1], 
					possibileCinq[2], possibileCinq[3], possibileCinq[4]);
			if ( ruota.containsCombinazione(cinq) ) return true;
		}
		return false;
	}
	
	public boolean containsAmbata(Ruota ruota, Numero... numeri){
		if ( numeri == null || numeri.length < 1 ) return false;

		for ( Numero n: numeri ){
			if ( ruota.containsCombinazione(n) ) return true;
		}
		return false;
	}
	
	/**
	 * Il metodo restituisce la lista di tutte le permutazioni ottenibili di lunghezza
	 * definita (presa in input) a partire da un array di numeri.
	 * Restituisce una lista di sottoliste. Ogni sottolista è una possibile permutazione.
	 * @param lunghezzaListaOutput
	 * @param numeri
	 * @return
	 */
	public static List<Numero[]> permute(int lunghezzaListaOutput, Numero... numeri){
		List<Numero[]> subsets = new ArrayList<>();

		int[] indices = new int[lunghezzaListaOutput]; // here we'll keep indices 
		                                       // pointing to elements in input array

		if (lunghezzaListaOutput <= numeri.length) {
		    // first index sequence: 0, 1, 2, ...
		    for (int i = 0; (indices[i] = i) < lunghezzaListaOutput - 1; i++);  
		    subsets.add(getSubset(numeri, indices));
		    for(;;) {
		        int i;
		        // find position of item that can be incremented
		        for (i = lunghezzaListaOutput - 1; i >= 0 && indices[i] == numeri.length - lunghezzaListaOutput + i; i--); 
		        if (i < 0) {
		            break;
		        }
		        indices[i]++;                    // increment this item
		        for (++i; i < lunghezzaListaOutput; i++) {    // fill up remaining items
		            indices[i] = indices[i - 1] + 1; 
		        }
		        subsets.add(getSubset(numeri, indices));
		    }
		}
		
		return subsets;
	}
	
	// generate actual subset by index sequence
	private static Numero[] getSubset(Numero[] input, int[] subset) {
	    Numero[] result = new Numero[subset.length]; 
	    for (int i = 0; i < subset.length; i++) 
	        result[i] = input[subset[i]];
	    return result;
	}
	
	public static List<? extends Combinazione> getPermutazioneCombinazione(Ruota ruota, int combLen){
		List<Combinazione> combinazioni = new ArrayList<>();
		List<Numero[]> permutazioni = RuotaController.permute(combLen, ruota.getArrayNumeri());
		
		for( Numero[] numeri: permutazioni ){
			Combinazione comb = CombinazioneFactory.createCombinazione(numeri);
			if ( !combinazioni.contains(comb) ){
				combinazioni.add(comb);
			}
		}
		return combinazioni;
	}
	
}
