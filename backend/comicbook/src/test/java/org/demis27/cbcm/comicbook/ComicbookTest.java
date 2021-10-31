package org.demis27.cbcm.comicbook;

import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
class ComicbookTest {

    @Inject
    ComicBookRepository repository;

    @Inject
    @Client("/")
    HttpClient client;

    @MockBean(MongoDBComicBookRepository.class)
    ComicBookRepository repository() {
        return mock(ComicBookRepository.class);
    }

    @Test
    void emptyList() throws Exception {
        when(repository.list()).thenReturn(Flux.empty());
        assertEquals(client.toBlocking().exchange("/api/v1/comicbooks").getStatus().getCode(), 200);
    }

}
