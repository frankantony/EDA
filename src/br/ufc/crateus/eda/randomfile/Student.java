package br.ufc.crateus.eda.randomfile;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;

public class Student {
	private String id; // 9
	private String firstName; // 15
	private String lastName; // 15
	private String semester; // 1
	private String course; // 4
	private String email; // 20

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Student(String id, String firstName, String lastName, String semester, String course, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.semester = semester;
		this.course = course;
		this.email = email;
	}

	public byte[] toBytes() throws UnsupportedEncodingException {
		byte[] array = new byte[64];
		System.arraycopy(fixedLengthString(id, 9).getBytes("ISO-8859-1"), 0, array, 0, 9);
		System.arraycopy(fixedLengthString(firstName, 15).getBytes("ISO-8859-1"), 0, array, 9, 15);
		System.arraycopy(fixedLengthString(lastName, 15).getBytes("ISO-8859-1"), 0, array, 24, 15);
		System.arraycopy(fixedLengthString(semester, 1).getBytes("ISO-8859-1"), 0, array, 39, 1);
		System.arraycopy(fixedLengthString(course, 4).getBytes("ISO-8859-1"), 0, array, 40, 4);
		System.arraycopy(fixedLengthString(email, 20).getBytes("ISO-8859-1"), 0, array, 44, 20);
		return array;
	}

	public static byte[] toBytes(Collection<Student> estudantes) throws UnsupportedEncodingException {
		byte[] array = new byte[64 * estudantes.size()];

		int len = 0;
		for (Student e : estudantes) {
			System.arraycopy(e.toBytes(), 0, array, 64 * len, 64);
			len++;
		}

		return array;
	}

	public static Student fromBytes(byte[] array) throws UnsupportedEncodingException {
		String matricula = new String(Arrays.copyOfRange(array, 0, 9), "ISO-8859-1").trim();
		String primeiroNome = new String(Arrays.copyOfRange(array, 9, 24), "ISO-8859-1").trim();
		String ultimoNome = new String(Arrays.copyOfRange(array, 24, 39), "ISO-8859-1").trim();
		String semestre = new String(Arrays.copyOfRange(array, 39, 40), "ISO-8859-1").trim();
		String curso = new String(Arrays.copyOfRange(array, 40, 44), "ISO-8859-1").trim();
		String email = new String(Arrays.copyOfRange(array, 44, 64), "ISO-8859-1").trim();
		return new Student(matricula, primeiroNome, ultimoNome, semestre, curso, email);
	}

	private static String fixedLengthString(String str, int size) {
		if (str.length() > size)
			return str.substring(0, size);
		else
			return String.format("%1$" + size + "s", str);
	}

	@Override
	public String toString() {
		return " " + id + " " + firstName + " " + lastName + " " + semester + " " + course + " " + email;
	}

	public static byte[] toBytesTree(String chave, String deslocamento) throws UnsupportedEncodingException {
		byte[] array = new byte[19];

		System.arraycopy(fixedLengthString(chave, 9).getBytes("ISO-8859-1"), 0, array, 0, 9);
		System.arraycopy(fixedLengthString(deslocamento, 9).getBytes("ISO-8859-1"), 0, array, 9, 9);
		System.arraycopy(fixedLengthString(" ", 1).getBytes("ISO-8859-1"), 0, array, 18, 1);

		return array;
	}

	public static String stringToTree(byte[] array) throws UnsupportedEncodingException {
		String email = new String(Arrays.copyOfRange(array, 0, 9), "ISO-8859-1").trim();
		return email;
	}

}
