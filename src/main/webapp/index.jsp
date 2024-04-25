<%@ page import="uz.likwer.cinema.repo.MovieRepo" %>
<%@ page import="uz.likwer.cinema.entity.Movie" %>
<%@ page import="java.util.List" %>
<%@ page import="uz.likwer.cinema.entity.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%
    List<Movie> movies = MovieRepo.findAll();

    Object currentUserObj = session.getAttribute("currentUser");

    User currentUser = null;
    if (currentUserObj == null) {
        response.sendRedirect("/login.jsp");
        return;
    } else {
        currentUser = (User) currentUserObj;
    }
%>


<header>
    <nav id="main-navbar" class="navbar navbar-expand-lg navbar-light bg-white fixed-top">
        <!-- Container wrapper -->
        <div class="container-fluid">
            <ul class="navbar-nav ms-auto d-flex flex-row">
                <li class="nav-item dropdown" style="margin-right: 5px">
                    <a class="btn btn-success" href="${pageContext.request.contextPath}/profile.jsp">Profile</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="btn btn-success" href="${pageContext.request.contextPath}/login/logout">Logout</a>
                </li>
            </ul>
        </div>
    </nav>
</header>

<main style="margin-top: auto;">
    <div style="height: 57px"></div>


    <div style="display: flex; justify-content: center; " class="row">
        <% for (Movie movie : movies) { %>
        <div class="col-3 card m-3">
            <h5 class="card-header">NEW</h5>
            <div class="card-body">
                <h3 class="card-title"><%=movie.getTitle()%>
                </h3>
                <a href="${pageContext.request.contextPath}/sessions.jsp?movieId=<%=movie.getId().toString()%>"
                   class="btn btn-primary">Sessions</a>
            </div>
        </div>
        <% } %>
    </div>
</main>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>