package com.example.guestbook;

import java.util.List;

import com.google.appengine.repackaged.com.google.gson.JsonArray;
import com.google.appengine.repackaged.com.google.gson.JsonObject;
import com.googlecode.objectify.ObjectifyService;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class MessageResource extends ServerResource{
    @Get
    public StringRepresentation handle(Representation entity) {
        JsonObject jsonObject = new JsonObject();
        Form form = new Form(entity);  
        String student_id = form.getFirstValue("student_id");  
        String date = form.getFirstValue("date");

        Student s = ObjectifyService.ofy().load().type(Student.class).id(student_id).now();
        if (s == null) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Student does not exist");
            return new StringRepresentation(jsonObject.toString());
        }
        int group = s.getGroup();
        List<Attendance> attendances = ObjectifyService.ofy().load().type(Attendance.class).filter("tutorial_group_id", "" + group).filter("date >", date).list();

        JsonArray jsonArray = new JsonArray();
        for(Attendance a : attendances){
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("student_id", a.getStudentId());
            jsonObject2.addProperty("group", a.getGroupId());
            jsonObject2.addProperty("week", a.getWeek());
            jsonArray.add(jsonObject2);
        }

        jsonObject.addProperty("status", "SUCCESS");
        jsonObject.add("attendance_log", jsonArray);
        //TODO: nothing to update
        //TODO: validation
        return new StringRepresentation(jsonObject.toString()) ; 
    }
}