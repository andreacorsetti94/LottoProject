package numeri;

public class CombinazioneFactory {

	public static Combinazione createCombinazione(Numero[] numeri){
		if ( numeri == null || numeri.length == 0) return null;
		switch(numeri.length){
		case 1: 
			return numeri[0];
		case 2:
			return new Ambo(numeri[0], numeri[1]);
		case 3:
			return new Terno(numeri[0], numeri[1], numeri[2]);
		case 4:
			return new Quaterna(numeri[0], numeri[1], numeri[2], numeri[3]);
		case 5:
			return new Cinquina(numeri[0], numeri[1], numeri[2], numeri[3], numeri[4]);
		default:
				return null;
		}
	}
}
