package org.demis27.cbcm.comicbook;

public class ResourceNotFoundException extends Exception {

    String code;

    public ResourceNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }
}
