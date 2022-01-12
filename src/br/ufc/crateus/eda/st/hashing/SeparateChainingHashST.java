package br.ufc.crateus.eda.st.hashing;

import java.lang.management.MemoryNotificationInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import br.ufc.crateus.eda.st.ST;

public class SeparateChainingHashST<K, V> implements ST<K, V> {

	private int n;
	private Node[] table;
	private int m;

	public SeparateChainingHashST(int length) {
		this.n = length;
		table = new Node[length];
		m = 0;
	}

	private static class Node {
		Object key;
		Object value;
		Node next;
		int count;

		Node(Object key, Object value, Node next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}

	private int countNode(Node r) {
		int c = 0;
		while (r != null) {
			c++;
			r = r.next;
		}
		return c;
	}

	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % n;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) {
		int i = hash(key);
		for (Node l = table[i]; l != null; l = l.next)
			if (l.key.equals(key))
				return (V) l.value;
		return null;
	}

	@Override
	public void put(K key, V value) {
		if ((double) m / n >= 8)
			resize(2 * n);

		int i = hash(key);
		for (Node l = table[i]; l != null; l = l.next) {
			if (l.key.equals(key)) {
				l.value = value;
				return;
			}
		}

		table[i] = new Node(key, value, table[i]);
		m++;
	}

	private Node delete(K key, Node lst) {
		lst = new Node(null, null, lst);

		Node head = lst;
		Node node = head;

		while (node.next != null) {
			if (node.next.key.equals(key)) {
				node.next = node.next.next;
				m--;
				break;
			}
			node = node.next;
		}

		lst = head.next;
		return lst;
	}

	@Override
	public void delete(K key) {

		if ((double) m / n <= 2)
			resize(n / 2);

		int i = hash(key);
		table[i] = delete(key, table[i]);
	}

	@Override
	public boolean contains(K key) {
		return get(key) != null;
	}

	@Override
	public int size() {
		return m;
	}

	@Override
	public boolean isEmpty() {
		return m == 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<K> keys() {
		List<K> list = new ArrayList<>(m);
		for (int i = 0; i < n; i++)
			for (Node l = table[i]; l != null; l = l.next)
				list.add((K) l.key);
		return list;
	}

	@SuppressWarnings({ "unchecked" })
	private void resize(int m) {
		if (m == 0)
			return;
		SeparateChainingHashST<K, V> hash = new SeparateChainingHashST<>(m);
		for (int i = 0; i < this.n; i++) {
			for (Node l = table[i]; l != null; l = l.next) {
				hash.put((K) l.key, (V) l.value);
			}
		}
		this.n = hash.n;
		this.m = hash.m;
		this.table = hash.table;
	}

	public float average() {
		float f = 0;
		for (int i = 0; i < this.n; i++) {
			f += countNode(table[i]);
		}
		return f / this.n;
	}

	public double desvioPadrao() {
		float f = 0, media = average();

		for (int i = 0; i < this.n; i++) {
			int count = countNode(table[i]);
			f += (((count - media) * (count - media)) / (this.n));
		}

		return Math.sqrt(f);

	}

	public int menorListaEncadeada() {
		int c = countNode(table[0]);
		for (int i = 0; i < this.n; i++) {
			if (c > countNode(table[i]))
				c = countNode(table[i]);
		}
		return c;
	}

	public int maiorListaEncadeada() {
		int c = countNode(table[0]);
		for (int i = 0; i < this.n; i++) {
			if (c < countNode(table[i]))
				c = countNode(table[i]);
		}
		return c;
	}

	public static void main(String[] args) {
		insertN((int)Math.pow(10, 3));
		insertN((int)Math.pow(10, 4));
		insertN((int)Math.pow(10, 5));
		insertN((int)Math.pow(10, 6));
		
	}

	private static void insertN(int n) {
		SeparateChainingHashST<Integer, Integer> f = new SeparateChainingHashST<>(n/100);
		Random r = new Random(10L);
		for (int i = 0; i < n; i++) {
			int nInt = r.nextInt();
			f.put(nInt, null);
		}
		System.out.println("Menor lista : " + f.menorListaEncadeada());
		System.out.println("Maior lista : " + f.maiorListaEncadeada());
	}

}
