package com.example.guestbook;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ValidateResource extends ServerResource{
    /**
     * site: /rest/validate
     * @param entity
     * @return
     */
    @Get
    public StringRepresentation handle(Representation entity) {
        Form form = new Form(entity);  
        String validate_id = form.getFirstValue("validate_id");  
        String student_id = form.getFirstValue("student_id");  
        String result = form.getFirstValue("result");

        //TODO: validate
        
        return new StringRepresentation("ERROR: not implemented yet") ; 
    }
}