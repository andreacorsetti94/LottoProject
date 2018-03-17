package query;

import helper.CollectionHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import numeri.Ambo;
import ruote.RuotaID;
import estrazioni.Estrazione;

public class RuotaRitardiQuery {

	private RuotaID id;
	private RitardoQuery ritardoQuery;
	
	public RuotaRitardiQuery(RuotaID id, List<Estrazione> estrazioni) {
		ritardoQuery = new RitardoQuery(estrazioni);
		this.id = id;
	}
	
	public LinkedHashMap<Ambo,Integer> ambiRitardatari(int limit){
		return ritardoQuery.ambiPiuRitardatari(id, limit);
	}
	
	public LinkedHashMap<Ambo,Integer> ambiRitardatariCapogioco(int limit){
		return ritardoQuery.ambiPiuRitardatariConCapogioco(id, limit);
	}
	
	public LinkedHashMap<Ambo,Integer> ritardiAmbi(int limit, List<Ambo> inputAmbi){
		Map<Ambo,Integer> ambi = new HashMap<>();

		for(Ambo ambo: inputAmbi){
			int rit = ritardoQuery.ritardoCombinazioneRuota(id, ambo);
			ambi.put(ambo,rit);
		}
		return CollectionHelper.sortHashMapByIntegerValue(ambi, limit, true);
	}
	
}
