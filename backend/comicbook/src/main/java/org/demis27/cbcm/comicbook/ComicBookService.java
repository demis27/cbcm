package org.demis27.cbcm.comicbook;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Singleton
public class ComicBookService {

    private final ComicBookRepository repository;

    public Flux<ComicBook> getAll() {
        return repository.list();
    }

    public Mono<ComicBook> create(ComicBook comicBook) {
        if (comicBook.getId() == null) {
            comicBook.setId(UUID.randomUUID().toString());
        }
        return repository.save(comicBook).flatMap(result -> find(comicBook.getId()));
    }

    public Mono<ComicBook> update(ComicBook comicBook) {
        return repository
                .update(comicBook)
                .filter(result -> result)
                .flatMap(result -> find(comicBook.getId()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new ResourceNotFoundException(ComicBook.class, comicBook.getId()))));
    }

    public Mono<ComicBook> find(String id) {
        return repository
                .find(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new ResourceNotFoundException(ComicBook.class, id))));
    }

    public Mono<Boolean> delete(String id) {
        return repository.delete(id)
                .filter(deleteResult -> deleteResult)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new ResourceNotFoundException(ComicBook.class, id))));
    }
}
