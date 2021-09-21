package code;

import java.util.LinkedList;

public class Polynomial {

    private LinkedList<Term> terms;

    public Polynomial(){
        terms = new LinkedList<>();
    }
    public Polynomial(Polynomial original){
        terms = new LinkedList<>();
        for(Term term:original.terms){
            terms.add(term.clone());
        }
    }

    public void addTerm(Term term){
        if (terms.size()==0){
            terms.add(term);
            return;
        }
        int counter = 0;
        for(;counter<terms.size()&&terms.get(counter).compareTo(term)>0;counter++){ // empty loop to iterate to desired index
        }
        if (terms.get((counter= counter>=terms.size()?counter-1:counter)).compareTo(term)==0){ //counter needs to be checked just in case it is out of bounds
            addMutateCoefficient(terms.get(counter),term);
        }else if (terms.get(counter).compareTo(term)<0){ // if term is larger than the last
            terms.add(counter,term);
        }
        else{
            terms.add(term);
        }

    }

    public int getNumTerms(){
        return terms.size();
    }

    public Term getTerm(int index){
        return terms.get(index);
    }

    public void clear(){
        terms.clear();
    }

    /**
     *
     * @param first this is the Term to be mutated, Coefficient changes
     * @param second this object is not mutated and is only used to get its Coefficient
     */
    private void addMutateCoefficient(Term first, Term second){
        first.setCoefficient(first.getCoefficient()+second.getCoefficient());
    }
    //@TODO see comment at bottom
    public Polynomial add(Polynomial other){
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (terms==null||terms.size()==0){
            return "0";
        }
        int counter = 0;
        for (Term term: terms){
            builder.append(counter++==0?term.toString().substring(1):term.toString());
        }
        return builder.toString().replaceAll("[|]","");
    }
}
// @TODO make the add method so two polynomials can be added together should mutate the current polynomials list, IE merge the two together