package helper;
import iofetch.LocalConfigurationManager;
import ruote.Ruota;
import estrazioni.Estrazione;
import estrazioni.EstrazioneController;
import estrazioni.RitardoController;


public class Test {

	public static void main(String[] args) {
		RitardoController contr = new RitardoController(EstrazioneController.retrieveListEstrazioni());
		
	}

	public static boolean isDevMode(){
		return isDevMode;
	}
	
	private static boolean isDevMode;
	static {
		isDevMode = Boolean.parseBoolean(LocalConfigurationManager.getConfProperties().getProperty("devMode"));
	}
}
