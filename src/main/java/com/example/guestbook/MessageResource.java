package com.example.guestbook;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
        Long slID = Long.parseLong(student_id);

        Student s = ObjectifyService.ofy().load().type(Student.class).id(slID).now();
        if (s == null) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Student does not exist");
            return new StringRepresentation(jsonObject.toString());
        }
        int group = s.getGroup();
        List<Attendance> attendances = ObjectifyService.ofy().load().type(Attendance.class).filter("tutorial_group_id", "" + group).filter("date >", date).list();
        if(!attendances.isEmpty()){
            JsonArray jsonArray = new JsonArray();
            for(Attendance a : attendances){
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("student_id", a.getStudentId());
                jsonObject2.addProperty("group", a.getGroupId());
                jsonObject2.addProperty("week", a.getWeek());
                jsonArray.add(jsonObject2);
            }

            jsonObject.addProperty("status", "NEW_ATTENDANCE");
            jsonObject.add("attendance_log", jsonArray);
        }else{
            // //TODO: validation
            // List<Validate> validates = ObjectifyService.ofy().load().type(Validate.class).filter("tutorial_group_id", "" + group).filter("date >", date).list();
            // if(!validates.isEmpty()){
            //     JsonArray jsonArray = new JsonArray();
            //     for(Validate v : validates){
            //         JsonObject jsonObject2 = new JsonObject();
            //         jsonObject2.addProperty("validate_id", v.getValidateId());
            //         jsonObject2.addProperty("student_id", v.getStudentId());
            //         jsonObject2.addProperty("token", v.getToken());
            //         jsonObject2.addProperty("week", v.getWeek());
            //         jsonObject2.addProperty("group", v.getGroupId());
            //         jsonArray.add(jsonObject2);
            //     }
            //     jsonObject.addProperty("status", "VALIDATION");
            //     jsonObject.add("validations", jsonArray);
            // }else{
            //     // nothing to update
            //     jsonObject.addProperty("status", "SUCCESS");
            // }
        }
 
        return new StringRepresentation(jsonObject.toString()) ; 
    }
}