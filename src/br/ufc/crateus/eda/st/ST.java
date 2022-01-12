package br.ufc.crateus.eda.st;

public interface ST<K, V> {
	V get(K key);
	void put(K key, V value);
	void delete(K key);
	boolean contains(K key);
	int size();
	boolean isEmpty();
	Iterable<K> keys();
}
