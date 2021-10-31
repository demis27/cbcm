package org.demis27.cbcm.comicbook;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
class ComicbookTest {

    @Inject
    ComicBookRepository repository;

    @Inject
    ComicBookService service;

    @Test
    void emptyListe() throws Exception {
        when(repository.list()).thenReturn(Flux.empty());
        StepVerifier.create(service.getAll()).expectNextCount(0).verifyComplete();
    }

    @MockBean(MongoDBComicBookRepository.class)
    ComicBookRepository repository() {
        return mock(ComicBookRepository.class);
    }
}
