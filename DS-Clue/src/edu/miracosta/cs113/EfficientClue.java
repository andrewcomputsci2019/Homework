package edu.miracosta.cs113;

import model.AssistantJack;
import model.Theory;

import java.util.Scanner;

/**
 * An algo implementation designed to have a max run time of O(w+l+p-2)
 * space complexity of o(1)
 * test can be find in file test/TheoryTest
 */
public class EfficientClue {

    private int[] lookUp  = {1,1,1}; // weapon location person
    private int guessCount =0; //used in main driver

    public EfficientClue(){

    }

    /**
     * Method to find the correct solution of the given answer set
     * @param jack the needed helper for this method
     * @return the correct theory
     */
    private Theory findSolution(AssistantJack jack){
        while (lookUp[2]<=6 && lookUp[1]<=10 && lookUp[0]<=6){
            int test = jack.checkAnswer(lookUp[0],lookUp[1],lookUp[2]);
            if (test==0){
                break;
            }else{
                lookUp[test-1]++;
            }
        }
        return new Theory(lookUp[0],lookUp[1],lookUp[2]);
    }

    /**
     *
     * @param answerSet the answer set to be used
     * @return a theory of the correct answer from the answer set
     */
    public Theory getSolution(int answerSet){
        AssistantJack jack = new AssistantJack(answerSet);
        Theory temp = findSolution(jack);
        guessCount = jack.getTimesAsked();
        return temp;
    }
    public Theory getSolution(AssistantJack jack){
        Theory temp = findSolution(jack);
        guessCount = jack.getTimesAsked();
        return temp;
    }
    public int getGuessCount(){
        return guessCount;
    }

    public static void main(String[] args) {
        EfficientClue efficientClue = new EfficientClue();
        Scanner scanner = new Scanner(System.in);
        System.out.print("enter 1 for set 1, 2 for set 2, 3 for random");
        int set  = scanner.nextInt();
        scanner.close();
        Theory theory = efficientClue.getSolution(set);
        if (efficientClue.getGuessCount()<=20){
            System.out.println("took less than 20 times");
            System.out.println(theory);
        }else{
            System.out.println("failed to find correct answer in 20 guesses");
        }
    }

}
