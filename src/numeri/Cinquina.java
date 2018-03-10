package numeri;

import java.util.ArrayList;
import java.util.List;

public class Cinquina extends Combinazione {
	
	private Numero primo;
	private Numero secondo;
	private Numero terzo;
	private Numero quarto;
	private Numero quinto;
	
	public Cinquina(Numero primo, Numero secondo, Numero terzo, Numero quarto, Numero quinto) {
		super(primo, secondo, terzo, quarto, quinto);
		
		if ( NumeroController.checkDoppioni(primo,secondo,terzo,quarto, quinto) ){
			throw new IllegalArgumentException("Si vuole creare una cinquina con valori doppioni!");
		}
	}

	public Cinquina(int primo, int secondo, int terzo, int quarto, int quinto) {
		this(new Numero(primo), new Numero(secondo), new Numero(terzo), new Numero(quarto), 
				new Numero(quinto));
	}

	public boolean contains(Numero numero) {
		return primo.equals(numero) || secondo.equals(numero) || terzo.equals(numero)
				|| quarto.equals(numero) || quinto.equals(numero);
	}
	
	@Override
	public String toString() {
		return primo + "," + secondo + "," + terzo + "," + quarto + "," + quinto;
	}

	@Override
	protected List<Numero> populateValori(Numero... numeri) {
		this.primo = numeri[0];
		this.secondo = numeri[1];
		this.terzo = numeri[2];
		this.quarto = numeri[3];
		this.quinto = numeri[4];
		
		List<Numero> valori = new ArrayList<>();
		valori.add(primo);
		valori.add(secondo);
		valori.add(terzo);
		valori.add(quarto);
		valori.add(quinto);
		return valori;
	}
}
