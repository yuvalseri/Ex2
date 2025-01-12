package ex2;
// Add your documentation below:

public class CellEntry  implements Index2D {
private String XY;
public CellEntry(String XY){
    this.XY= XY;
}

    @Override
    public boolean isValid() {
        if (XY == null || XY.length() < 2) {
            return false;
        }

        char firstChar = XY.charAt(0);
        String numPart = XY.substring(1);


        if (!Character.isLetter(firstChar)) {
            return false;
        }

        try {
           int number = Integer.parseInt(numPart);
           if (number < 0 || number > 99) {
                 return false;
           }
        }
        catch (NumberFormatException e) {
        return false;
        }
        return true;
    }



    @Override
    public int getX() {
        if(isValid()){
            return XY.charAt(0) - 'A';
        }
        return Ex2Utils.ERR;}

    @Override
    public int getY() {
        if(isValid()){
            String numPart =XY.substring(1);
            int number = Integer.parseInt(numPart)-1;
            return number;
        }
        return Ex2Utils.ERR;}
}
