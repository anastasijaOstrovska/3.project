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

class Huffman {
	
	Boolean eof;
	
	public Huffman(){eof = false;}
  
// main function for compress 
	public void Huffman_encoding(File f, File outfile){
		try{
			File infile = f;
			int[] chFreqs = countFrequency(infile);
			HuffmanTree<Character> huffTree = buildTreeCoding(chFreqs);
			HashMap<Character,String> codeMap = buildMapCoding(new HashMap<Character,String>(), huffTree, new StringBuilder());
			writeFileCoding(huffTree,codeMap, chFreqs[256], outfile, infile);
		}catch(Exception e){
			System.out.println("error");
		}
	}
//function countfrequency - count every characters frequency in file 	
	private int[] countFrequency(File infile){
		int[] chFreqs = new int[257];
    byte[] bytes = new byte[(int) infile.length()];
    // reads file byte by byte and add to chFreqs in ASCII order
    try{
      FileInputStream fis = new FileInputStream(infile);
      fis.read(bytes);
      for(int i = 0; i < bytes.length; i++){
        chFreqs[bytes[i]& 0xff]++;
				chFreqs[256]++;
      }
			fis.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return chFreqs;
	}

// makes codebook by going throught huffman tree
  	private HashMap<Character, String> buildMapCoding(HashMap<Character,String> codeMap, HuffmanTree<Character> huffTree, StringBuilder code){
		if (huffTree.symbol != null){
			codeMap.put(huffTree.symbol,code.toString());
		} else {

			code.append(0);
			codeMap = buildMapCoding(codeMap, huffTree.left, code);
			code.deleteCharAt(code.length()-1);
			

			code.append(1);
			codeMap = buildMapCoding(codeMap, huffTree.right, code);
			code.deleteCharAt(code.length()-1);
		}
		return codeMap;
	}
// make huffman tree from character frequencies array
	private HuffmanTree<Character> buildTreeCoding(int[] chFreqs){
		PriorityQueue<HuffmanTree<Character>> huffQueue = new PriorityQueue<HuffmanTree<Character>>();
		HuffmanTree<Character> tempTree, leftTree, rightTree;
		for(int i = 0; i < chFreqs.length -1 ; i++){
			if(chFreqs[i] > 0){
				tempTree = new HuffmanTree<Character>((char) i , chFreqs[i]);
				huffQueue.offer(tempTree);// insert new values to queue
			}
		}
    // until priority queue has only one element makes combination of nodes 
		while(huffQueue.size() > 1){
			leftTree = huffQueue.poll();
			rightTree = huffQueue.poll();
			tempTree = new HuffmanTree<Character>(leftTree, rightTree);
			huffQueue.offer(tempTree);
		}
		return huffQueue.poll();
	}

// writes to file hufman codebook by recoursively going throught all tree
	private void writeHeader(BinaryWriter output, HuffmanTree<Character> huffTree){
		if(huffTree.symbol == null){
			output.write(0);
			if(huffTree.left != null)  writeHeader(output,huffTree.left);
			if(huffTree.right != null) writeHeader(output,huffTree.right);
		}else{
			output.write(1);
			Integer symbol = (int) huffTree.symbol.charValue();
			output.writeByte( Integer.toBinaryString(symbol) );
		}
	}
	
// writes to fail by replacing all characters from input file by codes from codebook 
	private void writeFileCoding(HuffmanTree<Character> huffTree, HashMap<Character,String> codeMap, int textLength, File outfile, File infile){
    String code;
		try{
			FileWriter decode = new FileWriter(outfile);
			BinaryWriter output = new BinaryWriter ( new FileOutputStream(outfile, true) );
			
			writeHeader(output, huffTree);
			output.write(1); //making boarder to separate codebook and text file 
			output.writeByte("0");
			byte[] bytes = new byte[(int) infile.length()];

      FileInputStream fis = new FileInputStream(infile);
      fis.read(bytes);
      for(int i = 0; i < bytes.length; i++){
          int b = bytes[i]& 0xff;
					code = codeMap.get((char)b);
					for( char bit : code.toCharArray() ){
						output.write((int) (bit - 48)); //writing in ASCII symbols 
					}
      }
			fis.close();
			output.close();
      decode.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	
	
}

// main function for decompression
	public void Huffman_decoding(File f, File outfile){
		try{
			File infile = f;
			BinaryReader bitreader = new BinaryReader( new FileInputStream(infile) );
			bitreader.readByte();
			HuffmanTree<Character> huffTree = buildTree(bitreader);
			HashMap<String,Character> codeMap = buildMap(new HashMap<String,Character>(), huffTree, new StringBuilder());
			bitreader.readByte(); bitreader.read();
			writeFile(outfile, bitreader, codeMap);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
// by reading file makes huffman tree 
	private HuffmanTree<Character> buildTree(BinaryReader bitreader){
		try{
			int bit = 0;
			int symbol;
			do{
				bit = bitreader.read();
				if(bit == 1){
					symbol = bitreader.readByte();
					if(symbol > 0){
						return new HuffmanTree<Character>((char) symbol, 0);
					}else{
						eof = true;
					}
				}else if (bit == 0){
					HuffmanTree<Character> leftTree = buildTree(bitreader);
					HuffmanTree<Character> rightTree = buildTree(bitreader);
					return new HuffmanTree<Character>(leftTree,rightTree);
				}
			}while(bit!=-1 && eof!=true);
		}catch(Exception e){
			e.printStackTrace();
		}
		return new HuffmanTree<Character>((char) 0,0);
	}
	
// makes codebook from huffman tree
	private HashMap<String,Character> buildMap(HashMap<String,Character> codeMap, HuffmanTree<Character> huffTree, StringBuilder code){
		if (huffTree.symbol != null){
			codeMap.put(code.toString(),huffTree.symbol);
		} else {
      
			code.append(0);
			codeMap = buildMap(codeMap, huffTree.left, code);
			code.deleteCharAt(code.length()-1);
			
			code.append(1);
			codeMap = buildMap(codeMap, huffTree.right, code);
			code.deleteCharAt(code.length()-1);
		}
		
		return codeMap;
	}
	
// decodes main text using created codebook and writes bytes to file 
	private void writeFile(File outfile, BinaryReader bitreader,HashMap<String, Character> codeMap){
  StringBuilder code = new StringBuilder();
		StringBuilder decoded = new StringBuilder();
		int bit = bitreader.read();
		do{
			code.append(bit);
			if (codeMap.containsKey(code.toString())){
				decoded.append(codeMap.get(code.toString()));
				code.setLength(0);
			}
			bit = bitreader.read();
		}while(bit!=-1);
  try {
    byte [] b = new byte[decoded.length()];
    for(int i = 0; i < decoded.length(); i++){
      Integer myValue = Integer.valueOf((int)decoded.charAt(i));  
      b[i] = (myValue.byteValue());
    }
    FileOutputStream outputStream = new FileOutputStream(outfile);
    outputStream.write(b);
		outputStream.close();
	}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
// special class for huffman tree, constructors for huffman tree 
class HuffmanTree<Symbol> implements Comparable<HuffmanTree<Symbol>>{
	
	final Symbol symbol;
	final Integer frequency;
	final HuffmanTree<Symbol> left, right;
	
	HuffmanTree(Symbol symbol, Integer frequency){
		this.symbol = symbol;
		this.frequency = frequency;
		this.left = null;
		this.right = null;
	}
	
	HuffmanTree(HuffmanTree<Symbol> left, HuffmanTree<Symbol> right){
		symbol = null;
		this.left = left;
		this.right = right;
		frequency = left.frequency + right.frequency;
	}

	public int compareTo(HuffmanTree<Symbol> tree ) {
		return this.frequency - tree.frequency;
	}
	

}
// class which allows to write file byte by byte 
class BinaryWriter {

	private OutputStream output;
	private int currentBit, theByte;
	
	public BinaryWriter(OutputStream out){
		try{
			output = out;
			currentBit = 0;
			theByte = 0;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void write(int bit){
		try{
			if( (bit != 0 && bit != 1)){
			   throw new IllegalArgumentException();
			}
			theByte = theByte << 1 | bit;
			currentBit++;
			if(currentBit == 8){
				output.write(theByte);
				currentBit = 0;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void writeByte(String b){
		try{
			char[] bits = b.toCharArray();
			if (bits.length > 8){
				throw new IndexOutOfBoundsException();
			}
			for( int i=0; i<8-bits.length; i++){
				write(0);
			}
			for( int i=0 ; i<bits.length ; i++){
				write((int) (bits[i]-48)); //makes to ASCII table 
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void close(){
		try{
			while (currentBit != 0){
				write(0);
			}
			output.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
//class which allows to read file byte by byte 
class BinaryReader {

	private InputStream input;
	private int currentBit, theByte;
	
	public BinaryReader(InputStream in){
		try{
			input = in;
			currentBit = 0;
			theByte = 0;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int read(){
		try{
			if(currentBit == 8){
				theByte = input.read();
				if(theByte == -1){
					return -1;
				}
				currentBit = 0;
			}
			currentBit++;
		}catch(Exception e){
			e.printStackTrace();
		}
		return (theByte >>> (8-currentBit)) & 1;
	}
	
	public int readByte(){
		int result = 0;
		int b;
		try{
			for(int i=7 ; i>=0 ; i--){
				b = read();
				if(b == -1){
					break;
				}

				result+=b*Math.pow(2, i);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
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
