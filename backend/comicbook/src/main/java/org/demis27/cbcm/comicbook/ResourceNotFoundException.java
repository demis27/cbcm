package org.demis27.cbcm.comicbook;

public class ResourceNotFoundException extends Exception {

    public final static String RESOURCE_NOT_FOUND = "RESSOURCE_NOT_FOUND";

    public final static String DEFAULT_MESSAGE_TEMPLATE = "%s with #id: %s not found";

    ErrorMessage errorMessage;

    public ResourceNotFoundException(String message) {
        super(message);
        errorMessage = new ErrorMessage(RESOURCE_NOT_FOUND, message);
    }

    public ResourceNotFoundException(Class aClass, String id) {
        this(String.format(DEFAULT_MESSAGE_TEMPLATE, aClass.getSimpleName(), id));
    }
}
