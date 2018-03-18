package numeri;

import java.util.ArrayList;
import java.util.List;

public class Quaterna extends Combinazione {
	
	private Numero primo;
	private Numero secondo;
	private Numero terzo;
	private Numero quarto;
	
	public Quaterna(Numero primo, Numero secondo, Numero terzo, Numero quarto) {
		super(primo, secondo, terzo, quarto);
		
		if ( NumeroController.checkDoppioni(primo,secondo,terzo,quarto) ){
			throw new IllegalArgumentException("Si vuole creare una quaterna con valori doppioni!");
		}
		
	}

	public Quaterna(int primo, int secondo, int terzo, int quarto) {
		this(new Numero(primo), new Numero(secondo), new Numero(terzo), new Numero(quarto));
	}

	public boolean contains(Numero numero) {
		return primo.equals(numero) || secondo.equals(numero) || terzo.equals(numero)
				|| quarto.equals(numero);
	}
	
	@Override
	public String toString() {
		return primo + "," + secondo + "," + terzo + "," + quarto;
	}

	@Override
	protected List<Numero> populateValori(Numero... numeri) {
		this.primo = numeri[0];
		this.secondo = numeri[1];
		this.terzo = numeri[2];
		this.quarto = numeri[3];
		
		List<Numero> valori = new ArrayList<>();
		valori.add(this.primo);
		valori.add(this.secondo);
		valori.add(this.terzo);
		valori.add(this.quarto);
		return valori;
	}
	
}
