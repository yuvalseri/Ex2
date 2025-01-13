package ex2;
// Add your documentation below:

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SCell implements Cell {
    private String line;
    private int type;
    private int order;
    private boolean isBeingCalculated = false;
    //private List<SCell> dependC;
    public ArrayList<SCell> dependC;
    public SCell(String s) {
        // Add your code here
        this.order=0;
        this.line=s;
        setData(s);
        this.type=getType();
        this.dependC= new ArrayList<>();
        if (this.type == Ex2Utils.FORM) {
            this.dependC = (ArrayList<SCell>) SCell.getDependencies(s);
            System.out.println("Dependencies for " + s + ": " + this.dependC);
        }
        else{
            this.dependC = new ArrayList<>();
        }
    }
    public static boolean isNumber(String content) {
        if (content == null || content.isEmpty()) {
            return false;
        }

        for (char c : content.toCharArray()) {
            if (!Character.isDigit(c) && c != '.') {
                return false;
            }
        }
        return true;
    }

    public static boolean isForm(String content) {
        if (content == null || content.isEmpty()) {
            return false;
        }

        if (content.charAt(0) != '=') {// formula must start in '='
            return false;
        }
        if(content.charAt(0) == '=') {
            String formula = content.substring(1);
            if (formula.contains("()")) {
                return false; // if there are empty parentheses
            }

            if (content.contains("[") && (!(content.contains("]"))) || content.contains("]") && (!(content.contains("[")))) {
                return false;
            }

            formula = content.substring(1);
            if (formula.isEmpty()) {// if the formula is empty after the '='
                return false;
            }

            String validChars = "0123456789.+-/*()ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            String operators = "+-*/";

            int openParentheses = 0;

            for (int i = 0; i < formula.length(); i++) { // loop passes all the chars in the string
                char ch = formula.charAt(i);

                if (validChars.indexOf(ch) == -1) { // there is an invalid char
                    return false;
                }

                if (ch == '(') {
                    openParentheses++;
                } else if (ch == ')') {
                    openParentheses--;
                }
                if (openParentheses < 0) { //the number of ')' is greater than the number of '('
                    return false; //
                }
                if (Character.isLetter(ch)) { //if the char is a letter
                    if (i + 1 < formula.length() && Character.isLetter(formula.charAt(i + 1))) {//if the next char is also letter
                        return false;//the formula is invalid
                    } else if (i + 1 < formula.length() && Character.isDigit(formula.charAt(i + 1))) {//if after the letter there is a digit
                        int count = 1;// counter of the digits
                        for (int j = i + 2; j < formula.length(); j++) { // a loop passes all the string from the char that after the first digit
                            if (Character.isDigit(formula.charAt(j))) {// if the char is digit
                                count++; // update the count by 1
                            } else {
                                break;
                            }
                            if (count > 2) { //if after the letter there is a number that bigger than 99
                                return false; // the formula is invalid
                            }
                        }

                    }
                    continue;
                }

                if (operators.indexOf(ch) >= 0) {// if the char is operator
                    if (i + 1 < formula.length() && operators.indexOf(formula.charAt(i + 1)) >= 0) { // the next char is operator
                        return false;// the formula is invalid
                    }
                }
            }
            if (Character.isLetter(formula.charAt(formula.length() - 1)) || operators.indexOf(formula.charAt(formula.length() - 1)) >= 0) { // if the last char of the formula is invalid last char
                return false;// the formula is invalid
            }
            if (openParentheses != 0) {//if there are parentheses that aren't close
                return false; // the formula is invalid
            }
        }
    return true;
    }

    public static boolean isText(String content){
        boolean ans = false;
        if (content == null || content.toString().isEmpty() || content== " ") {
            return ans;
        }
        else if(!isNumber(content) && content.indexOf("=") !=0){
            ans= true;
        }
    return ans;
    }


    @Override
    public int getOrder() {

        // Add your code here
        if(type== Ex2Utils.NUMBER || type== Ex2Utils.TEXT){ // if the type of the cell is text or number
        return 0;} // the depth is 0
        if (isBeingCalculated) { //if the cell in calculating process
            throw new IllegalStateException("Circular dependency detected!");
        }
        else if(type== Ex2Utils.FORM){
            isBeingCalculated = true; //
            int max=0;

            for(SCell cell : dependC){
               max= Math.max(max, cell.getOrder()); // update max to the max value dependence
            }
            isBeingCalculated = false; // the calculating process is over
            int order= 1+ max; // udapting the order by 1
            return order;
        }
    return -1;
    }

    @Override
    public String toString() {
        return getData();

    }

    @Override
public void setData(String s) {
        // Add your code here
        this.line = s;
        if(isNumber(s)){
            this.type= Ex2Utils.NUMBER;
        }
        if(isText(s)){
            this.type= Ex2Utils.TEXT;
        }
        if(isForm(s)){
            this.type= Ex2Utils.FORM;
        }
        else if(!(isNumber(s)) && !(isText(s)) && !(isForm(s))){
            this.type= Ex2Utils.ERR_FORM_FORMAT;
        }


        /////////////////////
    }
    @Override
    public String getData() {
        return line;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int t) {
        this.type = t;
    }

    @Override
    public void setOrder(int t) {
        // Add your code here
       this.order= t;
    }
    public static List<SCell> getDependencies(String formula) {
        List<SCell> dependencies = new ArrayList<>(); //creating a list which will contain the dependencies of the cell

        if (formula.indexOf("=") == 0) { // if the first char is '='
            String[] tokens = formula.substring(1).split("[*+\\-/()]"); //spliting the formula by the operators
            for (String token : tokens) { //a loop passes all the parts of the split formula
                if (isValidCellReference(token)) { //if is valid cell reference
                    dependencies.add(new SCell(token)); // adding the token to the list
                }
            }
        }

        return dependencies; //return the list of dependencies
    }

    public static boolean isValidCellReference(String token) {
        return token.matches("[A-Za-z]\\d+"); // valid reference is a letter and then number
    }
}
