package br.ufc.crateus.eda.test;

import java.io.IOException;

import br.ufc.crateus.eda.st.btree.Page;

public class Test {
	public static void main(String[] args) throws IOException {
		Page<String, Long> p = new Page<>(4, true);

		p.add(0);
		System.out.println(p.keys());
	}
}
