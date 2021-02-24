package second.lab.c;

import java.util.Random;

public class Monk implements Comparable {
    private Integer energy;
    private String monastery;

    public Monk() {
        Random r = new Random();
        energy = r.nextInt(100);
        monastery = (r.nextInt(2) == 0) ? "Huan-un" : "Huan-in";
    }

    public Integer getEnergy() {
        return energy;
    }

    public String getMonastery() {
        return monastery;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public void setMonastery(String monastery) {
        this.monastery = monastery;
    }

    @Override
    public String toString() {
        return "Monk from " + monastery +" with energy: "+energy;
    }

    public int compareTo(Object o) {
        Monk other = (Monk)o;
        if(this.energy > other.energy) {
            return 1;
        } else if (this.energy < other.energy){
            return -1;
        } else {
            return 0;
        }
    }

    public static Monk max(Object first, Object second) {
        return Monk.max((Monk)first, (Monk)second);
    }

    static Monk max(Monk first, Monk second){
        if(first.energy > second.energy){
            return first;
        } else {
            return second;
        }
    }
}
