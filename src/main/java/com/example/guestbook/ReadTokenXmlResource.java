package com.example.guestbook;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class ReadTokenXmlResource extends ServerResource{
    @Post
    public StringRepresentation handle(Representation entity) {
        Form form = new Form(entity);  
        String attendance_id = form.getFirstValue("attendance_id");  
        String student_id = form.getFirstValue("student_id");  
        String group = form.getFirstValue("group");
        String week = form.getFirstValue("week");
        String presented = form.getFirstValue("presented");

        //TODO: add attendance into objectify service
        
        return new StringRepresentation(student_id + " has attendence id: " + attendance_id + " is currently registered in group: " + group) ; 
    }
}