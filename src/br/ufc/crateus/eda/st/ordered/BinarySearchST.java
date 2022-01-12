package br.ufc.crateus.eda.st.ordered;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import br.ufc.crateus.eda.st.STEntry;

public class BinarySearchST<K extends Comparable<K>, V> implements OrderedST<K, V> {

	private List<Entry<K, V>> list = new ArrayList<>();

	@Override
	public V get(K key) {
		int j = rank(key);
		return (j < size() && list.get(j).getKey().compareTo(key) == 0) ? list.get(j).getValue() : null;
	}

	public void put(K key, V value) {
		int i = rank(key);
//		if (value != null) {
			list.add(i, new STEntry<K, V>(key, value));
//		} 
//		else if (value == null) {
//			delete(key);
//		}
	}

	@Override
	public void delete(K key) {
		if(isEmpty()) return;
		list.remove(rank(key));
	}

	@Override
	public boolean contains(K key) {
		int j = rank(key);
		if(j == size()) return false;
		return list.get(j).getKey().compareTo(key) == 0;
	}


	@Override
	public Iterable<K> keys() {
		List<K> aux = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			aux.add((K) select(i));
		}
		return aux;
	}

	@Override
	public K min() {
		return list.get(0).getKey();
	}

	@Override
	public K max() {
		return list.get(list.size() - 1).getKey();
	}

	@Override
	public K floor(K key) {  //teto
		if (isEmpty() || key.compareTo(min()) < 0)
			return null;
		
		int i = rank(key);
		if(i == size()) return max();
		else if (key.equals(select(i)))
			return key;
		else
			return select(i - 1);
	}

	@Override
	public K ceiling(K key) {   //piso.
		
		if (isEmpty() || key.compareTo(max()) > 0 )
			return null;
		int i = rank(key);
		return list.get(i).getKey();
	}

	@Override
	public int rank(K key) {
		int lo = 0, hi = size() - 1;

		while (lo <= hi) {
			int mid = (hi + lo) / 2;
			int cmp = key.compareTo(select(mid));
			if (cmp < 0)
				hi = mid - 1;
			else if (cmp > 0)
				lo = mid + 1;
			else
				return mid;
		}

		return lo;
	}
	@Override
	public int size() {
		return list.size();
	}
	
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public K select(int i) {
		return list.get(i).getKey();
	}
	
	@Override
	public void deleteMin() {
		delete(min());
	}

	@Override
	public void deleteMax() {
		delete(max());
	}

	@Override
	public int size(K lo, K hi) {
		List<K> lst = new ArrayList<>(); 
		keys(lst, lo, hi);
		return lst.size();
	}

	@Override
	public Iterable<K> keys(K lo, K hi) {
		List<K> lst = new ArrayList<>();
		keys(lst, lo, hi);
		
		return lst;
	}
	
	private void keys (List<K> l,K lo,K hi) {
		int a = rank(lo),b = rank(hi);
		for(int i = a;b < size() ? i <= b : i < b;i++) {
			K chave = list.get(i).getKey();
				l.add(chave);
		}

		
	}
	
}
