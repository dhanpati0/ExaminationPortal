<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
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

<%@ include file="proctorNavbar.jsp" %>
</head>
<body>
<c:if test="${notAvialable eq 'emptyQuest'}">
<script type="text/javascript">
    swal({
        title: "Not Found",
        text: "AnswerSheet Not Found For this UserName",
        icon: "error",
        button: "Ok"
    })
    
</script>
</c:if>
<c:if test="${noCandidate eq 'noCand'}">
<script type="text/javascript">
    swal({
        title: "Not Found",
        text: "Candidate Not Found",
        icon: "error",
        button: "Ok"
    })
    
</script>
</c:if>
<c:if test="${notConfigure eq 'failed'}">
<script type="text/javascript">
    swal({
        title: "Not Configure",
        text: "Scheduling Not done Yet!!!",
        icon: "error",
        button: "Ok"
    })
    
</script>
</c:if>
<div class=" text-white p-1 w-100  text-left" style="background-color: black">
        <a href="./getListOfExams">Dashboard</a>
    </div>
<div class="container text-center">
<div style="margin-top: 100px; margin-left: 200px; margin-right: 200px; border: 2px solid black;">
<form action="./getCandidateAnswerSheet" method="post"
					enctype="multipart/form-data">
					<h4 style ="color: red">Find the Answer Sheet Of Candidate</h4>
					<div class="form-group col-md-8 offset-md-2">
						<label class="font-weight-bold">Enter Candidate Username :<span class="text-danger"></span>*</label> 
						<input type="text" name="candidateEmail" id="candidateEmail" class="form-control">
					</div>
					<div class="form-group col-md-6 offset-md-3" align="center">
						<input type="submit" id="tabOneSubmit" class="btn btn-success" >
					</div>
</form>

</div>

</div>
<%@ include file="footer.jsp" %>
</body>
</html>