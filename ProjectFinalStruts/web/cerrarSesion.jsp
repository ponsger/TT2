<%@page contentType="text/plain" pageEncoding="UTF-8"%>
<%
  out.println("Cerrando sesión");
  response.sendRedirect("index.html");
%>
