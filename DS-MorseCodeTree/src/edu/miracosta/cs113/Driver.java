package edu.miracosta.cs113;

import java.util.Scanner;

public class Driver {
    private final Scanner scanner;
    private final MorseCodeTree morseTree;
    public Driver(){
        morseTree = new MorseCodeTree();
        scanner = new Scanner(System.in);
    }
    public void run(){
        while(true) {
            System.out.println("-----------------------");
            System.out.println("1 enter morse code    |");
            System.out.println("2 enter normal text   |");
            System.out.println("3 or q to exit        |");
            System.out.println("-----------------------");
            String code = scanner.nextLine();
            if (code.equalsIgnoreCase("q") || code.equals("3")) {
                scanner.close();
                return;
            }
            if(code.equals("1")){
                System.out.println("enter a line of morse code");
                try {
                    System.out.println(morseTree.translateFromMorseCode(scanner.nextLine()));
                } catch (Exception e) {
                    System.out.println("an error has occurred");
                    e.printStackTrace();
                }
            }
            else if (code.equals("2")){
                System.out.println("enter some text to translate to morse code");
                try {
                    System.out.println(morseTree.textToMorse(scanner.nextLine()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
