package code;

import java.io.IOException;
import java.util.*;

public class Driver {
    private enum OS{
        Windows,
        UNIX
    }
    private static final OS os = getOs();
    private static final String prefix = "polynomials adder ";
    private static final List<String> options = Arrays.asList("create polynomial","add to polynomial","delete polynomial","list polynomials","Clear Screen","HELP");
    private ArrayList<Polynomial> polynomials;
    private final static String helpMessage = getHelpMessage();
    private final Scanner keyboard;
    public Driver(){
        polynomials = new ArrayList<>();
        keyboard = new Scanner(System.in);
    }
    private static OS getOs(){
        if (System.getProperty("os.name").contains("Windows")){
            return OS.Windows;
        }else{
            return OS.UNIX;
        }
    }
    public void start(){
        System.out.println(prefix+":"+" enter q to quit");
        while (true){
            for(int i =0; i<options.size(); i++){
                System.out.println(i+":"+options.get(i));
            }
            System.out.print(prefix+":");
            String input  = keyboard.nextLine();
            if (input.equals("q")){
                break;
            }
            String[] args= null;
            if (input.contains("--")){
                String[] temp = input.split("--");
                args = new String[Math.min(temp.length - 1, 2)];
                for (int i=1; i< temp.length&&i<args.length+1; i++){
                    args[i-1] = temp[i];
                }
                input = input.split("--")[0];
            }
            switch (Integer.parseInt(input.replaceAll(" ",""))){
                case 0:createPolynomial(); break;
                case 1:addToPolynomial(args); break;
                case 2:removePolynomial(args); break;
                case 3:listPolynomials(); break;
                case 4: try{clearScreen();}catch (IOException | InterruptedException e){
                    e.printStackTrace();
                    System.exit(2);
                }break;
                case 5: printHelp();
            }
        }
        keyboard.close();
    }
    private static String getHelpMessage(){
        return "Creating a polynomial needs to be done in specific format otherwise the program will likely give unwanted results\nthe format can be defined as coefficient followed by x then a exponent constant should not have powers\nexamples: 2x^3+2x-1\n3x^1-8x+3" +
                "\n2x^-5-5x+12\nadd to a polynomial can take an argument in which is the index assigned to already existing polynomial\nexample on usage 1 --index --index the -- is used to program to look for a index in the string\nif not given an argument the program will expect the user to type a polynomial in" +
                "\ndelete polynomial functions in two ways you can give an argument using -- or you can enter it after executing the command examples 2 --index or 2 followed by entering the index you want to delete";
    }
    private void clearScreen() throws IOException, InterruptedException {
        switch (os){
            case Windows: new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();break; // powershell does allow clear but to be safe cls should be used.
            case UNIX: Runtime.getRuntime().exec("clear");break; //bash/zsh command to clear screen
        }
    }
    private void printHelp(){
        System.out.println(helpMessage);
        keyboard.nextLine(); //user just needs to enter something in to advance back to the loop
    }
    private void createPolynomial(){
        System.out.print(prefix+"-create:"+"enter polynomial\n"+prefix+"-create:");
        String input = keyboard.nextLine();
        if (verifyString(input)) {
            this.polynomials.add(new Polynomial(input));
        }else{
            System.out.println("the given polynomial was invalid, if you keep getting this error please check the help section");
        }
    }
    private boolean verifyString(String toBeChecked){
        if (toBeChecked.equals("")||(!Character.isDigit(toBeChecked.charAt(0))&&(toBeChecked.charAt(0)!='x'&&toBeChecked.charAt(0)!='+'&&toBeChecked.charAt(0)!='-'))){
            return false;
        }
        for (int i=1; i<toBeChecked.toCharArray().length;i++){
            char temp;
            if (!Character.isDigit(temp=toBeChecked.charAt(i))&&(temp!='+'&&temp!='-'&&temp!='^'&&temp!='x')){
                return false;
            }
            if ((temp == '+' || temp == '-' || temp == '^'||temp=='x')&&temp == toBeChecked.charAt(i-1)) {
                return false; //duplicate character ie ^^ or ++ --
            }
            if (temp=='^'&&toBeChecked.charAt(i-1)!='x'){
                return false;
            }
        }
        return true;
    }
    private boolean addToPolynomial(String[] args){
        if (args==null){
            System.out.print(prefix+"-add:"+"please enter a polynomial\n"+prefix+"-add:");
            String input = keyboard.nextLine();
            if (verifyString(input)){
                System.out.print(prefix+"-add:"+"do you want to add to already existing polynomial? [Y/N]");
                if (keyboard.nextLine().equalsIgnoreCase("y")){
                    System.out.print(prefix+"-add:enter a index for to add to:");
                    int local  = keyboard.nextInt();
                    if (local>=this.polynomials.size()){
                        System.out.println(prefix+"-add: the index you have entered is invalid. largest index is "+(this.polynomials.size()-1));
                        System.out.print(prefix+"-add: please enter a new index:");
                        while(true){
                            try {
                                local = keyboard.nextInt();
                            }catch (Exception e){
                                System.out.println(prefix+"-add: exiting the add operation");
                                return false;
                            }
                            if (local>=this.polynomials.size()){
                                System.out.print(prefix+"-add: index given is invalid. largest index is "+(this.polynomials.size()-1)+" enter q if you want to stop the add operation:");
                            }else{
                                break;
                            }
                        }
                    }
                    this.polynomials.get(local).add(new Polynomial(input));
                    return true;
                }else{
                    System.out.print(prefix+"-add:enter a new polynomial\n"+prefix+"-add:");
                    String input1 = keyboard.nextLine();
                    if (verifyString(input1)){
                        Polynomial temp = new Polynomial(input1);
                        temp.add(new Polynomial(input));
                        this.polynomials.add(temp);
                        return true;
                    }else{
                        System.out.println(prefix+"-add:the given polynomial is invalid, if you keep getting this error please use the help command");
                        return false;
                    }
                }
            }else{
                System.out.println("polynomial entered was invalid");
                return false;
            }
        }else{
            if (args.length<2) {
                Polynomial obj = null;
                try {
                    obj = this.polynomials.get(Integer.parseInt(args[0].replaceAll(" ","")));
                } catch (IndexOutOfBoundsException | NumberFormatException exception) {
                    exception.printStackTrace();
                    System.out.println("please see the help section if you keep getting errors");
                    return false;
                }
                System.out.print(prefix+"-add:Do you want to use an already existing Polynomial? [Y/N]:");
                if (keyboard.nextLine().equalsIgnoreCase("y")){
                    System.out.print(prefix+"-add:What index do you to use:");
                    int local = 0;
                    while (true){
                        try {
                            local = Integer.parseInt(keyboard.nextLine());
                        }catch (NumberFormatException e){
                            System.out.print(prefix+"-add:Exiting the add operation");
                            return false;
                        }
                        if (local>=this.polynomials.size()){
                            System.out.print(prefix+"-add:The number you entered is invalid please enter a new one:");
                        }else{
                            break;
                        }
                    }
                    this.polynomials.get(local).add(new Polynomial(obj));
                    return true;
                }else{
                    System.out.print(prefix+"-add:please enter the polynomial you want to add too:");
                    String input = keyboard.nextLine();
                    if (verifyString(input)){
                        obj.add(new Polynomial(input));
                        return true;
                    }else{
                        System.out.println(prefix+"-add:the given polynomial is invalid, if you keep getting this error please use the help command");
                        return  false;
                    }
                }
            }else{
                Polynomial obj1;
                Polynomial obj2;
                try{
                    obj1 = this.polynomials.get(Integer.parseInt(args[0].replaceAll(" ","")));
                    obj2 = this.polynomials.get(Integer.parseInt(args[1].replaceAll(" ","")));
                }catch (IndexOutOfBoundsException | NumberFormatException exception){
                    exception.printStackTrace();
                    System.out.println("please see the help section if you keep getting errors");
                    return false;
                }
                obj1.add(obj2);
                return true;
            }
        }
    }
    private boolean removePolynomial(String[] arg){
        if (arg==null||arg[0].equals("")){
            System.out.print(prefix+"-delete:Please enter the index you want to delete:");
            try {
                this.polynomials.remove(Integer.parseInt(keyboard.nextLine()));
                return true;
            }catch (NumberFormatException |IndexOutOfBoundsException exception){
                exception.printStackTrace();
                System.out.println(prefix+"-delete:The given index is invalid please see the help page if you keep getting this error");
                return false;
            }
        }else{
            try {
                this.polynomials.remove(Integer.parseInt(arg[0].replaceAll(" ","")));
                return true;
            }catch (NumberFormatException |IndexOutOfBoundsException exception){
                exception.printStackTrace();
                System.out.println(prefix+"-delete:The given index is invalid please see the help page if you keep getting this error");
                return false;
            }
        }
    }

    private void listPolynomials(){
        if (polynomials.size()==0){
            System.out.println("empty");
            return;
        }
        String[] nodeStrings = new String[this.polynomials.size()];
        int longestLength =0;
        for (int i=0;i<this.polynomials.size();i++){
            nodeStrings[i] = this.polynomials.get(i).toString();
            longestLength = Math.max(nodeStrings[i].length(), longestLength);
        }
        int tableLength = longestLength+3;
        String tableUnderscores = String.join("",Collections.nCopies(tableLength,"_"));
        String tableDashes = String.join("",Collections.nCopies(tableLength,"-"));
        System.out.println(tableUnderscores);
        for (int i =0; i<nodeStrings.length;i++){
            System.out.println("|"+i+"|"+nodeStrings[i]+String.join("",Collections.nCopies(longestLength-nodeStrings[i].length()," "))+"|");
            System.out.println(tableDashes);
        }

    }

    //testing only
    private void listPolynomialsTest(){
        for (Polynomial node:this.polynomials) {
            System.out.println(node.toString());
        }
    }
}

