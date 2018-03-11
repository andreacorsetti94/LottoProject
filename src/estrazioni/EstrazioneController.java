package estrazioni;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EstrazioneController {
	
	private static Comparator<Estrazione> fromRecentComp = (es1, es2) -> {
		Calendar es1Date = es1.getDate();
		Calendar es2Date = es2.getDate();
		
		if ( es1Date.before(es2Date) ) return 1;
		if ( es1Date.after(es2Date) ) return -1;
		return 0;
	};
	
	private static Comparator<Estrazione> fromOldestComp = (es1, es2) -> {
		Calendar es1Date = es1.getDate();
		Calendar es2Date = es2.getDate();
		
		if ( es1Date.before(es2Date) ) return -1;
		if ( es1Date.after(es2Date) ) return 1;
		return 0;
	};
	
	private static List<Estrazione> STORICO_ESTRAZIONI = new ArrayList<>();
	
	public static List<Estrazione> retrieveListEstrazioni(){
		return STORICO_ESTRAZIONI;
	}
	
	public static List<Estrazione> fetchSortedEstrFromOldest(){
		return sortFromOldest(STORICO_ESTRAZIONI);
	}
	
	public static List<Estrazione> fetchSortedEstrFromRecent(){
		return sortFromRecent(STORICO_ESTRAZIONI);
	}
	
	public static List<Estrazione> fetchEstrazioniInYear(int year){
		return STORICO_ESTRAZIONI.parallelStream()
				.filter( estr -> estr.getDate().get(Calendar.YEAR) == year)
				.sorted(fromOldestComp)
				.collect(Collectors.toList());
	}
	
	public static Estrazione piuRecente(){
		List<Estrazione> moreRecent = fetchSortedEstrFromRecent();
		if ( moreRecent == null || moreRecent.isEmpty() ){
			System.out.println("Catalogo estrazioni vuoto");
			return null;
		}
		return moreRecent.get(0);
		
	}
	
	/**
	 * NOTE: MONTH is 0-BASED (0: January, 11: December)
	 * @param month
	 * @param year
	 * @return
	 */
	public static List<Estrazione> fetchEstrazioniInMonthYear(int month, int year){
		return STORICO_ESTRAZIONI.parallelStream()
				.filter( estr -> 
					(estr.getDate().get(Calendar.YEAR) == year) && (estr.getDate().get(Calendar.MONTH) == month))
				.sorted(fromOldestComp)
				.collect(Collectors.toList());
	}
	
	/**
	 * Ritorna una nuova lista ordinata di estrazioni dalla più antica alla più recente,
	 * ordinate per data
	 * @param toSort
	 * @return
	 */
	public static List<Estrazione> sortFromOldest(List<Estrazione> toSort){
		return toSort.parallelStream().sorted(fromOldestComp).collect(Collectors.toList());
	}
	
	/**
	 * Ritorna una nuova lista ordinata di estrazioni dalla più recente alla più antica,
	 * ordinate per data
	 * @param toSort
	 * @return
	 */
	public static List<Estrazione> sortFromRecent(List<Estrazione> toSort){
		return toSort.parallelStream().sorted(fromRecentComp).collect(Collectors.toList());
	}
	
	static {
		STORICO_ESTRAZIONI = EstrazioneFetcher.fetchCompleteList();
	}
	
	static void appendEstrazioniToPresent(List<Estrazione> nuoveEstrazioni){
		
		Estrazione tmpLast = fetchSortedEstrFromOldest().get(STORICO_ESTRAZIONI.size() - 1);
		for ( Estrazione e: nuoveEstrazioni ){
			if ( tmpLast.getDate().get(Calendar.YEAR) != e.getDate().get(Calendar.YEAR) ){
				e.setNumeroAnnuale(1);
			}
			else{
				e.setNumeroAnnuale(tmpLast.getNumeroAnnuale() + 1);
			}
			tmpLast = e;
			STORICO_ESTRAZIONI.add(e);
		}
		
		System.out.println("Aggiunte " + nuoveEstrazioni.size() + " estrazioni.");
	}
}
