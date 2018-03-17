package query;

import java.util.List;

import estrazioni.Estrazione;

public abstract class AbstractQuery {

	private List<Estrazione> estrazioni;
	
	protected List<Estrazione> getEstrazioni(){
		return this.estrazioni;
	}
	
	protected AbstractQuery(List<Estrazione> estrazioni){
		this.estrazioni = estrazioni;
	}
	
	
}
