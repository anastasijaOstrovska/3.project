// 221RDB202 Edvards Bārtulis
// 221RDB200 Anželika Krasiļņikova
// 221RDB198 Anastasija Ostrovska

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
		int method=1;
		switch (method){
		case 1:
			LZ77 lz= new LZ77(sourceFile,resultFile);
			lz.compress();
			break;
		case 2:
			break;
		case 3:
			break;
		default:
			System.out.println("wrong method");
		}
	}

	public static void decomp(String sourceFile, String resultFile) {
		// TODO: implement this method
		int method=1;
		switch (method){
		case 1:
			LZ77 lz= new LZ77(sourceFile,resultFile);
			lz.decompress();
			break;
		case 2:
			break;
		case 3:
			break;
		default:
			System.out.println("wrong method");
		}
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
		System.out.println("221RDB202 Edvards Bārtulis");
		System.out.println("221RDB200 Anželika Krasiļņikova");
		System.out.println("221RDB198 Anastasija Ostrovska");
	}
}
// Anželika Krasiļņikova
class LZ77{
	private String sourceFile, resultFile;
	private int searchBufferSize=1000;
	
	public LZ77(String sf, String rf) {
		// inicializē LZ77 algoritmu
		String sourceFile=sf;
		String resultFile=rf;
		System.out.println("lz77 inicializācija");
	}
	public void compress() {
		// saspež failu
		System.out.println("saspiešana");
		
		// meklē atkārtojumus un aizvieto tos ar atsaucēm
		byte[] b= {0,0};
		findMatch(b,0);
	}
	
	public void decompress() {
		// veic faila decompresiju
		System.out.println("dekompresija");
	}
	private byte[] findMatch (byte[] buffer, int startPosition) {
		// meklē garāko atkārtojumu un atgriež atsauci uz to
		System.out.println("meklē garāko atkārtojumu");
		
		boolean matched = true;
		// ja atkārtojums netika atrasts atgriež 'null'
		if (!matched) return null; 
		else {
			byte[] b= {0,0};
			return b;
		}
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

  public ArrayList<String> huf_reader(String name){
    //nolasa failu, saglabajot katru burtu sarakstā
    System.out.println("I am reading file ");
	return null;
  }
  public void Huffman_comp(String firstname, String secondname){
    //nolasa failu
    ArrayList<String> text = new ArrayList<String>();
    text = huf_reader(firstname);
    //no nolasita faila taisa mapu ar burtiem un biežumiem
    Map<Character, Integer> map = new HashMap<Character, Integer>();
    //taisa haffmana koku 
    System.out.println("huffman compression");
    //kodē failu 
  }
  public void Huffman_decomp(String firstname, String secondname){
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
