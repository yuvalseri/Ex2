package ex2;
// Add your documentation below:

import java.util.List;

public class SCell implements Cell {
    private String line;
    private int type;
    private int order;
    private List<SCell> dependC;
    public SCell(String s) {
        // Add your code here
        setData(s);
        this.type=getType();
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

    public static boolean isForm(String content){
        if (content == null || content.isEmpty()) {
            return false;
        }
        if (!(content.charAt(0) == '=')) { // formula must start in '="
                return false;
            }
        String formula = content.substring(1);
        int openParentheses = 0;
        /*for (char ch : formula.toCharArray()) {
            if (ch == '(') {
                openParentheses++;
            } else if (ch == ')') {
                openParentheses--;
            }
            if (openParentheses < 0) {
                return false; // סוגריים לא סגורים כראוי
            }
        }
        if (openParentheses != 0) {
            return false; // אם יש סוגריים שלא נסגרו כראוי
        }*/
        if (formula.contains("()")) {
            return false; // if there are empty parentheses
        }

        if (content.contains("[") && (!(content.contains("]"))) || content.contains("]") && (!(content.contains("["))) ){
            return false;
        }
        if (content == null || content.isEmpty()) {
            return false;
        }

        if (content.charAt(0) != '=') {
            return false;
        }

        formula = content.substring(1);
        if (formula.isEmpty()) {// if the formula is empty after the '='
            return false;
        }

        String validChars = "0123456789.+-*/()ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String operators = "+-*/";

        boolean lastCharWasOperator = false; // the former char is an operator
        boolean lastCharWasLetter = false;   // the former char is a letter
        boolean expectingNumberAfterLetter = false; // we expect to a number after a letter
        StringBuilder numberBuffer = new StringBuilder();

        openParentheses = 0;

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




            // אות
            if (Character.isLetter(ch)) {
                if (lastCharWasLetter) { // if there are two adjoint letters
                    return false; //
                }
                lastCharWasLetter = true;
                expectingNumberAfterLetter = true;
                numberBuffer.setLength(0); // reset
                continue;
            }

            // מספר
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
            }

            // אופרטור
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

        return true;
    }

    public static boolean isText(String content){
        boolean ans = false;
        if (content == null || content.toString().isEmpty() || content== " ") {
            return ans;
        }
        else if(!isNumber(content) && !isForm(content)){
             ans= true;
    }
    return ans;
    }


    @Override
    public int getOrder() {
        // Add your code here
    if(type== Ex2Utils.NUMBER || type== Ex2Utils.TEXT){
        return 0;}
    else if(type== Ex2Utils.FORM){
        int max=0;
        for(SCell cell : dependC){
          max= Math.max(max, cell.getOrder());
        }
        int order= 1+ max;
        return order;
    }
    return -1;
        // ///////////////////
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
}
