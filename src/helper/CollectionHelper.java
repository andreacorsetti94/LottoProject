package helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectionHelper {

	public static <K> LinkedHashMap<K,Integer> sortHashMapByIntegerValue(Map<K,Integer> mapToSort, int limit, boolean reverseOrder){
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
	
	public static <K> List<K> getListFromLinkedHashMapKeys(LinkedHashMap<K,Integer> hashMap){
		List<K> list = new ArrayList<>();
		hashMap.forEach( (key, value) -> {
			list.add(key);
		});
		return list;
	}
	
	public static <K,L> List<L> getListFromLinkedHashMapValues(LinkedHashMap<K,L> hashMap){
		List<L> list = new ArrayList<>();
		hashMap.forEach( (key, value) -> {
			list.add(value);
		});
		return list;
	}
	
	public static <T> List<T> listFromArray(T[] array){
		return Arrays.asList(array);
	}
	
}
