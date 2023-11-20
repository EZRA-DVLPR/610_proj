// Isaiah Martinez
// Project 3
// ClauseList Class

/**************************************************************************************************************/
import java.util.ArrayList;
import java.util.Comparator;

public class ClauseList {
    private ArrayList<Clause> CL;
    private ArrayList<String> uniqueLiterals;    // all unique literals (X2 == !X2 => X2) {2}
    private ArrayList<String> allLiterals;  // all literals (X2 != !X2 => X2, !X2) {X2, !X2}

    public ClauseList () {
        this.CL = new ArrayList<Clause>();
        this.uniqueLiterals = new ArrayList<String>();
        this.allLiterals = new ArrayList<String>();
    }

    public Clause addClause (Clause C) {
        this.CL.add(C);
        
        //add literals if not encountered before into allLiterals
        if (!(this.allLiterals.contains(C.getLHS().toString()))) {
            this.allLiterals.add(C.getLHS().toString());
        }

        if (!(this.allLiterals.contains(C.getRHS().toString()))) {
            this.allLiterals.add(C.getRHS().toString());
        }

        //add literals only if the non-negated form is in uniqueLiterals
        if (!(this.uniqueLiterals.contains(C.getLHS().getNameAsString()))) {
            this.uniqueLiterals.add(C.getLHS().getNameAsString());
        }

        if (!(this.uniqueLiterals.contains(C.getRHS().getNameAsString()))) {
            this.uniqueLiterals.add(C.getRHS().getNameAsString());
        }

        //sort them in natural order ("X2" < "X3", etc.)
        this.uniqueLiterals.sort(Comparator.naturalOrder());
        this.allLiterals.sort(Comparator.naturalOrder());

        return C;
    }

    public String[] getUniqueLiterals () {
        String[] uniqueLiteralsAsStringArr = new String[this.uniqueLiterals.size()];
        uniqueLiteralsAsStringArr = this.uniqueLiterals.toArray(uniqueLiteralsAsStringArr);
        return uniqueLiteralsAsStringArr;
    }

    public String[] getAllLiterals () {
        String[] allLiteralsAsStringArr = new String[this.allLiterals.size()];
        allLiteralsAsStringArr = this.allLiterals.toArray(allLiteralsAsStringArr);
        return allLiteralsAsStringArr;
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

    //Input: a boolean[] Vals
    //      Note: Vals is in the same order given when calling `getUniqueLiterals` or `getClauseList`
    //Output: an int representing the Number of True Clauses for this CL given Vals
    public int getNumberTrueClauses (boolean[] Vals) {

        if (Vals.length != this.uniqueLiterals.size()) {
            return -1;
        }

        int numTrue = 0;

        for (int i = 0; i < this.getLength(); i++) {
            //this.CL.get(i)
            //gives the current clause
            
            //this.CL.get(i).getLHS().getNameAsString()
            //prints the name of the LHS of the current Clause as string 

            //find the index of the above string within the list of unique Literals for the LHS and RHS
            
            boolean LHSBool = Vals[this.uniqueLiterals.indexOf(this.CL.get(i).getLHS().getNameAsString())];
            boolean RHSBool = Vals[this.uniqueLiterals.indexOf(this.CL.get(i).getRHS().getNameAsString())];

            if (this.CL.get(i).evaluates(LHSBool, RHSBool)) {
                numTrue++;
            }

            //prints the currently evaluted clause
            //System.out.println(this.CL.get(i).evaluates(LHSBool, RHSBool));
        }
        
        return numTrue;
    }

    //Input: String representing the string representation of a literal
    //Output: int result of the amount of occurences of such a literal
    public int getNumberOccurences (String LitName) {
        int count = 0;

        for (int i = 0; i < this.getLength(); i++) {
            //this.CL.get(i)
            //gives the current clause
            
            //this.CL.get(i).getLHS().getLitAsString()
            //prints the LHS of the current Clause as string
            String LHS = this.CL.get(i).getLHS().getLitAsString();
            String RHS = this.CL.get(i).getRHS().getLitAsString();

            if (LHS.compareTo(LitName) == 0) {
                count++;
            }

            if (RHS.compareTo(LitName) == 0) {
                count++;
            }
        }
        
        return count;
    }

    public void printUniqueLiterals () {
        for (int i = 0; i < this.uniqueLiterals.size(); i++) {
            System.out.println("X" + this.uniqueLiterals.get(i));
        }
    }

    public void printAllLiterals () {
        for (int i = 0; i < this.allLiterals.size(); i++) {
            System.out.println(this.allLiterals.get(i));
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