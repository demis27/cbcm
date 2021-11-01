package org.demis27.cbcm.comicbook;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComicBookDTO {

    String id;

    String name;

    @Builder
    public ComicBookDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
