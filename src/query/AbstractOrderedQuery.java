package query;

import java.util.List;

import estrazioni.Estrazione;
import estrazioni.EstrazioneController;

public abstract class AbstractOrderedQuery extends AbstractQuery {

	protected AbstractOrderedQuery(List<Estrazione> estrazioni) {
		super(EstrazioneController.sortFromRecent(estrazioni));
	}
	

}
