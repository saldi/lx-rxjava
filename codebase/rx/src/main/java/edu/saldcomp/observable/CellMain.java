package edu.saldcomp.observable;

public class CellMain {

    public static void main(String[] args) {

        Cell cell = new Cell();
        Cell cell2 = new Cell((i) -> i + 20);

        cell2.subscribeOn(cell);

        cell.update(20);
        System.out.println(cell2.getValue());
        cell2.removeSubscribe();
        cell2.update(5);
        cell.update(10);

        System.out.println(cell2.getValue());

    }

}
