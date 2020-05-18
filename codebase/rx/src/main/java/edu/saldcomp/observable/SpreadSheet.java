package edu.saldcomp.observable;


import java.util.HashMap;
import java.util.Map;

public class SpreadSheet {

    private Map<String, Cell> cells;

    public SpreadSheet() {
        this.cells = new HashMap();
    }

    public void add(String location, Cell cell) {
        cells.put(location, cell);
    }

    public Cell getCell(String id) {
        return cells.get(id);
    }

}
