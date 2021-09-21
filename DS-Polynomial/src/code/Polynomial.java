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

    //@TODO see comment at bottom
    public void addTerm(Term term){
        terms.add(term);
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
// @TODO terms sorted when enter and combine like terms when entered
// @TODO make the add method so two polynomials can be added together