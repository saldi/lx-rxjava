package edu.rx.observable.file;

import io.reactivex.rxjava3.core.Observable;
import java.io.File;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
public class FileChangesTest {

    @Test
    public void fileChangeTest() {

    }


    private long getLastChangeDate(File file) {
        return file.lastModified();
    }

}
