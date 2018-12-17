package com.example.guestbook;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.routing.Router;
import org.restlet.Request;
import org.restlet.Response;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.List;

public class FirstStepsApplication extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());

        // Defines only one route
        // router.attachDefault(HelloWorldResource.class);

        // TODO: Read first parameter {Guestbook_Name}
        Restlet guestbook = new Restlet() {
            @Override
            public void handle(Request request, Response response) {
                String message = "Get rest request 1: " + request.getAttributes().get("guestbook");
                response.setEntity(message, MediaType.TEXT_PLAIN);
            }
        };

        Restlet greeting_id = new Restlet() {
            @Override
            public void handle(Request request, Response response) {
                // add header
                String message = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
                message += "" + request.getAttributes().get("guestbook") + " and " + request.getAttributes().get("greeting_id");
                
                response.setEntity(message, MediaType.TEXT_PLAIN);
            }
        };

        // Read first parameter {Guestbook_Name}
        router.attach("/attendance", AttendanceResource.class);
        router.attach("/{guestbook}/", guestbook);
        router.attach("/{guestbook}/{greeting_id}", greeting_id);
        

        // work with restlet
        // https://restlet.com/open-source/documentation/user-guide/2.3/core/routing/hierarchical-uris

        return router;
    }
}