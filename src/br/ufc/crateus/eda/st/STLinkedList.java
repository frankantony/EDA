package br.ufc.crateus.eda.st;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;


@SuppressWarnings("hiding")
public class STLinkedList<K, Object> implements ST<K, Object> {
	
	private List<Entry<K, Object>> list = new LinkedList<>();

	@Override
	public Object get(K key) {
		Entry<K, Object> e = getEntry(key);
		return (e != null)? e.getValue() : null;
	}
	
	private Entry<K, Object> getEntry(K key) {
		for (Entry<K, Object> e : list)
			if (e.getKey().equals(key))
				return e;
		return null;
	}

	@Override
	public void put(K key, Object value) {
		Entry<K, Object> e = getEntry(key); 
		if (value != null) {
			if (e == null) {
				e = new STEntry<>(key, value);
				list.add(e);
			}
			else e.setValue(value);
		}
		else {
			if (e != null) list.remove(e);
		}
	}

	@Override
	public void delete(K key) {
		put(key, null);
	}

	@Override
	public boolean contains(K key) {
		return get(key) != null;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public Iterable<K> keys() {
		List<K> keys = new LinkedList<>();
		for (Entry<K, Object> e : list) 
			keys.add(e.getKey());		
		return keys;
	}
	
	
	public static void main(String[] args) {
		ST<String, Integer> st = new STLinkedList<String, Integer>();
		st.put("João", 23);
		st.put("Maria", 40);
		
		for (String key : st.keys()) {
			System.out.println("1. Key = " + key + ", Value = " + st.get(key));
		}
		
		st.put("Uálison", 25);
		st.put("Ayrton", 21);
		st.put("Bruno", 25);
		for (String key : st.keys()) {
			System.out.println("2. Key = " + key + ", Value = " + st.get(key));
		}
		
		st.delete("Ayrton");
		st.put("Bruno", 22);
		st.put("Felipe", 17);
		
		for (String key : st.keys()) {
			System.out.println("3. Key = " + key + ", Value = " + st.get(key));
		}

	}
}
