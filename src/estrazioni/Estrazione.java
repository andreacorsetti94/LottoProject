package estrazioni;

import helper.DateHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import ruote.Ruota;
import ruote.RuotaID;
import ruote.RuoteFactory;

public class Estrazione {

	private static final String DATE_FORMAT = "yyyy/MM/dd";
	private static final String PRESENTATION_DATE_FORMAT = "dd-MM-yyyy";
	
	private Calendar date;
	private int numeroAnnuale;

	private TipoEstrazione tipoEstrazione;

	private List<Ruota> ruote;
	
	public List<Ruota> getRuote() {
		return ruote;
	}

	public Estrazione(String data, int numeroAnnuale){
		this(DateHelper.fetchCalendarDate(DATE_FORMAT, data), numeroAnnuale);		
	}
	
	public Estrazione(Calendar date, int numeroAnnuale) {
		super();
		this.date = date;
		
		this.setTipoEstrazione(date);
		this.ruote = RuoteFactory.creaListaRuote(this.tipoEstrazione);
		this.numeroAnnuale = numeroAnnuale;
	}
	
	private void setTipoEstrazione(Calendar date){
		Calendar intro_naz = Calendar.getInstance();
		
		//prima estrazione con ruota nazionale: 4 maggio 2005
		intro_naz.set(2005, 4, 3);
		
		if ( date.after(intro_naz) || date.equals(intro_naz) ){
			this.tipoEstrazione = TipoEstrazione.MODERNA;
		}
		else{
			this.tipoEstrazione = TipoEstrazione.SENZA_NAZ;
		}
			
	}
	
	public Calendar getDate() {
		return date;
	}
	public void setDate(GregorianCalendar date) {
		this.date = date;
	}
	public int getNumeroAnnuale() {
		return numeroAnnuale;
	}
	public void setNumeroAnnuale(int numeroAnnuale) {
		this.numeroAnnuale = numeroAnnuale;
	}

	public String getFormattedDate(){
		return DateHelper.fetchStringDate(PRESENTATION_DATE_FORMAT, this.getDate());
	}
	
	@Override
	public String toString() {
		
		String ruoteEstratte = "";
		for ( Ruota ruota: this.getRuote() ){
			ruoteEstratte += ruota.toString() + "\n";
		}
		
		String estrazione = "Estrazione del " + this.getFormattedDate() + "\nEstrazione n. " 
				+ this.numeroAnnuale + "\n\n" + ruoteEstratte;
		return estrazione.trim() + "\n";
	}
	
	public Ruota getRuota(RuotaID id){
		for( Ruota ruota: this.ruote ){
			if ( ruota.getRuota() == id ){
				return ruota;
			}
		}
		/*
		System.out.println("Nessuna ruota trovata con questo id: " + id
				+ "Estrazione: " + this.getFormattedDate() + " Tipo: " + this.tipoEstrazione);
		*/
		return Ruota.emptyRuota();
	}
	
	public boolean isInAnno(int year){
		return this.getDate().get(Calendar.YEAR) == year;
	}
		
}
