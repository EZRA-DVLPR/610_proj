public class Clause {
    Literal LHS;
    Literal RHS;

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

    @Override
    public String toString () {
        return  LHS + " || " + RHS;
    }
}