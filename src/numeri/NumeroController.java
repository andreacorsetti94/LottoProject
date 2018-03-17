package numeri;

import helper.CollectionHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import query.RitardoController;
import ruote.RuotaController;
import ruote.RuotaID;
import estrazioni.EstrazioneController;

public class NumeroController {

	private static List<List<Numero>> figure = new ArrayList<>();
	private static List<List<Numero>> controFigure = new ArrayList<>();
	private static List<List<Numero>> quartine = new ArrayList<>();
	private static List<List<Numero>> cinquinePent = new ArrayList<>();
	private static List<List<Numero>> sestineEsa = new ArrayList<>();

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

		LinkedHashMap<Numero, Integer> map = new RitardoController(EstrazioneController.getList()).
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

		return CollectionHelper.listToArray(list);

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

		//controfigure
		List<Numero> controFigura1 = Arrays.asList( new Numero(1), new Numero(12), new Numero(23), new Numero(34), new Numero(45),
				new Numero(56), new Numero(67), new Numero(78), new Numero(89));

		List<Numero> controFigura2= Arrays.asList( new Numero(2), new Numero(13), new Numero(24), new Numero(35), new Numero(46),
				new Numero(57), new Numero(68), new Numero(79), new Numero(90));

		List<Numero> controFigura3 = Arrays.asList( new Numero(3), new Numero(14), new Numero(25), new Numero(36), new Numero(47),
				new Numero(58), new Numero(69), new Numero(80), new Numero(11));

		List<Numero> controFigura4 = Arrays.asList( new Numero(4), new Numero(15), new Numero(26), new Numero(37), new Numero(48),
				new Numero(59), new Numero(70), new Numero(81), new Numero(22));

		List<Numero> controFigura5 = Arrays.asList( new Numero(5), new Numero(16), new Numero(27), new Numero(38), new Numero(49),
				new Numero(60), new Numero(71), new Numero(82), new Numero(33));

		List<Numero> controFigura6 = Arrays.asList( new Numero(6), new Numero(17), new Numero(28), new Numero(39), new Numero(50),
				new Numero(61), new Numero(72), new Numero(83), new Numero(44));

		List<Numero> controFigura7 = Arrays.asList( new Numero(7), new Numero(18), new Numero(29), new Numero(40), new Numero(51),
				new Numero(62), new Numero(73), new Numero(84), new Numero(55));

		List<Numero> controFigura8 = Arrays.asList( new Numero(8), new Numero(19), new Numero(30), new Numero(41), new Numero(52),
				new Numero(63), new Numero(74), new Numero(85), new Numero(66));

		List<Numero> controFigura9= Arrays.asList( new Numero(9), new Numero(20), new Numero(31), new Numero(42), new Numero(53),
				new Numero(64), new Numero(75), new Numero(86), new Numero(77));

		List<Numero> controFigura10= Arrays.asList( new Numero(10), new Numero(21), new Numero(32), new Numero(43), new Numero(54),
				new Numero(65), new Numero(76), new Numero(87), new Numero(88));

		controFigure.add(controFigura1);
		controFigure.add(controFigura2);
		controFigure.add(controFigura3);
		controFigure.add(controFigura4);
		controFigure.add(controFigura5);
		controFigure.add(controFigura6);
		controFigure.add(controFigura7);
		controFigure.add(controFigura8);
		controFigure.add(controFigura9);
		controFigure.add(controFigura10);

		//cinquine pentagonali
		List<Numero> cinq1 = Arrays.asList( new Numero(1), new Numero(19), new Numero(37), new Numero(55), new Numero(73));

		List<Numero> cinq2= Arrays.asList( new Numero(2), new Numero(20), new Numero(38), new Numero(56), new Numero(74));

		List<Numero> cinq3 = Arrays.asList( new Numero(3), new Numero(21), new Numero(39), new Numero(57), new Numero(75));

		List<Numero> cinq4 = Arrays.asList( new Numero(4), new Numero(22), new Numero(40), new Numero(58), new Numero(76));

		List<Numero> cinq5 = Arrays.asList( new Numero(5), new Numero(23), new Numero(41), new Numero(59), new Numero(77));

		List<Numero> cinq6 = Arrays.asList( new Numero(6), new Numero(24), new Numero(42), new Numero(60), new Numero(78));

		List<Numero> cinq7 = Arrays.asList( new Numero(7), new Numero(25), new Numero(43), new Numero(61), new Numero(79));

		List<Numero> cinq8 = Arrays.asList( new Numero(8), new Numero(26), new Numero(44), new Numero(62), new Numero(80));

		List<Numero> cinq9= Arrays.asList( new Numero(9), new Numero(27), new Numero(45), new Numero(63), new Numero(81));

		List<Numero> cinq10= Arrays.asList( new Numero(10), new Numero(28), new Numero(46), new Numero(64), new Numero(82));

		List<Numero> cinq11= Arrays.asList( new Numero(11), new Numero(29), new Numero(47), new Numero(65), new Numero(83));

		List<Numero> cinq12= Arrays.asList( new Numero(12), new Numero(30), new Numero(48), new Numero(66), new Numero(84));

		List<Numero> cinq13= Arrays.asList( new Numero(13), new Numero(31), new Numero(49), new Numero(67), new Numero(85));

		List<Numero> cinq14= Arrays.asList( new Numero(14), new Numero(32), new Numero(50), new Numero(68), new Numero(86));

		List<Numero> cinq15= Arrays.asList( new Numero(15), new Numero(33), new Numero(51), new Numero(69), new Numero(87));

		List<Numero> cinq16= Arrays.asList( new Numero(16), new Numero(34), new Numero(52), new Numero(70), new Numero(88));

		List<Numero> cinq17= Arrays.asList( new Numero(17), new Numero(35), new Numero(53), new Numero(71), new Numero(89));

		List<Numero> cinq18= Arrays.asList( new Numero(18), new Numero(36), new Numero(54), new Numero(72), new Numero(90));

		cinquinePent.add(cinq1);
		cinquinePent.add(cinq2);
		cinquinePent.add(cinq3);
		cinquinePent.add(cinq4);
		cinquinePent.add(cinq5);
		cinquinePent.add(cinq6);
		cinquinePent.add(cinq7);
		cinquinePent.add(cinq8);
		cinquinePent.add(cinq9);
		cinquinePent.add(cinq10);
		cinquinePent.add(cinq11);
		cinquinePent.add(cinq12);
		cinquinePent.add(cinq13);
		cinquinePent.add(cinq14);
		cinquinePent.add(cinq15);
		cinquinePent.add(cinq16);
		cinquinePent.add(cinq17);
		cinquinePent.add(cinq18);

		//sestine esa
		List<Numero> sest1 = Arrays.asList( new Numero(1), new Numero(16), new Numero(31), new Numero(46), new Numero(61), new Numero(76) );

		List<Numero> sest2= Arrays.asList( new Numero(2), new Numero(17), new Numero(32), new Numero(47), new Numero(62), new Numero(77) );

		List<Numero> sest3 = Arrays.asList( new Numero(3), new Numero(18), new Numero(33), new Numero(48), new Numero(63), new Numero(78) );

		List<Numero> sest4 = Arrays.asList( new Numero(4), new Numero(19), new Numero(34), new Numero(49), new Numero(64), new Numero(79) );

		List<Numero> sest5 = Arrays.asList( new Numero(5), new Numero(20), new Numero(35), new Numero(50), new Numero(65), new Numero(80) );

		List<Numero> sest6 = Arrays.asList( new Numero(6), new Numero(21), new Numero(36), new Numero(51), new Numero(66), new Numero(81) );

		List<Numero> sest7 = Arrays.asList( new Numero(7), new Numero(22), new Numero(37), new Numero(52), new Numero(67), new Numero(82) );

		List<Numero> sest8 = Arrays.asList( new Numero(8), new Numero(23), new Numero(38), new Numero(53), new Numero(68), new Numero(83) );

		List<Numero> sest9= Arrays.asList( new Numero(9), new Numero(24), new Numero(39), new Numero(54), new Numero(69), new Numero(84) );

		List<Numero> sest10= Arrays.asList( new Numero(10), new Numero(25), new Numero(40), new Numero(55), new Numero(70), new Numero(85) );

		List<Numero> sest11= Arrays.asList( new Numero(11), new Numero(26), new Numero(41), new Numero(56), new Numero(71), new Numero(86) );

		List<Numero> sest12= Arrays.asList( new Numero(12), new Numero(27), new Numero(42), new Numero(57), new Numero(72), new Numero(87) );

		List<Numero> sest13= Arrays.asList( new Numero(13), new Numero(28), new Numero(43), new Numero(58), new Numero(73), new Numero(88) );

		List<Numero> sest14= Arrays.asList( new Numero(14), new Numero(29), new Numero(44), new Numero(59), new Numero(74), new Numero(89) );

		List<Numero> sest15= Arrays.asList( new Numero(15), new Numero(30), new Numero(45), new Numero(60), new Numero(75), new Numero(90) );

		sestineEsa.add(sest1);
		sestineEsa.add(sest2);
		sestineEsa.add(sest3);
		sestineEsa.add(sest4);
		sestineEsa.add(sest5);
		sestineEsa.add(sest6);
		sestineEsa.add(sest7);
		sestineEsa.add(sest8);
		sestineEsa.add(sest9);
		sestineEsa.add(sest10);
		sestineEsa.add(sest11);
		sestineEsa.add(sest12);
		sestineEsa.add(sest13);
		sestineEsa.add(sest14);
		sestineEsa.add(sest15);
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

	public boolean isControfigura(Numero... numeri ){
		if ( numeri == null || numeri.length < 2 ) return false;

		List<Numero> controfigure = null;
		for ( List<Numero> controfigura: controFigure ){
			if ( controfigura.contains(numeri[0]) ){
				controfigure = controfigura;
				break;
			}
		}

		if ( controfigure == null ) return false;

		for( int i=1; i < numeri.length; i++){
			if ( !controfigure.contains(numeri[i]) ) return false;
		}

		return true;
	}

	public boolean isCinquinaPentagonale(Numero... numeri){
		if ( numeri == null || numeri.length < 2 ) return false;

		List<Numero> cinquine = null;
		for ( List<Numero> cinquina: cinquinePent ){
			if ( cinquina.contains(numeri[0]) ){
				cinquine = cinquina;
				break;
			}
		}

		if ( cinquine == null ) return false;

		for( int i=1; i < numeri.length; i++){
			if ( !cinquine.contains(numeri[i]) ) return false;
		}

		return true;
	}

	public static List<List<Numero>> getTerniConsecutivi(){
		if ( terniConsecutivi == null ){
			int i = 1;
			int j = 2;
			int k = 3;

			terniConsecutivi = new ArrayList<>();
			for( int count = 0; count < 88; count++){
				List<Numero> terno = new ArrayList<>();
				terno.add(new Numero(i));
				terno.add(new Numero(j));
				terno.add(new Numero(k));
				terniConsecutivi.add(terno);
				i++;
				j++;
				k++;
			}
		}
		return terniConsecutivi;
	}

	private static List<List<Numero>> terniConsecutivi;

	public static List<List<Numero>> getTerzinePari(){
		if ( terzinePari == null ){
			terzinePari = new ArrayList<>();
			int i = 2;
			int j = 4;
			int k = 6;

			for(int count = 0; count < 43; count++){
				List<Numero> terzina = new ArrayList<>();
				terzina.add(new Numero(i));
				terzina.add(new Numero(j));
				terzina.add(new Numero(k));
				terzinePari.add(terzina);
				i += 2;
				j += 2;
				k += 2;
			}
		}
		return terzinePari;
	}

	private static List<List<Numero>> terzinePari;

	public static List<List<Numero>> getTerzineDispari(){
		if ( terzineDispari == null ){
			terzineDispari = new ArrayList<>();
			int i = 1;
			int j = 3;
			int k = 5;

			for(int count = 0; count < 43; count++){
				List<Numero> terzina = new ArrayList<>();
				terzina.add(new Numero(i));
				terzina.add(new Numero(j));
				terzina.add(new Numero(k));
				terzineDispari.add(terzina);
				i += 2;
				j += 2;
				k += 2;
			}
		}
		return terzineDispari;
	}

	private static List<List<Numero>> terzineDispari;

	public static List<List<Numero>> getControfigure(){
		return controFigure;
	}

	public static List<List<Numero>> getFigure(){
		return figure;
	}

	public static List<List<Numero>> getCadenze(){
		if ( cadenze == null ){
			cadenze = new ArrayList<>();
			int i = 1;
			for( int count = 0; count < 10; count++){
				List<Numero> cadenza = new ArrayList<>();
				int tmp = i;

				for ( int j = 0; j < 9; j++){
					cadenza.add(new Numero(tmp));
					tmp += 10;
				}
				cadenze.add(cadenza);
				i++;
			}
		}
		return cadenze;
	}

	private static List<List<Numero>> cadenze;

	public static List<List<Numero>> getDecine(){
		if ( decine == null ){
			decine = new ArrayList<>();
			int i = 10;
			for( int count = 0; count < 8; count++){
				List<Numero> decina = new ArrayList<>();
				int tmp = i;

				for ( int j = 0; j < 10; j++){
					decina.add(new Numero(tmp));
					tmp++;
				}
				decine.add(decina);
				i += 10;
			}
			List<Numero> ultima = new ArrayList<>();
			ultima.add(new Numero(1));
			ultima.add(new Numero(2));
			ultima.add(new Numero(3));
			ultima.add(new Numero(4));
			ultima.add(new Numero(5));
			ultima.add(new Numero(6));
			ultima.add(new Numero(7));
			ultima.add(new Numero(8));
			ultima.add(new Numero(9));
			ultima.add(new Numero(90));
			decine.add(ultima);
		}
		return decine;
	}

	private static List<List<Numero>> decine;

	public static List<List<Numero>> getQuartine(){
		return quartine;
	}

	public static List<List<Numero>> getCinquine(){
		return cinquinePent;
	}

	public static List<List<Numero>> getSestine(){
		return sestineEsa;
	}

	public static List<Ambo> ambiFromNumeri(List<List<Numero>> combs){
		List<Ambo> ambi = new ArrayList<>();
		for ( List<Numero> comb: combs){
			for(Numero[] n : RuotaController.permute(2, CollectionHelper.listToArray(comb))){
				Ambo ambo = new Ambo(n[0], n[1]);
				ambi.add(ambo);
			}

		}
		return ambi;
	}

}
