package br.ufc.crateus.eda.st.hashing;

import java.util.ArrayList;
import java.util.List;

import br.ufc.crateus.eda.st.ST;
import br.ufc.crateus.eda.st.ordered.BinarySearchTree;

public class SeparateTreeHash<K extends Comparable<K>, V> implements ST<K, V> {

	private int length;
	private BinarySearchTree<K, V>[] table;
	int size;

	@SuppressWarnings("unchecked")
	public SeparateTreeHash(int length) {
		this.length = length;
		table = (BinarySearchTree<K, V>[]) new BinarySearchTree[length];
		size = 0;
	}

	@Override
	public V get(K key) {
		for (int i = hash(key); table[i] != null; i = (i + 1) % length) {
			BinarySearchTree<K, V> obj = table[i];
			if (obj != null)
				if (obj.contains(key))
					return (V) table[i].get(key);
		}
		
		return null;
	}

	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % length;
	}

	@Override
	public void put(K key, V value) {
		if ((double) size / length >= 8)
			resize(2*length);
		int i = hash(key);
		BinarySearchTree<K, V> obj = table[i];
		if (obj == null) {
			obj = new BinarySearchTree<>();
			obj.put(key, value);
			table[i] = obj;
		} else {
			obj.put(key, value);
			table[i] = obj;

		}

		size++;
	}

	@Override
	public void delete(K key) {
		if ((double) size / length <= 2)			
			resize(length/2);
		
		int i = hash(key);
		BinarySearchTree<K, V> obj = table[i];
		if (obj == null) {
			return;
		} else {
			obj.delete(key);
			table[i] = obj;

		}
	}

	@Override
	public boolean contains(K key) {
		return get(key) != null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterable<K> keys() {
		List<K> aux = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			if (table[i] != null)
				for (K key : table[i].keys())
					aux.add(key);
		}
		return aux;
	}

	private void resize(int length) {
		SeparateTreeHash<K, V> aux = new SeparateTreeHash<>(length);
		for (K key : keys()) {
			aux.put(key, get(key));
		}
		this.length = aux.length;
		this.size = aux.size;
		this.table = aux.table;
	}
	
}
