import java.lang.Math;

public class Literal {
    boolean isNotNegated;
    int name;

    public Literal (int number) {
        if (number >= 0) {
            this.isNotNegated = true;  // is Not Negated -- eg. x1
        } else {
            this.isNotNegated = false; // is Negated     -- eg. !x2
        }
        this.name = Math.abs(number);
    }

    public boolean getSign () {
        return this.isNotNegated;
    }

    public String getName () {
        return Integer.toString(this.name);
    }

    @Override
    public String toString () {
        if (this.isNotNegated) {
            return "X" + this.name;
        }
        return  "!X" + this.name;
    }
}