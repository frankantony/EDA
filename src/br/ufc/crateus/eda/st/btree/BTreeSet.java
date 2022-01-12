package br.ufc.crateus.eda.st.btree;

import java.util.HashSet;
import java.util.Set;

public class BTreeSet<K extends Comparable<K>, V> {
	private Page<K, Object> root;
	private int countPage = 1, count = 0;
	private int m;

	public BTreeSet(K sentinel, V value, int m) {
		this.m = m;
		root = new Page<>(m, true);
		root.insert(sentinel, value);

	}

	public boolean contains(K key) {
		return contains(root, key);
	}

	private boolean contains(Page<K, Object> r, K key) {
		if (r.isExternal())
			return r.holds(key);

		return contains(r.next(key), key);
	}

	public void add(K key, V value) {
		count++;
		add(root, key, value);
		if (root.hasOverflowed()) {
			Page<K, Object> left = root;
			Page<K, Object> right = root.split();
			root = new Page<>(m, false);
			root.enter(left);
			root.enter(right);

		}
	}

	private void keys(Page<K, Object> p, Set<K> l) {
		if (p.isExternal()) {
			for (K key : p.st.keys())
				l.add(key);
		} else {
			for (K key : p.st.keys()) {
				keys(p.next(key), l);
			}

		}

	}

	public int numberRecords() {
		return count;
	}

	public Iterable<K> keys() {
		Set<K> keys = new HashSet<>();
		keys(root, keys);
		return keys;
	}

	private void add(Page<K, Object> r, K key, V value) {
		if (r.isExternal()) {
			r.insert(key, value);
			return;
		}
		Page<K, Object> next = r.next(key);
		if (next != null) {
			add(next, key, value);
			if (next.hasOverflowed()) {
				r.enter(next.split());
				countPage = countPage + 1;
			}
		}
	}

	private void delete(Page<K, Object> r, K key) {
		if (r.isExternal()) {
			r.st.delete(key);
			return;
		}
		Page<K, Object> next = r.next(key);
		if (next != null) {
			delete(next, key);
		}

	}

	public void delete(K key) {
		delete(root, key);
	}

	public K min() {
		return min(root);
	}

	private K min(Page<K, Object> r) {
		if (r.isExternal()) {
			int i = r.st.rank(r.st.min());
			return r.st.select(i + 1);
		} else
			return min(r.next(r.st.min()));
	}

	public K max() {
		return max(root);
	}

	private K max(Page<K, Object> r) {
		if (r.isExternal()) {
			return r.st.max();
		} else
			return max(r.next(r.st.max()));
	}

	private K floor(Page<K, Object> r, K key) {
		if (r.isExternal()) {
			return r.st.floor(key);
		} else {
			return floor(r.next(key), key);
		}

	}

	public Object floor(K key) {
		return floor(root, key);
	}

	private K ceiling(Page<K, Object> r, K key) {
		if (r.isExternal()) {
			return r.st.ceiling(key);
		} else {
			return ceiling(r.next(key), key);
		}
	}

	public Object ceiling(K key) {
		return ceiling(root, key);
	}

	public Object get(K key) {
		return get(root, key);
	}

	@SuppressWarnings("unchecked")
	private Object get(Page<K, Object> r, K key) {
		if (r.isExternal()) {
			return (V) r.st.get(key);
		} else {
			return get(r.next(key), key);
		}

	}

	@SuppressWarnings("unused")
	private void deleteMax() {
		root.st.deleteMax();
	}

	@SuppressWarnings("unused")
	private void deleteMin() {
		root.st.deleteMin();
	}

	public int countPage() {
		return countPage;
	}

}
