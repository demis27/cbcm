package org.demis27.cbcm.comicbook;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.*;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller("/api/v1/comicbooks")
@RequiredArgsConstructor
public class ComicBookController {

    private final ComicBookService service;

    @Get(produces = MediaType.APPLICATION_JSON)
    public Flux<ComicBook> getAll() {
        return service.getAll();
    }

    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public Mono<ComicBook> get(@PathVariable(name = "id") String id) {
        return service.find(id);
    }

    @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    @Status(HttpStatus.CREATED)
    public Mono<ComicBook> post(@Body ComicBook comicBook) {
        return service.create(comicBook);
    }

    @Put(uri = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Mono<ComicBook> put(@PathVariable(name = "id") String id, @Body ComicBook comicBook) {
        comicBook.setId(id);
        return service.update(comicBook);
    }

    @Delete(uri = "/{id}")
    public Mono<HttpResponse> delete(@PathVariable String id) {
        return service.delete(id).map(result -> HttpResponse.noContent());
    }

    @Error
    public HttpResponse<ErrorMessage> error(HttpRequest request, ResourceNotFoundException resourceNotFoundException) {
        return HttpResponse.notFound(resourceNotFoundException.errorMessage);
    }
}
