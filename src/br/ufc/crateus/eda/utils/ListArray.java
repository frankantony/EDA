package br.ufc.crateus.eda.utils;


public class ListArray<T> {
	private T[] vet;
	private int cap;
	private int numElem = 0;
	
	@SuppressWarnings("unchecked")
	public ListArray(int cap) {
		this.cap = cap;
		this.vet = (T[]) new Object[cap];
	}
	
	public ListArray() {
		this(10);
	}
	
	public void add(T t) {
		if (numElem == cap) {
			resize();
		}
		vet[numElem++] = t;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void remove (T key) {
		
		T aux[] = (T[]) new Object[cap];
		int p = 0;
		for (int i = 0;i < numElem;i++) {
			if(vet[i] != key) {
				aux[p++] = vet[i];
			}
		}
		this.numElem = p;
		this.vet = aux;
	}
	
	public void remove (int id) {
		remove(vet[id]);
	}
	
	@SuppressWarnings("unchecked")
	private void resize () {
		cap *= 2;
		T[] aux = (T[]) new Object[cap];
		for (int i = 0; i < numElem; i++) {
			aux[i] = vet[i];
		}
		vet = aux;
	}
	public void keys() {
		System.out.print("[");
		for(T key : vet)
			if (key != null)
				System.out.print(key + " ");
		
		 System.out.print("]");
	}
	
	public void add (int i , T value) {
		if (i == numElem) resize();
		
		for (int k = numElem; k > i ;k--) {
			vet[k] = vet[k-1];
			numElem++;
		}
		vet[i] = value;
	}
	
	public T get(int i) {
		return (i < numElem) ? vet[i] : null;
	}
	
	public int size() {
		return numElem;
	}
	
	public static void main(String[] args) {
		ListArray<Integer> ar = new ListArray<>();
		
		ar.add(1);
		ar.add(2);
		ar.add(3);
		ar.add(4);
		ar.add(5);
		ar.add(6);
		ar.add(6);
		
		for (int i = 0; i < ar.size(); i++)
			System.out.print(ar.get(i) + " ");
		System.out.println();
		
		ar.remove(6);
		ar.remove(3);
		ar.remove(1);

		for (int i = 0; i < ar.size(); i++)
			System.out.print(ar.get(i) + " ");
		System.out.println();
	}
}
