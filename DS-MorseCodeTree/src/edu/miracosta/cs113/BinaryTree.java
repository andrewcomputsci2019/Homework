package edu.miracosta.cs113;
import java.util.Arrays;
import java.util.Scanner;

public class BinaryTree<T>  {
        //implement methods in here
    private boolean empty = false;
    private Node<T> root;
    private BinaryTree<T> left;
    private BinaryTree<T> right;
    private static class Node<T> {
        private T value;
        public Node() {

        }
        public Node(T value) {
            this.value = value;
        }
        public T getValue(){
            return value;
        }

        @Override
        public String toString() {
            return value==null?"null\n": value +"\n";
        }
    }
    public BinaryTree(){
        //this is only used to check if the tree was called in a default way
        empty =true;
    }
    public BinaryTree(T value, BinaryTree<T> left,BinaryTree<T> right){
        this.root= new Node<>(value);
        this.left = left;
        this.right = right;
    }
    public static BinaryTree<String> readBinaryTree(Scanner scanner){
        String temp = scanner.nextLine();
        if(temp.replaceAll(" ","").equalsIgnoreCase("null")){
            return null;
        }
        return new BinaryTree<>(temp.replaceAll(" ",""), readBinaryTree(scanner), readBinaryTree(scanner));
    }
    public BinaryTree<T> getRightSubtree(){
        return right;
    }
    public T getData(){
        return root.getValue();
    }
    public BinaryTree<T> getLeftSubtree(){
        return left;
    }
    public boolean isLeaf(){
        if(empty){
            throw new NullPointerException("tree is empty");
        }
        return left==null&&right==null;
    }

    @Override
    public String toString() {
        int depth =1;
        if(empty){
            return "null\n";
        }else{
            StringBuilder ans = new StringBuilder(root.toString());
            if(left==null){
                ans.append(" ").append("null").append("\n");
            }else{
                ans.append(left.toString(depth));
            }
            if(right==null){
                ans.append(" ").append("null").append("\n");
            }else{
                ans.append(right.toString(depth));
            }
            return ans.toString();
        }
    }
    public String toString(int depth){
        Character[] array = new Character[depth];
        Arrays.fill(array,' ');//fills the char array with spaces
        StringBuilder builder = new StringBuilder();
        for(Character character: array){
            builder.append(character);
        }
        builder.append(root.toString());
        if(left==null){
            for(Character character: array){
                builder.append(character);
            }
            builder.append(" ").append("null").append("\n");
        }else{
            builder.append(left.toString(depth+1));
        }
        if(right==null){
            for(Character character: array){
                builder.append(character);
            }
            builder.append(" ").append("null").append("\n");
        }else{
            builder.append(right.toString(depth+1));
        }
        return builder.toString();
    }
    public void setLeft(BinaryTree<T> left){
        if(this.left==null && left!=null){
            this.left= left;
            empty=false;
        }
    }
    public void setRight(BinaryTree<T> right){
        if(this.right==null && right!=null){
            this.right= right;
            empty=false;
        }
    }
    public void setValue(T value){
        if(value!=null) {
            root = new Node<>(value);
            empty = false;
        }
    }
}
