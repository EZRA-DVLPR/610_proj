// Isaiah Martinez
// Project 1
// Max 2-SAT Brute Force

/**************************************************************************************************************/

import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Project1 {
    //Note that these two values get set only after bruteForceIterative done
    static int maxTrue;
    static boolean[] boolVals;

    public static void main (String[] args) throws Exception{
        
        //make the clause list
        ClauseList CL = makeClauseList("input.txt");

        bruteForceIterative(CL);

        System.out.println(maxTrue);

        printBoolVals(CL);

        // System.out.println("Brute Force Recursion gives us a maximum number of True clauses with");
        // System.out.println(bruteForceRecursive(CL));
        // System.out.println();

        // System.out.println("Brute Force Iteration gives us a maximum number of True clauses with");
        // System.out.println(bruteForceIterative(CL));
        // System.out.println();
        
        //printAllClauses(CL);
        //printAllLiterals(CL);
        //testTrueClauses(CL);
        //testEquivalences(CL);
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

    //Automatically updates maxTrue and boolVals to their respective values
    //Input: ClauseList CL to brute Force Iteratively find the max number of possible true clauses
    //Output: An int indicating the max number of possible true clauses
    public static int bruteForceIterative (ClauseList CL) {

        if (CL.getLength() <= 0) {
            return -1;
        }

        int max = -1;
        String[] uniqueLit = CL.getUniqueLiterals();
        boolean[] boolArr = new boolean[uniqueLit.length];

        //# of bits needed to represent total amount of possibilites for bools
        int numBits = uniqueLit.length;
        
        //calculate the binary representation for all possible T/F values given the # of literals
        //then use the binary representation to populate BoolArr
        //0 - False ; 1 - True
        for (int currNum = 0; currNum < Math.pow(2, numBits); currNum++) {
            String binary = Integer.toBinaryString(currNum);
            
            //Pad with leading zeros
            //Note that by printing `binary` we will see what values the variables must have to be true/false
            //      for a particular step (including the maximum)
            while (binary.length() < numBits) {
                binary = "0" + binary;
            }

            //populate boolArr
            for (int i = 0; i < binary.length(); i++) {
                char c = binary.charAt(i);
                if (c == '0') {
                    boolArr[i] = false;
                } else {
                    boolArr[i] = true;
                }
            }

            //update global variable boolVals and update local variable max if calculated max is >= max
            if (CL.getNumberTrueClauses(boolArr) >= max) {
                max = CL.getNumberTrueClauses(boolArr);
                boolVals = boolArr.clone();
            }
        }

        //update global variable maxTrue
        maxTrue = max;
        return max;
    }

    //Driver function for bruteForceRecursive
    //Input: a ClauseList
    //Output: an int representing the maximum # of true clauses possible
    public static int bruteForceRecursive (ClauseList CL) {
        if (CL.getLength() <= 0) {
            return -1;
        } else {
            String[] uniqueLit = CL.getUniqueLiterals();
            boolean[] boolArr = new boolean[uniqueLit.length];
            return bruteForceRecursive(CL, uniqueLit, boolArr, 0, -1);
        }
    }

    //Helper Function for bruteForceRecursive
    //Input: a ClauseList, String[] of Unique Literals from the CL, boolean[] boolArr which holds the boolean
    //        values that will be used, currBoolInd indicating the index of the current bool to modify, and currMax 
    //Output: an integer representing the max number of true clauses possible from the given CL
    public static int bruteForceRecursive (ClauseList CL, String[] uniqueLit, boolean[] boolArr, int currBoolInd, int currMax) {
        if (uniqueLit.length != 1) {
            //add a boolean value to the boolList

            //try true
            boolArr[currBoolInd] = true;
            int newMax = bruteForceRecursive(CL, Arrays.copyOfRange(uniqueLit, 1, uniqueLit.length), boolArr, currBoolInd + 1, currMax);
            currMax = Math.max(newMax, currMax);

            //try false
            boolArr[currBoolInd] = false;
            newMax = bruteForceRecursive(CL, Arrays.copyOfRange(uniqueLit, 1, uniqueLit.length), boolArr, currBoolInd + 1, currMax);
            currMax = Math.max(newMax, currMax);
        } else {
            //only 1 elt left. Assign true then evaluate the CL. Do the same for false.
            boolArr[currBoolInd] = true;
            int numIfTrue = CL.getNumberTrueClauses(boolArr);

            boolArr[currBoolInd] = false;
            int numIfFalse = CL.getNumberTrueClauses(boolArr);

            //currMax becomes the max of the 3 values
            currMax = Math.max(Math.max(currMax, numIfTrue), numIfFalse);
        }
        return currMax;
    }

    //Input: a ClauseList
    //Output: N/A
    //Prints Out the evaluation for Pre-determined Tests. Evaluation should be: True, True, False, False.
    public static void testEquivalences (ClauseList CL) {
        System.out.println("True Clause equivalence Tests:");
        System.out.println("Comparing CL[0] to CL[0]");
        System.out.println(Clause.equivClause(CL.getClause(0), CL.getClause(0)));
        System.out.println("Comparing CL[0] to a new clause (X2 || !X4)");
        System.out.println(Clause.equivClause(CL.getClause(0), new Clause(new Literal(2), new Literal(-4))));
        System.out.println();

        System.out.println("False Clause equivalence Tests:");
        System.out.println("Comparing CL[0] to CL[1]");
        System.out.println(Clause.equivClause(CL.getClause(0), CL.getClause(1)));
        System.out.println("Comparing CL[0] to a new clause (!X2 || !X3)");
        System.out.println(Clause.equivClause(CL.getClause(0), new Clause(new Literal(-2), new Literal(-3))));
        System.out.println();
    }

    //Input: a ClauseList
    //Output: an int representing the maximum # of true clauses possible
    public static void testTrueClauses (ClauseList CL) {
        boolean[] Vals = {true, false, false};
        System.out.println("Finding the number of true clauses given the boolean values (t,f,f) for (x2,x3,x4) respectively");
        System.out.println(CL.getNumberTrueClauses(Vals)); 
        System.out.println();
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