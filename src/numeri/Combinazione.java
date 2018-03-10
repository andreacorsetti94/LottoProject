package numeri;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Combinazione {

	private List<Numero> valoriOrdinati;
	private List<Numero> valori;
	
	public Combinazione(Numero... numeri){
		this.valori = this.populateValori(numeri);
	}
	
	protected abstract List<Numero> populateValori(Numero... numeri);
	
	public final List<Numero> getValori(){
		return valori;
	}
	
	public final List<Numero> getValoriOrdinati(){
		if ( valoriOrdinati == null ){
			//ordina una sola volta

			valoriOrdinati = this.getValori().stream().sorted( (n1, n2) -> {
				if ( n1.value() < n2.value() ) return -1;
				if ( n2.value() < n1.value() ) return 1;
				return 0;
			}).collect(Collectors.toList());
		}
		
		return valoriOrdinati;
	}
	
	protected final boolean compareValori(Combinazione comb1, Combinazione comb2){
		
		List<Numero> comb1Ordinati = comb1.getValoriOrdinati();
		List<Numero> comb2Ordinati = comb2.getValoriOrdinati();
		
		if ( comb1Ordinati.size() != comb2Ordinati.size() ) return false;
		
		for ( int i = 0; i < comb1Ordinati.size(); i++ ){
			if ( comb1Ordinati.get(i).value() != comb2Ordinati.get(i).value() ) return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		int toAdd = 0;
		List<Numero> orderedValues = this.getValoriOrdinati();
		for ( int i = 0; i < orderedValues.size(); i++ ){
			Numero n = orderedValues.get(i);
			if ( i%2==0 ){
				toAdd += n.value();
			}
			else{
				toAdd -= n.value();
			}
		}
		result = result * prime + toAdd;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Combinazione other = (Combinazione) obj;
		
		return this.compareValori(this, other);
	}
}
