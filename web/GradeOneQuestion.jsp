
<%@page import= "Project.QBean" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id= "id" class= "Project.QBean" scope= "session">
</jsp:useBean>
<% id.setSelect(request.getParameter("Select")); %>
<% id.setChecked(request.getParameterValues("choices")); %>
<% id.setSelected(request.getParameter("choices")); %>
<% id.printPage();%>
<!DOCTYPE html>
<html>
    <head>
        <style type = "text/css">
            #body {font-family: "Courier New", sans-serif; font-size: 100%; color: black}
        </style>
        <style> 
            #h3style {color: white; font-family: Helvetica, sans-serif;  font-size: 100%; border-color: #6193cb; text-align: center;margin-bottom: 0.5em; background-color: #6193cb;}  </style>
    </head>
    <body>
        <form>
            <div>
                <h3 id="h3style" style="width: 500px auto; max-width: 620px; margin: 0 auto; "> Multiple-Choice Question <jsp:getProperty name="id" property="title" /> </h3>                
            </div>
            <div style="width: 500px auto; max-width: 620px; margin: 0 auto; border: 1px solid #f6912f; font-weight: normal ">
                <p><jsp:getProperty name="id" property="page" /></p>
            </div>
        </form>
    </body>
</html>