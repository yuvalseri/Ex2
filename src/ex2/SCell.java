package ex2;
// Add your documentation below:

import java.util.List;

public class SCell implements Cell {
    private String line;
    private int type;

    private List<SCell> dependC;
    public SCell(String s) {
        // Add your code here
        setData(s);
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
        return 1+ max;
    }
    return -1;
        // ///////////////////
    }

    //@Override
    @Override
    public String toString() {
        return getData();
    }

    @Override
public void setData(String s) {
        // Add your code here
        line = s;
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
        type = t;
    }

    @Override
    public void setOrder(int t) {
        // Add your code here

    }
}
