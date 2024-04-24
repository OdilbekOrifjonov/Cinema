<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login/Sign Up</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>

<%
    Object currentUserObj = session.getAttribute("currentUser");
    if (currentUserObj!=null) {
        response.sendRedirect("/");
    }
%>

<div id="errorAlert" class="alert alert-danger" style="display: none;">
    <strong>Error!</strong> <%=request.getSession().getAttribute("error")%>
</div>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h3 class="card-title text-center">Login/Sign Up</h3>
                </div>
                <div class="card-body">
                    <form action="../login" method="post">
                        <div class="mb-3">
                            <label for="username" class="form-label">Username</label>
                            <input type="text" class="form-control" id="username" name="username" required>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                        </div>
                        <div class="text-center">
                            <button type="submit" class="btn btn-primary">Login/Sign Up</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script>
    test();

    function test() {
        const error = "<%=session.getAttribute("error") == null?"null":session.getAttribute("error")%>";

        if (error !== "null") {
            showSuccessAlert()
        }
    }

    function showSuccessAlert() {
        const alertElement = document.getElementById("errorAlert");
        alertElement.style.display = "block";
        setTimeout(function () {
            alertElement.style.display = "none";
        }, 5000);
        <%
        session.removeAttribute("error");
        %>
    }
</script>
</body>
</html>
