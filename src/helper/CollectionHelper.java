package helper;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectionHelper {

	public static <K> LinkedHashMap<K,Integer> sortAmboHashMapByIntegerValue(Map<K,Integer> mapToSort, int limit, boolean reverseOrder){
		Comparator<Integer> comparator = null;
		if ( reverseOrder ){
			comparator = Comparator.reverseOrder();
		}
		else{
			comparator = Comparator.naturalOrder();
		}
		
		LinkedHashMap<K,Integer> sorted =
				mapToSort.entrySet()
				.parallelStream()
				.sorted(Map.Entry.comparingByValue(comparator))
				.limit(limit)
				.collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));;

		return sorted;
	}
}
