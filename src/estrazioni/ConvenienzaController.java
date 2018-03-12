package estrazioni;

import java.util.List;

import numeri.Combinazione;
import ruote.RuotaID;

public class ConvenienzaController {

	private List<Estrazione> storico;
	
	public ConvenienzaController(List<Estrazione> estrazioni){
		super();
		this.storico = estrazioni;
	}
	
	public double indiceConvenienzaCombinazioneRuota(RuotaID id, Combinazione comb){
		RitardoController ritContr = new RitardoController(this.storico);
		FrequenzaController freqContr = new FrequenzaController(this.storico);
		
		int ritardoComb = ritContr.ritardoCombinazioneRuota(id, comb);
		int freqComb = freqContr.getFreqCombinazioneRuota(id, comb);
		
		int countEstrazioniValide = 0;
		for( Estrazione e: storico ){
			if ( !e.getRuota(id).getNumeri().isEmpty() ){
				countEstrazioniValide++;
			}
		}
		
		return (double) freqComb/countEstrazioniValide*ritardoComb;
	}
	
}
