package com.example.guestbook;

import java.nio.charset.StandardCharsets;

import com.google.appengine.repackaged.com.google.common.hash.Hashing;
import com.google.appengine.repackaged.com.google.gson.JsonObject;
import com.googlecode.objectify.ObjectifyService;

import org.restlet.data.Cookie;
import org.restlet.data.CookieSetting;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

public class LoginResource extends ServerResource {
    @Post
    public StringRepresentation handle(Representation entity) {
        JsonObject jsonObject = new JsonObject();
        Series<Cookie> cookies = this.getRequest().getCookies();
        Cookie cookie = cookies.getFirst("sessionID");
        // TODO: if session expired
        if (cookie != null) {
            if (cookie.getValue() != null && cookie.getValue() != "") {
                jsonObject.addProperty("status", "SUCCESS");
                String id = cookie.getValue();
                jsonObject.addProperty("student_id", id);
                Person p = ObjectifyService.ofy().load().type(Person.class).id(id).now();
                jsonObject.addProperty("is_tutor", Boolean.toString(p instanceof Tutor));
                jsonObject.addProperty("reason", "You have already logged in");
                return new StringRepresentation(jsonObject.toString());
            }
        }

        Form form = new Form(entity);
        // TODO: invalid input
        String email = form.getFirstValue("email");
        // TODO: invalid input
        String pwd = form.getFirstValue("password");
        String codedPwd = Hashing.sha256().hashString(pwd, StandardCharsets.UTF_8).toString();

        Person p = ObjectifyService.ofy().load().type(Person.class).filter("email", email).first().now();

        // validate login
        if(p != null && p.validatePwd(codedPwd)) {
            // TODO: add session to database
            CookieSetting cS = new CookieSetting(0, "sessionID", p.getId());
            this.getResponse().getCookieSettings().add(cS);
            jsonObject.addProperty("status", "SUCCESS");
            jsonObject.addProperty("student_id", p.getId());
            jsonObject.addProperty("is_tutor", Boolean.toString(p instanceof Tutor));
            return new StringRepresentation(jsonObject.toString());
        }else{
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Either email or password is incorrect");        
        }
        return new StringRepresentation(jsonObject.toString());
    }
}