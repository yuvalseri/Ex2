import ex2.Ex2Sheet;
import ex2.SCell;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SCell a1 = new SCell("5");
        SCell a2 = new SCell("10");
        SCell a3 = new SCell("=A1+A2");

        a3.dependC = new ArrayList<>(List.of(a1, a2));

        for (SCell dependency : a3.dependC) {
            System.out.println("Dependency: " + dependency.getData());
        }

        String formula = "=A1+A2";
        List<SCell> dependencies = SCell.getDependencies(formula);

        for (SCell dependency : dependencies) {
            System.out.println("Dependency: " + dependency.getData());
        }

        SCell a6 = new SCell("=A1+5");
        SCell a7 = new SCell("=A2+2");
        SCell c1 = new SCell("=A3+A4");

        a7.dependC = new ArrayList<>(List.of(a6));

// בדיקה ידנית
        System.out.println("a7 depends on:");
        for (SCell dep : a7.dependC) {
            System.out.println(dep.getData());
        }

        Ex2Sheet sheet = new Ex2Sheet(3, 3);

        sheet.set(0, 0, "=5+3*2");
        sheet.set(0, 1, "=10/(2+3)");
        sheet.set(0, 2, "Hello");
        sheet.set(1, 0, "=(1+2)*3/(2/5)");

        System.out.println(sheet.eval(0, 0));
        System.out.println(sheet.eval(0, 1));
        System.out.println(sheet.eval(0, 2));
        System.out.println(sheet.eval(1, 0));
    }



}