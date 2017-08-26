import java.io.*;
import java.util.*;

public class HuffmanDecode {
    private HuffmanInputStream hIn;
    private String[] paths = new String[128];
    private int charCount = 0;
    public HuffmanDecode(String in, String out) {
        //implements the Huffman Decode Algorithm
        //Add private methods and instance variables as needed
        
        //steps:
            //1: read in compressed file
            //2: re-create the huffman tree
            //3: read the file one bit at a time while going through paths
        
        hIn = new HuffmanInputStream(in); //1
        String tree = hIn.getTree();       
        
        HuffmanTree t = new HuffmanTree(tree, (char)128); //2
        decode(t, out); //3
    }
    
    private void decode(HuffmanTree t, String out) {
        //while num chars written is less than total characters
        
        try {            
            FileWriter fout = new FileWriter(out);
            BufferedWriter writer = new BufferedWriter(fout);
            t.moveRoot();
            
            while(charCount < hIn.totalChars()) {
                if(t.atLeaf()) {
                    writer.write(t.current());
                    charCount++;
                    t.moveRoot();
                } else {
                    if(hIn.readBit() == 0) {
                        t.moveLeft();
                    } else {
                        t.moveRight();
                    }
                }
            }     
            
            writer.close();
        }
        catch(FileNotFoundException e) {
        }
        catch(IOException e) {
        }
    }

    public static void main(String args[]) {
        //args[0] is the name of a file created by Huffman Encode
        //args[1] is the name of the output file for the uncompressed file
        new HuffmanDecode(args[0], args[1]);
    }
}