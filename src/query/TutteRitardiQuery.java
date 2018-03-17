package query;

import helper.CollectionHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import numeri.Ambo;
import estrazioni.Estrazione;

public class TutteRitardiQuery {
	private RitardoQuery ritardoQuery;

	public TutteRitardiQuery(List<Estrazione> estrazioni) {
		this.ritardoQuery = new RitardoQuery(estrazioni);
	}

	public LinkedHashMap<Ambo,Integer> ambiRitardatariTutte(int limit){
		return ritardoQuery.ambiPiuRitardatariTutte(limit);
	}
	
	public LinkedHashMap<Ambo,Integer> ritardiAmbiTutte(int limit, List<Ambo> inputAmbi){
		Map<Ambo,Integer> ambi = new HashMap<>();

		for(Ambo ambo: inputAmbi){
			int rit = ritardoQuery.ritardoCombinazioneTutte(ambo);
			ambi.put(ambo,rit);
		}
		return CollectionHelper.sortHashMapByIntegerValue(ambi, limit, true);
	}
}
