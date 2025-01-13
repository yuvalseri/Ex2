package ex2;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    }
    @Override
    public void eval() {
        int[][] dd = depth();
        // Add your code here
        for (int i = 0; i < dd.length; i++) {
            for (int j = 0; j < dd[i].length; j++) {

                if (dd[i][j] != -1) {
                    String cellValue = get(i, j).toString();
                    if (SCell.isForm(cellValue)) {
                        String result = eval(i,j);
                        set(i, j, result);
                    }
                }
            }
        }

    }

    @Override
    public boolean isIn(int xx, int yy) {
        boolean ans = xx>=0 && yy>=0 && xx<width() && yy<height();
        return ans;
    }

    @Override


    public int[][] depth() {
        int[][] ans = new int[width()][height()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                ans[i][j] = -2;  //initializing all the value of the cells to -2
            }
        }

        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                if (ans[i][j] == -2) { // if the depth wasn't calculated yet
                    ans[i][j] = calculateDepth(i, j, ans); //calculating the depth of the cell
                }
            }
        }

        return ans; // return the array with the values
    }

    private int calculateDepth(int x, int y, int[][] ans) {
        if (ans[x][y] != -2) {
            return ans[x][y];
        }
        String formula = get(x, y).toString();
        List<SCell> dependencies = getDependencies(formula);

        int maxDepth = 0;
        for (SCell dep : dependencies) {

            CellEntry cellEntry = new CellEntry(dep.toString());
            if (cellEntry.isValid()) {
                int depX = cellEntry.getX();
                int depY = cellEntry.getY();
                maxDepth = Math.max(maxDepth, calculateDepth(depX, depY, ans));
            }
        }

        maxDepth = Math.max(maxDepth, get(x, y).getOrder());

        ans[x][y] = maxDepth;
        return maxDepth;
    }



    @Override
    public void load(String fileName) throws IOException {
        // Add your code here
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isTitle = true; // this line is the title

            while ((line = reader.readLine()) != null) { // while there are lines to read
                if (isTitle) { // if this is the title
                    isTitle = false; // from now the line is not title
                    continue; // ignore the title
                }

                String[] parts = line.split("," ); // split the line by ','
                if (parts.length >= 3) { //if there are more than 3 parts
                    try {
                        int x = Integer.parseInt(parts[0]); //invert the first and the second part to integers
                        int y = Integer.parseInt(parts[1]);
                        String DataCell = parts[2]; //the third part represented the value of the cell
                        set(x, y, DataCell);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
            }
        }

    }

    @Override
    public void save(String fileName) throws IOException {
        // Add your code here
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("I2CS ArielU: SpreadSheet (Ex2) assignment\n"); //the title
            for (int i = 0; i < width(); i++) {
                for (int j = 0; j < height(); j++) {
                    Cell cell = get(i, j); // getting the cell
                    if (cell != null && !cell.getData().equals(Ex2Utils.EMPTY_CELL)) { // if the cell isn't null
                        writer.write(i + "," + j + "," + cell.getData() + "\n"); // write the data of the cell in the file
                    }
                }
            }
        }
        /////////////////////
    }

    @Override
    public String eval(int x, int y) {
        String ans = get(x, y).toString();
        if (ans == null) {
            return null;
        }

        if (SCell.isText(ans)) {
            return ans; // return the text
        }

        if (SCell.isForm(ans)) {
            return calculateExpression(ans.substring(1)).toString();//calculating the value of the expression
        }

        if (isNumber(ans)) {
            return ans; //return the number
        }

        throw new IllegalArgumentException("Invalid cell value: " + ans);
    }

    private String calculateExpression(String ans) {
        while (ans.contains("(")) {
            int openIndex = ans.lastIndexOf("(");
            int closeIndex = ans.indexOf(")", openIndex);
            if (closeIndex == -1) { //if there isn't a ')'
                throw new IllegalArgumentException("Unmatched parentheses in expression: " + ans); //the expression is invalid
            }

            String innerExpression = ans.substring(openIndex + 1, closeIndex); // the expression that in the parentheses
            String innerValue = calculateExpression(innerExpression); //recursive calculating of the inner expression

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

        int mainOpIndex = indOfMainOp(ans); //finding the lhe main operator

        if (mainOpIndex == -1) {//if there isn't main operator
            return ans; //return the string
        }

        String leftPart = ans.substring(0, mainOpIndex);// the left expression that before the maim op
        String rightPart = ans.substring(mainOpIndex + 1);//the right expression that before the maim op
        char operator = ans.charAt(mainOpIndex);
        double leftValue = Double.parseDouble(calculateExpression(leftPart)); //recursive calculating on the left expression
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

        return String.valueOf(result);
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
