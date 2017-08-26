import java.io.*;
public class HuffmanOutputStream extends BitOutputStream {
    private int b;
    private int count = 0;
    
    public HuffmanOutputStream(String filename, String tree, int totalChars) {
        //add additional private variables and methods as needed
        super(filename);
        try {
            d.writeUTF(tree);
            d.writeInt(totalChars);
        }
        catch (IOException e) {
        }
    }
    
    public void writeBit(int bit) {
        //PRE bit == 0 || bit == 1
        //Writes a bit to the file
        //Bits must be packed 8 to a byte before writing to the file
        
        b = b*2 + bit;
        count++;
        
        if(count == 8) {
            try {
                d.writeByte(b);
                b = 0;
                count = 0;
            }
            catch(IOException e) {
            }
        }
        
    }

    public void close() {
        //be sure to write any partial byte to the file before closing
        
        while(count < 8) {
            b = b*2;
            count++;
        }
        
        try {
            d.writeByte(b);
            d.close();
        }
        catch(IOException e) {
        }
    }
    
    public static void main(String[] args) {
        HuffmanOutputStream h = new HuffmanOutputStream("test.txt", "dcab", 10);
        
        h.writeBit(1);
        h.writeBit(1);
        h.writeBit(0);
        h.writeBit(1);
        h.writeBit(1);
        h.writeBit(1);
        h.writeBit(1);
        h.writeBit(1);
        
        h.writeBit(1);
        h.writeBit(1);
        h.writeBit(0);
        h.writeBit(1);
        h.writeBit(0);
        h.writeBit(1);
        h.writeBit(0);
        h.writeBit(0);
        
        h.writeBit(0);
        h.writeBit(0);
        h.writeBit(0);
        
        h.close();
    }
}