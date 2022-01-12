package br.ufc.crateus.eda.st.hashing;

import java.util.ArrayList;
import java.util.List;

import br.ufc.crateus.eda.st.ST;

public class LinearProbingHashST<K, V> implements ST<K, V> {
	int m;
	private K[] keys;
	private V[] values;
	int n;

	@SuppressWarnings("unchecked")
	public LinearProbingHashST(int length) {
		this.m = length;
		keys = (K[]) new Object[length];
		values = (V[]) new Object[length];
		n = 0;
	}

	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % m;
	}

	@Override
	public V get(K key) {
		for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
			if (keys[i].equals(key))
				return values[i];
		return null;
	}

	public void put(K key, V value) {
		if ((double) n / m >= 1.0f / 2)
			resize(2 * m);
		int i;
		for (i = hash(key); keys[i] != null && !key.equals(keys[i]); i = (i + 1) % m)
			;
		if (keys[i] == null) {
			keys[i] = key;
			n++;
		}
		values[i] = value;
	}

	@Override
	public void delete(K key) {
		if ((double) n / m <= 1.0f / 8)
			resize(m / 2);
		int i;
		for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
			if (keys[i].equals(key)) {
				values[i] = null;
				this.n--;
				break;
			}
		}

	}

	@Override
	public boolean contains(K key) {
		return get(key) != null;
	}

	@Override
	public int size() {
		return n;
	}

	@Override
	public boolean isEmpty() {
		return n == 0;
	}

	private void resize(int length) {
		LinearProbingHashST<K, V> hash = new LinearProbingHashST<>(length);
		for (int i = 0; i < this.m; i++)
			if (values[i] != null)
				hash.put(keys[i], values[i]);
		this.keys = hash.keys;
		this.values = hash.values;
		this.m = hash.m;

	}

	@Override
	public Iterable<K> keys() {
		List<K> aux = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			if (values[i] != null)
				aux.add(keys[i]);
		}
		return aux;
	}

}
