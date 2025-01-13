package ex2;
import java.io.IOException;
import java.util.ArrayList;

import static ex2.SCell.getDependencies;
import static ex2.SCell.isNumber;
// Add your documentation below:

public  class Ex2Sheet implements Sheet {
    private Cell[][] table;

    // Add your code here

    // ///////////////////
    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        for(int i=0;i<x;i=i+1) {
            for(int j=0;j<y;j=j+1) {
                table[i][j] = new SCell("");
            }
        }
        eval();
    }
    public Ex2Sheet() {
        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
    }

    @Override
    public String value(int x, int y) {
        String ans = Ex2Utils.EMPTY_CELL;
        Cell c = get(x, y);

        if (c != null) {
            String cellValue = c.toString();

            if (cellValue.isEmpty()) {
                return Ex2Utils.EMPTY_CELL;
            }
            if (SCell.isForm(cellValue)) {
                ans = eval(x, y);
            }
            else if (SCell.isNumber(cellValue)) {
                double num = Double.parseDouble(cellValue);
                ans = String.valueOf(num);
            }
            else if (SCell.isText(cellValue)) {
                ans = cellValue;
            }
            else {
                ans = Ex2Utils.ERR_FORM;
            }
        }
        return ans;
    }



    @Override
    public Cell get(int x, int y) {
    if(isIn(x,y)){
        return table[x][y];
    }
    else{
        throw new IllegalArgumentException(String.valueOf(Ex2Utils.ERR));
    }
    }

    @Override
    public Cell get(String XY) {
        Index2D index = new CellEntry(XY);
            if (index.isValid()) { //if the index is valid
                Cell ans = get(index.getX(), index.getY()); // ans is the cell in sheet[x][y]
                if (ans == null || ans.toString().isEmpty()) { //if the cell is null
                    return new SCell(Ex2Utils.EMPTY_CELL); // return the cell is empty
                }
                else { // if the cell is not empty
                    return ans; // return the value of the cell
                }
            }
            else { //if the index is invalid
                throw new IllegalArgumentException(String.valueOf(Ex2Utils.ERR));
            }
    }



    @Override
    public int width() {
        return table.length;
    }
    @Override
    public int height() {
        return table[0].length;
    }
    @Override
    public void set(int x, int y, String s) {
        Cell c = new SCell(s);
        table[x][y] = c;
        // Add your code here

        /////////////////////
    }
    @Override
    public void eval() {
        int[][] dd = depth();
        // Add your code here

        // ///////////////////
    }

    @Override
    public boolean isIn(int xx, int yy) {
        boolean ans = xx>=0 && yy>=0 && xx<width() && yy<height();
        // Add your code here

        /////////////////////
        return ans;
    }

    @Override
    public int[][] depth() {
        int[][] ans = new int[width()][height()];
        // Add your code here
        for (int i = 0; i <width() ; i++) { // a loop passes all the rows
            for (int j = 0; j <height() ; j++) { // a loop passes all the cols
                ans[i][j] = -2; // The initial value, before calculating the depth
            }
        }
        for (int i = 0; i <width() ; i++) { // a loop passes all the rows
            for (int j = 0; j <height() ; j++) { // a loop passes all the cols

                ans[i][j] = getDependencies(get(i,j).toString()).size();
            }
        }
        // ///////////////////
        return ans;
    }


    @Override
    public void load(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    public void save(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    /*public String eval(int x, int y) {
        String ans = get(x, y).toString();
        if (ans == null) {
            return null;
        }

        ans = ans.trim();

        if (SCell.isText(ans)) {
            return ans;
        }

        if (SCell.isForm(ans)) {
            return calculateExpression(ans.substring(1)).toString(); // הסר "=" וחשב
        }

        if (isNumber(ans)) {
            return ans;
        }

        throw new IllegalArgumentException("Invalid cell value: " + ans);
    }

    private String calculateExpression(String ans) {
        while (ans.contains("(")) {
            int openIndex = ans.lastIndexOf("("); // סוגר שמאלי פנימי ביותר
            int closeIndex = ans.indexOf(")", openIndex); // סוגר ימני המתאים
            if (closeIndex == -1) {
                throw new IllegalArgumentException("Unmatched parentheses in expression: " + ans);
            }

            String innerExpression = ans.substring(openIndex + 1, closeIndex);
            String innerValue = calculateExpression(innerExpression);

            ans = ans.substring(0, openIndex) + innerValue + ans.substring(closeIndex + 1);
        }

        if (isNumber(ans)) {
            return ans;
        }


        int mainOpIndex = indOfMainOp(ans); //the centeral operator

        if (mainOpIndex == -1) {
            return ans;
        }

        String leftPart = ans.substring(0, mainOpIndex).trim();
        String rightPart = ans.substring(mainOpIndex + 1).trim();
        char operator = ans.charAt(mainOpIndex);

        double leftValue = Double.parseDouble(calculateExpression(leftPart));
        double rightValue = Double.parseDouble(calculateExpression(rightPart));

        double result;
        switch (operator) { //calculating the value according the operator
            case '+': result = leftValue + rightValue; break;
            case '-': result = leftValue - rightValue; break;
            case '*': result = leftValue * rightValue; break;
            case '/':
                if (rightValue == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                result = leftValue / rightValue;
                break;
            default: throw new IllegalArgumentException("Invalid operator: " + operator);
        }

        return String.valueOf(result);
    }


    /*public static int indOfMainOp(String a) {
        double[] indValue = new double[a.length()];
        for (int j = 0; j < a.length(); j++) {
            indValue[j] = -1;
        }
        double value = -1;
        int indexofop = -1;

        if (SCell.isNumber(a)) {
            return -1;
        }

        if (SCell.isForm(a))
            for (int i = 1; i < a.length(); i++) {
                if (a.charAt(i) == '(') {
                    for (int k = i + 1; k < a.length(); k++) {
                        if (a.charAt(k) == ')') {
                                i = k + 1;
                            }
                    }
                    continue;
                }

                if (a.charAt(i) == '-' || a.charAt(i) == '+') {
                    indValue[i] = 0;
                }

                if (a.charAt(i) == '*' || a.charAt(i) == '/') {
                    indValue[i] = 0.5;
                }
            }
        for (int t = 0; t < indValue.length; t++) {
            if (indValue[t] > value) {
                value = indValue[t];
                indexofop = t;
            }
        }
        return indexofop;
    }*/

    public String eval(int x, int y) {
        String ans = get(x, y).toString();
        if (ans == null) {
            return null;
        }

        ans = ans.trim();

        if (SCell.isText(ans)) {
            return ans;
        }

        if (SCell.isForm(ans)) {
            return calculateExpression(ans.substring(1)).toString();
        }

        if (isNumber(ans)) {
            return ans;
        }

        throw new IllegalArgumentException("Invalid cell value: " + ans);
    }

    private String calculateExpression(String ans) {
        while (ans.contains("(")) {
            int openIndex = ans.lastIndexOf("(");
            int closeIndex = ans.indexOf(")", openIndex);
            if (closeIndex == -1) {
                throw new IllegalArgumentException("Unmatched parentheses in expression: " + ans);
            }

            String innerExpression = ans.substring(openIndex + 1, closeIndex);
            String innerValue = calculateExpression(innerExpression);

            ans = ans.substring(0, openIndex) + innerValue + ans.substring(closeIndex + 1);
        }

        if (isNumber(ans)) {
            return ans;
        }

        if (isValidCellReference(ans)) {

            CellEntry cell = new CellEntry(ans);
            if (cell.isValid()) {
                String cellValue = eval(cell.getX(), cell.getY());
                return calculateExpression(cellValue);
            } else {
                throw new IllegalArgumentException("Invalid cell reference: " + ans);
            }
        }

        int mainOpIndex = indOfMainOp(ans);

        if (mainOpIndex == -1) {
            return ans;
        }

        String leftPart = ans.substring(0, mainOpIndex).trim();
        String rightPart = ans.substring(mainOpIndex + 1).trim();
        char operator = ans.charAt(mainOpIndex);


        double leftValue = Double.parseDouble(calculateExpression(leftPart));
        double rightValue = Double.parseDouble(calculateExpression(rightPart));


        double result;
        switch (operator) {
            case '+': result = leftValue + rightValue; break;
            case '-': result = leftValue - rightValue; break;
            case '*': result = leftValue * rightValue; break;
            case '/':
                if (rightValue == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                result = leftValue / rightValue;
                break;
            default: throw new IllegalArgumentException("Invalid operator: " + operator);
        }

        return String.valueOf(result);  // החזר את התוצאה כמספר
    }

    public static int indOfMainOp(String form) {
        int IndexOfMainOp = -1;
        int current = Integer.MAX_VALUE;
        int openParent = 0;

        for (int i = 0; i < form.length(); i++) {
            char c = form.charAt(i);

            if (c == '(') {
                openParent++;
            } else if (c == ')') {
                openParent--;
            }

            if (openParent == 0) {
                int precedence = -1;

                if (c == '+' || c == '-') {
                    precedence = 1;
                } else if (c == '*' || c == '/') {
                    precedence = 2;
                }

                if (precedence > -1 && precedence <= current) {
                    current = precedence;
                    IndexOfMainOp = i;
                }
            }
        }
        return IndexOfMainOp;
    }

    private boolean isValidCellReference(String a) {
        return a.matches("[A-Z]+[0-9]+"); //
    }

}
