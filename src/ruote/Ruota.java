package ruote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import numeri.Combinazione;
import numeri.Numero;

public class Ruota {

	private RuotaID ruota;
	private List<Numero> numeri = new ArrayList<>();
	
	public Ruota(RuotaID ruota, List<Numero> numeri){
		this.ruota = ruota;
		this.numeri = numeri;
	}
	
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
	}
	
	public void popolaRuota(Numero... numeri){
		this.numeri = Arrays.asList(numeri);
	}
	
	public Numero[] getArrayNumeri(){
		List<Numero> numeri = this.getNumeri();
		if ( numeri.isEmpty() ){
			return new Numero[]{};
		}
		return (Numero[]) this.getNumeri().toArray();
		
	}
	
	public static Ruota emptyRuota(){
		Ruota empty = new Ruota(null);
		return empty;
	}

}
