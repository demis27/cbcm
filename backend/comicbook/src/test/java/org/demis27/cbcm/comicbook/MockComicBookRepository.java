package org.demis27.cbcm.comicbook;

import io.micronaut.context.annotation.Replaces;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Singleton
@Replaces(ComicBookRepository.class)
public class MockComicBookRepository implements ComicBookRepository {

    private Map<String, ComicBook> comicBooks = new HashMap<>();

    @Override
    public Flux<ComicBook> list() {
        return Flux.fromIterable(comicBooks.values());
    }

    @Override
    public Mono<Boolean> save(ComicBook comicBook) {
        comicBooks.put(comicBook.getId(), comicBook);
        return Mono.just(true);
    }

    @Override
    public Mono<Boolean> update(ComicBook comicBook) {
        comicBooks.put(comicBook.getId(), comicBook);
        return Mono.just(true);
    }

    @Override
    public Mono<ComicBook> find(String id) {
        return Mono.just(comicBooks.get(id));
    }

    @Override
    public Mono<Boolean> delete(String id) {
        comicBooks.remove(id);
        return Mono.just(true);
    }

    public void cleanAll() {
        comicBooks.clear();
    }
}
