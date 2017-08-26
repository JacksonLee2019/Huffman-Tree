import java.io.*;
import java.util.*;

public class HuffmanEncode {
    private H4 h = new H4(128, 2);
    private int[] frequency = new int[128];
    private char[] characters = new char[128];
    private String[] paths = new String[128];
    private int charCount = 0;
    private HuffmanOutputStream hOut;
    
    public HuffmanEncode(String in, String out) {
        //Implements the huffman encoding algorithm
        //Add private methods and instance variables as needed
        
        //create postfix representation of the tree
        //give that postfix representation to the pathiterator to generate the paths
        
        getFrequency(in);
        HuffmanTree t = makeHuffmanTree();       
        hOut = new HuffmanOutputStream(out, t.toString(), charCount);
        getPaths(t);     
        encode(in);
    }
    
    private void encode(String s) {
        try {
            FileReader fin = new FileReader(s);
            BufferedReader reader = new BufferedReader(fin);
            int character = reader.read();
            
            while(character != -1) {   
                String path = paths[character];
                
                for(int i = 0; i < path.length(); i++) {
                    if(path.charAt(i) == '0') {
                        hOut.writeBit(0);
                    }
                    
                    if(path.charAt(i) == '1') {
                        hOut.writeBit(1);
                    }
                }
                character = reader.read();
            }
            hOut.close();
            reader.close();
        }
        catch(FileNotFoundException e) {
        }
        catch(IOException e) {
        }
    }
    
    private void getPaths(HuffmanTree t) {
        Iterator i = t.iterator();
        
        while(i.hasNext()) {
            String s = (String)i.next(); 
            paths[s.charAt(0)] = s.substring(1);
        }
    }
    
    private void getFrequency(String s) {
        try {
            FileReader fin = new FileReader(s);
            BufferedReader reader = new BufferedReader(fin);
            int character = reader.read();
            
            while(character != -1) {                               
                frequency[character]++;
                characters[character] = (char)character;
                charCount++;
                character = reader.read();
            }
            
            reader.close();
            
            for(int i = 0; i < 128; i++) {
                if(frequency[i] != 0) {
                    h.insert(frequency[i], new HuffmanTree(characters[i]));
                }
            }
        }
        catch(FileNotFoundException e) {
        }
        catch(IOException e) {
        }
    }
        
    private HuffmanTree makeHuffmanTree() {
        while(h.getSize() > 1) {       
            HuffmanTree t1 = (HuffmanTree)h.getMinData();
            int f1 = (int)h.getMinKey();
            h.removeMin();
            
            HuffmanTree t2 = (HuffmanTree)h.getMinData();
            int f2 = (int)h.getMinKey();
            h.removeMin();
            
            int f = f1 + f2;
            h.insert(f, new HuffmanTree(t1, t2, (char)128));
        }
        
        return (HuffmanTree)h.getMinData();
    }

    public static void main(String args[]) {
        //args[0] is the name of the file whose contents should be compressed
        //args[1] is the name of the output file that will hold the compressed content if the input file
        new HuffmanEncode(args[0], args[1]);
    }
}