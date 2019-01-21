package com.example.guestbook;

import com.google.gson.JsonObject;

import org.restlet.data.Cookie;
import org.restlet.data.CookieSetting;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

public class LogoutResource extends ServerResource {
    /**
     * site: /rest/logout
     * Interface for logout, using GET method
     * 
     * Validate user state via Cookies
     * redirect user to home page
     * @param entity request input
     * @return null
     */
    @Get
    public StringRepresentation handle(Representation entity) {
        JsonObject jsonObject = new JsonObject();
        Series<Cookie> cookies = this.getRequest().getCookies();
        Cookie cookie = cookies.getFirst("sessionID");

        if (cookie != null) {
            if (cookie.getValue() != null && cookie.getValue() != "") {
                // TODO: if session expired
            }
        }

        CookieSetting cS = new CookieSetting(0, "sessionID", null);
        this.getResponse().getCookieSettings().add(cS);
        this.getResponse().redirectSeeOther(this.getRequest().getHostRef());
        return new StringRepresentation(jsonObject.toString());
    }

    /**
     * site: /rest/logout
     * Interface for logout, using POST method
     * 
     * Validate user state via Cookies
     * 
     * @param entity request elements
     * @return json format resposne
     */
    @Post
    public StringRepresentation post(Representation entity) {
        JsonObject jsonObject = new JsonObject();
        Series<Cookie> cookies = this.getRequest().getCookies();
        Cookie cookie = cookies.getFirst("sessionID");

        if (cookie != null) {
            if (cookie.getValue() != null && cookie.getValue() != "") {
                // TODO: if session expired
            }
        }

        CookieSetting cS = new CookieSetting(0, "sessionID", null);
        this.getResponse().getCookieSettings().add(cS);
        jsonObject.addProperty("status", "SUCCESS");
        jsonObject.addProperty("message", "You have successfully logged out");
        return new StringRepresentation(jsonObject.toString());
    }
}