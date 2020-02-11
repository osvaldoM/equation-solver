# Solution for mozdevz challenge from 2016

I was still a student when I did this, so it probably has many naive mistakes and contributions are welcome. Nevertheless, it was lots of fun and it works.

---

Prepared by: Abdul Mueid Akhtar (abdul.mueid@gmail.com)

# Challenge: Equation Solver

**Description:**

1. Create a program that takes an equation as string and solves for an answer.
2. Equation can contain variables which need to be assigned before solving.
3. The program should be able to compute the following operations
    - . +
    - . -
    - . /
    - . *
    - . ^
    - . sqrt
    - . log
    - . sin
    - . cos
    - . abs
4. Create a class Expression using the interface definition below. You may add more methods and properties as
    needed.

```
class Expression
{
public:
Expression(string equation);
bool assignValue(char variable, double value);
double solve();
private:
string equation;
}; (^)
```

5. Test your class against the following test cases (Pseudocode):

**TEST 1**

```
// Create a new Expression
Expression exp = new Expression('x + y + sqrt 25 - 3 ');
```
```
// Assign Values to variables
exp->assignValue(x,5);
exp->assignValue(y,3);
```
```
// Solve the expression
result = exp->solve();
```
```
// Print the result to screen
```
print result; // Answer should be 10 (^)


Prepared by: Abdul Mueid Akhtar (abdul.mueid@gmail.com)

## TEST 2

```
// Create a new Expression
Expression exp = new Expression('z ^ 3 - y');
```
```
// Assign Values to variables
exp->assignValue(y, 3 );
exp->assignValue(z, 2 );
```
```
// Solve the expression
result = exp->solve();
```
```
// Print the result to screen
```
print result; // Answer should be 5 (^)
**Rules:**
Use any language you are comfortable with.
Be honest and do not copy code as is. Try to understand the challenge. It will be more fun.
Discuss the challenge with friends and help others understand it as well.
Bonus points for clean code, relevant comments, correct exception handling and additional test cases.
Code will be checked against what’s out there and cheaters will be disqualified.
**Recommended Reading:**
https://en.wikipedia.org/wiki/Shunting-yard_algorithm
[http://www.cs.utexas.edu/~EWD/MCReps/MR35.PDF](http://www.cs.utexas.edu/~EWD/MCReps/MR35.PDF)

# Happy Hacking!



