package org.demis27.cbcm.comicbook;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ComicBookRepository {
    Flux<ComicBook> list();

    Mono<Boolean> save(ComicBook comicBook);

    Mono<Boolean> update(ComicBook comicBook);

    Mono<ComicBook> find(String id);

    Mono<Boolean> delete(String id);
}
