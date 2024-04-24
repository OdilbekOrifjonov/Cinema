<%@ page import="uz.likwer.cinema.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>iTicket</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<%
    Object currentUserObj = session.getAttribute("currentUser");

    User currentUser = null;
    if (currentUserObj==null) {
        response.sendRedirect("/");
        return;
    }else {
        currentUser = (User) currentUserObj;
    }
%>





<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
