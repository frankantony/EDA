package br.ufc.crateus.eda.st.btree;

import java.io.UnsupportedEncodingException;

import br.ufc.crateus.eda.randomfile.RandomFileHelper;
import br.ufc.crateus.eda.randomfile.Student;
import br.ufc.crateus.eda.st.ordered.BinarySearchST;

public class Page<K extends Comparable<K>, V> {

	private int m;
	BinarySearchST<K, Object> st;
	private boolean botton;

	public Page(int m, boolean botton) {
		this.botton = botton;
		this.st = new BinarySearchST<>();
		this.m = m;
	}

	public Page(int m, boolean botton, long deslcamento) {
		this.botton = botton;
		this.st = new BinarySearchST<>();
		this.m = m;
	}

	@SuppressWarnings("unchecked")
	public void add(long deslocamento) throws UnsupportedEncodingException {
		this.st = new BinarySearchST<>();

		for (int i = 0; i < m; i++) {
			if (!this.botton) {
				byte[] bytes = RandomFileHelper.read("estudante.ix1", deslocamento + 19 * i, 19);
				ParId par = ParId.fromPar(bytes);
				st.put((K) par.getId(), par.getDeslocamento());
				System.out.println("Entrou aqui no if");

			} else {
				System.out.println("Entrou aqui no else");
				byte[] bytes = RandomFileHelper.read("Student.data", deslocamento + 64 * i, 64);
				Student student = Student.fromBytes(bytes);
				st.put((K) student.getId(), deslocamento);
			}
		}

	}

	public void close() {

	}

	public void insert(K key, V value) {
		if (!hasOverflowed()) {
			st.put(key, value);
		}
	}

	public void enter(Page<K, V> page) {
		if (page != null)
			st.put(page.st.min(), page);
	}

	public boolean isExternal() {
		return botton;
	}

	public boolean holds(K key) {
		return st.contains(key);
	}

	@SuppressWarnings("unchecked")
	Page<K, Object> next(K key) {

		return (Page<K, Object>) st.get(st.floor(key));
	}

	public boolean hasOverflowed() {
		return (st.size() == m) ? true : false;
	}

	public Page<K, Object> split() {
		Page<K, Object> tmp = new Page<>(m, true);

		int i = st.size();
		for (int j = i / 2; j < i; j++) {
			tmp.st.put(st.select(j), st.get(st.select(j)));
		}

		int m = i / 2;
		K kMeio = st.select(m);
		int j = st.size() - 1;

		while (!kMeio.equals(st.select(j))) {
			st.delete(st.select(j));
			j--;
		}

		st.delete(st.select(j));

		return tmp;
	}

	public Iterable<K> keys() {
		return st.keys();
	}

}