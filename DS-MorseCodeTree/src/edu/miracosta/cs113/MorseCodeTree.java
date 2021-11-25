package edu.miracosta.cs113;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * MorseCodeTree : A BinaryTree, with Nodes of type Character to represent each letter of the English alphabet,
 * and a means of traversal to be used to decipher Morse code.
 *
 * @version 1.0
 */
public class MorseCodeTree extends BinaryTree<Character> {

    private static String[] asciLookUp = null; //subtract each value by 97
    public MorseCodeTree(){
        try {
            readMorseCodeTree();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // TODO:
    // Build this class, which includes the parent BinaryTree implementation in addition to
    // the `translateFromMorseCode` and `readMorseCodeTree` methods. Documentation has been suggested for the former,
    // where said exceptional cases are to be handled according to the corresponding unit tests.

    /**
     * Non-recursive method for translating a String comprised of morse code values through traversals
     * in the MorseCodeTree.
     *
     * The given input is expected to contain morse code values, with '*' for dots and '-' for dashes, representing
     * only letters in the English alphabet.
     *
     * This method will also handle exceptional cases, namely if a given token's length exceeds that of the tree's
     * number of possible traversals, or if the given token contains a character that is neither '*' nor '-'.
     *
     * @param morseCode The given input representing letters in Morse code
     * @return a String representing the decoded values from morseCode
     */
    public String translateFromMorseCode(String morseCode) throws Exception {
        StringBuilder builder = new StringBuilder();
        String[] codes = morseCode.split(" ");
        for(String code: codes){
            //do things
            builder.append(findNode(code.toCharArray()));
        }
        return builder.toString().replaceAll("[|]","");
    }
    private Character findNode(char[] code) throws Exception {
        BinaryTree<Character> tree = this;
        for(int i=0; i<code.length-1;i++){
            if(code[i]=='*'){
                if(tree.getLeftSubtree()==null){
                    throw new Exception("code is invalid");
                }
                tree = tree.getLeftSubtree();
            }
            else if (code[i] == '-') {
                if(tree.getRightSubtree()==null){
                    throw new Exception("code is invalid");
                }
                tree = tree.getRightSubtree();
            }else{
                throw new Exception("invalid character");
            }
        }
        if (code[code.length - 1] == '*') {
            if(tree.getLeftSubtree()==null){
                throw new Exception("code is invalid");
            }else{
                return tree.getLeftSubtree().getData();
            }
        } else if (code[code.length - 1] == '-') {
            if(tree.getRightSubtree()==null){
                throw new Exception("code is invalid");
            }else{
                return tree.getRightSubtree().getData();
            }
        }
        else{
            throw new Exception("code is invalid");
        }
    }
    private void readMorseCodeTree() throws IOException {
        super.setValue(null);
        BufferedReader reader = new BufferedReader(new FileReader("./src/edu/miracosta/cs113/ToMorseCode.txt"));
        ArrayList<String> list = new ArrayList<>(26);
        String line;
        while ((line= reader.readLine())!=null){
            list.add(line);
        }
        Comparator<String> comparator = Comparator.comparingInt(String::length);
        list.sort(comparator);
        if(asciLookUp==null){
            asciLookUp = new String[26];
            for(String lines: list){
                BinaryTree<Character> characterBinaryTree = this;
                String[] array = lines.split(" ");
                char character = array[0].toCharArray()[0];
                char[] instructions = array[1].toCharArray();
                asciLookUp[character -97] = array[1];
                fillTree(characterBinaryTree, character, instructions);
            }
        }else{
            for(String lines: list){
                BinaryTree<Character> characterBinaryTree = this;
                String[] array = lines.split(" ");
                char character = array[0].toCharArray()[0];
                char[] instructions = array[1].toCharArray();
                fillTree(characterBinaryTree, character, instructions);
            }
        }
    }

    private void fillTree(BinaryTree<Character> characterBinaryTree, Character character, char[] instructions) {
        for(int i=0; i< instructions.length-1; i++){
            if(instructions[i]=='*'){
                characterBinaryTree = characterBinaryTree.getLeftSubtree();
            }else if(instructions[i] == '-'){
                characterBinaryTree = characterBinaryTree.getRightSubtree();
            }else{
                throw new IllegalArgumentException("instruction "+instructions[i]+" is invalid should be * or -");
            }
        }
        if(instructions[instructions.length-1]=='*'){
            if(characterBinaryTree.getLeftSubtree()!=null){
                System.err.println("left sub tree is not null warning");
            }
            characterBinaryTree.setLeft(new BinaryTree<>(character,null,null));
        }else if(instructions[instructions.length-1]=='-'){
            if(characterBinaryTree.getRightSubtree()!=null){
                System.err.println("right sub tree is not null warning");
            }
            characterBinaryTree.setRight(new BinaryTree<>(character,null,null));
        }else{
            throw new IllegalArgumentException("instruction "+instructions[instructions.length-1]+" is invalid should be * or -");
        }
    }
} // End of class MorseCodeTree