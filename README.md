# Description

This repo contains all of the projects assigned in my Comp 610 Class at CSUN Fall 2023. Below are the descriptions of each project.
The file structure of this repo is as follows:
1 folder per project
All folders are envisioned to be separate from another, so there may be duplicate files when looking at the different folders.

There will be a total of 3 projects for this class, thus the basic structure is:
* Project1 (See:)
[Project 1 Description](#comp-610-project-1-description)
* Project2 (See:)
[Project 2 Description](#comp-610-project-2-description)
* Project3 (See:)
[Project 3 Description](#comp-610-project-3-description)

# COMP 610 Project 1 Description

Construct a Max 2-SAT solver that will:
* Read a file containing:
    * The first line containing the number of clauses given
    * all other lines containing two integer values per line, separated by whitespace (There will be however many the first line says).
* If the given integer value is negative, then it represents the literal of the negated variable with that number (eg. -1 indicates !x1).
* Otherwise the value represents the literal of that number (eg. 2 indicates x2).
* Interpret the clauses given on each line (eg. -1 2 indicates !x1 || x2).
* Find the maximum amount of truthful clauses for the given list of clauses after assigning boolean values to the variables.
* Truth assignments are from the range 1 $\` \leq \`$ i $\` \leq \`$ k. Where k is the largest integer variable mentioned (eg. for input.txt k = 4). 
* Output an integer defining the maximum amount of truthful clauses possible, as well as the truth assignments that achieve this amount.(eg. 4 TTTT).

### Additional Notes

See: 
* [Max SAT](https://en.wikipedia.org/wiki/Maximum_satisfiability_problem)
* [2-SAT](https://en.wikipedia.org/wiki/2-satisfiability)
* [Max 2-SAT](https://en.wikipedia.org/wiki/2-satisfiability#Maximum-2-satisfiability)

## See input.txt for an example of given input

The 2-SAT problem given from input.txt is:
( x2 || !x4) &&
( x3 || !x2) &&
( x4 ||  x2) &&
( x4 || !x2) &&
(!x2 || !x4)

which yields an output of:
4
TTTT

indicating that there is a maximum possible number of true clauses of 4, as well as the truth value assignments to variables x1, x2, x3, x4 respectively as TTTT

> [!NOTE]
> x1 does not appear in the input, yet it's output was still yielded as T.
> When such a variable/literal is encountered, it will be handled similarly.

## See input2.txt for another example of given input

The 2-SAT problem given from input2.txt is:
( x1 ||  x2) &&
(!x1 ||  x2) &&
( x2 ||  x4) &&
( x1 || !x2) &&
(!x1 || !x2) &&
( x1 ||  x3) &&
( x1 || !x3) &&
(!x2 || !x4) &&
(!x1 ||  x3) &&
(!x1 || !x3)

which yields an output of:
8
TTTF

indicating that there is a maximum possible number of true clauses of 8, as well as the truth value assignments to variables x1, x2, x3, x4 respectively as TTTF

> [!NOTE]
> x1 through x4 are defined and used in the output. Notice it contains the truth assignments needed to attain the max SAT.


# COMP 610 Project 2 Description

...

# COMP 610 Project 3 Description

...