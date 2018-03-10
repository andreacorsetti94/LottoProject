package ruote;

import java.util.ArrayList;
import java.util.List;

import estrazioni.TipoEstrazione;

public class RuoteFactory {

	public static List<Ruota> creaListaRuote(TipoEstrazione tipo){
		
		List<Ruota> list = new ArrayList<>();
		list.add(0, new Ruota(RuotaID.BARI));
		list.add(1, new Ruota(RuotaID.CAGLIARI));
		list.add(2, new Ruota(RuotaID.FIRENZE));
		list.add(3, new Ruota(RuotaID.GENOVA));
		list.add(4, new Ruota(RuotaID.MILANO));
		list.add(5, new Ruota(RuotaID.NAPOLI));
		list.add(6, new Ruota(RuotaID.PALERMO));
		list.add(7, new Ruota(RuotaID.ROMA));
		list.add(8, new Ruota(RuotaID.TORINO));
		list.add(9, new Ruota(RuotaID.VENEZIA));
		
		switch ( tipo ){
		case MODERNA:
			list.add(10, new Ruota(RuotaID.NAZIONALE));
			return list;
		case SENZA_NAZ:
			return list;
		default:
			return new ArrayList<>();
		}
	}
}
