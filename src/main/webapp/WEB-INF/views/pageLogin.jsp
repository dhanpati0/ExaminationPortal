<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page Here..</title>
<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.min.css">
<link
rel="https://cdnjs.datatables/1.12.1/css/dataTables.bootstrap4.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script
src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
<script
src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap4.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<style>

body {
background: url(/img/loginbg.jpg) !important;
background-size: cover !important;
padding-top: 20vh !important;
background-position: bottom;
}

.marginbox {
margin-top: 30px;
}

.loginbox {
box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0
rgba(0, 0, 0, 0.19);
}

.inputtext {
width: 100%;
padding: 12px 30px;
margin: 12px 0;
box-sizing: border-box;
border: none;
border-bottom: 1px solid grey;
}

.roundcircle {
width: 30%;
border-radius: 50px;
padding: 10px;
margin-bottom: 50px;
}
</style>
</head>
<body onload="noBack();">
<div id="alertId">
<c:if test="${Blank eq 'isBlank'}">
<script type="text/javascript">
swal({
title : "Blank Inputs",
text : "Blank !!!!!",
icon : "error",
button : "try again!",
});
</script>
</c:if>
<c:if test="${NotFound eq 'isFound'}">
<script type="text/javascript">
swal({
title : "Not Found",
text : "UserName Not Found !",
icon : "error",
button : "try again!",
});
</script>
</c:if>
<c:if test="${requestFailed eq 'emailFailed'}">
<script type="text/javascript">
swal({
title : "Request failed !",
text : "Invalid email !",
icon : "error",
button : "try again!",
});
</script>
</c:if>
<c:if test="${alert eq 'failed'}">
<script type="text/javascript">
swal({
title : "Login failed !",
text : "Invalid credentials !",
icon : "error",
button : "try again!",
});
</script>
</c:if>
<c:if test="${invalid eq 'login failed'}">
<script type="text/javascript">
swal({
title : "Login failed !",
text : "Invalid credentials !",
icon : "error",
button : "try again!",
});
</script>
</c:if>
<c:if test="${cannotLogin eq 'login'}">
<script type="text/javascript">
    swal({
        title: "Can't Login",
        text: "You Finished Your Exam !!",
        icon: "error",
        buttons: {
            tryAgain: {
                text: "Try Again",
                value: "tryAgain",
            },
            cancel: "Cancel",
        },
    }).then((value) => {
        if (value === "tryAgain") {
            // Handle "Try Again" button click
            window.location.href = 'logout'; // Replace with your actual logout endpoint
        }
    });
    
</script>
</c:if>

<c:if test="${TimeNot eq 'NotStarted'}">
<script type="text/javascript">
    swal({
        title: "Can't Login",
        text: "You are trying to login, Before Schdeuled Time",
        icon: "error",
        button: "Ok"
    })
    
</script>
</c:if>


<c:if test="${DateNot eq 'NotDateToday'}">
<script type="text/javascript">
    swal({
        title: "Can't Login",
        text: "You are trying to login, Before Schdeuled Date",
        icon: "error",
        button: "Ok"
    })
    
</script>
</c:if>


<c:if test="${Progress1 eq 'AutoLogout'}">
<script type="text/javascript">
    swal({
        title: "Can't Login",
        text: "You are Logout,Go To request for login !!",
        icon: "error",
        button: "Ok"
    })
    
</script>
</c:if>



<c:if test="${Progress2 eq 'BlockedCandidate'}">
<script type="text/javascript">
    swal({
        title: "Can't Login",
        text: "You are Blocked,Go To request for login !! ",
        icon: "error",
        button: "Ok"
    })
    
</script>
</c:if>
</div>
<div id="alertId">
<c:if test="${TimeNotStart eq 'LoginFailed'}">
<script type="text/javascript">
swal({
title : "Login According to your Time Schedule",
text : "plz check email",
icon : "error",
button : "try again!",
});
</script>
</c:if>
</div>



<div>


<div class="col-md-8 offset-md-2 loginbox">

<c:if test="${msg ne null}">
<div class="alert alert-danger">${msg}</div>
</c:if>
</br> </br>


<h3>Welcome to</h3>
<h4 >CAS(Campus Automation System) </br>Talent Hunt</h4>

<form method="post" action="./loginHere">
<div class="col-md-5">
<h5>LOGIN</h5>
<input type="text" onchange="checkUsername()" name="username" id="username" class="inputtext" ></input>
</div>
<div class="col-md-5">
<h5>PASSWORD</h5>
<input type="password" name="password" id="password" class="inputtext" ></input>
</div>
<div class="row">
<div class="col-md-8">
<input type="submit" class="btn btn-success roundcircle"
value="Log in">
<!-- <a id="requestButton" name="requestButton" hidden="true" class="btn btn-danger roundcircle" onclick="requestToLogin()">Request To Login</a> -->
<a id="requestButton" name="requestButton" hidden="true" class="btn btn-danger roundcircle ml-4" onclick="requestToLogin()" >LoginRequest<a>
</div>
</div>
</form>



</div>

</div>
<script type="text/javascript">
document.addEventListener("DOMContentLoaded",() => {
setTimeout(function(){
document.getElementById('alertId').style.display='none';
},3000);
});
function noBack(){
window.history.forward();
}

function requestToLogin(){

var usernameValue = document.getElementById("username").value;
var url = "./requestLogin?username=" + encodeURIComponent(usernameValue);

window.location.href =  url;
}

function checkUsername(){
var username = document.getElementById("username").value;
$.ajax({
url: "./checkCandidate",
method: "POST",
data: {
username:username
},
success: function(res){
if(res=="true"){
document.getElementById("requestButton").removeAttribute('hidden');
}
else{
document.getElementById("requestButton").setAttribute('hidden',true);
}
}
})
}
</script>
<%@ include file="footer.jsp" %>
</body>
</html>