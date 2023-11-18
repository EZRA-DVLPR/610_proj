// Isaiah Martinez
// Project 2
// Approximation Alg for Max 2-SAT

/**************************************************************************************************************/

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

class Project2 {
    //Note that these two values get set only after bruteForceIterative done
    static int maxTrue;
    static int approxTrue;
    static boolean[] boolValsActual;
    static boolean[] boolValsApprox;

    public static void main (String[] args) throws Exception{
        
        //make the clause list
        ClauseList CL = makeClauseList("input.txt");
        
        approximate(CL);
        
        System.out.println(approxTrue);

        printboolValsActual(CL, boolValsApprox);

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

    //The approx algorithm used to approximate the max # of true possible clauses
    //Input: ClauseList CL
    //Output: An int indicating the max number of possible true clauses
    public static int approximate (ClauseList CL) {

        Dictionary<String, Integer> literalCounts = new Hashtable<>();
        String[] allLit = CL.getAllLiterals();
        String[] uniqueLit = CL.getUniqueLiterals();

        // for each unique Literal, add its number of occurrences to the hashtable
        // also add its number of negated occurrences
        
        //populate literalCounts
        for (String lit : allLit) {
            literalCounts.put(lit, CL.getNumberOccurences(lit));
        }

        //remove values from dictionary if they are less than their literal counterpart
        for (int i = 0; i < uniqueLit.length; i++) {
            if ((null == literalCounts.get("X" + uniqueLit[i])) || (null == literalCounts.get("!X" + uniqueLit[i]))){
                //we will use that # Occurrences since the (non)negated version DNE
            } else {
                //both notNegated and Negated exist so we compare the two values
                boolean compare = literalCounts.get("X" + uniqueLit[i]) >= literalCounts.get("!X" + uniqueLit[i]);
                
                //drop the lower of the two
                if (compare) {
                    // notNegated > Negated
                    literalCounts.remove("!X" + uniqueLit[i]);
                } else {
                    // notNegated < Negated
                    literalCounts.remove("X" + uniqueLit[i]);
                }
            }
        }

        //literalCounts has only the highest occurring literals within the CL

        boolean[] boolArr = new boolean[literalCounts.size()];
        int index = 0;

        //populate boolArr with T/F if the hashmap has no`!`/has a `!`
        for (int i = 0; i < uniqueLit.length; i++) {
            if (null == literalCounts.get("X" + uniqueLit[i])) {
                //is negated
                boolArr[index] = false;
            } else {
                //is not negated
                boolArr[index] = true;
            }
            index++;
        }

        boolValsApprox = boolArr.clone();
        approxTrue = CL.getNumberTrueClauses(boolValsApprox);

        return approxTrue;
    }



    //Automatically updates maxTrue and boolValsActual to their respective values
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

            //update global variable boolValsActual and update local variable max if calculated max is >= max
            if (CL.getNumberTrueClauses(boolArr) >= max) {
                max = CL.getNumberTrueClauses(boolArr);
                boolValsActual = boolArr.clone();
            }
        }

        //update global variable maxTrue
        maxTrue = max;
        return max;
    }

    //Input: a ClauseList
    //Output: N/A
    //Prints truth assignments output desired for project submission
    public static void printboolValsActual (ClauseList CL, boolean[] boolArr) {

        String[] uniqueLits = CL.getUniqueLiterals();

        //used for searching the list of strings
        List<String> uniqueLiterals = Arrays.asList(uniqueLits);

        if (!(boolArr == null)) {
            int boolValIndex = 0;
            for (int i = 1; i <= Integer.parseInt(uniqueLits[uniqueLits.length - 1]) ; i++) {
                //i represents all possible k's from 1 to max Numbered Literal
                //if uniqueLiterals containes i as a string, then we print the associated
                //  boolean value from boolArr
                if (uniqueLiterals.contains(Integer.toString(i))) {
                    //Convert True/False to T/F for printing purposes
                    if (boolArr[boolValIndex]) {
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
