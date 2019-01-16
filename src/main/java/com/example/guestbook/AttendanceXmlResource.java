package com.example.guestbook;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class AttendanceXmlResource extends ServerResource{
    @Post
    public StringRepresentation handle(Representation entity) {
        return new StringRepresentation("Not in use yet!");
    }
}