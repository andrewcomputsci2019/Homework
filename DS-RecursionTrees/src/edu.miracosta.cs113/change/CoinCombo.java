package edu.miracosta.cs113.change;

public class CoinCombo implements Comparable<CoinCombo> {
    int Penny;
    int Nickel;
    int Dime;
    int Quarter;
    public CoinCombo(int quarter,int dime,int nickel,int penny){
        this.Quarter = quarter;
        this.Dime = dime;
        this.Nickel = nickel;
        this.Penny =penny;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoinCombo coinCombo = (CoinCombo) o;

        if (Penny != coinCombo.Penny) return false;
        if (Nickel != coinCombo.Nickel) return false;
        if (Dime != coinCombo.Dime) return false;
        return Quarter == coinCombo.Quarter;
    }

    @Override
    public int hashCode() {
        int result = Penny;
        result = 31 * result + Nickel;
        result = 31 * result + Dime;
        result = 31 * result + Quarter;
        return result;
    }

    @Override
    public int compareTo(CoinCombo o) {
       if (o.Quarter < this.Quarter){
           return -1;
       }else if (o.Quarter > this.Quarter){
           return 1;
       }
        if (o.Dime < this.Dime){
            return -1;
        }else if (o.Dime > this.Dime){
            return 1;
        }
        if (o.Nickel < this.Nickel){
            return -1;
        }else if (o.Nickel > this.Nickel){
            return 1;
        }
        if (o.Penny < this.Penny){
            return -1;
        }else if (o.Penny > this.Penny){
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.format("[%dQ,%dD,%dN,%dP]",Quarter,Dime,Nickel,Penny);
    }
}
