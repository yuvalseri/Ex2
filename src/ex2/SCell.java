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
    }
    public static boolean isNumber(String content) {
     String number= "01234556789";
     if(content.contains(number)){
        return true;
     }
     return false;
    }

    public static boolean isForm(String content){
        if (content == null || content.isEmpty()) {
            return false;
        }

        if (!(content.charAt(0) == '=')) {
                return false;
            }
        String formula = content.substring(1);
        int openParentheses = 0;
        for (char ch : formula.toCharArray()) {
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
        }
        if (formula.contains("()")) {
            return false; // פורמולה ריקה בתוך סוגריים
        }

        if (content.contains("[") && (!(content.contains("]"))) || content.contains("]") && (!(content.contains("["))) ){
            return false;
        }

     return true;
    }

    public static boolean isNumeric(String s) {
        String number= "01234556789.";
        if(s.contains(number)){
            return true;
        }
        return false;
    }
    public static boolean isText(String content){
        boolean ans = false;
        if (content == null || content.toString().isEmpty()) {
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
