<%@ page import="java.util.UUID" %>
<%@ page import="java.util.List" %>
<%@ page import="uz.likwer.cinema.entity.Session" %>
<%@ page import="uz.likwer.cinema.repo.SessionRepo" %>
<%@ page import="uz.likwer.cinema.utils.Utils" %>
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

    String movieIdStr = request.getParameter("movieId");

    UUID movieId = null;
    List<Session> sessions = List.of();
    if (movieIdStr!=null) {
        movieId = UUID.fromString(movieIdStr);
        sessions = SessionRepo.findAllByMovieId(movieId);
    }

%>

<div style="display: flex; justify-content: center; align-items: center; ">
    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-success" style="margin: 5px">Home</a>
</div>

<div style="display: flex; justify-content: center; " class="row">
    <% if (movieId!=null) { %>
        <% for (Session hallSession : sessions) { %>
            <div class="col-3 card m-3" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title"><%=hallSession.getMovie().getTitle()%></h5>
                    <h6 class="card-subtitle mb-2 text-muted">Start <%=Utils.prettyDateTime(hallSession.getStartTime())%></h6>
                    <h6 class="card-subtitle mb-2 text-muted">End <%=Utils.prettyDateTime(hallSession.getEndTime())%></h6>
                    <p class="card-text">Hall: <%=hallSession.getHall().getName()%></p>
                    <a href="${pageContext.request.contextPath}/seats.jsp?sessionId=<%=hallSession.getId()%>" class="card-link">Buy Ticket</a>
                </div>
            </div>
        <%}%>
    <% } %>
</div>




<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
