package helper;
import iofetch.LocalConfigurationManager;
import numeri.Ambo;
import estrazioni.EstrazioneController;
import estrazioni.RitardoController;


public class Test {

	public static void main(String[] args) {
		
		RitardoController contr = new RitardoController(EstrazioneController.retrieveListEstrazioni());
		System.out.println(contr.ritardoMedioTutte(new Ambo(12,89)));
		//System.out.println(contr.ritardoPrecedenteCombinazioneRuota(RuotaID.BARI,new Numero(1)));

	}

	public static boolean isDevMode(){
		return isDevMode;
	}
	
	private static boolean isDevMode;
	static {
		isDevMode = Boolean.parseBoolean(LocalConfigurationManager.getConfProperties().getProperty("devMode"));
	}
}
