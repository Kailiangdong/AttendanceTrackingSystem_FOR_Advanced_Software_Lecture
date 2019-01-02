package com.example.guestbook;

import com.google.appengine.repackaged.com.google.gson.JsonObject;
import com.googlecode.objectify.ObjectifyService;

import org.restlet.data.Cookie;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

public class AttendanceResource extends ServerResource {
    @Post
    public StringRepresentation handle(Representation entity) {
        JsonObject jsonObject = new JsonObject();
        Series<Cookie> cookies = this.getRequest().getCookies();
        Cookie cookie = cookies.getFirst("sessionID");
        if (cookie == null || cookie.getValue() == null) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Your session is expired. Please log in again");
        }

        String id = cookie.getValue();
        Person p = ObjectifyService.ofy().load().type(Person.class).id(id).now();
        if (p instanceof Tutor) {
            Form form = new Form(entity);
            String token = form.getFirstValue("token");
            String student_id = form.getFirstValue("student_id");
            String group = form.getFirstValue("group");
            String week = form.getFirstValue("week");
            String presented = form.getFirstValue("presented");

            Student s = ObjectifyService.ofy().load().type(Student.class).id(student_id).now();
            if (s == null) {
                jsonObject.addProperty("status", "ERROR");
                jsonObject.addProperty("reason", "Student does not exist");
                return new StringRepresentation(jsonObject.toString());
            }
            // check id token used before
            // TODO: duplicated tokens result error
            Attendance a = ObjectifyService.ofy().load().type(Attendance.class).filter("token", token).first().now();
            if (a != null) {
                jsonObject.addProperty("status", "ERROR");
                jsonObject.addProperty("reason", "Token used before");
                return new StringRepresentation(jsonObject.toString());
            }
            int iGroup = Integer.parseInt(group);
            if (iGroup < 1 || iGroup > 6 || iGroup != s.getGroup()) {
                jsonObject.addProperty("status", "ERROR");
                jsonObject.addProperty("reason", "Invalid group number");
                return new StringRepresentation(jsonObject.toString());
            }
            int iWeek = Integer.parseInt(week);
            if (iWeek < 1 || iWeek > 12) {
                jsonObject.addProperty("status", "ERROR");
                jsonObject.addProperty("reason", "Invalid week number");
                return new StringRepresentation(jsonObject.toString());
            }
            if (!s.validateToken(token, iWeek)) {
                jsonObject.addProperty("status", "ERROR");
                jsonObject.addProperty("reason", "Invalid token");
                return new StringRepresentation(jsonObject.toString());
            }
            Attendance aNew = new Attendance(token, student_id, group, week, Boolean.parseBoolean(presented));
            ObjectifyService.ofy().save().entity(aNew);
            jsonObject.addProperty("status", "SUCCESS");
            jsonObject.addProperty("reason", "");

        } else {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "You are not a tutor");
        }

        return new StringRepresentation(jsonObject.toString());
    }
}