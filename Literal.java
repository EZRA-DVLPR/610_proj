// Isaiah Martinez
// Project 2
// Literal Class

/**************************************************************************************************************/
import java.lang.Math;

public class Literal {
    private boolean isNotNegated;
    private int name;

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

    public String getNameAsString () {
        return Integer.toString(this.name);
    }

    public int getNameAsInt () {
        return this.name;
    }

    //Input: Literals L1, L2
    //Ouptput: A boolean true if L1 and L2 have the same `notNegated` and `name`
    //eg. X1, X1 ==> True. !X2, !X2 ==> True. All others are false.
    public static boolean sameLiteral (Literal L1, Literal L2) {
        if ((L1.getNameAsInt() == L2.getNameAsInt()) && (L1.getSign() == L2.getSign())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString () {
        if (this.isNotNegated) {
            return "X" + this.name;
        }
        return  "!X" + this.name;
    }
}