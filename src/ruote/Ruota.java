package ruote;

import java.util.ArrayList;
import java.util.List;

import numeri.Combinazione;
import numeri.Numero;

public class Ruota {

	private RuotaID ruota;
	private List<Numero> numeri = new ArrayList<>();
	
	private Numero primo;
	private Numero secondo;
	private Numero terzo;
	private Numero quarto;
	private Numero quinto;
	
	public Ruota(RuotaID ruota){
		this.ruota = ruota;
	}
	
	public boolean containsCombinazione(Combinazione comb){
		for ( Numero numero: comb.getValori() ){
			if ( !this.getNumeri().contains(numero) ) return false;
		}
		
		return true;
	}

	/**
	 * <Nome ruota: >Ruota
	 * <>.len deve essere 11 spazi
	 */
	@Override
	public String toString() {
		StringBuilder spacebuilder = new StringBuilder();
		
		String primaparte = this.ruota.toString() + ": ";
		
		int whitespaces_count = 11 - primaparte.length();
		
		for ( int i = 0; i < whitespaces_count; i++){
			spacebuilder.append(" ");
		}
		
		String estrazione = "";
		for ( Numero numero: this.numeri ){
			estrazione += numero.toString() + " ";
		}
		
		return primaparte + spacebuilder.toString() + estrazione.trim();
	}
	
	public List<Numero> getNumeri(){
		if ( this.numeri == null || this.numeri.size() == 0 ){
			//System.err.println("Si vuole controllare i numeri di una ruota vuota. "+ruota);
			return new ArrayList<>();
		}
		
		return this.numeri;
	}

	public RuotaID getRuota() {
		return ruota;
	}

	public void setRuota(RuotaID ruota) {
		this.ruota = ruota;
	}

	public void popolaRuota(List<Numero> numeri) {
		this.numeri = numeri;
		
		if ( numeri.size() != 5 ){
			System.err.println();
			throw new IllegalArgumentException("Size of " + this.toString() 
					+ " is: " + numeri.size());
		}
		
		this.primo = numeri.get(0);
		this.secondo = numeri.get(1);
		this.terzo = numeri.get(2);
		this.quarto = numeri.get(3);
		this.quinto = numeri.get(4);
	}
	
	public Numero[] getArrayNumeri(){
		List<Numero> numeri = this.getNumeri();
		if ( numeri.isEmpty() ){
			return new Numero[]{};
		}
		Numero[] numeriArray = new Numero[this.getNumeri().size()];
		for ( int i = 0; i < this.getNumeri().size(); i++){
			numeriArray[i] = this.getNumeri().get(i);
		}
		return numeriArray;
		
	}
	
	public static Ruota emptyRuota(){
		Ruota empty = new Ruota(null);
		return empty;
	}
	
	public Numero getPrimo(){
		return this.primo;
	}
	
	public Numero getSecondo(){
		return this.secondo;
	}
	
	public Numero getTerzo(){
		return this.terzo;
	}
	
	public Numero getQuarto(){
		return this.quarto;
	}
	
	public Numero getQuinto(){
		return this.quinto;
	}
	
	/**
	 * pos varia tra 1 e 5
	 * @param pos
	 * @return
	 */
	public Numero getDeterminato(int pos){
		if ( pos < 1 || pos > 5){
			throw new IllegalArgumentException("Si cerca un determinato in una posizione non"
					+ "compresa tra 1 e 5!");

		}
		else{
			switch(pos){
			case 1:
				return this.primo;
			case 2:
				return this.secondo;
			case 3:
				return this.terzo;
			case 4:
				return this.quarto;
			case 5:
				return this.quinto;
			default:
				return null;
			}
			
		}

	}

	public boolean isEmpty(){
		return this.getNumeri().isEmpty();
	}
}
