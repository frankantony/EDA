package br.ufc.crateus.eda.st.trabalho;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import br.ufc.crateus.eda.randomfile.RandomFileHelper;
import br.ufc.crateus.eda.randomfile.Student;
import br.ufc.crateus.eda.st.btree.BTreeSet;

public class CreateIndexPerId {

	@SuppressWarnings("resource")
	static List<String> readLines(String file) throws IOException {
		BufferedReader buffer = new BufferedReader(new FileReader(new File(file)));
		List<String> lst = new ArrayList<>();
		while (buffer.ready()) {
			String st = buffer.readLine();
			lst.add(st);

		}
		return lst;
	}

	public static void main(String[] args) throws IOException {
		execute();

	}

	private static void execute() throws IOException, UnsupportedEncodingException {
		BTreeSet<String, Long> btree = new BTreeSet<String, Long>("", null, 6);
		addDataFilePerId("datafile.data", "Student.data");

		BTreeSet<String, Long> d = loadFile("Student.data", 6);

		executeCmd("cmdfile1.txt", d, "Student.data");
	}


	// adicionar os estudantes do arquivo texto filein , no arquivo fileNew.
	static void addDataFilePerId(String fileIn, String fileNew) throws IOException, UnsupportedEncodingException {
		List<String> lst = readLines(fileIn);
		long i = 0, deslocamento = 0;
		for (String h : lst) {
			String[] linha = h.split(" ");
			Student student = new Student(linha[0], linha[1], linha[2], linha[3], linha[4], linha[5]);
			RandomFileHelper.write(fileNew, 64 * i, student.toBytes());
			RandomFileHelper.append("estudante.ix1", Student.toBytesTree(student.getId(), String.valueOf(64 * i)));
			i++;
		}
	}

	static void addDataFilePerName(String fileIn, String fileNew, BTreeSet<String, Object> btree)
			throws IOException, UnsupportedEncodingException {

		List<String> lst = readLines(fileIn);
		long i = 0;
		for (String h : lst) {
			String[] linha = h.split(" ");
			Student student = new Student(linha[0], linha[1], linha[2], linha[3], linha[4], linha[5]);
			RandomFileHelper.write(fileNew, 64 * i, student.toBytes());
			i++;
			btree.add(linha[1], 64 * i);
		}

	}

	static BTreeSet<String, Long> loadFile(String file, int m) throws IOException {
		BTreeSet<String, Long> r = new BTreeSet<String, Long>("", null, m);

		for (int i = 0; true; i++) {
			byte[] bytes = RandomFileHelper.read(file, 64 * i, 64);
			Student e1 = Student.fromBytes(bytes);
			long deslocamento = 64 * i;
			if (e1.getId().length() != 0) {
				r.add(e1.getId(), deslocamento);
			} else {
				break;
			}

		}
		return r;
	}

	static void executeCmd(String fileIn, BTreeSet<String, Long> r, String fileData) throws IOException {
		List<String> lines = readLines(fileIn);
		// PrintWriter writer = new PrintWriter(new FileWriter(new
		// File("outputfile.txt")));
		System.out.println("NUMBER RECORS : " + r.numberRecords() + ", ID INDEX : " + r.countPage() + " pages ");
		for (String j : lines) {
			String[] p = j.split(" ");
			System.out.println();
			if (p.length == 3 && p[0].equals("find") && p[1].equals("ID")) {
				System.out.println("COMAND : find ID " + p[2]);
				findId(r, fileData, p[2]);
			} else if (p.length == 3 && p[0].equals("find") && p[1].equals("name")) {
				System.out.println("COMAND : find name " + p[2]);
			} else if (p.length == 7 && p[0].equals("add")) {
				System.out.println(
						"COMMAND: add " + p[1] + " " + p[2] + " " + p[3] + " " + " " + p[4] + " " + p[5] + " " + p[6]);
				addStudent(r, fileData, p);
				System.out
						.println("STUDENT " + p[0] + " ADDED " + "(" + r.get(p[1]) + ";" + "" + r.countPage() + ";0)");
				// writer.println();
			} else if (p.length == 3 && p[0].equals("dump") && p[1].equals("by") && p[2].equals("ID")) {
				System.out.println("dump by ID");
				dumpById(r, fileData);
			} else if (p.length == 3 && p[0].equals("dump") && p[1].equals("by") && p[2].equals("name")) {
				System.out.println("dump by name ");
			} else {
				System.out.println("ERROR!");
			}

		}
		System.out.println("NUMBER RECORDS : " + r.numberRecords() + ", ID INDEX : " + r.countPage() + " pages ");
	}

	static void addStudent(BTreeSet<String, Long> btreeId, String fileData, String[] p)
			throws UnsupportedEncodingException {

		Student s = new Student(p[1], p[2], p[3], p[4], p[5], p[6]);
		byte[] bytes = s.toBytes();
		if (btreeId.contains(p[1])) {
			RandomFileHelper.write(fileData, (long) btreeId.get(p[1]), bytes);
			// RandomFileHelper.append("estudante.ix1",
			// Student.toBytesTree(p[1], String.valueOf((long)
			// btreeId.get(p[1]))));
		} else {
			long y = RandomFileHelper.append(fileData, bytes);
			btreeId.add(p[1], y);
			RandomFileHelper.append("estudante.ix1", Student.toBytesTree(p[1], String.valueOf(y)));
		}
	}

	static void dumpById(BTreeSet<String, Long> btreeId, String file) throws UnsupportedEncodingException {

		for (String x : btreeId.keys()) {
			if (x == "")
				continue;

			byte[] bytes = RandomFileHelper.read(file, (long) (btreeId.get(x)), 64);
			Student e = Student.fromBytes(bytes);
			System.out.println(" " + btreeId.get(x) + " : " + e);
		}

	}

	static void findId(BTreeSet<String, Long> btreeId, String file, String id) throws UnsupportedEncodingException {
		long position = 0;
		if (btreeId.get(id) != null) {
			position = (long) btreeId.get(id);
		}

		byte[] bytes;

		bytes = RandomFileHelper.read(file, position, 64);
		Student e = Student.fromBytes(bytes);
		if (btreeId.get(id) != null)
			System.out.println(btreeId.get(id) + " : " + e);
		else
			System.out.println("NOT FOUND");
	}

}
