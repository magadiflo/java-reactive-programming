package dev.magadiflo.app.sec04.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileReaderServiceImpl implements FileReaderService {

    private static final Logger log = LoggerFactory.getLogger(FileReaderServiceImpl.class);

    @Override
    public Flux<String> read(Path path) {
        return Flux.generate(
                () -> this.openFile(path),
                this::readFile,
                this::closeFile
        );
    }

    private BufferedReader openFile(Path path) throws IOException {
        log.info("Abriendo archivo");
        return Files.newBufferedReader(path);
    }

    private BufferedReader readFile(BufferedReader reader, SynchronousSink<String> synchronousSink) {
        try {
            String line = reader.readLine();
            log.info("Leyendo línea: {}", line);

            if (Objects.isNull(line)) {
                synchronousSink.complete();
            } else {
                synchronousSink.next(line);
            }

        } catch (IOException e) {
            synchronousSink.error(e);
        }
        return reader;
    }

    private void closeFile(BufferedReader reader) {
        try {
            log.info("Cerrando archivo");
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
