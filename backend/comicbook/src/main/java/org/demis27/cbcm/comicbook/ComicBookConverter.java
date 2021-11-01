package org.demis27.cbcm.comicbook;

import jakarta.inject.Singleton;

@Singleton
public class ComicBookConverter {

    public ComicBookDTO convert(ComicBook comicBook) {
        return ComicBookDTO.builder().id(comicBook.getId()).name(comicBook.getName()).build();
    }

    public ComicBook convert(ComicBookDTO dto) {
        return ComicBook.builder().id(dto.getId()).name(dto.getName()).build();
    }
}
