package br.ufc.crateus.eda.st.trabalho;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import br.ufc.crateus.eda.randomfile.RandomFileHelper;
import br.ufc.crateus.eda.randomfile.Student;
import br.ufc.crateus.eda.st.btree.BTreeSet;

public class CreateIndexPerName {

	public static void main(String[] args) throws IOException {

		BTreeSet<String, Long> btree = new BTreeSet<String, Long>("", null, 6);
		addDataFilePerName("datafile.data", "Student.data", btree);

		executeCmd("cmdfile1.txt", btree, "Student.data");

	}

	static void addDataFilePerName(String fileIn, String fileNew, BTreeSet<String, Long> btreeName)
			throws IOException, UnsupportedEncodingException {
		List<String> lst = readLines(fileIn);
		long i = 0;
		for (String h : lst) {
			String[] atribute = h.split(" ");
			Student student = new Student(atribute[0], atribute[1], atribute[2], atribute[3], atribute[4], atribute[5]);
			if (!btreeName.contains(atribute[1]))
				btreeName.add(atribute[1], 64 * i);
			RandomFileHelper.append(fileNew, student.toBytes());
			RandomFileHelper.append("estudante.ix2", Student.toBytesTree(atribute[1], String.valueOf(64 * i)));
			i++;
		}

	}

	static BTreeSet<String, Long> loadFile(String file) throws IOException {
		BTreeSet<String, Long> r = new BTreeSet<String, Long>("", null, 6);

		for (int i = 0; true; i++) {
			byte[] bytes = RandomFileHelper.read(file, 64 * i, 64);
			Student e1 = Student.fromBytes(bytes);
			long deslocamento = 64 * i;
			if (e1.getId().length() != 0) {
				r.add(e1.getFirstName(), deslocamento);
			} else {
				break;
			}

		}
		return r;
	}

	static void addStudent(BTreeSet<String, Long> btreeName, String fileData, String[] atribute) throws IOException {

		Student s = new Student(atribute[1], atribute[2], atribute[3], atribute[4], atribute[5], atribute[6]);
		byte[] bytes = s.toBytes();
		if (btreeName.contains(atribute[2])) {
			List<Student> students = new ArrayList<>();
			long pos = (long) btreeName.get(atribute[2]);
			while (true) {
				byte[] bites = RandomFileHelper.read(fileData, pos, 64);
				Student e = Student.fromBytes(bites);

				if (e.getId().length() == 0 || !(e.getFirstName().equals(atribute[2])))
					break;
				students.add(e);
				pos = pos + 64;
			}
			if (pos == RandomFileHelper.length(fileData))
				return;
			long posicao = RandomFileHelper.append(fileData, students.get(0).toBytes());
			for (int i = 1; i < students.size(); i++) {
				RandomFileHelper.append(fileData, students.get(i).toBytes());
			}
			RandomFileHelper.append(fileData, bytes);
			btreeName.add(atribute[2], posicao);
			RandomFileHelper.write("estudante.ix2", posicao, Student.toBytesTree(atribute[2], String.valueOf(posicao)));

		} else {
			long y = RandomFileHelper.append(fileData, bytes);
			btreeName.add(atribute[2], y);
			RandomFileHelper.append("estudante.ix2", Student.toBytesTree(atribute[2], String.valueOf(y)));
		}
	}

	static void executeCmd(String fileIn, BTreeSet<String, Long> r, String fileData) throws IOException {
		List<String> lines = readLines(fileIn);
		System.out.println("NUMBER RECORS : " + r.numberRecords() + ", ID INDEX : " + r.countPage() + " pages ");
		for (String j : lines) {
			String[] p = j.split(" ");
			System.out.println();
			if (p.length == 3 && p[0].equals("find") && p[1].equals("ID")) {
				System.out.println("COMAND : find ID " + p[2]);
				// findId(r, fileData, p[2]);
			} else if (p.length == 3 && p[0].equals("find") && p[1].equals("name")) {
				System.out.println("COMAND : find name " + p[2]);
				findName(r, fileData, p[2]);
			} else if (p.length == 7 && p[0].equals("add")) {
				System.out.println(
						"COMMAND: add " + p[1] + " " + p[2] + " " + p[3] + " " + " " + p[4] + " " + p[5] + " " + p[6]);
				addStudent(r, fileData, p);
				System.out.println("STUDENT " + p[1] + " ADDED " + "(" + r.get(p[2]) + ";" + "0;0)");
			} else if (p.length == 3 && p[0].equals("dump") && p[1].equals("by") && p[2].equals("ID")) {
				System.out.println("dump by ID");
				// dumpById(r, fileData);
			} else if (p.length == 3 && p[0].equals("dump") && p[1].equals("by") && p[2].equals("name")) {
				System.out.println("dump by name");
				dumpByName(r, fileData);
			} else {
				System.out.println("ERROR!");
			}

		}
		System.out.println("NUMBER RECORS : " + r.numberRecords() + ", ID INDEX : " + r.countPage() + " pages ");

	}

	static void dumpByName(BTreeSet<String, Long> btreeName, String file) throws UnsupportedEncodingException {

		for (String x : btreeName.keys()) {
			if (x == "")
				continue;
			findName(btreeName, file, x);
		}

		// System.out.println("paginas = " + btreeName.countPage());
	}

	static void findName(BTreeSet<String, Long> btreeName, String file, String name)
			throws UnsupportedEncodingException {

		if (btreeName.get(name) != null) {
			long p = (long) btreeName.get(name);
			Student e = null;
			while (true) {
				byte[] bytes = RandomFileHelper.read(file, p, 64);
				e = Student.fromBytes(bytes);

				if (e.getId().length() == 0 || !(e.getFirstName().equals(name)))
					break;

				System.out.println(p + " : " + e);

				p = p + 64;

			}
		} else
			System.out.println("NOT FOUND");
	}

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

}
