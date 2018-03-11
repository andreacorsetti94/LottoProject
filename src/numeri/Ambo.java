package numeri;

import java.util.ArrayList;
import java.util.List;

public class Ambo extends Combinazione {
	private Numero primo;
	private Numero secondo;
	
	public Ambo(Numero primo, Numero secondo) {
		super(primo, secondo);
		
		if ( primo.equals(secondo) ){
			throw new IllegalArgumentException("Si vuole creare un ambo con due valori uguali!");
		}
		
	}

	public Ambo(int primo, int secondo) {
		this(new Numero(primo), new Numero(secondo));
	}

	public boolean contains(Numero numero) {
		return primo.equals(numero) || secondo.equals(numero);
	}

	@Override
	public String toString() {
		return primo + "," + secondo;
	}

	@Override
	protected List<Numero> populateValori(Numero... numeri) {
		this.primo = numeri[0];
		this.secondo = numeri[1];
		
		List<Numero> valori = new ArrayList<>();
		valori.add(this.primo);
		valori.add(this.secondo);
		return valori;		
	}
	
	public boolean isGemello(){
		return this.primo.value() % 11 == 0  && this.secondo.value() % 11 == 0;
	}
	
	public boolean isVertibile(){
		String val1 = String.valueOf(this.primo.value());
		String val2 = String.valueOf(this.secondo.value());
		
		if ( val1.length() != val2.length() || val1.length() < 2 || val2.length() < 2) return false;
		
		return val1.charAt(0) == val2.charAt(1) && val2.charAt(0) == val1.charAt(1);
		
	}
	
	public boolean isSimmetrico(){
		return this.primo.value() + this.secondo.value() == 91;
	}
	
	public boolean aDivisorComune(){
		return Math.abs(this.primo.value() - this.secondo.value()) == 45;
	}
	
	public boolean isComplementare(){
		return this.primo.value() + this.secondo.value() == 90;
	}
	
	public boolean isConsecutivo(){
		return Math.abs(this.primo.value() - this.secondo.value()) == 1;
	}
}
