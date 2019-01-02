package com.example.guestbook;

import com.google.appengine.repackaged.com.google.gson.JsonArray;
import com.google.appengine.repackaged.com.google.gson.JsonObject;
import com.googlecode.objectify.ObjectifyService;

import org.restlet.data.Cookie;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

public class GetTokenJsonResource extends ServerResource{
    /**
     * Request parameters: none
     * id will identified via cookie
     * 
     * @param entity
     * @return JsonObject in StringRepresentation
     */
    @Get
    public StringRepresentation handle(Representation entity) {
        JsonObject jsonObject = new JsonObject();
        // Read cookie contents and find id
        Series<Cookie> cookies = this.getRequest().getCookies();
        Cookie cookie = cookies.getFirst("sessionID");
        if (cookie == null || cookie.getValue() == null) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Your session is expired. Please log in again");
            return new StringRepresentation(jsonObject.toString());
        }

        String id = cookie.getValue();
        Long lID = Long.parseLong(id);
        Person p = ObjectifyService.ofy().load().type(Person.class).id(lID).now();
        if(p != null && p instanceof Student){
            JsonArray jsonArray = new JsonArray();
            String[] tokens = ((Student)p).getTokens();
            //TODO: change to 13
            for(int i = 1; i < 12; i++){
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("week", "" + i);
                jsonObject2.addProperty("token", tokens[i-1]);
                jsonArray.add(jsonObject2);
            }
            jsonObject.addProperty("status", "SUCCESS");
            jsonObject.add("token", jsonArray);         
        }else{
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "You are not a student");
        }
        
        return new StringRepresentation(jsonObject.toString());
    }
}