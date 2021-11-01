package org.demis27.cbcm.comicbook;

import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@Introspected
@NoArgsConstructor
public class ComicBook {

    @BsonProperty("_id")
    private String id;

    private String name;

    @Builder
    public ComicBook(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
