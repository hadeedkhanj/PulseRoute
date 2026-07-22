package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class CriticalResource implements Serializable {

    private static final long serialVersionUID = 1L;
    private String resourceId;
    private String location;
    private LocalDateTime createdAt;
    
    public CriticalResource(String resourceId, String location) {
        this.resourceId = resourceId;
        this.location = location;
        this.createdAt = LocalDateTime.now();
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // this is an abstract method that must be implemented in the child classes later on
    public abstract String getDetails();
}
