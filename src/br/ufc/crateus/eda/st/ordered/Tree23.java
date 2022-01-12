package br.ufc.crateus.eda.st.ordered;

public class Tree23<K extends Comparable<K>, V> {
	Node23 root;

	class Node23 {
		K keyRigth, keyLeft;
		V valueLeft, valueRigth;
		Node23 mid, left, rigth;

		Node23(K key, V value) {
			this.keyLeft = key;
			this.valueLeft = value;
		}
	}

	// busca em uma arvore 2-3.

	public V get(K key) {
		Node23 n = get(root, key);
		if (n == null)
			return null;
		int cmp1 = key.compareTo(n.keyLeft);
		if (cmp1 == 0)
			return n.valueLeft;
		return n.valueRigth;
	}

	private Node23 get(Node23 r, K key) {
		if (r == null)
			return null;
		int cmpLeft = key.compareTo(r.keyLeft);
		int cmpRigth = key.compareTo(r.keyRigth);
		if (cmpRigth == 0 || cmpLeft == 0)
			return r;
		if (cmpLeft > 0 && cmpRigth < 0)
			return get(r.mid, key);
		else if (cmpLeft < 0)
			return get(r.left, key);
		else
			return get(r.rigth, key);
	}

	public boolean contains(K key) {

		return get(key) != null;
	}

	private void troca(K leftKey, K rigthKey, V valueLeft, V valueRigth) {
		K o = leftKey;
		leftKey = rigthKey;
		rigthKey = o;

		V v = valueLeft;
		valueLeft = valueRigth;
		valueRigth = v;
	}

	private Node23 put(Node23 r, K key, V value) {
		if (r == null)
			return new Node23(key, value);

		if (r.keyRigth == null && r.left == null && r.mid == null && r.rigth == null) { // insere
																						// nas
																						// folhas
																						// primeiramente.
			r.keyRigth = key;
			r.valueRigth = value;
			int cmp = r.keyLeft.compareTo(r.keyRigth);
			if (cmp > 0)
				troca(r.keyLeft, r.keyRigth, r.valueLeft, r.valueRigth);
			return r;
		}

		int cmpLeft = key.compareTo(r.keyLeft);
		int cmpRigth = key.compareTo(r.keyRigth);
		if (cmpLeft > 0 && cmpRigth < 0)
			r.mid = put(r.mid, key, value);
		else if (cmpLeft > 0)
			r.left = put(r.left, key, value);
		else if (cmpRigth > 0)
			r.rigth = put(r.rigth, key, value);
		else if (cmpLeft == 0)
			r.valueLeft = value;
		else if (cmpRigth == 0)
			r.valueRigth = value;

		// segue abaixo
		// casos em que a arvore ja esta com duas chaves preeenchidas no nó r
		// é necessario inserir outra chave no no .

		if (cmpLeft > 0 && cmpRigth < 0) {
			// split
			Node23 node_mid = new Node23(key, value);
			Node23 node_left = new Node23(r.keyLeft, value);
			Node23 node_rigth = new Node23(r.keyRigth, value);
			r.left = node_left;
			r.rigth = node_rigth;
			r.left.rigth = r.mid;
			r = node_mid;
		}

		if (cmpRigth > 0) {
			// split
			System.out.println(r.valueRigth);
			System.out.println(r.valueLeft);
			Node23 node_mid = new Node23(r.keyRigth, value);
			Node23 node_rigth = new Node23(key, value);
			Node23 node_left = new Node23(r.keyLeft, value);
			r = node_mid;
			r.left = node_left;
			r.rigth = node_rigth;
			r.left.rigth = r.mid;
		} else if (cmpLeft < 0) {
			// split
			Node23 node_mid = new Node23(r.keyLeft, value);
			Node23 node_left = new Node23(r.keyRigth, value);
			Node23 node_rigth = new Node23(key, value);
			r.left = node_left;
			r.rigth = node_rigth;
			r.left.rigth = r.mid;
			r = node_mid;
		}
		return r;
	}

	public void put(K key, V value) {
		root = put(root, key, value);

	}

}
