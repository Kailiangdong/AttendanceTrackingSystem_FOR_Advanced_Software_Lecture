<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.googlecode.objectify.cmd.Query" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.guestbook.Student" %>
<%@ page import="com.example.guestbook.Group" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
    <link type="text/css" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto"/>
    <script src="scripts/func.js"></script>
</head>

<body>

<%
    //
    String guestbookName = request.getParameter("guestbookName");
    if (guestbookName == null) {
        guestbookName = "default";
    }
    pageContext.setAttribute("guestbookName", guestbookName);
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
        pageContext.setAttribute("user", user);
%>

<p>Hello, ${fn:escapeXml(user.nickname)}! (You can
    <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%
        String nickname = user.getNickname();
        Query<Student> q = ObjectifyService.ofy().load().type(Student.class).filter("nickname", nickname);
        for(Student s : q){
%>
<p>Something found in query</p>
<%
        }
        Student student = q.first().now();
        //Student student = ObjectifyService.ofy().load().type(Student.class).filter("nickname ==", user.getNickname()).first().now();
        
%>
<p>please select your current group:</p>
<form>
    <div><select id="select" name="selectGroup" onchange="" style="width:200px; margin-left:32px; ">
        <option selected>Please select your group</option>
        <option value="Group 1">Group 1</option>
        <option value="Group 2">Group 2</option>
        <option value="Group 3">Group 3</option>
        <option value="Group 4">Group 4</option>
        <option value="Group 5">Group 5</option>
        <option value="Group 6">Group 6</option>
    </select>
    <input type="submit" value="select" onclick="sendRest()"/>
    </div>
</form>
<%           
} else {
%>
<p>Hello!
    <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
    to include your name with greetings you post.</p>
<%
    }
%>

<form action="/guestbook.jsp" method="get">
    <div><input type="text" name="guestbookName" value="${fn:escapeXml(guestbookName)}"/></div>
    <div><input type="submit" value="Switch Guestbook"/></div>
</form>

<%
    // Create the correct Ancestor key
    // Key<Guestbook> theBook = Key.create(Guestbook.class, guestbookName);

    // Run an ancestor query to ensure we see the most up-to-date
    // view of the Greetings belonging to the selected Guestbook.
      List<Student> students = ObjectifyService.ofy()
          .load()
          .type(Student.class) // We want only Greetings
          // .ancestor(theBook)    // Anyone in this book
          // .order("-date")       // Most recent first - date is indexed.
          //.limit(10)             // Only show 10 of them.
          .list();

    if (students.isEmpty()) {
%>
<p>No Student in the library</p>
<%
    } else {
%>
<p>Students in list</p>
<%
      // Look at all of our greetings
        for (Student stud : students) {
            pageContext.setAttribute("student_name", stud.nickname);
            pageContext.setAttribute("student_group", stud.groupname);
%>
<p><b>${fn:escapeXml(student_name)}</b> has group:<b>${fn:escapeXml(student_group)}</b></p>
<%
        }
    }
%>

</body>
</html>
<%-- //[END all]--%>
