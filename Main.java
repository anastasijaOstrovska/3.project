// 221RDB202 Edvards Bārtulis
// 221RDB200 Anželika Krasiļņikova
// 221RDB198 Anastasija Ostrovska

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
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
			Huffman Hf = new Huffman(sourceFile,resultFile);
			Hf.Huffman_comp();
			break;
		case 3:
			Gzip gz= new Gzip(sourceFile,resultFile);
			gz.mainGz("comp");
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
			Huffman Hf = new Huffman(sourceFile,resultFile);
			Hf.Huffman_decomp();
			break;
		case 3:
			Gzip gz= new Gzip(sourceFile,resultFile);
			gz.mainGz("decomp");
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
  public void Huffman_comp(){
    //nolasa failu
    ArrayList<String> text = new ArrayList<String>();
    text = huf_reader(firstname);
    //no nolasita faila taisa mapu ar burtiem un biežumiem
    Map<Character, Integer> map = new HashMap<Character, Integer>();
    //taisa haffmana koku 
    System.out.println("huffman compression");
    //kodē failu 
  }
  public void Huffman_decomp(){
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
// Edvards Bārtulis 
class Gzip {
	public static String inputFile, outputFile;
	
	public Gzip(String inp, String out) {
		String inputFile = inp;
		String outputFile = out;
		System.out.println("Gzip");
	}
    	public static void mainGz(String command) {
        
        switch(command){
	        case "comp":
	        	// izsauc failu kompresiju
			try {
			        GzipLZ77Compression.compress(inputFile);
			} catch (IOException e) {
				System.out.println("Error compressing file: " + e.getMessage());
			}
			try {
			        GzipHuffmanCompression.compress(inputFile+".lz77", outputFile);
			        System.out.println("File compressed successfully.");
			} catch (IOException e) {
			        e.printStackTrace();
			        System.out.println("Error compressing file: " + e.getMessage());
			}
			break;
	        case "decomp":
			// izsauc failu dekompresiju
			try {
				GzipLZ77Compression.decompress(inputFile);
				    
			} catch (IOException e) {
				System.out.println("Error decompressing file: " + e.getMessage());
			}
		        try {
			       GzipHuffmanCompression.decompress(inputFile+".lz77", outputFile);
			       System.out.println("File decompressed successfully.");
			} catch (IOException e) {
			        e.printStackTrace();
			       System.out.println("Error decompressing file: " + e.getMessage());
			}
			break;
		default:
		    	System.out.println("wrong command");
        }
    }
	    
}
class GzipLZ77Compression {
    private static final int WINDOW_SIZE=4096; 
    private static final int LOOKAHEAD_SIZE=15;

    public static void compress(String inputFile) throws IOException {
	    // saspiež failu izmantojot LZ77 algoritmu
	    System.out.println("LZ77Compression");
    }
    public static void decompress(String inputFile) throws IOException {
	    // dekomprese failu, aizvietojot atsauces ar datu kopijām no faila
	    System.out.println("LZ77Decompression");
    }
}

class GzipHuffmanCompression {
    private static final int EOF=0;
    private static final int MAX_BITS=0; 


    private static class Node implements Comparable<Node> {
	    // palīgklase, kas satur informāciju par simbolu, tā frekvenci ievades datu straumē 
	    // un saites uz kreiso un labo atvasi Hafmena kokā.


	    private static Node buildHuffmanTree(int[] freq) {
		    // veido Huffman koku
		    System.out.println("Huffman Tree created");
		    return null;
	    }

	    private static String[] buildCodeTable(Node root) {
		    // veido kodu tabulu 
		    System.out.println("Huffman table created");
		    return null;
	    }

		@Override
		public int compareTo(GzipHuffmanCompression.Node o) {
			// TODO Auto-generated method stub
			return 0;
		}
    }

    private static void buildCodeTableHelper(Node node, String code, String[] codeTable) {
	    // palīgfunkcija funkcijai buildCodeTable
	    System.out.println("HuffmanTableHelper");
    }

   
    private static void encode(Reader in, Writer out, String[] codeTable) throws IOException {
	    // atgriež kodēto virkni saskaņā ar Huffman koda koku un kodu tabulu.
	    System.out.println("HuffmanCompressionHelper");
    }

    public static void compress(String inputFile, String outputFile) throws IOException {
	    // saspiež avota tekstu, izmantojot Huffman algoritmu
	    System.out.println("HuffmanCompression");
	    //encode();
    }

    public static void decompress(String inputFile, String outputFile) throws IOException {
	    // dekomprese saspiestu failu tā sākotnējā formātā. 
            System.out.println("HuffmanDecompression");
	    //decode();
    }

   
    private static void decode(InputStream in, Writer out, Node root, String[] codeTable) throws IOException {
	    // palig metode Huffaman dekompresijai
	    System.out.println("HuffmanDecompressionHelper");
        
    }
}
