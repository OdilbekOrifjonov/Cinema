<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>iTicket - Check Email</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<%
    Object currentUserObj = session.getAttribute("currentUser");
    Object userTempObj = session.getAttribute("userTemp");

    if (currentUserObj!=null || userTempObj==null) {
        response.sendRedirect("/");
        return;
    }
%>



<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h3 class="card-title text-center">Check Email</h3>
                </div>
                <div class="card-body">
                    <form action="../login/check-mail" method="post">
                        <div class="mb-3">
                            <label for="code" class="form-label">Code</label>
                            <input autofocus type="text" class="form-control" id="code" name="code" required>
                        </div>
                        <div class="text-center">
                            <button type="submit" class="btn btn-primary">Check</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>



<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
