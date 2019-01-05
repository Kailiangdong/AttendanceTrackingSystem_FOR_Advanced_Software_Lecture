package com.example.guestbook;

import com.google.gson.JsonObject;
import com.googlecode.objectify.ObjectifyService;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class ReadTokenJsonResource extends ServerResource{
    @Post
    public StringRepresentation handle(Representation entity) {
        JsonObject jsonObject = new JsonObject();
        Form form = new Form(entity);
        String token = form.getFirstValue("token");
        String student_id = form.getFirstValue("student_id");
        String group = form.getFirstValue("group");
        String week = form.getFirstValue("week");
        String presented = form.getFirstValue("presented");
        Long slID;
        try {
            slID = Long.parseLong(student_id);
        } catch (NumberFormatException e) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Invalid student id");
            return new StringRepresentation(jsonObject.toString());
        }

        Student s = ObjectifyService.ofy().load().type(Student.class).id(slID).now();
        if (s == null) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Student does not exist");
            return new StringRepresentation(jsonObject.toString());
        }

        try {
            int iGroup = Integer.parseInt(group);
            if (iGroup < 1 || iGroup > 6 || iGroup != s.getGroup()) {
                jsonObject.addProperty("status", "ERROR");
                jsonObject.addProperty("reason", "Invalid group number");
                return new StringRepresentation(jsonObject.toString());
            }
        } catch (NumberFormatException e) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Invalid group number");
            return new StringRepresentation(jsonObject.toString());
        }

        int iWeek;
        try {
            iWeek = Integer.parseInt(week);
            if (iWeek < 1 || iWeek > 12) {
                jsonObject.addProperty("status", "ERROR");
                jsonObject.addProperty("reason", "Invalid week number");
                return new StringRepresentation(jsonObject.toString());
            }
        } catch (NumberFormatException e) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Invalid week number");
            return new StringRepresentation(jsonObject.toString());
        }
        if (!s.validateToken(token, iWeek)) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Invalid token");
            return new StringRepresentation(jsonObject.toString());
        }
        // check if token used before
        // TODO: duplicated tokens result error
        Attendance a = ObjectifyService.ofy().load().type(Attendance.class).filter("token", token).first().now();
        if (a != null) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Token used before");
            return new StringRepresentation(jsonObject.toString());
        }

        if(!presented.equals("false") || !presented.equals("true")){
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Invalid input");
            return new StringRepresentation(jsonObject.toString());
        }
        Attendance aNew = new Attendance(token, student_id, group, week, Boolean.parseBoolean(presented));
        ObjectifyService.ofy().save().entity(aNew);
        jsonObject.addProperty("status", "SUCCESS");
        jsonObject.addProperty("reason", "");

        return new StringRepresentation(jsonObject.toString()); 
    }
}