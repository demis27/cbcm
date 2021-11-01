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

    private final ComicBookConverter converter;

    @Get(produces = MediaType.APPLICATION_JSON)
    public Flux<ComicBookDTO> getAll() {
        return service.getAll().map(converter::convert);
    }

    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public Mono<ComicBookDTO> get(@PathVariable(name = "id") String id) {
        return service.find(id).map(converter::convert);
    }

    @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    @Status(HttpStatus.CREATED)
    public Mono<ComicBookDTO> post(@Body ComicBookDTO dto) {
        return service.create(converter.convert(dto)).map(converter::convert);
    }

    @Put(uri = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Mono<ComicBookDTO> put(@PathVariable(name = "id") String id, @Body ComicBookDTO dto) {
        dto.setId(id);
        return service.update(converter.convert(dto)).map(converter::convert);
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
