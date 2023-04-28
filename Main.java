// 221RDB202 Edvards Bārtulis
// 221RDB200 Anželika Krasiļņikova
// 221RDB198 Anastasija Ostrovska

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.InputStream;
import java.math.BigInteger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;


public class Main {


	public static void main(String[] args) throws IOException{
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

	public static void comp(String sourceFile, String resultFile) throws IOException {
		int method=1; 	// change to see different methods, 
						// change decomp too! 
		switch (method){
		case 1: // Anželika Krasiļņikova - LZSS
			LZSS lz= new LZSS(sourceFile,resultFile);
			lz.compress();
			break;
		case 2: // Anastasija Ostrovska - Huffman
			Huffman Hf = new Huffman();
   			File infile = new File(sourceFile);
    		File outfile = new File(resultFile);
			Hf.Huffman_encoding(infile, outfile);
		case 3: // Edvards Bārtulis - LZ77
			ArrayList<LZ77.LZ77Token> compressed = LZ77.compress(input);
        	LZ77.writeToFile(compressed, resultFile);
			break;
		case 4: // LZ77 + Huffman
			ArrayList<LZ77.LZ77Token> compressed = LZ77.compress(input);
        	LZ77.writeToFile(compressed, resultFile+".temporaryFile");

			Huffman Hf = new Huffman();
   			File infile = new File(resultFile+".temporaryFile");
    		File outfile = new File(resultFile);
			Hf.Huffman_encoding(infile, outfile);

			File f = new File(resultFile+".temporaryFile");
			f.delete();
			break;
		case 5: // LZSS + Huffman - the best!
			LZSS lz= new LZSS(sourceFile,resultFile+".temporaryFile");
			lz.compress();

			Huffman Hf = new Huffman();
   			File infile = new File(resultFile+".temporaryFile");
    		File outfile = new File(resultFile);
			Hf.Huffman_encoding(infile, outfile);

			File f = new File(resultFile+".temporaryFile");
			f.delete();
			break;
		default:
			System.out.println("wrong method");
		}
	}

	public static void decomp(String sourceFile, String resultFile) throws IOException{
		int method=1;	// change to see different methods, 
						// change comp too! 
		switch (method){
		case 1: // Anželika Krasiļņikova - LZSS
			LZSS lz= new LZSS(sourceFile,resultFile);
			lz.decompress();
			break;
		case 2: // Anastasija Ostrovska - Huffman
			Huffman Hf = new Huffman();
    		File infile = new File(sourceFile);
    		File outfile = new File(resultFile);
			Hf.Huffman_decoding(infile, outfile);
			break;
		case 3: // Edvards Bārtulis - LZ77
			Gzip gz= new Gzip(sourceFile,resultFile);
			gz.mainGz("decomp");
			break;
		case 4: // LZ77 + Huffman
			Huffman Hf = new Huffman();
   			File infile = new File(sourceFile);
    		File outfile = new File(sourceFile+".temporaryFile");
			Hf.Huffman_decoding(infile, outfile);

			ArrayList<LZ77.LZ77Token> compressedFromFile = LZ77.readCompressedFromFile(sourceFile+".temporaryFile");
        	String decompressed = LZ77.decompress(compressedFromFile);
        	LZ77.writeToDecompressedFile(decompressed, resultFile);

			File f = new File(sourceFile+".temporaryFile");
			f.delete();
			break;
		case 5: // LZSS + Huffman - the best!
			Huffman Hf = new Huffman();
   			File infile = new File(sourceFile);
    		File outfile = new File(sourceFile+".temporaryFile");
			Hf.Huffman_decoding(infile, outfile);

			LZSS lz= new LZSS(sourceFile+".temporaryFile",resultFile);
			lz.decompress();

			File f = new File(sourceFile+".temporaryFile");
			f.delete();
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
class LZSS{
	private String sourceFile, resultFile;
	private int bufferSize1,bufferSize2;
	byte[] buffer1, buffer2;
    
	public LZSS(String sfile, String rfile) {
		sourceFile=sfile;
		resultFile=rfile;
		bufferSize1=4096;
        bufferSize2=4096;
		//System.out.println("lz77 inicializācija");
		
	}
	public void compress() throws IOException{
		//System.out.println("saspiešana");
		FileInputStream input;
		try {		
			input = new FileInputStream(this.sourceFile);
		
		}catch(FileNotFoundException ex){
			System.out.println("File does not exist");
	        return;
	    }
		// modificē bufera lielumu, ja fails ir mazāks 
		this.bufferSize1 = Math.min(this.bufferSize1,input.available()/2+input.available()%2);
        if (bufferSize1<bufferSize2) this.bufferSize2 = this.bufferSize1-input.available()%2;
		this.buffer1 = new byte[this.bufferSize1];
		// nolasa pirmos datus
        input.read(this.buffer1);
        this.buffer2 = new byte[this.bufferSize2];
        input.read(this.buffer2);
        
		FileOutputStream output = new FileOutputStream(this.resultFile);
		byte[] outbuf = new byte[32]; //izvades buferis
        
	 	
		byte outIndex=0; // izseko izvades bufera poziciju
		byte flag=0; // izseko karogus (atsauce vai vienkaršs baits)
		byte flagIndex=0; // skaita baitus lidz 8, lai pievenotu karogus
		int position=0; // tagadeja saspiešanas pozicija 
        
		while (true) {
            // meklē atkartojumus 
			byte[] pointer = findMatch(position);
            
			if (pointer==null) {
                // ja atkartojumu nav, pievieno 1 simbolu
				outbuf[outIndex]=bufferGet(position);
				outIndex++;
				flagIndex++;
				position++;
                
			} else {
				// ja atkartojums ir atrasts, pievieno atsauci (length; distance)
				for (byte b:pointer) {
					outbuf[outIndex]=b;
					outIndex++;
				}
				flagIndex++;
				flag |= 1<<(8-flagIndex); // piešķir vieninieku bitam taja pozicija kur jābut atsauce
				
				position +=(pointer[0] & 127); // iegūt īsto length vērtību 
				
			}
			
			if (flagIndex==8) {
				// kad karoga baits ir aizpildīts, izvada to un saglabāto izvades buferi 
				output.write(flag);
				output.write(outbuf,0,outIndex);
                
				flag=0;
				outIndex=0;
				flagIndex=0;
			}
			
			if (position>this.bufferSize1*1.5 && input.available()!=0){
				// kad saspieāšanas pozicija parsniedz bufera x1.5 lielumu,
				// pirmais buferis tiek saglabāts, un nolasīta jauna informācija otrā buferī
				position-=this.bufferSize1;
				this.buffer1=this.buffer2;
				if (input.available() < this.bufferSize2){ // ja atlikušais lielums parāk mazs
					this.bufferSize2 = input.available();
				}
				this.buffer2 = new byte[this.bufferSize2];
				input.read(this.buffer2);
				//System.out.println("left: "+input.available());
            		}
            
			if((position >= this.bufferSize1+this.bufferSize2) && input.available()==0) break;
		}
        
		if (flagIndex>0) { // pievieno atlikušus baitus
            		output.write(flag);
			output.write(outbuf,0,outIndex);
		}
        
		input.close();
		output.close();
		System.out.println("done");
	}
	
	public void decompress() throws IOException {
		//System.out.println("dekompresija");
		FileInputStream input;
		try {
			input = new FileInputStream(this.sourceFile);
		}catch(FileNotFoundException ex){
	        System.out.println("File does not exist");
	        return;
	    }
		

		FileOutputStream output = new FileOutputStream(this.resultFile);

		this.bufferSize1=this.bufferSize1*2; //palielinā uzmeru izmēru lai parliecinatos, ka 
		this.bufferSize2=this.bufferSize1;
		this.buffer1 = new byte[this.bufferSize1]; 
        this.buffer2 = new byte[this.bufferSize2];
        
		int outIndex=0; // izseko izvades bufera poziciju
		byte flagIndex=1; // izseko karogu pozīciju
		byte flag=(byte)input.read(); // izseko karogus (atsauce vai vienkaršs baits)
		int distance = 0;
		int length = 0;
        

		while (true) {
			
			if ((flag & 1<<(8-flagIndex)) > 0) {
				// ja karoga bits sakrīt ar 1, nolasa atsauci
				length = input.read();
				if ((length & 1<<7) >0){ // ja pirmais bits ir 1, kopējais atsauces izmērs ir 4 baiti.
					// nolasa un atgriež īsto int vertību
					length &= 127; 
					distance = input.read();
					distance = distance<<8 | input.read();
					
				} else distance = input.read();
				
				for (int i=0;i<length;i++) {
					// kopē baitus uz kuriem norāda atsauce
				    	if (outIndex<this.bufferSize1){
    				    	this.buffer1[outIndex]=this.buffer1[outIndex-distance];
                    		} else {
                        		this.buffer2[outIndex-this.bufferSize1]=bufferGet(outIndex-distance);
                    		}
					outIndex++;
				}
				
			}else {
                if (outIndex<this.bufferSize1){
				 	this.buffer1[outIndex]=(byte)input.read();
                } else {
                    this.buffer2[outIndex-this.bufferSize1]=(byte) input.read();
                }
				outIndex++;
			}
			flagIndex++;
			
			
			if (flagIndex>8) {
				// nolasa jaunu karogu
				flag=(byte)input.read();
				flagIndex=1;
			}

            	if (outIndex>this.bufferSize1*1.5 && input.available()!=0){
					// kad izvades pozicija parsniedz bufera x1.5 lielumu,
					// pirmais buferis tiek saglabāts un atbrivots otrais
					outIndex-=this.bufferSize1;
					output.write(buffer1);
					this.buffer1=this.buffer2;
					this.buffer2 = new byte[this.bufferSize2];
					//System.out.println(input.available());
            	}
            
			if(input.available()==0) break;

		}
		if (outIndex<this.bufferSize1){ // izvāda atlikušus baitus
            		output.write(buffer1,0,outIndex);
		} else{
		    	output.write(buffer1);
		    	output.write(buffer2,0,outIndex-this.bufferSize1);
		}
		
		input.close();
		output.close();
		System.out.println("done");
	}
	private byte bufferGet (int pos) {
		// palīdz izsekot buferus 
		// atgriež vertību no pirmā vai otrā bufera
		if (pos>=this.bufferSize1) {
			return this.buffer2[pos-this.bufferSize1];
		} else return this.buffer1[pos];
    }
    
	private byte[] findMatch (int startPosition) {
		// atgriež atkartojuma atsauci
		
		int maxDistance = 0;
		int maxLength = 0;
        	int bufferSize = this.bufferSize1+this.bufferSize2-1;
        
		// meklē  garāko atkartojumu
		for (int i = 0; i < startPosition; i++) {
		    	int length = 0;
		    	// iegūt atkartojuma garumu
		    	while (startPosition + length < bufferSize && bufferGet(startPosition + length) == bufferGet(i + length) ) {
				length++;
				//System.out.println(i+" "+length+" "+ buffer[startPosition + length]+"=" + buffer[i + length] );
		    	}
		    	if (length >= maxLength && length>=3 && length<127) {
				maxLength = length;
				maxDistance = startPosition - i;
		    	}
		}
	
		if (maxLength==0) {
			return null; // atgriež null, ja garums ir mazāks par 3
		} else {
			byte[] pointer;
			if (maxDistance>255) {
				// ja vērtība ir parāk liela, ieraksta skaitļus divos baitos
				pointer = new byte[3];
				pointer[0] = (byte) maxLength;
				pointer[1] = (byte) ((maxDistance & 0xFF00) >> 8);
				pointer[2] = (byte) ((maxDistance & 0xFF) >> 0);
				
				pointer[0]=(byte) (pointer[0] | 1<<7); // piešķir pirmām bitam "1" vertību, kas norāda atsauces baitu garumu 
			} else {
				pointer = new byte[2];
				pointer[0]=(byte)maxLength;
				pointer[1]=(byte)maxDistance;
			}
			//System.out.println(maxDistance+" "+maxLength);
			return pointer;
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
class LZ77 {
    
    private static final int DICTIONARY_BUFFER_SIZE = 255;
    private static final int MAX_MATCH_LENGTH = 255;
    
        
    public static String readFromFile(String filename) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return result.toString();
    }


    
    public static void writeToFile(ArrayList<LZ77Token> compressed, String filename) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            for (LZ77Token token : compressed) {
                dos.writeByte(token.getIndex());
                dos.writeByte(Math.min(token.getLength(), MAX_MATCH_LENGTH));
                dos.write(new String(new char[]{token.getCharacter()}).getBytes("UTF-16BE"));


            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    public static void writeToDecompressedFile(String output, String filename) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"))) {
            writer.write(output);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static ArrayList<LZ77Token> readCompressedFromFile(String filename) {
        ArrayList<LZ77Token> result = new ArrayList<LZ77Token>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            while (dis.available() > 0) {
                int b1 = dis.readUnsignedByte();
                int b2 = dis.readUnsignedByte();
                char c = dis.readChar();


                result.add(new LZ77Token(b1, b2, c));
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return result;
    }

    
    public static ArrayList<LZ77Token> compress(String input) {
        ArrayList<LZ77Token> result = new ArrayList<LZ77Token>();
        char[] inputChars = input.toCharArray();

        int i = 0;
        while (i < inputChars.length) {
            int matchIndex = 0;
            int matchLength = 0;
            for (int j = 1; j <= Math.min(MAX_MATCH_LENGTH, inputChars.length - i); j++) {
                boolean foundMatch = false;
                for (int k = Math.max(0, i - DICTIONARY_BUFFER_SIZE); k < i; k++) {
                    int l = 0;
                    while (l < j && inputChars[k + l] == inputChars[i + l]) {
                        l++;
                    }
                    if (l == j) {
                        matchIndex = i - k;
                        matchLength = j;
                        foundMatch = true;
                        break;
                    }
                }
                if (!foundMatch) {
                    break;
                }
            }
            char nextChar = (i + matchLength < inputChars.length) ? inputChars[i + matchLength] : '\0';
            result.add(new LZ77Token(matchIndex, matchLength, nextChar));
            i += matchLength + 1;
        }
        
        return result;
    }
    
    public static String decompress(ArrayList<LZ77Token> tokens) {
        StringBuilder result = new StringBuilder();
        for (LZ77Token token : tokens) {
            int startIndex = result.length() - token.getIndex();
            if (startIndex < 0) {
                startIndex = 0;
            }
            int endIndex = startIndex + token.getLength();
            if (endIndex <= result.length()) {
                result.append(result.substring(startIndex, endIndex));
            } else {
                for (int i = startIndex; i < result.length() && i < endIndex; i++) {
                    result.append(result.charAt(i));
                }
            }
            if (token.getCharacter() != '\0') {
            
            	result.append(token.getCharacter());

	

            }
        }
        return result.toString();
    }


    
    public static class LZ77Token {
        
        private int index;
        private int length;
        private char character;
        
        public LZ77Token(int index, int length, char character) {
            this.index = index;
            this.length = length;
            this.character = character;
        }
        
        public int getIndex() {
            return index;
        }
        
        public int getLength() {
            return length;
        }
        
        public char getCharacter() {
            return character;
        }
        
    }
    
}
