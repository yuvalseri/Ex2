package ex2;
import java.io.IOException;
import java.util.ArrayList;
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
        // Add your code here

        Cell c = get(x,y);
        if(c!=null) {ans = c.toString();}

        /////////////////////
        return ans;
    }

    @Override
    public Cell get(int x, int y) {

        return table[x][y];
    }

    @Override
    public Cell get(String XY) {
        Cell ans = null; //Initializing the value of  the cell as null
        // Add your code here
        Index2D index = new CellEntry(XY);
        if (index.isValid()) { // if the index is valid
            ans = get(index.getX(), index.getY()); // return the cell
        }
        /////////////////////
        return ans;
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
        /*for (int i = 0; i <width() ; i++) { // a loop passes all the rows
            for (int j = 0; j <height() ; j++) { // a loop passes all the cols
                ans[i][j] = -2; // The initial value, before calculating the depth
            }
        }
        for (int i = 0; i <width() ; i++) { // a loop passes all the rows
            for (int j = 0; j <height() ; j++) { // a loop passes all the cols
                ans[i][j] = computeDepth(int row, int col, boolean[][] visited, int[][] result);
            }
        }*/
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
    public String eval(int x, int y) {
        String ans = null;
        if(get(x,y)!=null) {
            ans = get(x,y).toString();
        }
        if(SCell.isNumber(ans) || SCell.isText(ans)){
            return ans;
        }
        else if(SCell.isForm(ans)){

        }
        // Add your code here

        /////////////////////
        return ans;
        }


    public static int indOfMainOp(String a) {
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
    }
}
