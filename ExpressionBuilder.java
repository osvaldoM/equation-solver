
package expressionbuilder;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 *
 * @author OsvaldoMaria
 *
 * this class wont consider operators precedence so plz use parenthesis
 * contains basic methods for evaluating mathematical expressions any
 * improvement will be appreciated
 */
public class ExpressionBuilder {

    private String expression;
    final private Stack<String> operators;
    final private Queue<String> output;
    final private List<String> opAllowed; //list of allowed operators
    final private List<String> funAllowed; //list of allowed functions

    /**
     * initializes object with expression value
     *
     * @param expression the value for the expression
     *
     */
    public ExpressionBuilder(String expression) {
        operators = new Stack<>();
        output = new ArrayDeque<>();
        opAllowed = new LinkedList<>();
        funAllowed = new LinkedList<>();
        opAllowed.add("+");
        opAllowed.add("-");
        opAllowed.add("*");
        opAllowed.add("/");
        opAllowed.add("^");
        funAllowed.add("sqrt");
        funAllowed.add("log");
        funAllowed.add("sin");
        funAllowed.add("cos");
        funAllowed.add("abs");
        this.expression = expression;
    }

    /**
     * initializes object without expression value this value can be set later
     */
    public ExpressionBuilder() {
        operators = new Stack<>();
        output = new ArrayDeque<>();
        opAllowed = new LinkedList<>();
        funAllowed = new LinkedList<>();
        opAllowed.add("+");
        opAllowed.add("-");
        opAllowed.add("*");
        opAllowed.add("/");
        opAllowed.add("^");
        funAllowed.add("sqrt");
        funAllowed.add("log");
        funAllowed.add("sin");
        funAllowed.add("cos");
        funAllowed.add("abs");
        this.expression = null;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * evaluates and solves the expression
     *
     * @return a double result
     */
    public double solve() {
        return solve(rpn(expression));
    }

    /**
     * transforms the expression from infix to postfix notation
     *
     * @return the expression transformed.
     */
    public String rpn() {
        if (expression == null) {
            return "";
        }
        return rpn(expression).toString();
    }

    /**
     * assigns a value to variables in the expression
     *
     * @param x the variable to be assigned a value
     * @param value the value for the variable
     */
    public void assignValue(char x, double value) {
        expression=expression.replace(String.valueOf(x), String.valueOf(value));
    }

    /**
     * checks if it is a valid expression
     *
     * @param tokens Array with values to be tested
     * @return true if the array is valid
     */
    private boolean isValid(String[] tokens) {
        //
        for (int i = 0; i < tokens.length; i++) {
            if (!(opAllowed.contains(tokens[i]) || funAllowed.contains(tokens[i]) || isDouble(tokens[i]) || tokens[i].equals("(") || tokens[i].equals(")"))) {
                return false;
            }
        }
        return true;
    }

    /**
     * tests if the expression is valid and transforms it
     *
     * @param exp expression to be transformed
     * @return a list with the values in reverse polish notation
     */
    private List<String> rpn(String exp) {
        String[] tokens = exp.split(" ");
        if (isValid(tokens)) {
            return rpn(tokens);
        } else {
            throw new IllegalArgumentException("check your expression dude!");
        }
    }

    /**
     * does all the real job
     *
     * @param tokens array with single values
     * @return a list with the values in reverse polish notation
     */
    private List<String> rpn(String[] tokens) {
        String top = "";
        for (int i = 0; i < tokens.length; i++) {
            String current = tokens[i];
            //if number
            if (isDouble(current)) {
                output.add(current);
            } //if function ... eg. sqrt
            else if (funAllowed.contains(current)) {
                operators.push(current);
            } //if operator
            else if (opAllowed.contains(current)) {

                if (operators.size() != 0) {
                    top = operators.peek();
                    while (opAllowed.contains(top) && (!operators.isEmpty())) {
                        output.add(operators.pop());
                    }
                }
                operators.push(current);
            } //if left paenthesis
            else if ("(".equals(current)) {
                operators.push(current);
            } //if right parenthesis
            else if (")".equals(current)) {
                //still have to add if(operators.contains("(")) to check if parenthesis have been open
                while (!operators.peek().equals("(")) {
                    if (opAllowed.contains(operators.peek())) {
                        output.add(operators.pop());
                    }
                }
                if (!"(".equals(operators.peek())) {
                    throw new UnsupportedOperationException("Bruh... check your parenthesis");
                } else {
                    operators.pop();
                }

                if (!operators.isEmpty() && funAllowed.contains(operators.peek())) {
                    output.add(operators.pop());
                }

            }

        }
        while (!operators.isEmpty()) {
            top = operators.pop();
            if ((top.equals(")") || top.equals("("))) {
                //missmatched parenthesis   
                throw new IllegalArgumentException("looks like you have more parenthesis than expected");  
            }
            output.add(top);
        }
        List<String> l = new LinkedList<>();
        l.addAll(output);
        return l;
    }

    /**
     * implementation of regEx adapted from
     * http://docs.oracle.com/javase/6/docs/api/java/lang/Double.html#valueOf%28java.lang.String%29
     * to check if a value is double
     *
     * @param s the value to be tested
     * @return if is double
     */
    private boolean isDouble(String s) {
        final String Digits = "(\\p{Digit}+)";
        final String HexDigits = "(\\p{XDigit}+)";
        final String Exp = "[eE][+-]?" + Digits;
        final String fpRegex
                = ("[\\x00-\\x20]*" + "[+-]?(" + "NaN|"
                + "Infinity|" + "(((" + Digits + "(\\.)?(" + Digits + "?)(" + Exp + ")?)|"
                + "(\\.(" + Digits + ")(" + Exp + ")?)|"
                + "(("
                + "(0[xX]" + HexDigits + "(\\.)?)|"
                + "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")"
                + ")[pP][+-]?" + Digits + "))"
                + "[fFdD]?))"
                + "[\\x00-\\x20]*");

        if (Pattern.matches(fpRegex, s)) {
            return true; // Will not throw NumberFormatException
        }
        return false;
    }

    /**
     * final round.. solve the expression
     *
     * @param rpnExp expression in reverse polish notation
     * @return a double result to equation
     */
    private double solve(List<String> rpnExp) {
        double result = 0;
        double n1, n2;
        Stack<Double> temp = new Stack<>();
        for (String aux : rpnExp) {
            if (isDouble(aux)) {
                temp.push(Double.parseDouble(aux));
            } else if (opAllowed.contains(aux)) {
                n1 = temp.pop();
                n2 = temp.pop();
                switch (aux) {
                    case "+":
                        temp.push(n1 + n2);
                        break;
                    case "-":
                        temp.push(n2 - n1);
                        break;

                    case "*":
                        temp.push(n1 * n2);
                        break;

                    case "/":
                        temp.push(n2 / n1);
                        break;

                    case "^":
                        temp.push(Math.pow(n2, n1));
                        break;
                }
            } else {
                n1 = temp.pop();
                switch (aux) {
                    case "sqrt":
                        temp.push(Math.sqrt(n1));
                        break;
                    case "log":
                        temp.push(Math.log(n1));
                        break;

                    case "sin":
                        temp.push(Math.sin(n1));
                        break;

                    case "cos":
                        temp.push(Math.cos(n1));
                        break;

                    case "abs":
                        temp.push(Math.abs(n1));
                        break;
                }

            }

        }
        return temp.pop();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //test case 1
        System.out.println("***************-----------------------------test case 1------------------------------***************************");
        String inp="x + y + sqrt ( 25 ) - 3";

        
        ExpressionBuilder eb = new ExpressionBuilder(inp);
        System.out.println("input : " + eb.getExpression());
        eb.assignValue('x', 5);
        eb.assignValue('y', 3);
        System.out.println("rpn :" + eb.rpn());
        System.out.println("result :" + eb.solve());

        
        //test case 2
        System.out.println("***************-----------------------------test case 2------------------------------***************************");

        inp="z ^ ( 3 ) - y";
        eb = new ExpressionBuilder(inp);
        System.out.println("input : " + eb.getExpression());
        eb.assignValue('z', 2);
        eb.assignValue('y', 3);
        System.out.println("rpn :" + eb.rpn());
        System.out.println("result :" + eb.solve());
    }

}
