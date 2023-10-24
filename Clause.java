public class Clause {
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
    //eg. (X1 || X2) === (X2 || X1) ==> True. All others are false.
    public static boolean equivClause (Clause C1, Clause C2) {
        //C1 = (X1 || X2), C2 = (X2 || X1)
        //C1 = (X1 || X2), C2 = (X1 || X2)

        

        //compare C1-LHS to C2-LHS
        if (Literal.sameLiteral(C1.getLHS(), C2.getLHS())) {
            //compare C1-RHS to C2-RHS
            if (Literal.sameLiteral(C1.getRHS(), C2.getRHS())) {
                return true;
            }
        } else if (Literal.sameLiteral(C1.getLHS(), C2.getRHS())) {

        
        //compare C1-LHS to C2-RHS

            //compare C1-RHS to C2-LHS
            

        }
        //OR

        //compare C1-RHS to C2-LHS

            //compare C1-LHS to C2-RHS

        //compare C1-RHS to C2-RHS
    
            //compare C1-LHS to C2-LHS


        


        return true;
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