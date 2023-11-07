import java.util.ArrayList;

public class ClauseList {
    private ArrayList<Clause> CL;
    private ArrayList<String> uniqueLit;

    public ClauseList () {
        this.CL = new ArrayList<Clause>();
        this.uniqueLit = new ArrayList<String>();
    }

    public Clause addClause (Clause C) {
        this.CL.add(C);
        
        if (!(this.uniqueLit.contains(C.getLHS().getNameAsString()))) {
            uniqueLit.add(C.getLHS().getNameAsString());
        }

        if (!(this.uniqueLit.contains(C.getRHS().getNameAsString()))) {
            uniqueLit.add(C.getRHS().getNameAsString());
        }
        return C;
    }

    public String[] getUniqueLiterals () {
        String[] uniqueLitAsStringArr = new String[this.uniqueLit.size()];
        uniqueLitAsStringArr = this.uniqueLit.toArray(uniqueLitAsStringArr);
        return uniqueLitAsStringArr;
    }

    public Clause getClause (int index) {
        return this.CL.get(index);
    }

    public ArrayList<Clause> getClauseList () {
        return this.CL;
    }

    public int getLength () {
        return this.CL.size();
    }

    //Vals is in the same order given when calling getUniqueLiterals or getClauseList
    public int getNumberTrueClauses (boolean[] Vals) {
        int numTrue = 0;
        for (int i = 0; i < this.getLength(); i++) {
            //this.CL.get(i).getLHS().getNameAsInt() - 1
            //is the location of the boolean value to evaluate the LHS of the current (i) clause
            boolean LHSBool = Vals[this.CL.get(i).getLHS().getNameAsInt() - 1];
            boolean RHSBool = Vals[this.CL.get(i).getRHS().getNameAsInt() - 1];

            if (this.CL.get(i).evaluates(LHSBool, RHSBool)) {
                numTrue++;
            }

            //prints the currently evaluted clause
            //System.out.println(this.CL.get(i).evaluates(LHSBool, RHSBool));
        }
        
        return numTrue;
    }

    public void printUniqueLiterals () {
        for (int i = 0; i < this.uniqueLit.size(); i++) {
            System.out.println("X" + this.uniqueLit.get(i));
        }
    }
    
    public void printClauseList () {
        if (getLength() > 1) {
            for (int i = 0; i < this.CL.size() - 1; i++) {
                System.out.println("(" + getClause(i) + ") &&");
            }
            System.out.println("(" + getClause(getLength() - 1) + ")");
        } else if (getLength() == 1) {
            System.out.println(getClause(0));
        } else {
            //size == 0
            System.out.print("The List is Empty");
        }
        return;
    }
}