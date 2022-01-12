package br.ufc.crateus.eda.st.ordered;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BinarySearchTree<K extends Comparable<K>, V> implements OrderedST<K, V> {

	private String[][] st;
	protected Node root;

	protected class Node {
		K key;
		V value;
		int count;
		Node left, right;

		public Node(K key, V value, int count) {
			this.key = key;
			this.value = value;
			this.count = count;
		}
	}

	@Override
	public V get(K key) {
		Node node = getNode(key);
		return (node != null) ? node.value : null;
	}

	private Node getNode(K key) {
		Node r = root;
		while (r != null) {
			int cmp = key.compareTo(r.key);
			if (cmp < 0)
				r = r.left;
			else if (cmp > 0)
				r = r.right;
			else
				break;
		}
		return r;
	}

	@Override
	public void put(K key, V value) {
		root = put(root, key, value);
	}

	private Node put(Node r, K key, V value) {
		if (r == null)
			return new Node(key, value, 1);
		int cmp = key.compareTo(r.key);
		if (cmp < 0)
			r.left = put(r.left, key, value);
		else if (cmp > 0)
			r.right = put(r.right, key, value);
		else
			r.value = value;
		r.count = 1 + size(r.left) + size(r.right);
		return r;
	}

	@Override
	public void delete(K key) {
		root = delete(root, key);
	}

	protected Node delete(Node r, K key) {

		if (r == null)
			return null;
		int cmp = key.compareTo(r.key);
		if (cmp < 0)
			r.left = delete(r.left, key);
		else if (cmp > 0)
			r.right = delete(r.right, key);
		else {
			if (r.left == null)
				return r.right;
			if (r.right == null)
				return r.left;

			Node tmp = r;
			r = min(r.right);
			r.left = tmp.left;
			r.right = deleteMin(tmp.right);
		}

		r.count = size(r.left) + size(r.right) + 1;
		return r;
	}

	@Override
	public boolean contains(K key) {
		return contains(root, key);
	}

	private boolean contains(Node r, K key) {
		if (r == null)
			return false;
		int cmp = key.compareTo(r.key);
		if (cmp > 0)
			return contains(r.right, key);
		else if (cmp < 0)
			return contains(r.left, key);
		else
			return true;
	}

	@Override
	public int size() {
		return size(root);
	}

	protected int size(Node node) {
		return (node != null) ? node.count : 0;
	}

	@Override
	public boolean isEmpty() {
		return root == null;
	}

	@Override
	public Iterable<K> keys() {
		Queue<K> keys = new LinkedList<>();
		inorder(root, keys);
		return keys;
	}

	@Override
	public K min() {
		Node min = min(root);
		return (min != null) ? min.key : null;
	}

	protected Node min(Node r) {
		if (r == null)
			return null;
		Node min = min(r.left);
		return (min != null) ? min : r;
	}

	@Override
	public K max() {
		Node max = max(root);
		return (max != null) ? max.key : null;
	}

	private Node max(Node r) {
		if (r == null)
			return null;
		Node max = max(r.right);
		return (max != null) ? max : r;
	}

	@Override
	public K floor(K key) {
		Node node = floor(root, key);
		return (node != null) ? node.key : null;
	}

	private Node floor(Node r, K key) {
		if (r == null)
			return null;
		int cmp = key.compareTo(r.key);
		if (cmp == 0)
			return r;
		if (cmp < 0)
			return floor(r.left, key);
		Node t = floor(r.right, key);
		return (t != null) ? t : r;
	}

	@Override
	public K ceiling(K key) {
		Node node = ceiling(root, key);
		return (node != null) ? node.key : null;
	}

	private Node ceiling(Node r, K key) {
		if (r == null)
			return null;
		int cmp = key.compareTo(r.key);
		if (cmp == 0)
			return r;
		if (cmp > 0)
			return ceiling(r.right, key);
		Node t = ceiling(r.left, key);
		return (t != null) ? t : r;
	}

	@Override
	public int rank(K key) {
		return rank(root, key);
	}

	private int rank(Node r, K key) {
		if (r == null)
			return 0;
		int cmp = key.compareTo(r.key);
		if (cmp == 0)
			return size(r.left);
		if (cmp < 0)
			return rank(r.left, key);
		return 1 + size(r.left) + rank(r.right, key);
	}

	@Override
	public K select(int i) {
		Node r = select(root, i);
		return (r != null) ? r.key : null;
	}

	private Node select(Node r, int k) {
		if (r == null)
			return null;
		int t = size(r.left);
		if (t > k)
			return select(r.left, k);
		else if (t < k)
			return select(r.right, k - t - 1);
		else
			return r;
	}

	@Override
	public void deleteMin() {
		root = deleteMin(root);
	}

	protected Node deleteMin(Node r) {
		if (r.left == null)
			return r.right;
		r.left = deleteMin(r.left);
		r.count = 1 + size(r.left) + size(r.right);
		return r;
	}

	@Override
	public void deleteMax() {
		root = deleteMax(root);
	}

	private Node deleteMax(Node r) {
		if (r.right == null)
			return r.left;
		r.right = deleteMax(r.right);
		r.count = 1 + size(r.left) + size(r.right);
		return r;
	}

	@Override
	public int size(K lo, K hi) {
		List<K> result = new LinkedList<>();
		auxKeys(lo, hi, result);

		return result.size();
	}

	@Override
	public Iterable<K> keys(K lo, K hi) {

		List<K> result = new LinkedList<>();
		auxKeys(lo, hi, result);
		return result;
	}

	private void auxKeys(K lo, K hi, List<K> result) {

		int teto = rank(ceiling(lo)), piso = rank(floor(hi));

		for (int i = teto; i <= piso; i++) {
			result.add(this.select(i));
		}

	}

	void inorder(Node r, Queue<K> keys) {
		if (r != null) {
			inorder(r.left, keys);
			keys.add(r.key);
			inorder(r.right, keys);
		}
	}

	public int height() {
		return auxHeight(root);
	}

	private int auxHeight(Node r) {
		int left = 0, right = 0;
		if (r == null)
			return -1;
		if (r.left != null)
			left = auxHeight(r.left);
		if (r.right != null)
			right = auxHeight(r.right);
		return (left >= right) ? left + 1 : right + 1;
	}

	private int depth(K key) {
		return auxDepth(root, key);
	}

	private int auxDepth(Node r, K key) {
		if (r == null)
			return 0;
		int cmp = key.compareTo(r.key);
		if (cmp > 0)
			return auxDepth(r.right, key) + 1;
		else if (cmp < 0)
			return auxDepth(r.left, key) + 1;
		else
			return 1;
	}

	private void aux(int n, V value) {
		for (int i = 0; i < n; i++) {
			System.out.print("  ");
		}
		System.out.println(value);
	}

	public int altura() {
		return altura(root);
	}

	private int altura(Node r) {
		if (r == null)
			return 0;
		if (r.left == null && r.right == null)
			return 0;
		int h_esq = altura(r.left);
		int h_dir = altura(r.right);
		if (h_dir > h_esq)
			return h_dir + 1;
		else
			return h_esq + 1;
	}

	private int profundidade(Node r, K key) {
		if (r == null)
			return 0;
		int cmp = key.compareTo(r.key);
		if (cmp == 0)
			return 0;
		else if (cmp < 0)
			return profundidade(r.left, key) + 1;
		else
			return profundidade(r.right, key) + 1;
	}

	public int profundidade(K key) {
		return profundidade(root, key);
	}

	private void auxPrint(Node r) {
		if (r != null) {
			aux(depth(r.key), r.value);
			auxPrint(r.left);
			auxPrint(r.right);
		} else
			System.out.println("_");
	}

	@SuppressWarnings("unused")
	private void toPrint() {
		auxPrint(root);
	}

	@SuppressWarnings({ "unchecked" })
	private void arrayToTree(Node r, List<K> array) {
		int e = 0, d = array.size();
		int m = d / 2;

		for (int i = m; i > e; i--) {
			put(array.get(i), (V) array.get(i));
		}

		for (int i = m + 1; i < d; i++) {
			put(array.get(i), (V) array.get(i));
		}

	}

	public void arrayToTree(List<K> l) {
		arrayToTree(root, l);
	}

	// questao 06.

	@SuppressWarnings("unchecked")
	public void toStrings(Node r, int b) {
		if (r == null) {
			printNode((K) "-", b);
			return;
		}
		toStrings(r.left, b + 1);
		printNode(r.key, b);
		toStrings(r.right, b + 1);
	}

	private void printNode(K c, int b) {
		for (int i = 0; i < b; i++)
			System.out.print(" ");
		System.out.println(c);
	}

	public void toStrings() {
		this.toStrings(root, 0);
	}

	// questao 07

	public void transformaVetorEmArvore(K keys[], V value[]) {
		int mid = (0 + value.length) / 2;
		int inicio = mid;
		int fim = mid + 1;
		for (int i = 0; i < value.length; i++) {
			if (i % 2 == 0) {
				if (inicio >= 0) {
					this.put(keys[inicio], value[inicio]);
					inicio--;
				}
			} else {
				if (fim < value.length) {
					this.put(keys[fim], value[fim]);
					fim++;
				}
			}

		}
	}

	private String getNivel(Queue<K> keys, int p) {
		String result = "";
		for (K d : keys)
			if (profundidade(d) == p)
				result += (String) d + " ";
		return result;
	}

	public String getKeys(int p) {
		Queue<K> keys = new LinkedList<>();
		inorder(root, keys);
		return getNivel(keys, p);
	}

	// questao 08

	private void imprimeMatrix(Node r, int lin, int col, int y) {

		if (r != null) {
			st[lin][col] = (String) r.key;

			if (y == 0) {
				if (r.left != null)
					y = 1;
				else
					y = 2;
			}
			if (y == 1) {
				for (int i = 0; i < (int) Math.pow(2, altura() - profundidade(r.key)); i++) {
					st[lin + 1][col - 1] = "/";
					lin++;
					col--;
				}
			} else if (y == 2) {
				for (int i = 0; i < (int) Math.pow(2, altura() - profundidade(r.key)); i++) {
					st[lin + 1][col + 1] = "\\";
					lin++;
					col++;
				}
			}
			imprimeMatrix(r.left, lin + 1, col, 1);
			imprimeMatrix(r.right, lin + 1, col, 2);

		}

	}

	private void imprimeMatrixAux(Node r, int lin, int col, int y) {

		if (r != null) {
			st[lin][col] = (String) r.key;

			if (y == 0) {
				if (r.left != null)
					y = 1;
				else
					y = 2;
			}
			if (y == 1) {
				// for (int i = 0; i < (int) Math.pow(2, altura() -
				// profundidade(r.key)); i++) {
				st[lin + 1][col - 1] = "/";
				lin++;
				col--;
				// }
			} else if (y == 2) {
				// for (int i = 0; i < (int) Math.pow(2, altura() -
				// profundidade(r.key)); i++) {
				st[lin + 1][col + 1] = "\\";
				lin++;
				col++;
				// }
			}
			imprimeMatrixAux(r.left, lin + 1, col, 1);
			imprimeMatrixAux(r.right, lin + 1, col, 2);

		}

	}

	public void imprime() {
		alocation();
		imprimeMatrixAux(root, 0, (int) Math.pow(2, (double) altura() + 1) + size(), 0);
		for (int i = 0; i < (int) Math.pow(2, (double) altura() + 1) + size(); i++) {
			for (int j = 0; j < 2 * (int) Math.pow(2, (double) altura() + 1) + size(); j++) {
				if (st[i][j] != null)
					System.out.print(st[i][j]);
				else
					System.out.print(" ");
			}
			System.out.println();
		}
	}

	// O metodo aloca memoria para a matriz
	public void alocation() {
		st = new String[(int) Math.pow(2, (double) altura() + 1) + size()][2 * (int) Math.pow(2, (double) altura() + 1)
				+ size()];
	}

	private void printRaiz(Node r) {
		for (int i = 0; i <= altura(); i++) {
			System.out.println("valor = " + getKeys(i) + " , tamanho = " + getKeys(i).length());
		}
	}

	private void printRaiz() {
		printRaiz(root);
	}

	public static void main(String[] args) {
		BinarySearchTree<String, Integer> f = new BinarySearchTree<>();
		f.put("D", 1);
		f.put("B", 1);
		f.put("E", 1);
		f.put("C", 1);
		f.put("F", 1);
		f.put("A", 1);
		System.out.println(f.altura());

		f.printRaiz();
	}

	public String toString() {
		for (int i = 0; i <= altura(root); i++) {
			auxToString(root, i);
			System.out.println();
		}
		return "";
	}

	private void auxToString(Node r, int i) {
		if (r == null)
			return;
		if (profundidade(r.key) == i) {
			String e = "";
			for (int j = 0; j < r.count; j++) {
				e += " ";
			}
			System.out.print(e + r.key + e);
		}
		auxToString(r.left, i);
		auxToString(r.right, i);
	}

}
