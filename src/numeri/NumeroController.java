package numeri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import estrazioni.EstrazioneController;
import estrazioni.RitardoController;
import ruote.RuotaID;

public class NumeroController {

	private static List<List<Numero>> figure = new ArrayList<>();
	private static List<List<Numero>> quartine = new ArrayList<>();
	
	private static List<Numero> numeri = new ArrayList<>();
	
	public static List<Numero> getInsiemeNumeri(){
		return numeri;
	}
	
	public static List<Ambo> generaAmbi(){
		List<Ambo> ambi = new ArrayList<>();
		
		for( int i = 1; i <= 90; i++){
			for ( int j = 1; j <= 90; j++){
				if ( i == j ) continue;
				Ambo ambo = new Ambo(i,j);
				if ( !ambi.contains(ambo)) {
					ambi.add(ambo);
				}
			}
		}
		return ambi;
	}
	
	public static List<Ambo> generaAmbiAPartireDaNumero(Numero numero){
		List<Ambo> ambi = new ArrayList<>();
		for(int i = 1; i <= 90; i++){
			if ( numero.value() != i ){
				ambi.add(new Ambo(numero, new Numero(i)));
			}
		}
		return ambi;
	}
	
	public static List<Ambo> generaAmbiDaCapogioco(RuotaID id){
	
		LinkedHashMap<Numero, Integer> map = new RitardoController(EstrazioneController.retrieveListEstrazioni()).
					piuRitardatariPerRuota(id, 1);
		
		Numero capogiocoRitardatario = map.keySet().iterator().next();
		

		return generaAmbiAPartireDaNumero(capogiocoRitardatario);
	}
	
	/**
	 * A partire da un array di numeri, determina se questi fanno tutti parte di una decina o meno:
	 * Esempio di decina:
	 * (10,13,17,19,15), (20,21,29), (85,81,89).
	 * Caso particolare: la decina del 90 è la prima: (1,2,3,4,5,6,7,8,9,90)
	 * @param numeri
	 * @return
	 */
	public boolean isDecina(Numero... numeri){
		if ( numeri == null || numeri.length < 2 ) return false;
		
		//ogni numero dovrebbe avere lo stesso valore per Math.floor(numero), 
		//tranne casi particolari. Floor(90/10) dovrebbe essere considerato come 0
		
		int first_number_floor;
		if ( numeri[0].value() == 90 ){
			first_number_floor = 0;
		}
		else{
			first_number_floor = (int) Math.floor(numeri[0].value()/10);
		}

		for (int i = 1; i < numeri.length; i++){
			int current_num_floor;
			
			if ( numeri[i].value() == 90 ){
				current_num_floor = 0;
			}
			else{
				current_num_floor = (int) Math.floor(numeri[i].value()/10); 
			}
			
			if ( current_num_floor != first_number_floor ) return false;
		}
		return true;
		
	}
	
	/**
	 * Torna vero se tutti i numeri in input terminano con la stessa cifra, falso altrimenti
	 * @param numeri
	 * @return
	 */
	public boolean isCadenza(Numero... numeri){
		if ( numeri == null || numeri.length < 2 ) return false;

		int cadenza = numeri[0].value() % 10;
		for ( int i = 1; i < numeri.length; i++ ){
			if ( numeri[i].value() % 10 != cadenza ) return false;
		}
		
		return true;
	}
	
	/**
	 * Determina se i numeri presi in input siano o meno consecutivi
	 * @param numeri
	 * @return
	 */
	public boolean isConsecutiva(Numero... numeri){
		if ( numeri == null || numeri.length < 2 ) return false;

		Numero[] numeriOrdinati = ordina(numeri);
		
		int prec_value = numeriOrdinati[0].value();
		for ( int i = 1; i < numeri.length; i ++ ){
			int current_value = numeriOrdinati[i].value();
			if ( current_value - prec_value > 1 ) return false;
			prec_value = current_value;
		}
		
		return true;
	}
	
	/**
	 * Ordina i numeri presi in input dal più piccolo al più grande
	 * @param numeri
	 * @return
	 */
	private static Numero[] ordina(Numero... numeri){
		List<Numero> list = Arrays.asList(numeri);
		
		list.sort( (num1, num2) -> {
			if ( num1.value() < num2.value() ) return -1;
			else if ( num1.value() > num2.value() ) return 1;
			return 0;
		});
		
		return (Numero[]) list.toArray();
		
	}
	
	/**
	 * Controllo se tutti i numeri presi in input siano parte della stessa figura
	 * inizializzata in fase di creazione della classe
	 * @param numeri
	 * @return
	 */
	public boolean isFigura(Numero... numeri){
		if ( numeri == null || numeri.length < 2 ) return false;
		
		List<Numero> figura_di_appartenenza = null;
		for ( List<Numero> figura: figure ){
			if ( figura.contains(numeri[0]) ){
				figura_di_appartenenza = figura;
				break;
			}
		}
		
		if ( figura_di_appartenenza == null ) return false;
		
		for( int i=1; i < numeri.length; i++){
			if ( !figura_di_appartenenza.contains(numeri[i]) ) return false;
		}
		
		return true;
	}
	
	/**
	 * Controllo se tutti i numeri presi in input siano parte della stessa quartina radicale
	 * inizializzata in fase di creazione della classe
	 * @param numeri
	 * @return
	 */
	public boolean isRadicale(Numero... numeri){
		if ( numeri == null || numeri.length < 2 ) return false;

		List<Numero> quartina_appartenenza = null;
		for ( List<Numero> quarts: quartine ){
			if (quarts.contains(numeri[0])){
				quartina_appartenenza = quarts;
			}
		}
		
		if ( quartina_appartenenza == null ) return false;
		for( int i=1; i < numeri.length; i++){
			if ( !quartina_appartenenza.contains(numeri[i]) ) return false;
		}
		return true;
		
	}
	
	static {
		List<Numero> figura1 = Arrays.asList( new Numero(1), new Numero(10), new Numero(19), new Numero(28), new Numero(37),
				new Numero(46), new Numero(55), new Numero(64), new Numero(73), new Numero(82));
		
		List<Numero> figura2 = Arrays.asList( new Numero(2), new Numero(11), new Numero(20), new Numero(29), new Numero(38),
				new Numero(47), new Numero(56), new Numero(65), new Numero(74), new Numero(83));
		
		List<Numero> figura3 = Arrays.asList( new Numero(3), new Numero(12), new Numero(21), new Numero(30), new Numero(39),
				new Numero(48), new Numero(57), new Numero(66), new Numero(75), new Numero(84));
		
		List<Numero> figura4 = Arrays.asList( new Numero(4), new Numero(13), new Numero(22), new Numero(31), new Numero(40),
				new Numero(49), new Numero(58), new Numero(67), new Numero(76), new Numero(85));
		
		List<Numero> figura5 = Arrays.asList( new Numero(5), new Numero(14), new Numero(23), new Numero(32), new Numero(41),
				new Numero(50), new Numero(59), new Numero(68), new Numero(77), new Numero(86));
		
		List<Numero> figura6 = Arrays.asList( new Numero(6), new Numero(15), new Numero(24), new Numero(33), new Numero(42),
				new Numero(51), new Numero(60), new Numero(69), new Numero(78), new Numero(87));
		
		List<Numero> figura7 = Arrays.asList( new Numero(7), new Numero(16), new Numero(25), new Numero(34), new Numero(43),
				new Numero(52), new Numero(61), new Numero(70), new Numero(79), new Numero(88));
		
		List<Numero> figura8 = Arrays.asList( new Numero(8), new Numero(17), new Numero(26), new Numero(35), new Numero(44),
				new Numero(53), new Numero(62), new Numero(71), new Numero(80), new Numero(89));
		
		List<Numero> figura9 = Arrays.asList( new Numero(9), new Numero(18), new Numero(27), new Numero(36), new Numero(45),
				new Numero(54), new Numero(63), new Numero(72), new Numero(81), new Numero(90));
				
		figure.add(figura1);
		figure.add(figura2);
		figure.add(figura3);
		figure.add(figura4);
		figure.add(figura5);
		figure.add(figura6);
		figure.add(figura7);
		figure.add(figura8);
		figure.add(figura9);
		
		//quartine
		List<Numero> quartina1 = Arrays.asList(new Numero(1), new Numero(10), 
				new Numero(11), new Numero(19));
		
		List<Numero> quartina2 = Arrays.asList(new Numero(2), new Numero(20), 
				new Numero(22), new Numero(29));
		
		List<Numero> quartina3 = Arrays.asList(new Numero(3), new Numero(30), 
				new Numero(33), new Numero(39));
		
		List<Numero> quartina4 = Arrays.asList(new Numero(4), new Numero(40), 
				new Numero(44), new Numero(49));
		
		List<Numero> quartina5 = Arrays.asList(new Numero(5), new Numero(50), 
				new Numero(55), new Numero(59));
		
		List<Numero> quartina6 = Arrays.asList(new Numero(6), new Numero(60), 
				new Numero(66), new Numero(69));
		
		List<Numero> quartina7 = Arrays.asList(new Numero(7), new Numero(70), 
				new Numero(77), new Numero(79));
		
		List<Numero> quartina8 = Arrays.asList(new Numero(8), new Numero(80), 
				new Numero(88), new Numero(89));
		
		quartine.add(quartina1);
		quartine.add(quartina2);
		quartine.add(quartina3);
		quartine.add(quartina4);
		quartine.add(quartina5);
		quartine.add(quartina6);
		quartine.add(quartina7);
		quartine.add(quartina8);
		
		//popola collezione numeri
		for ( int i = 1; i <= 90; i++ ){
			numeri.add(new Numero(i));
		}
	}
	
	public static boolean checkDoppioni(Numero... numeri ){
		if ( numeri == null || numeri.length < 2 ) return false;
		
		List<Numero> list = Arrays.asList(ordina(numeri));
		Numero prec = list.get(0);
		
		for( int i = 1; i < list.size(); i++){
			if ( prec.value() == list.get(i).value() ) return true;
			prec = list.get(i);
		}
		return false;
	}
	
	public static List<Ambo> generaAmbiVertibili(){
		List<Ambo> ambi = new ArrayList<>();
		for( Ambo a: generaAmbi() ){
			if ( a.isVertibile() ) ambi.add(a);
		}
		return ambi;
	}
	
	public static List<Ambo> generaAmbiGemelli(){
		List<Ambo> ambi = new ArrayList<>();
		for( Ambo a: generaAmbi() ){
			if ( a.isGemello() ) ambi.add(a);
		}
		return ambi;
	}
	
	public static List<Ambo> generaAmbiSimmetrici(){
		List<Ambo> ambi = new ArrayList<>();
		for( Ambo a: generaAmbi() ){
			if ( a.isSimmetrico()) ambi.add(a);
		}
		return ambi;
	}
	
	public static List<Ambo> generaAmbiDivisoreComune(){
		List<Ambo> ambi = new ArrayList<>();
		for( Ambo a: generaAmbi() ){
			if ( a.aDivisorComune() ) ambi.add(a);
		}
		return ambi;
	}
	
	public static List<Ambo> generaAmbiComplementari(){
		List<Ambo> ambi = new ArrayList<>();
		for( Ambo a: generaAmbi() ){
			if ( a.isComplementare() ) ambi.add(a);
		}
		return ambi;
	}

	public static List<Ambo> generaAmbiConsecutivi(){
		List<Ambo> ambi = new ArrayList<>();
		for( Ambo a: generaAmbi() ){
			if ( a.isConsecutivo() ) ambi.add(a);
		}
		return ambi;
	}
	
	public static boolean isTerzinaDispari(Numero... numeri){
		if ( numeri.length != 3) return false;
		Numero[] ordinati = ordina(numeri);
		Numero uno = ordinati[0];
		Numero due = ordinati[1];
		Numero tre = ordinati[2];
		
		return tre.value() - due.value() == 2 && due.value() - uno.value() == 2 && uno.value()%2 == 1; 
	}
	
	public static boolean isTerzinaPari(Numero... numeri){
		if ( numeri.length != 3) return false;
		Numero[] ordinati = ordina(numeri);
		Numero uno = ordinati[0];
		Numero due = ordinati[1];
		Numero tre = ordinati[2];
		
		return tre.value() - due.value() == 2 && due.value() - uno.value() == 2 && uno.value()%2 == 0; 
	}
}
