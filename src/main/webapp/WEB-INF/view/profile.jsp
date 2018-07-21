<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.util.List" %>
<%@ page import= "java.util.UUID" %>

<%@ page import="codeu.model.data.Profile" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.store.basic.ProfileStore" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= request.getSession().getAttribute("user") %></title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <%@ include file="../reusables/navbar.jsp" %>
  
  <div id="container">
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hi my name is <%= request.getSession().getAttribute("user") %>!</a>
      <p>About me:</p>
      <hr/>
    <%} %>
    
    <% List<Profile> profiles = (List<Profile>) request.getAttribute("profiles");
      if(profiles == null) {
    %>
      <p>Welcome.</p>
    <% } else {  %>
    <%
      String aboutMe = (String) request.getAttribute("aboutMe");
    %>
      <%= aboutMe %>
    <%} %>

    <form action="/profile/" method="POST">
      <div class="form-group">
        <label class="form-control-label">Edit:</label>
        <input type="text" name="profileAbout">
      </div>       
      <button type="submit">Update</button>
    </form>
  <hr/>
  <% List<String> convos = (List<String>) request.getAttribute("convos");
    if(convos != null){
  %>
    <p>My Conversations:</p>
    <% for(int i = 0; i <convos.size(); i++){ %>
      <p><%= convos.get(i) %></p>
    <%} %> 
    <%} else {%> 
      <p>Add me in some conversations!</p>
    <%} %>
  <hr/>
  </div>  
</body>
</html>
