import java.util.*;

public class HuffmanTree {
    private class Node {
        private Node left;
        private char data;
        private Node right;
        
        private Node(Node L, char d, Node R) {
            left = L;
            data = d;
            right = R;
        }
    }

    private Node root;
    private Node current; //this value is changed by the move methods
    
    public HuffmanTree( ) {
        root = null;
        current = null;
    }

    public HuffmanTree(char d) {
        //makes a single node tree
        
        Node n = new Node(null, d, null);
        root = n;
        current = n;
    }

    public HuffmanTree(String t, char nonLeaf) {
        //Assumes t represents a post order representation of the tree as discussed in class
        //nonLeaf is the char value of the data in the non-leaf nodes
        
        Stack<Node> operands = new Stack<>();
        Node x;
        Node y;
        
        for(int i = 0; i < t.length(); i++) {
            if(t.charAt(i) != nonLeaf) {
                operands.push(new Node(null, t.charAt(i), null));
            } else {
                if(!operands.empty()) {
                    y = operands.pop();
                } else {
                    y = null;
                }

                x = operands.pop();
                operands.push(new Node(x, nonLeaf, y));
            }
            
            root = current;
        }  
        
        Node n = operands.pop();
        root = n;
        current = n;
    }

    public HuffmanTree(HuffmanTree b1, HuffmanTree b2, char d) {
        //makes a new tree where b1 is the left subtree and b2 is the right subtree
        //d is the data in the root
        
        Node n = new Node(b1.root, d, b2.root);
        root = n;
        current = n;
    }

    //use the methods moveRoot through current to help with the decoding process
    //the move methods change the value of current

    public void moveRoot() {
        current = root;
    }

    public void moveLeft() {
        current = current.left;
    }

    public void moveRight() {
        current = current.right;
    }

    public boolean atLeaf() {
        //returns true if current references a leaf otherwise returns false
        
        if(current.left == null && current.right == null) {
            return true;
        }
        
        return false;
    }

    public char current() {
        //returns the data value in the node referenced by current
        
        return current.data;
    }

    //uses this in the encoding process
    //the iterator returns the path (a series of 0s and 1s) to each leaf
    //it will be easier to construct all the paths when the iterator is created
    //add private methods and variables as needed
    public class PathIterator implements Iterator<String> {
        private ArrayList<String> list;
        
        public PathIterator() {
            list = new ArrayList<>();
            getPaths(root, "");
        }
                
        private void getPaths(Node r, String s) {
            if(r.left == null && r.right == null) {
                list.add(r.data + s);
                return;
            } else {
                getPaths(r.left, s + "0");
                getPaths(r.right, s + "1");
            }
        }

        public boolean hasNext() {
            return list.size() > 0;
        }

        public String next() {
            String s = list.remove(0);
            return s;
        }

        public void remove() {
            //optional method not implemented
        }
    }

    public Iterator<String> iterator() {
        //return a new iterator object        
        PathIterator i = new PathIterator();
        return i;
    }

    public String toString() {
        //returns a string representation of the tree using the format discussed in class
        return toPostOrder(root);
    }
    
    private String toPostOrder(Node r) {
        if(r.left == null && r.right == null) {
            return "" + r.data;
        } 
        
        return toPostOrder(r.left) + toPostOrder(r.right) + r.data;
    }
}