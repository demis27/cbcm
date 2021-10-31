package org.demis27.cbcm.comicbook;

import com.mongodb.client.model.Filters;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Singleton
public class ComicBookRepository {

    private final MongoDbConfiguration configuration;

    private final MongoClient mongoClient;

    public Flux<ComicBook> list() {
        return Flux.from(getCollection().find());
    }

    public Mono<Boolean> save(ComicBook comicBook) {
        return Mono.from(getCollection().insertOne(comicBook))
                .map(insertOneResult -> insertOneResult.getInsertedId() != null);
    }

    public Mono<Boolean> update(ComicBook comicBook) {
        return Mono.from(getCollection().replaceOne(Filters.eq("_id", comicBook.getId()), comicBook))
                .map(updateResult -> updateResult.getMatchedCount() == 1);
    }

    public Mono<ComicBook> find(String id) {
        return Mono.from(getCollection().find(Filters.eq("_id", id)).limit(1));
    }

    public Mono<Boolean> delete(String id) {
        return Mono
                .from(getCollection().deleteOne(Filters.eq("_id", id)))
                .map(deleteResult -> deleteResult.getDeletedCount() == 1);
    }

    private MongoCollection<ComicBook> getCollection() {
        return mongoClient.getDatabase(configuration.getName())
                .getCollection(configuration.getCollection(), ComicBook.class);
    }
}
