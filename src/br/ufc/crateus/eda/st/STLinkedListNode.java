package br.ufc.crateus.eda.st;

import java.util.Map.Entry;
import java.util.LinkedList;
import java.util.Queue;

public class STLinkedListNode<K, V> implements ST<K, V> {
	private Node lst;
	private int size = 0;
	
	private class Node {
		Entry<K, V> entry;
		Node next; 
		
		public Node(Entry<K, V> entry , Node next) {
			this.entry = entry;
			this.next = next;
		}
		
	}
	
	@Override
	public V get(K key) {
		Node node = getNode(key);
		return (node != null)? node.entry.getValue():null;
	}
	
	private Node getNode (K key) {
		for(Node n = lst;n != null;n = n.next) {
			if(n.entry.getKey().equals(key))
				return n;
		}
		return null;
	}
	
	@Override
	public void put(K key, V value) {
		Node node = getNode(key); 
		if(node == null) {
			Entry<K, V> entry = new STEntry<>(key,value);
			lst = new Node(entry, lst);
			size++;
		}
		else node.entry.setValue(value);
	}

	
	@Override
	public void delete(K key) {
		lst = new Node(null, lst);
		Node head = lst;
		Node node = head;
		
		while (node.next != null) {
			if(node.next.entry.getKey().equals(key)){
				node.next = node.next.next;
				size--;
				break;
			}
			node = node.next;
		}
		
		lst = head.next;
	}

	@Override
	public boolean contains(K key) {
		Node node = getNode(key);
		return node != null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return lst == null;
	}

	@Override
	public Iterable<K> keys() {
		Queue<K> queue = new LinkedList<>();
		for(Node n = lst;n != null;n = n.next) {
			queue.add(n.entry.getKey());
		}
		return queue;
	}

}
