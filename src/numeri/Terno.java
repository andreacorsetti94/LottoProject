package numeri;

import java.util.ArrayList;
import java.util.List;

public class Terno extends Combinazione {
	
	private Numero primo;
	private Numero secondo;
	private Numero terzo;
	
	public Terno(Numero primo, Numero secondo, Numero terzo) {
		super(primo, secondo, terzo);
		
		if ( NumeroController.checkDoppioni(primo,secondo,terzo) ){
			throw new IllegalArgumentException("Si vuole creare un terno con valori doppioni!");
		}
		
	}

	public Terno(int primo, int secondo, int terzo) {
		this(new Numero(primo), new Numero(secondo), new Numero(terzo));
	}

	public boolean contains(Numero numero) {
		return primo.equals(numero) || secondo.equals(numero) || terzo.equals(numero);
	}

	@Override
	public String toString() {
		return primo + "," + secondo + "," + terzo;
	}

	@Override
	protected List<Numero> populateValori(Numero... numeri) {
		this.primo = numeri[0];
		this.secondo = numeri[1];
		this.terzo = numeri[2];
		
		List<Numero> valori = new ArrayList<>();
		valori.add(primo);
		valori.add(secondo);
		valori.add(terzo);
		return valori;
	}
}
