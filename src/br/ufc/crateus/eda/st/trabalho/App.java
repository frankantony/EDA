package br.ufc.crateus.eda.st.trabalho;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import br.ufc.crateus.eda.randomfile.RandomFileHelper;
import br.ufc.crateus.eda.randomfile.Student;
import br.ufc.crateus.eda.st.btree.BTreeSet;

public class App {

	public static void main(String[] args) throws UnsupportedEncodingException, IOException {

		Scanner s = new Scanner(System.in);
		String z = s.nextLine();

		String[] st = z.split(" ");

		execute(st);

	}

	private static void execute(String[] st) throws UnsupportedEncodingException, IOException {
		int length = st.length;
		if (length == 2 && st[0].equals("btree")) {
			CreateIndexPerId.addDataFilePerId("datafile.data", st[1] + ".data");
		} else if (length == 5 && st[0].equals("btree")) {
			CreateIndexPerId.addDataFilePerId("datafile.data", st[1] + ".data");
			BTreeSet<String, Long> d = CreateIndexPerId.loadFile(st[1] + ".data", Integer.parseInt(st[2]));

			CreateIndexPerId.executeCmd(st[3], d, st[1] + ".data");
			geraArquivoDeindice(d);
		} else {
			System.err.println("ERROR");
			return;
		}

	}

	private static void geraArquivoDeindice(BTreeSet<String, Long> r) throws UnsupportedEncodingException {

		for (String x : r.keys()) {
			if (x != "")
				RandomFileHelper.append("estudante.ix1", Student.toBytesTree(x, String.valueOf(r.get(x))));
		}

	}

	private static void readArquivoIndice() throws IOException {
		long length = RandomFileHelper.length("estudante.ix1");
		long i = 0;
		while (i < length) {
			byte[] bytes = RandomFileHelper.read("estudante.ix1", i, 19);

			i = i + 19;
		}

	}

}