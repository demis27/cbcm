package org.demis27.cbcm.comicbook;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class ComicBookControllerTest {

    @Inject
    MockComicBookRepository repository;

    @Inject
    @Client("/")
    HttpClient client;

    @AfterEach
    void cleanData() {
        repository.cleanAll();
    }

    @Test
    void emptyList() {
        // Then the list of comicBooks is empty
        assertEquals(client.toBlocking().exchange("/api/v1/comicbooks").getStatus().getCode(), 200);
        assertTrue(client.toBlocking().retrieve(HttpRequest.GET("/api/v1/comicbooks"), Argument.listOf(ComicBookDTO.class)).isEmpty());
    }

    @Test
    void post() {
        // Given
        ComicBookDTO dto = ComicBookDTO.builder().name("Un Long Halloween").build();

        // When
        ComicBookDTO result = client.toBlocking().retrieve(HttpRequest.POST("/api/v1/comicbooks", dto), Argument.of(ComicBookDTO.class));

        // Then
        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        assertNotNull(result.getId());

        assertEquals(client.toBlocking().exchange("/api/v1/comicbooks").getStatus().getCode(), 200);
        assertEquals(client.toBlocking().retrieve(HttpRequest.GET("/api/v1/comicbooks"), Argument.listOf(ComicBookDTO.class)).size(), 1);
    }

    @Test
    void getAll() {
        // Given A comicBook I post on API
        ComicBookDTO dto = ComicBookDTO.builder().name("Un Long Halloween").build();
        client.toBlocking().retrieve(HttpRequest.POST("/api/v1/comicbooks", dto), Argument.of(ComicBookDTO.class));

        // When I get the list of comicBooks
        List<ComicBookDTO> results = client.toBlocking().retrieve(HttpRequest.GET("/api/v1/comicbooks"), Argument.listOf(ComicBookDTO.class));

        // Then we found one comicBook on the list and this comicBook have an id, and the same name
        assertNotNull(results);
        assertEquals(results.size(), 1);
        ComicBookDTO result = results.get(0);
        assertEquals(dto.getName(), result.getName());
        assertNotNull(result.getId());
    }

    @Test
    void get() {
        // Given A comicBook I post on API
        ComicBookDTO dto = ComicBookDTO.builder().name("Un Long Halloween").build();
        ComicBookDTO created = client.toBlocking().retrieve(HttpRequest.POST("/api/v1/comicbooks", dto), Argument.of(ComicBookDTO.class));

        // When I get this comicBook by Id.
        ComicBookDTO result = client.toBlocking().retrieve(HttpRequest.GET("/api/v1/comicbooks/" + created.getId()), Argument.of(ComicBookDTO.class));

        // Then we found the comicBook
        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        assertNotNull(result.getId());
    }

    @Test
    void put() {
        // Given A comicBook I post on API
        ComicBookDTO dto = ComicBookDTO.builder().name("Un Long Halloween").build();
        ComicBookDTO created = client.toBlocking().retrieve(HttpRequest.POST("/api/v1/comicbooks", dto), Argument.of(ComicBookDTO.class));
        created.setName("Batman - Un Long Halloween");

        // When I modify this comicBook by Id.
        ComicBookDTO result = client.toBlocking().retrieve(HttpRequest.PUT("/api/v1/comicbooks/" + created.getId(), created), Argument.of(ComicBookDTO.class));

        // Then we found the comicBook
        assertNotNull(result);
        assertEquals(created.getName(), result.getName());
        assertNotNull(result.getId());
    }

    @Test
    void delete() {
        // Given A comicBook I post on API
        ComicBookDTO dto = ComicBookDTO.builder().name("Un Long Halloween").build();
        ComicBookDTO created = client.toBlocking().retrieve(HttpRequest.POST("/api/v1/comicbooks", dto), Argument.of(ComicBookDTO.class));

        // When I delete this comicBook by Id.
        client.toBlocking().exchange(HttpRequest.DELETE("/api/v1/comicbooks/" + created.getId()));

        // Then we found the comicBook
        List<ComicBookDTO> results = client.toBlocking().retrieve(HttpRequest.GET("/api/v1/comicbooks"), Argument.listOf(ComicBookDTO.class));

        assertNotNull(results);
        assertEquals(results.size(), 0);
    }
}
