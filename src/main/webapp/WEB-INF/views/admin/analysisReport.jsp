<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Analysis Report</title>
<%@ include file="navbar.jsp"%>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.min.css">
<link
	rel="https://cdnjs.datatables/1.12.1/css/dataTables.bootstrap4.min.css">
<!-- Option 1: Include in HTML -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script
	src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
<script
	src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap4.min.js"></script>


<link rel='stylesheet'
	href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>
<link
	href="
https://cdn.jsdelivr.net/npm/sweetalert2@11.7.12/dist/sweetalert2.min.css
"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.12/dist/sweetalert2.all.min.js
"></script>
</head>

<body>
<div class="container">
		<h3 class="text-center text-danger">Analysis Report</h3>
	<div class="container" style="margin-top: 25px; margin-right: 50px; border: 1px solid black;">
				<ul >
					<li class="nav-item"><a href="./registerCandidatesReport" style="cursor:pointer;">Register Candidates</a></li>
					<li class="nav-item"><a href="./appearedCandidatesReport" style="cursor:pointer;">Appeared Candidates</a></li>
					<li class="nav-item"><a href="./notAppearedCandidatesReport" style="cursor:pointer;">Not Appeared Candidates</a></li>
					<li class="nav-item"><a href="./qualifiedCandidatesReport" style="cursor:pointer;">Qualified Candidates</a></li>						
					<li class="nav-item"><a href="./yearlyReport" style="cursor:pointer;">MIS Report</a></li>
				</ul>
			
			
		</div>	
			
</div>
<%@ include file="footer.jsp" %>
</body>

</html>