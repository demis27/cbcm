package org.demis27.cbcm.comicbook;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ComicBookRepository {
    Flux<ComicBook> list();

    Mono<ComicBook> save(ComicBook comicBook);

    Mono<ComicBook> update(ComicBook comicBook);

    Mono<ComicBook> find(String id);
}
