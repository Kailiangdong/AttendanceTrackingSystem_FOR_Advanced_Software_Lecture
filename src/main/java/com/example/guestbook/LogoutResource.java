package com.example.guestbook;

import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;
import com.googlecode.objectify.ObjectifyService;

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
     * site: /rest/logout Interface for logout, using GET method
     * 
     * Validate user state via Cookies redirect user to home page
     * 
     * @param entity request input
     * @return null
     */
    @Get
    public StringRepresentation handle(Representation entity) {
        JsonObject jsonObject = new JsonObject();
        Series<Cookie> cookies = this.getRequest().getCookies();
        Cookie cookie = cookies.getFirst("sessionID");

        if (cookie != null) {
            if (cookie.getValue() != null && !cookie.getValue().equals("")) {
                // delete session from session pool
                Date now = new Date();
                List<Session> expired_sessions = ObjectifyService.ofy().load().type(Session.class)
                        .filter("expired_data <", now).list();
                ObjectifyService.ofy().delete().entities(expired_sessions).now();
                String session_id = cookie.getValue();
                Long lSession;
                try {
                    lSession = Long.parseLong(session_id);
                    Session session = ObjectifyService.ofy().load().type(Session.class).id(lSession).now();
                    if (session != null)
                        ObjectifyService.ofy().delete().entity(session).now();
                } catch (NumberFormatException e) {
                    // handle exception
                }
            }
        }

        CookieSetting cS = new CookieSetting(0, "sessionID", null);
        this.getResponse().getCookieSettings().add(cS);
        this.getResponse().redirectSeeOther(this.getRequest().getHostRef());
        return new StringRepresentation(jsonObject.toString());
    }

    /**
     * site: /rest/logout Interface for logout, using POST method
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
            if (cookie.getValue() != null && !cookie.getValue().equals("")) {
                // delete session from session pool
                Date now = new Date();
                List<Session> expired_sessions = ObjectifyService.ofy().load().type(Session.class)
                        .filter("expired_data <", now).list();
                ObjectifyService.ofy().delete().entities(expired_sessions).now();
                String session_id = cookie.getValue();
                Long lSession;
                try {
                    lSession = Long.parseLong(session_id);
                } catch (NumberFormatException e) {
                    // handle exception
                    jsonObject.addProperty("status", "ERROR");
                    jsonObject.addProperty("reason", "Error in cookie, please contact to developer");
                    return new StringRepresentation(jsonObject.toString());
                }

                Session session = ObjectifyService.ofy().load().type(Session.class).id(lSession).now();
                if (session != null)
                    ObjectifyService.ofy().delete().entity(session).now();
            }
        }

        CookieSetting cS = new CookieSetting(0, "sessionID", null);
        this.getResponse().getCookieSettings().add(cS);
        jsonObject.addProperty("status", "SUCCESS");
        jsonObject.addProperty("message", "You have successfully logged out");
        return new StringRepresentation(jsonObject.toString());
    }
}