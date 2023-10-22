# COMP 610 Project Description

Construct a Max 2-SAT solver that will:
* Read a file containing two values per line, separated by whitespace, with a finite amount of lines.
* If the value is negative, then it represents the literal of the negated variable with that number (eg. -1 indicates !x1).
* Otherwise the value represents the literal of that number (eg. 2 indicates x2).
* Interpret the clauses given on each line (eg. -1 2 indicates !x1 || x2).
* Find the maximum amount of truthful clauses for the given list of clauses after assigning boolean values to the variables

## See input.txt for an example of given input

The 2-SAT problem given from input.txt is:
( x3 ||  x2) ^
( x1 ||  x3) ^
(!x2 || !x1) ^
(!x3 || !x2) ^
( x1 ||  x2)