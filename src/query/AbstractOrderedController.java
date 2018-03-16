package query;

import java.util.List;

import estrazioni.Estrazione;
import estrazioni.EstrazioneController;

public abstract class AbstractOrderedController extends AbstractController {

	protected AbstractOrderedController(List<Estrazione> estrazioni) {
		super(EstrazioneController.sortFromRecent(estrazioni));
	}
	

}
