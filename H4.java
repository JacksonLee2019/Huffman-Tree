//Jackson Lee

public class H4<T extends Comparable<? super T>> {
    //heap rule: the parent value (the key) is less than or equal to the children
    
    T keys[]; //the values used to order the heap
    Object data[];//additional data associated with the keys
                  //the data associated with the key in position
                  //i is stored in position i of data
    
    int maxChildren;
    int size;
    
    public H4(int s, int m) {
        keys = (T[])new Comparable[s + 1];//this will generate a warning
        data = new Object[s + 1];
        maxChildren = m;
        size = 0;
    }
    
    public T getMinKey() {
        //PRE: !empty()
        //returns the smallest key value in the heap
        
        return keys[0];
    }
    
    public Object getMinData() {
        //PRE: !empty()
        //returns the data associated with the smallest key value in the heap
        //since keys might not be unique the data returned should be the data 
        //that would be removed if remove was called instead of getMinData()
        
        return data[0];
    }
    
    public void removeMin() {
        //PRE: !empty()
        //removes the smallest key and associated data from the heap
        size--;
        T tempK = keys[size];
        Object tempD = data[size];       
        int parent = 0;
        int child = 1;
        int smallest = 0;
        
        while(child < size) {
            smallest = child;
            parent = (child - 1)/maxChildren;
            
            for(int i = child + 1; i < size && i < child + maxChildren; i++) {
                if(keys[i].compareTo(keys[smallest]) < 0) {
                    smallest = i;
                }
            }
            
            child = smallest;
            
            if(tempK.compareTo(keys[child]) < 0) {
                break;
            } else {
                keys[parent] = keys[child];
                data[parent] = data[child];
                child = (maxChildren*child) + 1;   
            }
        } 
        
        parent = (child - 1)/maxChildren;
        
        keys[parent] = tempK;
        data[parent] = tempD;
    }
    
    public void insert(T k, Object d) {
        //PRE: !full()
        //inserts a new key and associated data into the heap
        
        int parent;
        int child;
        size++;
        
        child = size - 1;           
        parent = (child - 1)/maxChildren;
        
        if(keys[parent] == null) {
            keys[child] = k;
            data[child] = d;
        } else {
            while(keys[parent].compareTo(k) > 0) {
                keys[child] = keys[parent];
                data[child] = data[parent];
                child = parent;
                parent = (child-1)/maxChildren;
                
                if(parent == child) {
                    break;
                }                   
            }
            
            keys[child] = k;
            data[child] = d;
        }
    }
    
    public boolean empty() {
        //return true when the heap is empty otherwise returnn false
        
        return size == 0;
    }
    
    public boolean full() {
        //return true when the heap is full otherwise return false
        
        return size == keys.length;
    }
    
    public int getSize() {
        //return the number of elements in the heap
        
        return size;
    }
    
    public static void main(String[] args) { 
        //tester
        
        H4 test = new H4(100, 2);
        
        for(int i = 0; i < 100; i++) {
            test.insert(i, i);
            System.out.println(test.keys[i]);
        }
        
        while(test.getSize() > 0) {
            System.out.println(test.getMinKey() + " " + test.getMinData());
            test.removeMin();
        }
   
    }
}