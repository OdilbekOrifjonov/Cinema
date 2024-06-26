<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login/Sign Up</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
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
                            <label for="email" class="form-label">Email</label>
                            <input autofocus type="email" class="form-control" id="email" name="email" required>
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
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
