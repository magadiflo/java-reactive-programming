package dev.magadiflo.app.sec04;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec04.assignment.FileReaderService;
import dev.magadiflo.app.sec04.assignment.FileReaderServiceImpl;

import java.nio.file.Path;

public class Lec09Assignment {
    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/sec04/file.txt");
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        fileReaderService.read(path)
                .take(6)
                .subscribe(Util.subscriber());
    }
}
