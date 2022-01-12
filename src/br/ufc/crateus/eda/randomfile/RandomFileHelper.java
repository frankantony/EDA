package br.ufc.crateus.eda.randomfile;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomFileHelper {

	public static byte[] read(String file, long position, int size) {
		byte[] bytes = new byte[size];
		try {
			RandomAccessFile fileStore = new RandomAccessFile(file, "r");
			fileStore.seek(position);
			fileStore.read(bytes);
			fileStore.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	public static long length(String file) throws IOException {
		RandomAccessFile fileStore = new RandomAccessFile(file, "rw");
		return fileStore.length();
	}

	public static void write(String file, long position, byte[] bytes) {
		try {
			RandomAccessFile fileStore = new RandomAccessFile(file, "rw");
			fileStore.seek(position);
			fileStore.write(bytes);
			fileStore.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static long append(String file, byte[] bytes) {
		long position = -1;
		try {
			RandomAccessFile fileStore = new RandomAccessFile(file, "rw");
			position = fileStore.length();
			fileStore.seek(position);
			fileStore.write(bytes);
			fileStore.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return position;
	}
	


}
