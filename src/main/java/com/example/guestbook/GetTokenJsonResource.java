package com.example.guestbook;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.googlecode.objectify.ObjectifyService;

import org.restlet.data.Cookie;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

public class GetTokenJsonResource extends ServerResource {
    /**
     * site: /rest/attendance/get/json 
     * Request parameters: none id will identified
     * via cookies
     * 
     * check if user is a student
     * return his/her tokens for this semester
     * 
     * @param entity request input(none)
     * @return JsonObject in StringRepresentation
     */
    @Get
    public StringRepresentation handle(Representation entity) {
        JsonObject jsonObject = new JsonObject();
        // Read cookie contents and find id
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

        Long lID = Long.parseLong(id);
        Person p = ObjectifyService.ofy().load().type(Person.class).id(lID).now();
        if (p != null && p instanceof Student) {
            JsonArray jsonArray = new JsonArray();
            String[] tokens = ((Student) p).getTokens();
            for (int i = 1; i < 13; i++) {
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("week", "" + i);
                jsonObject2.addProperty("token", tokens[i - 1]);
                jsonArray.add(jsonObject2);
            }
            jsonObject.addProperty("status", "SUCCESS");
            jsonObject.add("token", jsonArray);
        } else {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "You are not a student");
        }

        return new StringRepresentation(jsonObject.toString());
    }
}