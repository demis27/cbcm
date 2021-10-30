package org.demis27.cbcm.comicbook;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Singleton
public class ComicBookService {

    private final ComicBookRepository repository;

    public Flux<ComicBook> getAll() {
        return repository.list();
    }

    public Mono<ComicBook> create(ComicBook comicBook) {
        return repository.save(comicBook);
    }

    public Mono<ComicBook> update(ComicBook comicBook) {
        return repository.update(comicBook);
    }

    public Mono<ComicBook> find(String id) {
        return repository.find(id);
    }
}
