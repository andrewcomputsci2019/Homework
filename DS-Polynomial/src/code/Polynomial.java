package code;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class Polynomial {

    private LinkedList<Term> terms;

    public Polynomial(){
        terms = new LinkedList<>();
    }
    public Polynomial(Polynomial original){
        this(original.terms);
    }
    public Polynomial(String polynomial){
        this();
    }
    private Polynomial(LinkedList<Term> list){
        this.terms = new LinkedList<>();
        addAll(list);

    }
    private void addAll(Iterable<Term> terms){
        for (Term term:terms) {
            addTermClone(term);
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
            if (terms.get(counter).getCoefficient()==0){
                terms.remove(counter);
            }
        }else if (terms.get(counter).compareTo(term)<0){ // if term is larger than the last
            terms.add(counter,term);
        }
        else{
            terms.add(term);
        }

    }
    private void addTermClone(Term term){
        if (terms.size()==0){
            terms.add(term.clone());
            return;
        }
        int counter = 0;
        for(;counter<terms.size()&&terms.get(counter).compareTo(term)>0;counter++){ // empty loop to iterate to desired index
        }
        if (terms.get((counter= counter>=terms.size()?counter-1:counter)).compareTo(term)==0){ //counter needs to be checked just in case it is out of bounds
            addMutateCoefficient(terms.get(counter),term);
            if (terms.get(counter).getCoefficient()==0){
                terms.remove(counter);
            }
        }else if (terms.get(counter).compareTo(term)<0){ // if term is larger than the last
            terms.add(counter,term.clone());
        }
        else{
            terms.add(term.clone());
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
    public void add(Polynomial other){
    /*    Iterator<Term> termsIter = terms.iterator();// faster but takes up O(N) space, O(n) merge
        Iterator<Term> otherIter = other.terms.iterator();
        Term first = null;
        Term second = null;
        LinkedList<Term> temp = new LinkedList<>();
        while (termsIter.hasNext()&& otherIter.hasNext()){
            first = first==null? otherIter.next() : first;
            second = second==null?termsIter.next(): second;
            if (first.compareTo(second)>0){
                temp.add(first.clone());
                first=null;
            }else if (first.compareTo(second)==0){
                addMutateCoefficient(second,first);
                if (second.getCoefficient() != 0) {
                    temp.add(second);
                }
                second=null;
                first=null;
            }else{
                temp.add(second);
                second=null;
            }
        }
        if (second!=null){
            temp.add(second);
        }
        if (first!=null){
            temp.add(first);
        }
        termsIter.forEachRemaining(temp::add);
        termsIter.forEachRemaining(obj-> temp.add(obj.clone()));
        this.terms = temp;*/
        other.terms.iterator().forEachRemaining(this::addTermClone); // worst case O(N^2) time complexity but O(1) space
    }
    public Polynomial addNew(Polynomial other){
       Polynomial newPoly = new Polynomial(this.terms);
       other.terms.iterator().forEachRemaining(newPoly::addTermClone);
       return newPoly;
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