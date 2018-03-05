<%@page import= "Project.QBean" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id= "id" class= "Project.QBean" scope= "session">
</jsp:useBean>
<% 
    String chapter = "5";
   if (request.getParameter("chapterNo") != null) chapter = request.getParameter("chapterNo");
   String  question = "2";
   if (request.getParameter("questionNo") != null) question = request.getParameter("questionNo");
   String title = "Project";
   if (request.getParameter("title") != null) title = request.getParameter("title");
   if (request.getParameter("chapterNo") == null || request.getParameter("questionNo") == null || request.getParameter("title") == null) {
       out.println("<script>window.location.replace(\"" + request.getRequestURL() + "?chapterNo=" + chapter + "&questionNo=" + question + "&title=" + title + "\")</script>");
   }
    id.setSelect("Show");
    id.setChapterNo(Integer.parseInt(chapter));
    id.setQuestionNo(Integer.parseInt(question));
    id.setTitle(title);
    id.printPage();
%>

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
        <form action="GradeOneQuestion.jsp" method="GET">
            <div>
                <h3 id="h3style" style="width: 500px auto; max-width: 620px; margin: 0 auto; "> Multiple-Choice Question <jsp:getProperty name="id" property="title" /> </h3>                
            </div>
            <div style="width: 500px auto; max-width: 620px; margin: 0 auto; border: 1px solid #f6912f; font-weight: normal ">
                <p><jsp:getProperty name="id" property="page" /></p>
            </div>
        </form>
    </body>
</html>