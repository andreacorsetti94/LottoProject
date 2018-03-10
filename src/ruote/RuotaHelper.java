package ruote;


public class RuotaHelper {

	public static RuotaID fetchIdFromString(String input){
		switch(input){
		case "BA":
			return RuotaID.BARI;
		case "CA":
			return RuotaID.CAGLIARI;
		case "FI":
			return RuotaID.FIRENZE;
		case "GE":
			return RuotaID.GENOVA;
		case "NA":
			return RuotaID.NAPOLI;
		case "MI":
			return RuotaID.MILANO;
		case "RM":
			return RuotaID.ROMA;
		case "TO":
			return RuotaID.TORINO;
		case "VE":
			return RuotaID.VENEZIA;
		case "PA":
			return RuotaID.PALERMO;
		case "RN":
			return RuotaID.NAZIONALE;
		default:
			System.err.println("ERRORE TRADUZIONE. Input: " + input);
			return null;
		}
	}
	
	public static String fetchStringFromRuotaID(RuotaID id){
		switch(id){
		case BARI:
			return "BA";
		case CAGLIARI:
			return "CA";
		case FIRENZE:
			return "FI";
		case MILANO:
			return "MI";
		case GENOVA:
			return "GE";
		case VENEZIA:
			return "VE";
		case TORINO:
			return "TO";
		case NAZIONALE:
			return "RN";
		case NAPOLI:
			return "NA";
		case ROMA:
			return "RM";
		case PALERMO:
			return "PA";
		default:
			System.err.println("ERRORE TRADUZIONE. Input: " + id.toString());
			return "";
		}
	}
}
