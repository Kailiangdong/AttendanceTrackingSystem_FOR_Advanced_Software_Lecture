package com.example.guestbook;

import com.google.gson.JsonObject;

import java.security.SecureRandom;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.googlecode.objectify.ObjectifyService;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class RegisterResource extends ServerResource {
    /**
     * site: /rest/register
     * Interface for registration
     * 
     * Validates request elements(email, password, name, group name and is_tutor variable)
     * register new user into database
     * @param entity Request input
     * @return json format response
     */
    @Post
    public StringRepresentation handle(Representation entity) {
        JsonObject jsonObject = new JsonObject();

        Form form = new Form(entity);
        String email = form.getFirstValue("email");
        // invalid email input
        if (!ValidateFunc.validateEmail(email)) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Invalid email");
            return new StringRepresentation(jsonObject.toString());
        }
        // check if email address is already registered
        Person p = ObjectifyService.ofy().load().type(Person.class).filter("email", email).first().now();
        if (p != null) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Your email address is already registered");
            return new StringRepresentation(jsonObject.toString());
        }

        String pwd = form.getFirstValue("password");
        // invalid password input
        if (!ValidateFunc.validatePwd(pwd)) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason",
                    "Password muss contain at least 1 letter and 1 number and at least 8 digits long");
            return new StringRepresentation(jsonObject.toString());
        }

        String firstName = form.getFirstValue("first_name");
        String lastName = form.getFirstValue("last_name");
        // invalid name input
        if (!ValidateFunc.validateName(firstName) || !ValidateFunc.validateName(lastName)) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Invalid name");
            return new StringRepresentation(jsonObject.toString());
        }

        String isTutor = form.getFirstValue("is_tutor");

        // validate group number
        String groupNameStr = form.getFirstValue("group_name");
        int groupName = Integer.parseInt(groupNameStr);
        if (groupName < 1 || groupName > 6) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Invalid group number");
            return new StringRepresentation(jsonObject.toString());
        }

        // register new user into database
        SecureRandom random = new SecureRandom();
        byte[] bSalt = new byte[24];
        random.nextBytes(bSalt);
        String salt = new String(bSalt);
        String codedPwd = Hashing.sha256().hashString(pwd + salt, Charsets.UTF_8).toString();
        if (isTutor.equals("false")) {
            Student student = new Student(firstName, lastName, email, codedPwd, salt, groupName);
            ObjectifyService.ofy().save().entity(student).now();
            jsonObject.addProperty("status", "SUCCESS");
            jsonObject.addProperty("id", student.getId());
        } else if (isTutor.equals("true")) {
            Tutor tutor = new Tutor(firstName, lastName, email, codedPwd, salt);
            ObjectifyService.ofy().save().entity(tutor).now();
            jsonObject.addProperty("status", "SUCCESS");
            jsonObject.addProperty("id", tutor.getId());
        } else {
            // invalid isTutor input
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Invalid input");
            return new StringRepresentation(jsonObject.toString());
        }
        jsonObject.addProperty("reason", "");

        return new StringRepresentation(jsonObject.toString());
    }
}