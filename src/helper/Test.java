package helper;
import iofetch.LocalConfigurationManager;
import numeri.Ambo;
import ruote.RuotaID;
import estrazioni.EstrazioneController;
import estrazioni.RitardoController;


public class Test {

	public static void main(String[] args) {
		
		
//		List<Estrazione> specificSubset = EstrazioneController.retrieveListEstrazioni().parallelStream().
//				filter(estr -> 
//					estr.getDate().get(Calendar.YEAR) == 2018
//				).collect(Collectors.toList());
//				
//		
//		FrequenzaController contr = 
//				new FrequenzaController(specificSubset);
//		
//		for ( Numero n: NumeroController.getInsiemeNumeri() ){
//			System.out.println("FREQ ROMA per "+n+": "+contr.getFreqCombinazioneRuota(RuotaID.ROMA, n));
//		}
		
	
		/*
	
		System.out.println(contr.getRitardoCombinazioneSuRuota(RuotaID.BARI, new Ambo(5,66)));
		*/
		
//		FrequenzaController contr = new FrequenzaController(EstrazioneController.retrieveListEstrazioni());
	
//		contr.ambiPiuRitardatari(RuotaID.BARI, 10).forEach( (ambo,ritardo) -> {
//			//System.out.println("RUOTA: BARI. Ambo: " + ambo + " Ritardo: " + ritardo);
//		});
		
//		contr.combinazioniPiuFrequenti(RuotaID.ROMA, 20, 2).forEach( (terno, freq) -> {
//			System.out.println("RUOTA: ROMA. Terno: " + terno + " FREQ: " + freq);
//		});
		
//		RitardoController c = new RitardoController(EstrazioneController.retrieveListEstrazioni());
//		c.ambiPiuRitardatariConCapogioco(RuotaID.FIRENZE, 3).forEach( (ambo,ritardo) -> {
//			System.out.println("RUOTA: BARI. Ambo: " + ambo + " Ritardo: " + ritardo);
//		});
		
		System.out.println(new RitardoController(EstrazioneController.retrieveListEstrazioni()).
				getRitardoCombinazioneSuRuota(RuotaID.BARI, new Ambo(11,22
						)));
		
	}

	public static boolean isDevMode(){
		return isDevMode;
	}
	
	private static boolean isDevMode;
	static {
		isDevMode = Boolean.parseBoolean(LocalConfigurationManager.getConfProperties().getProperty("devMode"));
	}
}
