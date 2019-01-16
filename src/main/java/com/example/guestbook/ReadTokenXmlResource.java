package com.example.guestbook;

import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Node;

import java.util.Iterator;

import com.googlecode.objectify.ObjectifyService;

import org.restlet.ext.xml.DomRepresentation;
import org.restlet.ext.xml.NodeList;

public class ReadTokenXmlResource extends ServerResource{
    @Post
    public StringRepresentation handle(DomRepresentation entity) {
        String result = "<Attendance>";
        NodeList nodes = entity.getNodes("record");
        if (nodes == null || nodes.isEmpty()) {
            result += "<status>ERROR</status>";
            result += "<reason>Missing elements in request</reason>";
            result += "</attendance>";
            return new StringRepresentation(result);
        }
        try {
            Iterator<Node> itr = nodes.iterator();
            if (!itr.hasNext()) {
                result += "<status>ERROR</status>";
                result += "<reason>Wrong element in request</reason>";
                result += "</attendance>";
                return new StringRepresentation(result);
            }
            Node node = (Node) itr.next();
            Node start = node.getFirstChild();
            String token = "";
            String student_id = "";
            String group = "";
            String week = "";
            int count = 0;
            while (start != null) {
                if (start.getNodeName().equals("token")) {
                    token = start.getTextContent();
                } else if (start.getNodeName().equals("student_id")) {
                    student_id = start.getTextContent();
                } else if (start.getNodeName().equals("group")) {
                    group = start.getTextContent();
                } else if (start.getNodeName().equals("week")) {
                    week = start.getTextContent();
                } else {
                    result += "<status>ERROR</status>";
                    result += "<reason>Wrong elements in request</reason>";
                    result += "</attendance>";
                    return new StringRepresentation(result);
                }
                start = start.getNextSibling();
                count++;
            }
            if (count != 4) {
                result += "<status>ERROR</status>";
                result += "<reason>Missing elements in request</reason>";
                result += "</attendance>";
                return new StringRepresentation(result);
            }

            Long lID;
            try {
                lID = Long.parseLong(student_id);
            } catch (NumberFormatException e) {
                result += "<status>ERROR</status>";
                result += "<reason>Wrong student id</reason>";
                result += "</attendance>";
                return new StringRepresentation(result);
            }
            Person p = ObjectifyService.ofy().load().type(Person.class).id(lID).now();
            if (p == null || p instanceof Tutor) {
                result += "<status>ERROR</status>";
                result += "<reason>You are not a student!</reason>";
                result += "</attendance>";
                return new StringRepresentation(result);
            }

            int iGroup;
            try {
                iGroup = Integer.parseInt(group);
            } catch (NumberFormatException e) {
                result += "<status>ERROR</status>";
                result += "<reason>Wrong group number</reason>";
                result += "</attendance>";
                return new StringRepresentation(result);
            }
            if (((Student) p).getGroup() != iGroup) {
                result += "<status>ERROR</status>";
                result += "<reason>Wrong group number</reason>";
                result += "</attendance>";
                return new StringRepresentation(result);
            }

            int iWeek;
            try {
                iWeek = Integer.parseInt(week);
            } catch (NumberFormatException e) {
                result += "<status>ERROR</status>";
                result += "<reason>Wrong week number</reason>";
                result += "</attendance>";
                return new StringRepresentation(result);
            }
            if (!((Student) p).validateToken(token, iWeek)) {
                result += "<status>ERROR</status>";
                result += "<reason>Wrong token</reason>";
                result += "</attendance>";
                return new StringRepresentation(result);
            }

            result += "<status>SUCCESS</status>";
            result += "<reason></reason>";
            result += "</attendance>";
            return new StringRepresentation(result);

        } catch (Exception e) {
            result += "<status>ERROR</status>";
            result += "<reason>Error detected, please contact developer</reason>";
            result += "</attendance>";
            return new StringRepresentation(result);
        }
    }
}