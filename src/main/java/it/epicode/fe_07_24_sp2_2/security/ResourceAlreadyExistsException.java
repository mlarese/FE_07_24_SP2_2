package it.epicode.fe_07_24_sp2_2.security;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ResourceAlreadyExistsException extends RuntimeException {

    private String resourceName;
    private String resourceId;


    public ResourceAlreadyExistsException(String resourceName, Integer resourceId) {
        this(resourceName, resourceId.toString());
    }


    public String getMessage() {
        if (resourceName == null || resourceId == null)
            return null;
        return String.format("Resource '%s' already registered with id '%s'", resourceName, resourceId);
    }

}
