package numeri;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Numero extends Combinazione {

	private int value;
	
	public Numero(){
		super();
		this.value = new Random().nextInt(90) + 1;
	}
	
	public Numero(int value){
		super();
		if ( value <= 90 && value > 0 ){
			this.value = value;
		}
		else{
			throw new IllegalArgumentException("Si vuole creare numero maggiore di 90 o minore di 0!");
		}
	}
	
	public int value(){
		return this.value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	protected List<Numero> populateValori(Numero... numeri) {
		List<Numero> valori = new ArrayList<>();
		valori.add(this);
		return valori;
	}

}
