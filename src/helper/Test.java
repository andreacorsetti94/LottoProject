package helper;
import iofetch.LocalConfigurationManager;

public class Test {

	public static void main(String[] args) {
		LocalConfigurationManager.updateStorico();
	}

	public static boolean isDevMode(){
		return isDevMode;
	}
	
	private static boolean isDevMode;
	static {
		isDevMode = Boolean.parseBoolean(LocalConfigurationManager.getConfProperties().getProperty("devMode"));
	}
}
