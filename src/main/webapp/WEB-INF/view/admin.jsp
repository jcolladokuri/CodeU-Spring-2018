<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="java.util.Map" %>
<%
Map<String, String> labeledStats = (Map<String, String>) request.getAttribute("labeledStats");
%>

<!DOCTYPE html>
<html>
<head>
  <title>Admin Page</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <%@ include file="/WEB-INF/reusables/navbar.jsp" %>

  <div id="container">
    <h1>Admin Page</h1>

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } else { %>

      <h2>Hello, administrator!</h2>
      <p>Here is some data:</p>
      <ul>
        <%
          for (Map.Entry<String, String> labeledStat : labeledStats.entrySet()) {
        %>
        <li><strong><%= labeledStat.getKey() %></strong> <%= labeledStat.getValue() %></li>
        <% } %>
      </ul>

    <% } %>

  </div>
</body>
</html>
