package helper;
import iofetch.LocalConfigurationManager;

import java.util.List;

import numeri.Numero;
import numeri.NumeroController;


public class Test {

	public static void main(String[] args) {
		for ( List<Numero> terzina: NumeroController.getSestine()){
			String s = "";
			for ( Numero n: terzina ) s += n.toString() + ",";
			System.out.println(s);
		}
	}

	public static boolean isDevMode(){
		return isDevMode;
	}
	
	private static boolean isDevMode;
	static {
		isDevMode = Boolean.parseBoolean(LocalConfigurationManager.getConfProperties().getProperty("devMode"));
	}
}
