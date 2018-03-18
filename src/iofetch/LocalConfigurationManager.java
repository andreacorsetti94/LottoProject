package iofetch;

import helper.Test;

import java.util.List;
import java.util.Properties;

import estrazioni.StoricoManager;

public class LocalConfigurationManager {

	private static Properties confProperties;
	private static final String LOCAL_STORICO_FILE = "TRUE_storico.txt";
	private static final String TMP_STORICO_ZIP = "tmp.zip";
	private static final String TMP_STORICO_FILE = "storico.txt";
	
	private static List<String> storicoLines;
	
	public static Properties getConfProperties(){
		if ( confProperties == null ) confProperties = new PropertiesParser("conf.properties");
		return confProperties;
	}
	
	public static List<String> fetchStoricoStream(){

		if ( storicoLines == null ){
			
			if ( Test.isDevMode() ){
				storicoLines = FileManager.fetchLines("ex.txt");
			}
			else {
				storicoLines = FileManager.fetchLines(LOCAL_STORICO_FILE);
			}
		}
		return storicoLines;

	}
	
	public static void updateStorico(){
		boolean resultOk = Connector.downloadStorico(TMP_STORICO_ZIP);
		
		if (resultOk){
			Unzipper.unzip(TMP_STORICO_ZIP, "/"); //puts in resources

			List<String> newLines = FileManager.fetchLastLines(TMP_STORICO_FILE);
			for(String line: newLines) System.out.println(line);
			
			StoricoManager.updateEstrazioni(newLines);
			FileManager.appendLinesToFile(newLines, LOCAL_STORICO_FILE);
			FileManager.deleteFile(TMP_STORICO_FILE);
			FileManager.deleteFile(TMP_STORICO_ZIP);
			

		}
				
	}
}
