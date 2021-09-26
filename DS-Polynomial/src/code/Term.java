package code;


public class Term implements Cloneable,Comparable<Term>{
    private int coefficient;
    private int exponent;

    public Term(){
        coefficient = 1;
        exponent = 1;
    }
    public Term(int coefficient, int exponent){
        this.coefficient = coefficient;
        this.exponent = exponent;
    }
    public Term(String term){
        // set vars in here by extracting from string
       /* if (term==null||term.equals("")){ //used to check if value given is null or empty
            coefficient=0;
            exponent=0;
            return;
        }*/
        if (checkForX(term)){
            String[] termArray = term.split("x");
            if(termArray.length<=1){
                if (termArray[0].equals("+")){
                    this.coefficient =1;
                }
                else if (termArray[0].equals("-")){
                    this.coefficient = -1;
                }
                else{
                    this.coefficient = Integer.parseInt(termArray[0]);
                }
                this.exponent = 1;
            }else{
                if(checkLength(termArray[0].length())){
                    this.coefficient = Integer.parseInt(termArray[0]);
                }else{
                    if (termArray[0].equals("+")){
                        this.coefficient = 1;
                    }else{
                        this.coefficient=-1;
                    }
                }
                this.exponent = Integer.parseInt(termArray[1].replaceAll("\\^",""));
            }
        }
        else{//lin search for pieces
            String copy = term.replace(" ","");
            this.coefficient = Integer.parseInt(copy);
            this.exponent = 0;
            /*if (!checkForExponent(copy)){ // I do not think that non x values terms are given exponents
                this.coefficient = Integer.parseInt(copy);
                this.exponent =0;
            }else{
                String[] innerCopy = copy.split("\\^");
                this.coefficient = Integer.parseInt(innerCopy[0]);
                this.exponent = Integer.parseInt(innerCopy[1]);
            }*/
        }
    }
    private static boolean checkForExponet(String term){
        return term.contains("^");
    }
    private static boolean checkLength(int len){
        return len>1;
    }
    private static boolean checkForX(String term){
        return term.contains("x");
    }
    public Term(Term term){
        this.coefficient = term.coefficient;
        this.exponent = term.exponent;
    }
    public boolean setCoefficient(int coefficient){
        this.coefficient = coefficient;
        return true;
    }
    public boolean setExponent(int exponent){
        this.exponent = exponent;
        return true;
    }
    public boolean setAll(int coefficient, int exponent){
        this.coefficient = coefficient;
        this.exponent = exponent;
        return true;
    }
    public int getCoefficient(){
        return this.coefficient;
    }
    public int getExponent(){
        return this.exponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Term term = (Term) o;

        if (coefficient != term.coefficient) return false;
        return exponent == term.exponent;
    }

    @Override
    public int hashCode() { //needs to be implemented if the equals' method is overridden
        int result = coefficient;
        result = 31 * result + exponent;
        return result;
    }
    public int compareTo(Term term){
        if (term!=null) {
            return this.exponent - term.exponent;
        }else{
            return -1;
        }
    }

    @Override
    public Term clone() {
        try {
            Term clone = (Term) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            clone.coefficient = this.coefficient;
            clone.exponent = this.exponent;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        if (coefficient==0){
            return "";
        }
        if(coefficient ==1){
            if (exponent!=1){
                return "+x^"+this.exponent;
            }else{
                    return "+x";
            }
        }
        if (coefficient ==-1){
            if (exponent!=1){
                    return "-x^"+this.exponent;
            }else{
                return "-x";
            }
        }
        if (coefficient <0){
            if(exponent==0){
                return ""+this.coefficient;
            }
            if (exponent!=1){
                return this.coefficient+"x^"+this.exponent;
            }else{
                return ""+this.coefficient+"x";
            }
        }
        if (exponent==0){
            return "+"+this.coefficient;
        }
        if (exponent!=1){
            return "+"+this.coefficient+"x^"+this.exponent;
        }
        else{
            return "+"+this.coefficient+"x";
        }
        /*return (this.coefficient>0?"+":"")+this.coefficient+(this.exponent!=0?"x^"+this.exponent:"");*/
    }
}