package ex2;
// Add your documentation below:

public class CellEntry  implements Index2D {
private String XY;

    @Override
    public boolean isValid() {
        char firstchar= XY.charAt(0);
        String numPart =XY.substring(1);
        int number = Integer.parseInt(numPart);

        if(!Character.isLetter(firstchar) && (0>number || number>99 )){
            return false;
        }
        return true;
    }

    @Override
    public int getX() {return Ex2Utils.ERR;}

    @Override
    public int getY() {return Ex2Utils.ERR;}
}
