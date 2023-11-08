// Isaiah Martinez
// Project 3
// Clause Class

/**************************************************************************************************************/public class Clause {
    private Literal LHS;
    private Literal RHS;

    public Clause (Literal LHS, Literal RHS) {
        this.LHS = LHS;
        this.RHS = RHS;
    }

    public Literal getLHS () {
        return this.LHS;
    }

    public Literal getRHS () {
        return this.RHS;
    }

    //Input: Clauses C1 and C2
    //Output: A boolean true if C1 and C2 are logically Equivalent. False otherwise.
    public static boolean equivClause (Clause C1, Clause C2) {

        //C1-LHS === C2-LHS and C1-RHS === C2-RHS
        if ((Literal.sameLiteral(C1.getLHS(), C2.getLHS())) && (Literal.sameLiteral(C1.getRHS(), C2.getRHS()))) {
            return true;
        }

        //C1-LHS === C2-RHS and C1-RHS === C2-LHS
        else if ((Literal.sameLiteral(C1.getLHS(), C2.getRHS())) && (Literal.sameLiteral(C1.getRHS(), C2.getLHS()))) {
            return true;
        }

        return false;
    }

    //L1Val is the value of the LHS, L2Val is the value of the RHS
    public boolean evaluates (boolean L1Val, boolean L2Val) {
        if (this.LHS.getSign()) {
            if (this.RHS.getSign()) {
                return L1Val || L2Val;
            } else {
                //RHS is negated
                return L1Val || (!L2Val);
            }
        } else {
            //LHS is negated
            if (this.RHS.getSign()) {
                return (!L1Val) || L2Val;
            } else {
                //RHS is negated
                return (!L1Val) || (!L2Val);
            }
        }
    }

    @Override
    public String toString () {
        return  LHS + " || " + RHS;
    }
}