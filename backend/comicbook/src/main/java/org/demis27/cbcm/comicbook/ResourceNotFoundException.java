package org.demis27.cbcm.comicbook;

public class ResourceNotFoundException extends Exception {

    public final static String RESOURCE_NOT_FOUND = "RESSOURCE_NOT_FOUND";

    ErrorMessage errorMessage;

    public ResourceNotFoundException(String message) {
        super(message);
        errorMessage = new ErrorMessage(RESOURCE_NOT_FOUND, message);
    }
}
