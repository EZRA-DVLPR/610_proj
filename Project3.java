// Isaiah Martinez
// Project 3
// Max 2-SAT Local Search

/**************************************************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Project3 {
    //Note that these two values get set only after bruteForceIterative done
    static int numTrue;
    static boolean[] boolVals;

    public static void main (String[] args) throws Exception{
        
        //make the clause list
        ClauseList CL = makeClauseList("input.txt");

        localSearch(CL);

        System.out.println(numTrue);

        printBoolVals(CL);
    }

    //Input: a ClauseList
    //Output: an integer indicating the number of true clauses
    //      also updates boolVals with the corresponding truth assignments
    public static int localSearch (ClauseList CL) {

        //initial config => all variables are true
        String[] uniqueLits = CL.getUniqueLiterals();
        boolVals = new boolean[uniqueLits.length];
        for (int i = 0; i < boolVals.length; i++) {
            boolVals[i] = true;
        }

        //initiliaze variables for while loop
        int currMax = CL.getNumberTrueClauses(boolVals);
        int index = 0;

        //look for next config that produces higher number of truthful clauses (currMax)
        while (index < boolVals.length) {

            //tempVals is the same as boolVals except the current index is flipped
            boolean[] tempVals = boolVals.clone();
            tempVals[index] = !tempVals[index];

            //compare possMax (based on tempVals) and currMax
            int possMax = CL.getNumberTrueClauses(tempVals);
            if (possMax > currMax) {
                //update currMax and boolVals
                currMax = possMax;
                boolVals = tempVals.clone();

                //reset index to start from first variable
                index = 0;
            } else {
                //increment index
                index++;
            }
        } //no further increases to currMax
        
        numTrue = currMax;
        return numTrue;
    }

    //Driver function for `makeClauseList`
    //wraps in try-catch to catch exceptions
    //Input: a string containing the path to a file
    //Output: a ClauseList made from the file
    public static ClauseList makeClauseList (String filename) {
        try {
            ClauseList CL = clauseListGenerator(filename);
            return CL;
        } catch (Exception e) {
            System.out.println("Something went wrong reading the file.");
            System.out.println(e);
            ClauseList EmptyCL = new ClauseList();
            return EmptyCL;
        } 
    }

    //Input: a string containing the path to a file
    //Output: a ClauseList made from the file or throws an exception
    public static ClauseList clauseListGenerator (String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        ClauseList CL = new ClauseList();

        //determine the amount of lines to read in from the first line read
        String numClauses = sc.nextLine();

        //create the ClauseList by reading each of the lines after the first
        for (int i = 0; i < Integer.parseInt(numClauses); i++) {
            CL.addClause(getClause(sc.nextLine()));
        }
        
        //close scanner and return
        sc.close();
        return CL;
    }

    //Input: string of the form "INT [WHITESPACE] INT"
    //Output: a clause made from the given input, making Literals from the `INT`'s
    public static Clause getClause (String fileLine) {
        String[] line =  fileLine.split("\\s+");
        Literal l1 = new Literal(Integer.parseInt(line[0]));
        Literal l2 = new Literal(Integer.parseInt(line[1]));
        return new Clause(l1, l2);
    }

    //Input: a ClauseList
    //Output: N/A
    //Prints all clauses within given ClauseList
    public static void printAllClauses (ClauseList CL) {
        System.out.println("Print all the clauses:");
        CL.printClauseList();
        System.out.println();
    }

    //Input: a ClauseList
    //Output: N/A
    //Prints all Literals within given ClauseList
    public static void printAllLiterals (ClauseList CL) {
        System.out.println("Print all unique literals:");
        CL.printUniqueLiterals();
        System.out.println();
    }

    //Input: a ClauseList
    //Output: N/A
    //Prints truth assignments output desired for project submission
    public static void printBoolVals (ClauseList CL) {

        String[] uniqueLits = CL.getUniqueLiterals();

        //used for searching the list of strings
        List<String> uniqueLiterals = Arrays.asList(uniqueLits);

        if (!(boolVals == null)) {
            int boolValIndex = 0;
            for (int i = 1; i <= Integer.parseInt(uniqueLits[uniqueLits.length - 1]) ; i++) {
                //i represents all possible k's from 1 to max Numbered Literal
                //if uniqueLiterals containes i as a string, then we print the associated
                //  boolean value from boolVals
                if (uniqueLiterals.contains(Integer.toString(i))) {
                    //Convert True/False to T/F for printing purposes
                    if (boolVals[boolValIndex]) {
                        System.out.print("T");
                    } else {
                        System.out.print("F");
                    }
                    boolValIndex++;
                    
                } else {
                    //else we automatically print T
                    System.out.print("T");
                }
            }
        }
    }
}