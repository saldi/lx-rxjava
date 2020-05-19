package edu.rx.observable.cells;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
public class CellsExample {

    @Test
    public void example() {

        Cell cell = new Cell();
        Cell cell2 = new Cell((i) -> i + 20);

        cell2.subscribeOn(cell);

        cell.update(20);
        log.info(cell2.getValue());
        cell2.removeSubscribe();
        cell2.update(5);
        cell.update(10);

        log.info(cell2.getValue());

    }

}
