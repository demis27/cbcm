package org.demis27.cbcm.comicbook;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller("/api/v1/comicbooks")
@RequiredArgsConstructor
public class ComicBookController {

    private final ComicBookService service;

    @Get(produces = {MediaType.APPLICATION_JSON})
    public Flux<ComicBook> getAll() {
        return service.getAll();
    }

    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public Mono<ComicBook> get(@PathVariable(name = "id") String id) {
        return service.find(id);
    }

    @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Mono<ComicBook> post(@Body ComicBook comicBook) {
        return service.create(comicBook);
    }

    @Put(uri = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Mono<MutableHttpResponse<ComicBook>> put(@PathVariable(name = "id") String id, @Body ComicBook comicBook) {
        return service.update(comicBook).map(HttpResponse::ok).onErrorReturn(HttpResponse.notFound());
    }

    @Delete(uri = "/{id}")
    public void delete(@PathVariable String id) {

    }

}
