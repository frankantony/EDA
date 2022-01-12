package br.ufc.crateus.eda.utils;

import java.io.BufferedReader; 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;

import br.ufc.crateus.eda.st.hashing.SeparateChainingHashST;

public class WordCount2 {

	public static String normalizeStr(String str) {
		String semAcentos = Normalizer.normalize(str, Normalizer.Form.NFD);
		semAcentos = semAcentos.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		semAcentos = semAcentos.replaceAll("\\p{Punct}", "");
		return semAcentos.toLowerCase();
	}

	public static BufferedReader open(String name) throws IOException {
//		String path = "\\UFC\\3º Semestre\\Estrutura de Dados Avançado - EDA\\arquivo\\Trabalho\\";
		File file = new File(name);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao tentar abrir o arquivo.");
		}
		return reader;
	}

	public static void main(String[] args) throws IOException {
		ArrayList<String> lista = new ArrayList<>();
		lista.add("arquivo1.txt");
		lista.add("arquivo2.txt");

		// Map<String, Map<String, Integer>> map = new HashMap<>();
		SeparateChainingHashST<String, Integer> map2 = new SeparateChainingHashST<>(97);

		for (int i = 0; i < lista.size(); i++) {
			BufferedReader reader = open(lista.get(i));

			while (reader.ready()) {
				String line = reader.readLine();
				for (String word : line.split("\\s+")) {
					String nWord = word.toLowerCase();

					Integer count = map2.get(nWord);
					count = (count != null) ? count + 1 : 1;
					map2.put(nWord, count);
				}
			}

			reader.close();
		}

		for (String word : map2.keys()) {
			System.out.println(word + " = " + map2.get(word));
		}
	}
	
}
