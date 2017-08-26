import java.io.*;
public class HuffmanInputStream extends BitInputStream {

    private String tree;
    private int totalChars;
    private int count = 0;
    private int b;
    private int pos;
    private int[] bits = new int[8];
    
    public HuffmanInputStream(String filename) {
        //add additional private variables and methods as needed
        super(filename);
        try {
            tree = d.readUTF();
            totalChars = d.readInt();
        }
        catch (IOException e) {
        }
    }


    public int readBit() {
        //returns the next bit in the file
        
        if(count == 0) {
            try {
                b = d.readUnsignedByte();
                
                for(int i = 0; i < 8; i++) {
                    bits[i] = b%2;
                    b = b/2;
                }
            }
            catch(IOException e) {
            }
        }
        
        count++;
        
        pos = bits.length - count;
        
        if(count == 8) {
            count = 0;
        }
        
        return bits[pos];
    }

    public String getTree() { 
        return tree;
    }

    public int totalChars() {  
        return totalChars;
    }

    public void close() {  
        try {
            d.close();
        }
        catch (IOException e) {
        }
    }
    
    public static void main(String[] args) {
        HuffmanInputStream h = new HuffmanInputStream("test.txt");
        
        System.out.println(h.getTree());
        System.out.println(h.totalChars());
        
        for(int i = 0; i < 19; i++) {
            System.out.print(h.readBit() + " ");
        }
        h.close();
    }
}