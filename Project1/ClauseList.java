import java.util.ArrayList;
import java.util.Comparator;

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
            this.uniqueLit.add(C.getLHS().getNameAsString());
        }

        if (!(this.uniqueLit.contains(C.getRHS().getNameAsString()))) {
            this.uniqueLit.add(C.getRHS().getNameAsString());
        }

        //sort them in natural order ("X2" < "X3", etc.)
        this.uniqueLit.sort(Comparator.naturalOrder());

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
            //this.CL.get(i)
            //gives the current clause
            
            //this.CL.get(i).getLHS().getNameAsString()
            //prints the LHS of the current Clause as string

            //find the index of the above string within the list of unique Literals

            boolean LHSBool = Vals[this.uniqueLit.indexOf(this.CL.get(i).getLHS().getNameAsString())];
            boolean RHSBool = Vals[this.uniqueLit.indexOf(this.CL.get(i).getRHS().getNameAsString())];

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