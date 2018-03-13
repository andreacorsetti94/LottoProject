package estrazioni;

import java.util.List;

import numeri.Combinazione;
import numeri.Numero;
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
			if ( !e.getRuota(id).isEmpty() ){
				countEstrazioniValide++;
			}
		}

		return (double) freqComb/countEstrazioniValide*ritardoComb;
	}

	public double indiceConvenienzaCombinazioneTutte(Combinazione comb){
		RitardoController ritContr = new RitardoController(this.storico);
		FrequenzaController freqContr = new FrequenzaController(this.storico);

		int ritardoComb = ritContr.ritardoCombinazioneTutte(comb);
		int freqComb = freqContr.getFreqCombinazioneTutte(comb);

		int countEstrazioniValide = storico.size();
		return (double) freqComb/countEstrazioniValide*ritardoComb;
	}

	public double indiceConvenienzaDeterminatoTutte(Numero numero, int pos){
		DeterminatoController contr = new DeterminatoController(this.storico);
		int freq = contr.freqDetTutte(numero, pos);
		int rit = contr.ritardoDetTutte(numero, pos);

		int countEstrazioniValide = storico.size();
		return (double) freq/countEstrazioniValide*rit;
	}

	public double indiceConvenienzaDeterminatoRuota(RuotaID id, Numero numero, int pos){
		DeterminatoController contr = new DeterminatoController(this.storico);
		int freq = contr.frequenzaDetRuota(id, numero, pos);
		int rit = contr.ritardoDetRuota(id, numero, pos);

		int countEstrazioniValide = 0;
		for( Estrazione e: storico ){
			if ( !e.getRuota(id).isEmpty() ){
				countEstrazioniValide++;
			}
		}
		return (double) freq/countEstrazioniValide*rit;
	}

}
