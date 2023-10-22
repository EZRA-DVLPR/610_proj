import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Project {
    public static void main (String[] args) throws Exception{
        try {
            ClauseList CL = makeClauseList("input.txt");
            CL.printClauseList();

            //Find the maximum amount of truth assignments given the CL

                //Brute-force (recursive)
                

                //Brute-force (non-recursive)

                    //find the max value for the

                //Alternative Ideas:
                /*
                    Scan for equivalent Clauses
                    Scan for contradictory Clauses
                */
        } catch (Exception e) {
            System.out.println("Something went wrong with reading the file.");
            System.out.println(e);
        }
    }

    
    //Input: string of the form "INT [WHITESPACE] INT"
    //Output: a clause made from the given input, making Literals from the `INT`'s
    public static Clause getClause (String fileLine) {
        String[] line =  fileLine.split("\\s+");
        Literal l1 = new Literal(Integer.parseInt(line[0]));
        Literal l2 = new Literal(Integer.parseInt(line[1]));
        return new Clause(l1, l2);
    }

    //Input: a string containing the path to a file
    //Output: a ClauseList made from the file
    public static ClauseList makeClauseList (String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        ClauseList CL = new ClauseList();

        while (sc.hasNextLine()) {
            CL.addClause(getClause(sc.nextLine()));
        }
        sc.close();
        
        return CL;
    }
}