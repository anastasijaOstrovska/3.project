// 221RDB202 Edvards Bārtulis
// 221RDB200 Anželika Krasiļņikova
// 221RDB198 Anastasija Ostrovska

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;


public class Main {


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String choiseStr;
		String sourceFile, resultFile, firstFile, secondFile;
		
		loop: while (true) {
			
			choiseStr = sc.next();
								
			switch (choiseStr) {
			case "comp":
				System.out.print("source file name: ");
				sourceFile = sc.next();
				System.out.print("archive name: ");
				resultFile = sc.next();
				comp(sourceFile, resultFile);
				break;
			case "decomp":
				System.out.print("archive name: ");
				sourceFile = sc.next();
				System.out.print("file name: ");
				resultFile = sc.next();
				decomp(sourceFile, resultFile);
				break;
			case "size":
				System.out.print("file name: ");
				sourceFile = sc.next();
				size(sourceFile);
				break;
			case "equal":
				System.out.print("first file name: ");
				firstFile = sc.next();
				System.out.print("second file name: ");
				secondFile = sc.next();
				System.out.println(equal(firstFile, secondFile));
				break;
			case "about":
				about();
				break;
			case "exit":
				break loop;
			}
		}

		sc.close();
	}

	public static void comp(String sourceFile, String resultFile) {
		// TODO: implement this method
	}

	public static void decomp(String sourceFile, String resultFile) {
		// TODO: implement this method
	}
	
	public static void size(String sourceFile) {
		try {
			FileInputStream f = new FileInputStream(sourceFile);
			System.out.println("size: " + f.available());
			f.close();
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	public static boolean equal(String firstFile, String secondFile) {
		try {
			FileInputStream f1 = new FileInputStream(firstFile);
			FileInputStream f2 = new FileInputStream(secondFile);
			int k1, k2;
			byte[] buf1 = new byte[1000];
			byte[] buf2 = new byte[1000];
			do {
				k1 = f1.read(buf1);
				k2 = f2.read(buf2);
				if (k1 != k2) {
					f1.close();
					f2.close();
					return false;
				}
				for (int i=0; i<k1; i++) {
					if (buf1[i] != buf2[i]) {
						f1.close();
						f2.close();
						return false;
					}
						
				}
			} while (k1 == 0 && k2 == 0);
			f1.close();
			f2.close();
			return true;
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	public static void about() {
		// TODO insert information about authors
		System.out.println("000RDB000 Jānis Programmētājs");
		System.out.println("111RDB111 Ilze Programmētāja");
	}
}
// Anželika Krasiļņikova
class LZ77{
	private String sourceFile, resultFile;
	
	public LZ77(String sf, String rf) {
		String sourceFile=sf;
		String resultFile=rf;
		System.out.println("Inicialised");
		
	}
	public static void compress() {
		System.out.println("compressing");
		match(2,3);
	}
	
	public static void decompress() {
		System.out.println("decompressing");
	}
	private static boolean match(int a, int b) {
		System.out.println(a==b);
		return true;
	}
}
//Anastasija Ostrovska
class Huffman{
  private String firstname, secondname;

  public Huffman(String fn, String sn){
    String firstname = fn;
    String secondname = sn;
    System.out.println("made class");
  }

  public static ArrayList<String> huf_reader(String name){
    //nolasa failu, saglabajot katru burtu sarakstā
    System.out.println("I am reading file ");
  }
  public static void Huffman_comp(String firstname, String secondname){
    //nolasa failu
    ArrayList<String> text = new ArrayList<String>();
    text = huf_reader(firstname);
    //no nolasita faila taisa mapu ar burtiem un biežumiem
    Map<Character, Integer> map = new HashMap<Character, Integer>();
    //taisa haffmana koku 
    System.out.println("huffman compression");
    //kodē failu 
  }
  public static void Huffman_decomp(String firstname, String secondname){
    //nolasa failu
    ArrayList<String> text = new ArrayList<String>();
    text = huf_reader(firstname);
    //izmantojot haffmana koku dekodē failu
    System.out.println("huffman decompression");
  }
}
class Huffman_node{
    int data;
    char c;

    Huffman_node left;
    Huffman_node rigth;
}
