package br.ufc.crateus.eda.st.btree;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ParId {
	private String id;
	private String deslocamento;
	
	public ParId(String id,String deslocamento) {
		this.id = id;
		this.deslocamento = deslocamento;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeslocamento() {
		return deslocamento;
	}
	public void setDeslocamento(String deslocamento) {
		this.deslocamento = deslocamento;
	}
	
	public static ParId fromPar(byte[] array) throws UnsupportedEncodingException {
		String id = new String(Arrays.copyOfRange(array, 0, 9), "ISO-8859-1").trim();
		String deslocamento = new String(Arrays.copyOfRange(array, 9, 18), "ISO-8859-1").trim();
		return new ParId(id, deslocamento);
	}
	
}
