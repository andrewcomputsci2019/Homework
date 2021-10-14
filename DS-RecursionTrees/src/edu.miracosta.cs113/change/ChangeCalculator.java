package edu.miracosta.cs113.change;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * ChangeCalculator : Class containing the recursive method calculateChange, which determines and prints all
 * possible coin combinations representing a given monetary value in cents.
 *
 * Problem derived from Koffman & Wolfgang's Data Structures: Abstraction and Design Using Java (2nd ed.):
 * Ch. 5, Programming Project #7, pg. 291.
 *
 * NOTE: An additional method, printCombinationsToFile(int), has been added for the equivalent tester file to
 * verify that all given coin combinations are unique.
 */
public class ChangeCalculator {

    /**
     * Wrapper method for determining all possible unique combinations of quarters, dimes, nickels, and pennies that
     * equal the given monetary value in cents.
     *
     * In addition to returning the number of unique combinations, this method will print out each combination to the
     * console. The format of naming each combination is up to the user, as long as they adhere to the expectation
     * that the coins are listed in descending order of their value (quarters, dimes, nickels, then pennies). Examples
     * include "1Q 2D 3N 4P", and "[1, 2, 3, 4]".
     *
     * @param cents a monetary value in cents
     * @return the total number of unique combinations of coins of which the given value is comprised
     */
    public static int calculateChange(int cents) {
        // TODO:
        // Implement a recursive solution following the given documentation.
        HashSet<CoinCombo> coinComboHashSet = new HashSet<>();
        int ans= ChangeRecursion(0,0,0,cents,coinComboHashSet);
        System.out.println("DONE\n\n");
        return ans;
    }
    private static int ChangeRecursion(int quarter, int dime, int nickel, int penny, HashSet<CoinCombo>set){
        System.out.println(String.format("%dq,%dd,%dn,%dp",quarter,dime,nickel,penny));
        if (!set.add(new CoinCombo(quarter, dime, nickel, penny))){ // IDK could only think of this to solve duplicate issue
            return 0;
        }
        int sum=1;
        if (penny>=25){
            sum+=ChangeRecursion(quarter+1, dime, nickel, penny-25,set);
        }
        if (penny>=10){
            sum+=ChangeRecursion(quarter,dime+1,nickel,penny-10,set);
        }
        if (penny>=5){
            sum+=ChangeRecursion(quarter, dime, nickel+1, penny-5,set);
        }
        return sum;
    }

    /**
     * Calls upon calculateChange(int) to calculate and print all possible unique combinations of quarters, dimes,
     * nickels, and pennies that equal the given value in cents.
     *
     * Similar to calculateChange's function in printing each combination to the console, this method will also
     * produce a text file named "CoinCombinations.txt", writing each combination to separate lines.
     *
     * @param cents a monetary value in cents
     */
    public static void printCombinationsToFile(int cents) {
        // TODO:
        // This when calculateChange is complete. Note that the text file must be created within this directory.
        HashSet<CoinCombo> combos = new HashSet<>();
        ChangeRecursion(0,0,0,cents,combos);
        LinkedList<CoinCombo> Quarters = new LinkedList<>();
        LinkedList<CoinCombo> Dimes = new LinkedList<>();
        LinkedList<CoinCombo> Nickles = new LinkedList<>();
        final CoinCombo[] Penny = new CoinCombo[1];
        combos.forEach(obj-> {
            if (obj.Quarter>0){
                Quarters.add(obj);
            }else if (obj.Dime>0){
                Dimes.add(obj);
            }else if (obj.Nickel>0){
                Nickles.add(obj);
            }else{
                Penny[0] = obj;
            }
        });
        Quarters.sort(CoinCombo::compareTo);
        Dimes.sort(CoinCombo::compareTo);
        Nickles.sort(CoinCombo::compareTo);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("./src/edu.miracosta.cs113/change/CoinCombinations.txt"))){
            for (CoinCombo combo: Quarters) {
                writer.write(combo.toString());
                writer.newLine();
            }
            for (CoinCombo combo: Dimes) {
                writer.write(combo.toString());
                writer.newLine();
            }
            for (CoinCombo combo: Nickles) {
                writer.write(combo.toString());
                writer.newLine();
            }
            writer.write(Penny[0].toString());
            writer.newLine();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

} // End of class ChangeCalculator