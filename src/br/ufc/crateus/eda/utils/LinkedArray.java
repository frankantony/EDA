package br.ufc.crateus.eda.utils;

import java.util.ArrayList;   
import java.util.List;

public class LinkedArray<T> {
	
	private Node inicio;
	private Node fim;
	private int size = 0;
	
	
	private class Node {
		
		T[] array;
		int length = 0;
		Node next;

		Node(T[] array, Node next) {
			this.array = array;
			this.next = next;
		}
	}

	@SuppressWarnings("unchecked")
	public LinkedArray (int size) {
		inicio = fim = new Node((T[]) new Object[size], null);
	}

	@SuppressWarnings("unchecked")
	public void add(T key) {
		if (fim.length == fim.array.length) {
			Node newNode = new Node((T[]) new Object[fim.array.length], null);
			fim.next = newNode;
			fim = newNode;
		}
		fim.array[fim.length++] = key;
		this.size++;
	}
	
	public void add (int i,T key) {
		LinkedArray<T> array = new LinkedArray<>(inicio.length);
		int count = 0;
		if (i == inicio.length){
			add(key);
			return;
		}
		for (T k : keys()) {
			if (count == i) array.add(key);
			array.add(k);
			count++;
		}
		this.fim = array.fim;
		this.inicio = array.inicio;
		this.size = array.size;
	}
	
	public T get(int posicao) {
		if(inicio.length == 0) return null;
		int count = 0,valor = posicao / inicio.length,resto = posicao % inicio.length;
		Node p;
		for (p = inicio; p != null && count < valor ; p = p.next) count++;
		return (p != null) ? p.array[resto] : null;
	}
	
	public void remove (int i) {
		LinkedArray<T> array = new LinkedArray<>(size);
		int count = 0;
		for (T k : keys()) { 
			if(count != i)
				array.add(k);
				count++;
		}
		this.fim = array.fim;
		this.inicio = array.inicio;
		this.size = array.size;
	}
	
	public Iterable<T> keys () {
		
		List<T> list = new ArrayList<>(); 
		for (Node p = inicio; p != null; p = p.next) 
			for (int i = 0; i < p.length; i++) 
				list.add(p.array[i]);
		return list;
	}

	 public int size() {
		 return size;
	 }

}
