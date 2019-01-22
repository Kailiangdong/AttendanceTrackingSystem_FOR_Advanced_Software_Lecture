package com.example.guestbook;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.googlecode.objectify.ObjectifyService;

import org.restlet.data.Cookie;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

public class AttendanceLogResource extends ServerResource {
    /**
     * site: /rest/attendance/log Interface for receiving attendance log need
     * Cookies
     * 
     * Validate user login state via Cookies if user is a student, return his/her
     * attendance log If user is a tutor, check request inputs(group and week)
     * return selected attendance log
     * 
     * @param entity request input
     * @return json format response
     */
    @Post
    public StringRepresentation handle(Representation entity) {
        JsonObject jsonObject = new JsonObject();
        Series<Cookie> cookies = this.getRequest().getCookies();
        Cookie cookie = cookies.getFirst("sessionID");
        String id;
        if (cookie == null || cookie.getValue() == null) {
            // User is not logged in
            Form form = new Form(entity); // only for debug
            id = form.getFirstValue("student_id"); // only for debug
            if (id == null || id.equals("")) { // only for debug
                jsonObject.addProperty("status", "ERROR");
                jsonObject.addProperty("reason", "Your session is expired. Please log in again");
                return new StringRepresentation(jsonObject.toString());
            }
        } else {
            id = cookie.getValue();
        }

        Long lID;
        try {
            lID = Long.parseLong(id);
        } catch (NumberFormatException e) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Please contact to developer");
            return new StringRepresentation(jsonObject.toString());
        }

        Person p = ObjectifyService.ofy().load().type(Person.class).id(lID).now();
        if (p == null) {
            //user not found
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Undefined person");
            return new StringRepresentation(jsonObject.toString());
        }

        JsonArray jsonArray = new JsonArray();
        if (p instanceof Student) {
            // user is a student, return his/her attendance log
            List<Attendance> attendances = ObjectifyService.ofy().load().type(Attendance.class).filter("student_id", id)
                    .list();
            for (Attendance a : attendances) {
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("student_id", id);
                jsonObject2.addProperty("first_name", p.getFirstName());
                jsonObject2.addProperty("last_name", p.getLastName());
                jsonObject2.addProperty("group", a.getGroupId());
                jsonObject2.addProperty("week_num", a.getWeek());
                jsonArray.add(jsonObject2);
            }
        } else {
            // user is a tutor
            Form form = new Form(entity);
            String group = form.getFirstValue("group");
            String week = form.getFirstValue("week");
            try {
                int iGroup = Integer.parseInt(group);
                if (iGroup < 1 || iGroup > 6) {
                    jsonObject.addProperty("status", "ERROR");
                    jsonObject.addProperty("reason", "Invalid input");
                    return new StringRepresentation(jsonObject.toString());
                }
            } catch (NumberFormatException e) {
                if (!group.equals("all")) {
                    jsonObject.addProperty("status", "ERROR");
                    jsonObject.addProperty("reason", "Invalid input");
                    return new StringRepresentation(jsonObject.toString());
                }
            }

            try {
                int iWeek = Integer.parseInt(week);
                if (iWeek < 1 || iWeek > 12) {
                    jsonObject.addProperty("status", "ERROR");
                    jsonObject.addProperty("reason", "Invalid input");
                    return new StringRepresentation(jsonObject.toString());
                }
            } catch (NumberFormatException e) {
                if (!week.equals("all")) {
                    jsonObject.addProperty("status", "ERROR");
                    jsonObject.addProperty("reason", "Invalid input");
                    return new StringRepresentation(jsonObject.toString());
                }
            }

            // invalid input

            List<Attendance> attendances;
            if (group.equals("all") && week.equals("all")) {
                attendances = ObjectifyService.ofy().load().type(Attendance.class).list();
            } else if (group.equals("all")) {
                attendances = ObjectifyService.ofy().load().type(Attendance.class).filter("week_id", week).list();
            } else if (week.equals("all")) {
                attendances = ObjectifyService.ofy().load().type(Attendance.class).filter("tutorial_group_id", group).list();
            } else {
                attendances = ObjectifyService.ofy().load().type(Attendance.class).filter("tutorial_group_id", group)
                        .filter("week_id", week).list();
            }

            for (Attendance a : attendances) {
                Person p1 = ObjectifyService.ofy().load().type(Person.class).id(Long.parseLong(a.getStudentId())).now();
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("student_id", a.getStudentId());
                jsonObject2.addProperty("first_name", p1.getFirstName());
                jsonObject2.addProperty("last_name", p1.getLastName());
                jsonObject2.addProperty("group", a.getGroupId());
                jsonObject2.addProperty("week_num", a.getWeek());
                jsonArray.add(jsonObject2);
            }
        }

        jsonObject.addProperty("status", "SUCCESS");
        jsonObject.add("attandance_log", jsonArray);

        return new StringRepresentation(jsonObject.toString());
    }
}