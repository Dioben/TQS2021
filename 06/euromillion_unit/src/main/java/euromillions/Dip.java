package euromillions;

import sets.SetOfNaturals;

import java.util.Objects;
import java.util.Random;

/**
 * A set of 5 numbers and 2 starts according to the Euromillions ranges.
 *
 * @author ico0
 */
public class Dip {


    private SetOfNaturals numbers;
    private SetOfNaturals starts;
    private static Random generator= new Random();
    public static final int NUMBERCOUNT =5;
    public static final int NUMBERCAP =50;
    public static final int STARCOUNT =2;
    public static final int STARCAP =10;
    public Dip() {
        numbers = new SetOfNaturals();
        starts = new SetOfNaturals();

    }

    public Dip(int[] arrayOfNumbers, int[] arrayOfStarts) {
        this();

        if (NUMBERCOUNT == arrayOfNumbers.length && STARCOUNT == arrayOfStarts.length) {
            numbers.add(arrayOfNumbers);
            starts.add(arrayOfStarts);
        } else {
            throw new IllegalArgumentException("wrong number of elements in numbers/stars");
        }

    }

    public SetOfNaturals getNumbersColl() {
        return numbers;
    }

    public SetOfNaturals getStarsColl() {
        return starts;
    }

    public static Dip generateRandomDip() {

        Dip randomDip = new Dip();
        int i=0;
        while(i < NUMBERCOUNT) {
            int candidate = generator.nextInt(NUMBERCAP-1) + 1; //not efficient but it IS readable
            if (!randomDip.getNumbersColl().contains(candidate)) {
                randomDip.getNumbersColl().add(candidate);
                i++;
            }
        }
        i=0;
        while ( i < STARCOUNT ) {
            int candidate = generator.nextInt(STARCAP-1) + 1;
            if (!randomDip.getStarsColl().contains(candidate)) {
                randomDip.getStarsColl().add(candidate);
                i++;
            }
        }
        return randomDip;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.numbers);
        hash = 29 * hash + Objects.hashCode(this.starts);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dip other = (Dip) obj;
        if (!Objects.equals(this.numbers, other.numbers)) {
            return false;
        }
        return Objects.equals(this.starts, other.starts);
    }


    /**
     * prepares a string representation of the data structure, formated for
     * printing
     *
     * @return formatted string with data
     */
    public String format() {
        StringBuilder sb = new StringBuilder();
        sb.append("N[");
        for (int number : getNumbersColl()) {
            sb.append(String.format("%3d", number));
        }
        sb.append("] S[");
        for (int star : getStarsColl()) {
            sb.append(String.format("  %d", star));
        }
        sb.append("]");
        return sb.toString();
    }
}
