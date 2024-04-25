<%@ page import="java.util.UUID" %>
<%@ page import="java.util.List" %>
<%@ page import="uz.likwer.cinema.entity.Seat" %>
<%@ page import="uz.likwer.cinema.entity.User" %>
<%@ page import="uz.likwer.cinema.entity.Session" %>
<%@ page import="uz.likwer.cinema.repo.SessionRepo" %>
<%@ page import="java.util.Comparator" %>
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

    String sessionIdStr = request.getParameter("sessionId");

    UUID sessionId = null;

    List<Seat> seats = null;
    if (sessionIdStr != null) {
        sessionId = UUID.fromString(sessionIdStr);

        Session sessionMy = SessionRepo.findById(sessionId);
        sessionMy.getSeats().sort(Comparator.comparing(Seat::getId));
        seats = sessionMy.getSeats();
    }
    Session sessionMy = SessionRepo.findById(sessionId);
%>

<div style="display: flex; justify-content: center; align-items: center; ">
    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-success" style="margin: 5px">Home</a>
    <a href="${pageContext.request.contextPath}/sessions.jsp?movieId=<%=sessionMy.getMovie().getId()%>" class="btn btn-success">Sessions</a>
</div>

<div style="height: 25%">

</div>

<% if (sessionId != null) { %>
    <div style="display: flex; justify-content: center; " class="row">

    <% for (int i = 0; i < seats.size(); i++) { %>
        <div class="col-1 m-3" style="width: 3rem;">
            <a href="${pageContext.request.contextPath}/seat/book?seatId=<%=seats.get(i).getId().toString()%>&sessionId=<%=sessionMy.getId().toString()%>" type="button" class="btn btn-<%=seats.get(i).getIsBooked()?"danger":"success"%>"><%=seats.get(i).getIsBooked()?"Booked":"Book"%></a>
        </div>
        <% if ((i+1)%8==0) { %>
            </div>
            <div style="display: flex; justify-content: center; " class="row">
        <% } %>
        <% if (i == seats.size()-1) { %></div><% } %>
    <% } %>
<%}%>
<%%>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>