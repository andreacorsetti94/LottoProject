package query;

import java.util.List;

import estrazioni.Estrazione;

public abstract class AbstractController {

	private List<Estrazione> estrazioni;
	
	protected List<Estrazione> getEstrazioni(){
		return this.estrazioni;
	}
	
	protected AbstractController(List<Estrazione> estrazioni){
		this.estrazioni = estrazioni;
	}
	
	
}
