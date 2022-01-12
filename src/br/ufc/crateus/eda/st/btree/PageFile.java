package br.ufc.crateus.eda.st.btree;

public class PageFile {
	int m;
	boolean bottom;
	private Par[] pares;

	public PageFile(int m, boolean bottom) {
		this.m = m;
		this.bottom = bottom;
		pares = new Par[m];
	}
	
}
