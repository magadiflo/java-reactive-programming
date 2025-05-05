package dev.magadiflo.app.sec02;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec02.assignment.FileService;
import dev.magadiflo.app.sec02.assignment.FileServiceImpl;

public class Lec12Assignment {
    public static void main(String[] args) {
        String fileName = "my-file.txt";
        FileService fileService = new FileServiceImpl();

        fileService.write(fileName, "WebFlux Spring Boot")
                .subscribe(Util.subscriber());

        fileService.read(fileName)
                .subscribe(Util.subscriber());

        fileService.delete(fileName)
                .subscribe(Util.subscriber());
    }
}
