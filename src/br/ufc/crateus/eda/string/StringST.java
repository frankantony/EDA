package br.ufc.crateus.eda.string;

import java.util.LinkedList;
import java.util.Queue;

public class StringST<V> {

	private static final int R = 256;
	private Node root = new Node();

	private static class Node {
		Object value;
		Node next[] = new Node[R];
	}

	public void put(String key, V value) {
		root = put(root, key, value, 0);
	}

	private Node put(Node r, String key, V value, int i) {
		if (r == null)
			r = new Node();
		if (i == key.length()) {
			r.value = value;
		} else {
			char ch = key.charAt(i);
			r.next[ch] = put(r.next[ch], key, value, i + 1);
		}
		return r;
	}

	@SuppressWarnings("unchecked")
	public V get(String key) {
		Node n = get(root, key, 0);
		return (n != null) ? (V) n.value : null;
	}

	private Node get (Node r, String key, int i) {
		if (r == null)
			return null;
		if (i == key.length())
			return r;
		char ch = key.charAt(i);
		return get(r.next[ch], key, i + 1);
	}

	public void delete(String key) {
		root = delete(root, key, 0);
	}

	private Node delete(Node r, String key, int i) {
		if (r == null)
			return null;
		if (i == key.length())
			r.value = null;
		else {
			char ch = key.charAt(i);
			r.next[ch] = delete(r.next[ch], key, i + 1);
		}

		if (r.value != null)
			return r;
		for (Node n : r.next)
			if (n != null)
				return r;
		return null;
	}

	public Iterable<String> keys() {
		Queue<String> queue = new LinkedList<String>();
		collect(root, "", queue);
		return queue;
	}

	private void collect (Node node , String prefix, Queue<String> queue) {
		if (node != null) {
			if (node.value != null)
				queue.add(prefix);
			for (char i = 0; i < R; i++) {
				collect(node.next[i], prefix + i, queue);
			}
		}
	}

	public Iterable<String> keysWithPrefix(String prefix) {
		Queue<String> queue = new LinkedList<String>();
		Node node = get(root, prefix, 0);
		collect(node, prefix, queue);
		return queue;
	}

	public String longestPrefixOf(String query) {
		int length = search(root, query, 0, 0);
		return query.substring(0, length);
	}

	private int search(Node r, String query, int d, int length) {
		if (r == null)
			return length;
		if (r.value != null)
			length = d;
		if (d == query.length())
			return length;
		char ch = query.charAt(d);
		return search(r.next[ch], query, d + 1, length);
	}
	
	public boolean contains (String st) {
		return get(st) != null;
	}
	
}
