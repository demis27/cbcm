package org.demis27.cbcm.comicbook;

import com.mongodb.client.model.Filters;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Singleton
public class MongoDbComicBookRepository implements ComicBookRepository {

    private final MongoDbConfiguration configuration;

    private final MongoClient mongoClient;

    @Override
    public Flux<ComicBook> list() {
        return Flux.from(getCollection().find());
    }

    @Override
    public Mono<ComicBook> save(ComicBook comicBook) {
        if (comicBook.getId() == null) {
            comicBook.setId(UUID.randomUUID().toString());
        }
        return Mono.from(getCollection().insertOne(comicBook))
                .flatMap(result -> Mono.from(getCollection().find(Filters.eq("_id", result.getInsertedId())).limit(1)));
    }

    @Override
    public Mono<ComicBook> update(ComicBook comicBook) {
        return Mono.from(getCollection().replaceOne(Filters.eq("_id", comicBook.getId()), comicBook))
                .flatMap(result -> {
                    if (result.getUpsertedId() != null) {
                        return Mono.from(getCollection().find(Filters.eq("_id", comicBook.getId())).limit(1));
                    } else {
                        return Mono.error(new ResourceNotFoundException(String.format("ComicBook with #id: %s not found", comicBook.id)));
                    }
                });
    }

    @Override
    public Mono<ComicBook> find(String id) {
        return Mono.from(getCollection().find(Filters.eq("_id", id)).limit(1));
    }

    private MongoCollection<ComicBook> getCollection() {
        return mongoClient.getDatabase(configuration.getName())
                .getCollection(configuration.getCollection(), ComicBook.class);
    }
}
