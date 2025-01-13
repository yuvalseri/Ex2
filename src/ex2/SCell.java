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
        this.order= order;
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

        /*boolean lastCharWasOperator = false; // the former char is an operator
        boolean lastCharWasLetter = false;   // the former char is a letter
        boolean expectingNumberAfterLetter = false; // we expect to a number after a letter
        StringBuilder numberBuffer = new StringBuilder();
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

                if (Character.isLetter(ch)) {
                if (lastCharWasLetter) { // if there are two adjoint letters
                    return false; //
                }
                lastCharWasLetter = true;
                expectingNumberAfterLetter = true;
                numberBuffer.setLength(0); // reset
                continue;
            }

                if (Character.isDigit(ch)) {
                   if (expectingNumberAfterLetter) {
                       numberBuffer.append(ch);
                       if (numberBuffer.length() > 2) {
                           return false; // the number after the letter is grater than 99
                       }
                    lastCharWasLetter = false;
                    expectingNumberAfterLetter = false; // reset
                       continue;
                }
                   lastCharWasOperator= false;
            }

                if (operators.indexOf(ch) != -1) {
                if (lastCharWasOperator) {
                    return false; // two adjoint operators
                }
                lastCharWasOperator = true;
                lastCharWasLetter = false;
                expectingNumberAfterLetter = false;
            } else {
                lastCharWasOperator = false;
            }
        }
        if (openParentheses != 0) {
            return false; // if there are parentheses that aren't close
        }

        if (lastCharWasLetter || lastCharWasOperator) {// if the last char of the formula is invalid last char
            return false;
        }

        return true;*/

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
                if (Character.isLetter(ch)) {
                    if (i + 1 < formula.length() && Character.isLetter(formula.charAt(i + 1))) {
                        return false;
                    } else if (i + 1 < formula.length() && Character.isDigit(formula.charAt(i + 1))) {
                        int count = 1;
                        for (int j = i + 2; j < formula.length(); j++) {
                            if (Character.isDigit(formula.charAt(j))) {
                                count++;
                            } else {
                                break; // עצור אם הגעת לתו שאינו ספרה
                            }
                            if (count > 2) {
                                return false;
                            }
                        }

                    }
                    continue;
                }

                if (operators.indexOf(ch) >= 0) {
                    if (i + 1 < formula.length() && operators.indexOf(formula.charAt(i + 1)) >= 0) {
                        return false;
                    }
                }
            }
            if (Character.isLetter(formula.charAt(formula.length() - 1)) || operators.indexOf(formula.charAt(formula.length() - 1)) >= 0) { // if the last char of the formula is invalid last char
                return false;
            }
            if (openParentheses != 0) {
                return false; // if there are parentheses that aren't close
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
            //else if(!isNumber(content) && !isForm(content)){
             //ans= true;
    }
    return ans;
    }


    @Override
    public int getOrder() {

        // Add your code here
        if(type== Ex2Utils.NUMBER || type== Ex2Utils.TEXT){
        return 0;}
        if (isBeingCalculated) {
            throw new IllegalStateException("Circular dependency detected!");
        }
        else if(type== Ex2Utils.FORM){
            isBeingCalculated = true;
            int max=0;

            for(SCell cell : dependC){
               max= Math.max(max, cell.getOrder());
            }
            isBeingCalculated = false;
            int order= 1+ max;
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

        //}
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
        List<SCell> dependencies = new ArrayList<>();

        if (formula.indexOf("=") == 0) {
            String[] tokens = formula.substring(1).split("[*+\\-/()]");
            for (String token : tokens) {
                if (isValidCellReference(token)) {
                    dependencies.add(new SCell(token));
                }
            }
        }

        return dependencies;
    }

    public static boolean isValidCellReference(String token) {
        return token.matches("[A-Za-z]\\d+");
    }
}
