package projetS4;
import java.io.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Map m = new Map(3);
		System.out.println(m.findMap(new File("src/projetS4/BD01plus.bd")));
		//System.out.println(System.getProperties());
	}

}
