package com.example.guestbook;

import java.nio.charset.StandardCharsets;

import com.google.gson.JsonObject;
import com.google.common.hash.Hashing;
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
    /**
     * site: /rest/login
     * Interface for login, using POST method
     * need Cookies
     * 
     * Validate user state via Cookies
     * Validate request elements(email, password)
     * set Cookies
     * 
     * @param entity request input
     * @return json format response
     */
    @Post
    public StringRepresentation handle(Representation entity) {
        JsonObject jsonObject = new JsonObject();
        Series<Cookie> cookies = this.getRequest().getCookies();
        Cookie cookie = cookies.getFirst("sessionID");
        // Check if user allready logged in
        if (cookie != null) {
            if (cookie.getValue() != null && !cookie.getValue().equals("")) {
                // TODO: check if session is expired       
                String id = cookie.getValue();
                jsonObject.addProperty("id", id);
                Long lID;
                try {
                    lID = Long.parseLong(id);
                } catch (NumberFormatException e) {
                    jsonObject.addProperty("status", "ERROR");
                    jsonObject.addProperty("reason", "Please contact to developer, Error code: 2");
                    return new StringRepresentation(jsonObject.toString());
                }
                Person p = ObjectifyService.ofy().load().type(Person.class).id(lID).now();
                if(p == null) {
                    //user not found
                    jsonObject.addProperty("status", "ERROR");
                    jsonObject.addProperty("reason", "Undefined person");
                    return new StringRepresentation(jsonObject.toString());
                }
                jsonObject.addProperty("status", "SUCCESS");
                jsonObject.addProperty("first_name", p.getFirstName());
                jsonObject.addProperty("last_name", p.getLastName());
                jsonObject.addProperty("is_tutor", Boolean.toString(p instanceof Tutor));
                jsonObject.addProperty("reason", "You have already logged in");
                return new StringRepresentation(jsonObject.toString());
            }
        }

        Form form = new Form(entity);

        String email = form.getFirstValue("email");
        // validate email input
        if (!ValidateFunc.validateEmail(email)) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Either email or password is incorrect");
            return new StringRepresentation(jsonObject.toString());
        }

        String pwd = form.getFirstValue("password");
        // validate password input
        if (!ValidateFunc.validatePwd(pwd)) {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Either email or password is incorrect");
            return new StringRepresentation(jsonObject.toString());
        }

        Person p = ObjectifyService.ofy().load().type(Person.class).filter("email", email).first().now();

        String saltedPwd = "";
        if (p != null)
            saltedPwd = Hashing.sha256().hashString(pwd + p.getSalt(), StandardCharsets.UTF_8).toString();

        // validate login
        if (p != null && p.validatePwd(saltedPwd)) {
            // TODO: add session to database
            CookieSetting cS = new CookieSetting(0, "sessionID", p.getId());
            CookieSetting cS2 = new CookieSetting(0, "Path", "/");
            this.getResponse().getCookieSettings().add(cS);
            this.getResponse().getCookieSettings().add(cS2);
            jsonObject.addProperty("status", "SUCCESS");
            jsonObject.addProperty("id", p.getId());
            jsonObject.addProperty("first_name", p.getFirstName());
            jsonObject.addProperty("last_name", p.getLastName());
            jsonObject.addProperty("is_tutor", Boolean.toString(p instanceof Tutor));
            return new StringRepresentation(jsonObject.toString());
        } else {
            jsonObject.addProperty("status", "ERROR");
            jsonObject.addProperty("reason", "Either email or password is incorrect");
        }
        return new StringRepresentation(jsonObject.toString());
    }
}