package code;

public class Term implements Cloneable{
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
    public int hashCode() { //needs to be implemented if the equals' method is override
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
        if (coefficient>0){
            if (exponent==0){
                return "+"+this.coefficient;
            }
            if (exponent!=1){
                return "+"+this.coefficient+"x^"+this.exponent;
            }
            else{
                return "+"+this.coefficient+"x";
            }
        }
        /*return (this.coefficient>0?"+":"")+this.coefficient+(this.exponent!=0?"x^"+this.exponent:"");*/
        return "";
    }
}
