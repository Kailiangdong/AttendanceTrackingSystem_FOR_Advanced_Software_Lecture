package com.example.guestbook;

import com.google.appengine.repackaged.com.google.api.client.util.Charsets;
import com.google.appengine.repackaged.com.google.gson.JsonObject;
import com.google.common.hash.Hashing;
import com.googlecode.objectify.ObjectifyService;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class RegisterResource extends ServerResource{
    @Post
    public StringRepresentation handle(Representation entity) {
        Form form = new Form(entity); 
        // TODO: validate input 
        String email = form.getFirstValue("email");
        // TODO: validate input  
        String pwd = form.getFirstValue("password");  
        // TODO: validate input 
        String firstName = form.getFirstValue("first_name");
        // TODO: validate input 
        String lastName = form.getFirstValue("last_name");
        // TODO: validate input 
        String isTutor = form.getFirstValue("is_tutor");
        // TODO: validate input 
        String groupNameStr = form.getFirstValue("group_name");
        int groupName = Integer.parseInt(groupNameStr);

        JsonObject jsonObject = new JsonObject();

        // register new user into database
        String codedPwd = Hashing.sha256().hashString(pwd, Charsets.UTF_8).toString();
        if(isTutor.equals("false")){
            Student student = new Student(firstName, lastName, email, codedPwd, groupName);
            ObjectifyService.ofy().save().entity(student).now();
            jsonObject.addProperty("status", "SUCCESS");
            jsonObject.addProperty("id", student.getId());
        }else if(isTutor.equals("true")){
            Tutor tutor = new Tutor(firstName, lastName, email, codedPwd);
            ObjectifyService.ofy().save().entity(tutor).now();
            jsonObject.addProperty("status", "SUCCESS");
            jsonObject.addProperty("id", tutor.getId());
        }
     
        jsonObject.addProperty("reason", "");
        
        return new StringRepresentation(jsonObject.toString()) ; 
    }
}