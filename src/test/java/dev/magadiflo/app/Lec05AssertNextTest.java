package dev.magadiflo.app;


import dev.magadiflo.app.common.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Objects;

/*
assertNext, es un método en StepVerifier
assertNext = consumeNextWith
También podemos recolectar todos los items y probarlos.
 */
class Lec05AssertNextTest {
    record Book(int id, String author, String title) {
    }

    private Flux<Book> getBooks() {
        return Flux.range(1, 5)
                .map(i -> new Book(i, Util.faker().book().author(), Util.faker().book().title()));
    }

    @Test
    void assertNextTest() {
        // given
        // when
        Flux<Book> books = getBooks();

        // then
        StepVerifier.create(books)
                .assertNext(book -> {
                    Assertions.assertNotNull(book);
                    Assertions.assertEquals(1, book.id());
                    Assertions.assertNotNull(book.author());
                    Assertions.assertNotNull(book.title());
                })
                .assertNext(book -> Assertions.assertEquals(2, book.id()))
                .thenConsumeWhile(book -> Objects.nonNull(book.title()))
                .verifyComplete();

    }

    @Test
    void collectAllAndTest() {
        // given
        // when
        Flux<Book> books = getBooks();

        // then
        StepVerifier.create(books.collectList())
                .assertNext(bookList -> Assertions.assertEquals(5, bookList.size()))
                .verifyComplete();

    }
}
