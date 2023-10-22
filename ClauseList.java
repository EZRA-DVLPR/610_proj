import java.util.ArrayList;

public class ClauseList {
    ArrayList<Clause> CL;

    public ClauseList () {
        this.CL = new ArrayList<Clause>();
    }

    public Clause addClause (Clause C) {
        this.CL.add(C);
        return C;
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