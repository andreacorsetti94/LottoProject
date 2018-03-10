package estrazioni;

import iofetch.LocalConfigurationManager;

import java.util.List;

public class StoricoManager {
	private static List<String> storicoLines;
	
	public static void addLinesToStorico(List<String> toAdd){
		toAdd.forEach(line -> storicoLines.add(line));
	}
	
	public static List<String> getStoricoLines(){
		return storicoLines;
	}
	
	static {
		storicoLines = LocalConfigurationManager.fetchStoricoStream();
	}
}
