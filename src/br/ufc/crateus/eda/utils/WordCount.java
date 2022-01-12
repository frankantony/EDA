package br.ufc.crateus.eda.utils;

import java.util.Scanner;

import br.ufc.crateus.eda.st.ST;
import br.ufc.crateus.eda.st.STLinkedList;

public class WordCount {
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		ST<String, Integer> st = new STLinkedList<>();
		
		while (in.hasNext()) {
			String str = in.next();
			if (!st.contains(str)) st.put(str, 1);
			else st.put(str, st.get(str) + 1);
		}
		in.close();
		
		String strMax = "";
		int max = 0;
		st.put(strMax, max);
		
		for (String str : st.keys()) {
			if (st.get(str) > st.get(strMax)) {
				strMax = str;
				max = st.get(str);
			}
		}
		
		System.out.println("Max = " + strMax);
	}
}
