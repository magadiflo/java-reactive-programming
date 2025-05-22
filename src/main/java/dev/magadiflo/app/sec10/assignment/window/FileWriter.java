package dev.magadiflo.app.sec10.assignment.window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriter {

    private static final Logger log = LoggerFactory.getLogger(FileWriter.class);
    private final Path path;
    private BufferedWriter writer;

    private FileWriter(Path path) {
        this.path = path;
    }

    private void createFile() {
        log.info("Creando archivo: {}", this.path.getFileName());
        try {
            this.writer = Files.newBufferedWriter(this.path); // Abre el archivo (crea si no existe) y prepara un BufferedWriter para escribir de forma eficiente
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeFile() {
        log.info("Cerrando archivo: {}", this.path.getFileName());
        try {
            this.writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void write(String content) {
        log.info("Escribiendo en archivo: {}", this.path.getFileName());
        try {
            this.writer.write(content);
            this.writer.newLine();
            this.writer.flush(); //Fuerza a escribir inmediatamente en el archivo los datos que están en el búfer
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Mono<Void> create(Flux<String> flux, Path path) {
        log.info("Procesando flux (ventana)");
        FileWriter fileWriter = new FileWriter(path);
        return flux.doOnNext(fileWriter::write)
                .doFirst(fileWriter::createFile)
                .doFinally(signalType -> fileWriter.closeFile())
                .then();
    }
}
