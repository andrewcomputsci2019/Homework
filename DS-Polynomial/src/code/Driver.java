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
    private static final List<String> options = Arrays.asList("create polynomial","replace polynomial","add to polynomial","delete polynomial","list polynomials","Clear Screen","HELP");
    private final ArrayList<Polynomial> polynomials;
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
                args = new String[Math.min(temp.length - 1, 3)];
                for (int i=1; i< temp.length&&i<args.length+1; i++){
                    args[i-1] = temp[i];
                }
                input = input.split("--")[0];
            }
            switch (Integer.parseInt(input.replaceAll(" ",""))){
                case 0:createPolynomial(); break;
                case 1:replacePolynomial(args);break;
                case 2:addToPolynomial(args); break;
                case 3:removePolynomial(args); break;
                case 4:listPolynomials(); break;
                case 5:try{clearScreen();}catch (IOException | InterruptedException e){
                    e.printStackTrace();
                    System.exit(2);
                }break;
                case 6: printHelp();
            }
        }
        keyboard.close();
    }
    private static String getHelpMessage(){
        return "Creating a polynomial needs to be done in specific format otherwise the program will likely give unwanted results\nthe format can be defined as coefficient followed by x then a exponent constant should not have powers\nexamples: 2x^3+2x-1\n3x^1-8x+3" +
                "\n2x^-5-5x+12\n" +
                "Some functions offer flags that can be used to shortcut steps or change the functions behavior, functions that offer this are replacePolynomial,addToPolynomial,removePolynomial\n" +
                "replacePolynomial allows you to specify the index before hand by using 1 --index example is 1 --1 \n" +
                "This will prompt you to type in the new polynomial to replace the one in index one\n" +
                "If not supplied an argument the program will ask you for an index and then the polynomial\n" +
                "addToPolynomial offers the ability to specify index of the operation like if you wanted to add index 1 and 3 you could do 2 --1 --3\n" +
                "this tells addToPolynomial to modify index 1 by adding index 3 to it\n" +
                "If you do not want to modify the index but want to add a polynomial to it you can use the --noMod or --nomod option to tell the program to not store or change the index of the result\n" +
                "addToPolynomial also allows you to just enter a single index and then specify the new polynomial you want to add to i example\n" +
                "2 --1 this tells program to use index 1 as the polynomial to be added to then you would either use an already existing polynomial or make a new one\n" +
                "removePolynomial take an optional index flag that is used to shortcut specify one inside the function example \n" +
                "3 --1 removes the index 1 from the list\n" +
                "ListPolynomial can be used to get the index and values of polynomials\n" +
                "Clear screen clears the terminal screen works on Unix/linux based system as well as windows";
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
    private boolean replacePolynomial(String[] args){
        if (args!=null&&!args[0].equals("")){
            int local;
            try {
               local = Integer.parseInt(args[0].replaceAll(" ",""));
            }catch (NumberFormatException e){
                e.printStackTrace();
                System.out.println(prefix+"-replace:If you keep getting errors please see the help section");
                return false;
            }
            System.out.print(prefix+"-replace:Please enter the new polynomial:");
            String temp = keyboard.nextLine();
            if (verifyString(temp)){
                this.polynomials.set(local,new Polynomial(temp));
                return true;
            }else{
                return false;
            }

        }else{
            System.out.print(prefix+"-replace:Please enter the index you want to replace with a new polynomial:");
            int local;
            while (true){
                try {
                    local = Integer.parseInt(keyboard.nextLine());
                }catch (NumberFormatException e){
                    e.printStackTrace();
                    System.out.println(prefix+"-replace:If you keep getting errors please see the help section");
                    return false;
                }
                if (local>=this.polynomials.size()){
                    System.out.print(prefix+"-replace:The number you have entered is larger then the last index of "+this.polynomials.size()+" please enter a new number:");
                    continue;
                }
                break;
            }
            System.out.print(prefix+"-replace:Please enter the new polynomial:");
            String temp = keyboard.nextLine();
            if (verifyString(temp)){
                this.polynomials.set(local,new Polynomial(temp));
                return true;
            }else{
                System.out.println(prefix+"-replace:The polynomial you have entered is invalid please see the help section if you keep getting this error");
                return false;
            }
        }
    }
    private boolean addToNoMod(String[] args){
            if (args==null){
                System.out.print(prefix+"-add:noMod:Please enter the polynomial you want to add to:");
                String input = keyboard.nextLine();
                if (verifyString(input)){
                    System.out.print(prefix+"-add:noMod:Do you want to use an already existing polynomial? [Y/N]:");
                    if (keyboard.nextLine().equalsIgnoreCase("y")){
                        System.out.print(prefix+"-add:noMod:Enter the index you want to use:");
                        int local;
                        while (true) {
                            try {
                                local = Integer.parseInt(keyboard.nextLine());
                            }catch (NumberFormatException e){
                                e.printStackTrace();
                                System.out.println(prefix+"-add:noMod:If you keep getting errors please see the help page");
                                return false;
                            }
                            if (local>=this.polynomials.size()){
                                System.out.print(prefix+"-add:noMod:The number you have entered is invalid largest index is"+(this.polynomials.size()-1)+ " please enter a new one:");
                                continue;
                            }break;
                        }
                        System.out.println(prefix+"-add:noMod:result: "+(new Polynomial(input).addNew(this.polynomials.get(local))));
                        return true;
                    }else{
                        System.out.print(prefix+"-add:noMod:Please enter the polynomial:");
                        String input1 = keyboard.nextLine();
                        if (verifyString(input1)){
                            System.out.println(prefix+"-add:noMod:result: "+(new Polynomial(input).addNew(new Polynomial(input1))));
                            return true;
                        }else{
                            System.out.println(prefix+"-add:noMod:The given polynomial is invalid please see the help page if you keep getting this error");
                            return false;
                        }
                    }
                }else{
                    System.out.println(prefix+"-add:noMod:The given polynomial was invalid please see the help section if you keep getting this error");
                    return false;
                }
            }else if (args.length<2){
                Polynomial obj;
                try{
                    obj = polynomials.get(Integer.parseInt(args[0].replaceAll(" ", "")));
                }catch (NumberFormatException|IndexOutOfBoundsException e){
                    e.printStackTrace();
                    System.out.println(prefix+"-add:noMod:If you keep getting errors please see the help section");
                    return false;
                }
                System.out.print(prefix+"-add:noMod:Please enter a polynomial to add to:");
                String input = keyboard.nextLine();
                if (verifyString(input)){
                    System.out.println(prefix+"-add:noMod:result: "+ obj.addNew(new Polynomial(input)));
                    return true;
                }else{
                    System.out.println(prefix+"-add:noMod:The given polynomial is invalid if you keep getting this error please see the help section");
                    return false;
                }
            }else{
                    Polynomial obj;
                    Polynomial obj1;
                    try {
                        obj = this.polynomials.get(Integer.parseInt(args[0].replaceAll(" ","")));
                        obj1 = this.polynomials.get(Integer.parseInt(args[1].replaceAll(" ","")));
                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.println(prefix+"-add:noMod:If you keep getting errors please see the help section");
                        return false;
                    }
                    System.out.println(prefix+"-add:noMod:result: "+ obj.addNew(obj1));
                    return true;
            }
    }
    private boolean addToPolynomial(String[] args){
        if (args!=null){
           for(int i=0; i<args.length;i++){
               if (args[i].replaceAll(" ","").equalsIgnoreCase("nomod")){
                       if (args.length>1){
                           int counter = 0;
                           int index =0;
                           String[] innerArg = new String[args.length-1];
                           for (String string:args) {
                               if (counter!=i){
                                   innerArg[index++] = string;
                               }
                               counter++;
                           }
                           return addToNoMod(innerArg);

                       }else{
                         return addToNoMod(null);
                       }
               }
           }
        }

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
                    Polynomial temp = new Polynomial(input);
                    temp.add(this.polynomials.get(local));
                    this.polynomials.add(temp);
                    System.out.println(prefix+"-add:result: "+temp);
                    return true;
                }else{
                    System.out.print(prefix+"-add:enter a new polynomial\n"+prefix+"-add:");
                    String input1 = keyboard.nextLine();
                    if (verifyString(input1)){
                        Polynomial temp = new Polynomial(input1);
                        temp.add(new Polynomial(input));
                        this.polynomials.add(temp);
                        System.out.println(prefix+"-add:result: "+ temp);
                        return true;
                    }else{
                        System.out.println(prefix+"-add:the given polynomial is invalid, if you keep getting this error please use the help command");
                        return false;
                    }
                }
            }else{
                System.out.println(prefix+"-add:the given polynomial is invalid, if you keep getting this error please use the help command");
                return false;
            }
        }else{
            if (args.length<2) {
                Polynomial obj;
                try {
                    obj = this.polynomials.get(Integer.parseInt(args[0].replaceAll(" ","")));
                } catch (IndexOutOfBoundsException | NumberFormatException exception) {
                    exception.printStackTrace();
                    System.out.println("please see the help section if you keep getting errors");
                    return false;
                }
                System.out.print(prefix+"-add:Do you want to use an already existing Polynomial? [Y/N]:");
                if (keyboard.nextLine().equalsIgnoreCase("y")){
                    System.out.print(prefix+"-add:What index do you want to use:");
                    int local;
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
                    System.out.println(prefix+"-add:result: "+this.polynomials.get(local).toString());
                    return true;
                }else{
                    System.out.print(prefix+"-add:please enter the polynomial you want to add too:");
                    String input = keyboard.nextLine();
                    if (verifyString(input)){
                        obj.add(new Polynomial(input));
                        System.out.println(prefix+"-add:result: "+ obj);
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
                System.out.println(prefix+"-add:result: "+ obj1);
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
        int tableLength = longestLength+5;
        String tableUnderscores = String.join("",Collections.nCopies(tableLength,"_"));
        String tableDashes = String.join("",Collections.nCopies(tableLength,"-"));
        System.out.println(tableUnderscores);
        for (int i =0; i<nodeStrings.length;i++){
            System.out.println("|"+i+"| "+nodeStrings[i]+String.join("",Collections.nCopies(longestLength-nodeStrings[i].length()," "))+"|");
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

