<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="h" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Page here</title>
<%@ include file="proctorNavbar.jsp" %>
<link rel="stylesheet" type="text/css" href="styles.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
 <style type="text/css">
    a {
       		text-decoration: none; 
       		color: white;
      }
    </style>
<body style="background-color: lightcyan;">
<div class=" text-white p-1 w-100  text-left" style="background-color: black">
        <a href="./getListOfExams">Dashboard</a>
    </div>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-12">

				<div class="row">
					<c:forEach items="${examTimeList}" var="exam" varStatus="status">
						<div class="col-md-6">
							<div class="card bg-light blue-text-card mt-3">
								<div class="card-body">
									<h5 class="card-title">${exam.subTestTaker.testTaker.testTakerName}
										/ ${not empty exam.subTestTaker.subTestTakerName ? exam.subTestTaker.subTestTakerName : 'N/A'}</h5>
									<c:if test="${not empty examTimeList[status.index]}">
										<c:set var="configure" value="${examTimeList[status.index]}" />
										<p>Exam Date: ${configure.testDate}</p>
										<p>Start Time: ${configure.startTime}</p>
										<p>End Time: ${configure.endTime}</p>
									</c:if>
									<a
										href="./monitor?subtesttakerId=${configure.subTestTaker.subTestTakerId}"
										class="btn btn-light custom-button float-right underline-text px-2 py-1">
										<i class="bi-play"></i> Monitor
									</a>
									<button
										class="btn btn-light custom-button float-right underline-text px-2 py-1">
										<i class="bi-pause"></i> Pause
									</button>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>

			</div>
        </div>
          
    </div>
     <%@ include file="footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>