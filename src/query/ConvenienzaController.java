package query;

import java.util.List;

import estrazioni.Estrazione;
import numeri.Combinazione;
import numeri.Numero;
import ruote.RuotaID;

public class ConvenienzaController extends AbstractController{

	public ConvenienzaController(List<Estrazione> estrazioni){
		super(estrazioni);
	}

	public double indiceConvenienzaCombinazioneRuota(RuotaID id, Combinazione comb){
		RitardoController ritContr = new RitardoController(super.getEstrazioni());
		FrequenzaController freqContr = new FrequenzaController(super.getEstrazioni());

		int ritardoComb = ritContr.ritardoCombinazioneRuota(id, comb);
		int freqComb = freqContr.getFreqCombinazioneRuota(id, comb);

		int countEstrazioniValide = 0;
		for( Estrazione e: super.getEstrazioni() ){
			if ( !e.getRuota(id).isEmpty() ){
				countEstrazioniValide++;
			}
		}

		return (double) freqComb/countEstrazioniValide*ritardoComb;
	}

	public double indiceConvenienzaCombinazioneTutte(Combinazione comb){
		RitardoController ritContr = new RitardoController(super.getEstrazioni());
		FrequenzaController freqContr = new FrequenzaController(super.getEstrazioni());

		int ritardoComb = ritContr.ritardoCombinazioneTutte(comb);
		int freqComb = freqContr.getFreqCombinazioneTutte(comb);

		int countEstrazioniValide = super.getEstrazioni().size();
		return (double) freqComb/countEstrazioniValide*ritardoComb;
	}

	public double indiceConvenienzaDeterminatoTutte(Numero numero, int pos){
		DeterminatoController contr = new DeterminatoController(super.getEstrazioni());
		int freq = contr.freqDetTutte(numero, pos);
		int rit = contr.ritardoDetTutte(numero, pos);

		int countEstrazioniValide = super.getEstrazioni().size();
		return (double) freq/countEstrazioniValide*rit;
	}

	public double indiceConvenienzaDeterminatoRuota(RuotaID id, Numero numero, int pos){
		DeterminatoController contr = new DeterminatoController(super.getEstrazioni());
		int freq = contr.frequenzaDetRuota(id, numero, pos);
		int rit = contr.ritardoDetRuota(id, numero, pos);

		int countEstrazioniValide = 0;
		for( Estrazione e: super.getEstrazioni() ){
			if ( !e.getRuota(id).isEmpty() ){
				countEstrazioniValide++;
			}
		}
		return (double) freq/countEstrazioniValide*rit;
	}

}
