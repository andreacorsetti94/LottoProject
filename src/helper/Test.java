package helper;
import iofetch.LocalConfigurationManager;
import numeri.Numero;
import estrazioni.ConvenienzaController;
import estrazioni.EstrazioneController;


public class Test {

	public static void main(String[] args) {
		
		ConvenienzaController contr = new ConvenienzaController(EstrazioneController.retrieveListEstrazioni());
		System.out.println(contr.indiceConvenienzaCombinazioneTutte(new Numero(8)));
	
	}

	public static boolean isDevMode(){
		return isDevMode;
	}
	
	private static boolean isDevMode;
	static {
		isDevMode = Boolean.parseBoolean(LocalConfigurationManager.getConfProperties().getProperty("devMode"));
	}
}
