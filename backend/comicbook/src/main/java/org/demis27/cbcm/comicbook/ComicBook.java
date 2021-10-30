package org.demis27.cbcm.comicbook;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@Introspected
public class ComicBook {

    @BsonProperty("_id")
    String id;

    String name;
}
