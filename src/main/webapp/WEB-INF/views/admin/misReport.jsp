<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>MIS Reports here</title>

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
<script>
	/* window.addEventListener('DOMContentLoaded', (event) => {
	    // Extract unique college names from queryResult
	    var collegeNamesSet = new Set();
	    <c:forEach items="${queryResult}" var="row">
	        collegeNamesSet.add("${row[1]}");
	    </c:forEach>

	    // Count unique college names
	    var uniqueCollegeCount = collegeNamesSet.size;
	    console.log('Total Unique Colleges:', uniqueCollegeCount);

	    // Set the value of uniqueCollegeCount to the HTML element with ID 'uniqueCollegeCount'
	    var uniqueCollegeCountElement = document.getElementById('uniqueCollegeCount');
	    if (uniqueCollegeCountElement) {
	        uniqueCollegeCountElement.textContent = uniqueCollegeCount;
	    } else {
	        console.error('Element with ID "uniqueCollegeCount" not found.');
	    }
	}); */
</script>

<body>

	<div class="card" id="registerCandidate">


		<h3 class="card-title text-center text-dark">Summary report</h3>

		<div class="card-header">
			<h4 class="text-center text-danger">Filter</h4>
			<form class="form-group" method="post" action="./searchYear">
				<div class="row offset-md-3">


					<div class="col-md-3 control-label">
						<label class="text-primary font-weight-bold">Session</label> <select
							class="form-control" name="yearDropdown" id="yearDropdown">

						</select>
					</div>

					<div class="col-md-3 control-label">
						<label class="text-primary font-weight-bold">Select
							College Name </label> <select class="form-control" name="testTakerName"
							id="testTakerNameId">
							<option value="0">All</option>
							<c:forEach items="${testTakerList}" var="pv">
								<option value="${pv.testTakerId }"
									<c:if test = "${pv.testTakerId eq testtaker}">selected='selected' </c:if>>${pv.testTakerName}</option>
							</c:forEach>
						</select>

					</div>
					<div class="col-md-1 control-label">
						<div class="text-center " style="margin-top: 30px">
							<input type="submit" value="search" class="btn btn-success">
						</div>
					</div>
				</div>
			</form>


		</div>
		<!-- /.card-header -->
		<div class="card-body">
			<table id="example" class="table table-bordered table-striped">
				<thead>

					<tr>
						<th>Sl.No</th>
						<th>College Name</th>
						<th>Batch Name</th>
						<th>Session</th>
						<th>Enrolled</th>
						<th>Appeared</th>
						<th>Qualified</th>
						<th>Ratio</th>

					</tr>

				</thead>
				<tbody id="tableBody">
					<c:choose>
						<c:when test="${empty queryResult}">
							<tr>
								<td colspan="8" class="text-danger text-center">No data
									available</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="row" items="${queryResult}" varStatus="counter">
								<tr>
									<td>${counter.count}</td>
									<td>${row[1]}</td>
									<td>${row[2]}</td>
									<td><c:choose>
											<c:when
												test="${row[3] >= '2024-04-01' && row[3] <= '2025-03-31'}">
                                             April 2024 - March 2025
                							</c:when>
											<c:when
												test="${row[3] >= '2023-04-01' && row[3] <= '2024-03-31'}">
                    						April 2023 - March 2024
                							</c:when>
                							<c:when
												test="${row[3] >= '2022-04-01' && row[3] <= '2023-03-31'}">
                    						April 2022 - March 2023
                							</c:when>
											<c:otherwise>
                    						NA
                							</c:otherwise>
										</c:choose></td>
									<td>${row[4]}</td>
									<td>${row[5]}</td>
									<td>${row[6]}</td>
									<td id="ratio-${counter.index}"></td>
									<!-- Placeholder for ratio -->
									<c:set var="totalTotalStudents"
										value="${totalTotalStudents + row[4]}" />
									<c:set var="totalAppearedStudents"
										value="${totalAppearedStudents + row[5]}" />
									<c:set var="totalQualifiedStudents"
										value="${totalQualifiedStudents + row[6]}" />
								</tr>
								<script>
        						// Calculate ratio for this row
        						var appeared = ${row[5]};
        						var qualified = ${row[6]};
        						var ratio = qualified !== 0 ? getFraction(qualified, appeared) : "N/A";
        						document.getElementById("ratio-${counter.index}").innerText = ratio;
        
        						// Function to get simplified fraction
        						function getFraction(a, b) {
            					var gcd = function(a, b) {
                				return b ? gcd(b, a % b) : a;
            						};
            				var gcdValue = gcd(a, b);
            					return (a / gcdValue) + ":" + (b / gcdValue);
        							}
    							</script>
							</c:forEach>

							<tr>
								<td><strong>Total</strong></td>
								<td></td>
								<td></td>
								<td></td>
								<td><strong>${totalTotalStudents}<strong></td>
								<td><strong>${totalAppearedStudents}<strong></td>
								<td><strong>${totalQualifiedStudents}<strong></td>
								
								<td id="allRatio"></td>
								<!-- Leave this column empty for Ratio -->
							</tr>
							<script>
        						// Calculate ratio for this row
        						var totalQualified = ${totalQualifiedStudents};
        						var totalAppeared = ${totalAppearedStudents};
        						var ratio = totalQualified !== 0 ? getFraction(totalQualified, totalAppeared) : "N/A";
        						document.getElementById("allRatio").innerText = ratio;
        
        						// Function to get simplified fraction
        						function getFraction(a, b) {
            					var gcd = function(a, b) {
                				return b ? gcd(b, a % b) : a;
            						};
            				var gcdValue = gcd(a, b);
            					return (a / gcdValue) + ":" + (b / gcdValue);
        							}
    							</script>

						</c:otherwise>
					</c:choose>
				</tbody>


			</table>
		</div>
		<!-- /.card-body -->
	</div>



	<%@ include file="footer.jsp"%>
</body>
<script>
	// Get the current date
	var currentDate = new Date();
	var currentMonth = currentDate.getMonth();
	var startYear;

	if (currentMonth >= 3) { // April is month index 3
		startYear = currentDate.getFullYear();
	} else {
		startYear = currentDate.getFullYear() - 1;
	}

	var dropdown = document.getElementById("yearDropdown");

	//Define the selected session value from the server-side
	var selectedSession = "${session}";

	//Check if selectedSession is null or empty
	if (selectedSession === null || selectedSession === "") {
		// Set the dropdown value to "All"
		var allOption = document.createElement("option");
		allOption.text = "All";
		allOption.value = "";
		allOption.selected = true; // Set the selected attribute for "All"
		dropdown.add(allOption);
	}

	for (var i = 0; i < 3; i++) {
		var year = startYear - i;
		var startDate = year + "-04-01"; // April 1st of the year
		var endDate = (year + 1) + "-03-31";
		var option = document.createElement("option");
		option.text = "April " + year + " - March " + (year + 1);
		option.value = startDate + " - " + endDate;

		// Check if the current option matches the selected session and if it's not "All"
		if (selectedSession === option.value && selectedSession !== "") {
			option.selected = true; // Set the selected attribute
		}

		dropdown.add(option);
	}

	//Add "All" as an option if it's not already added
	if (selectedSession !== "") {
		var allOption = document.createElement("option");
		allOption.text = "All";
		allOption.value = ""; // Optionally set the value of the option to an empty string
		dropdown.add(allOption);
	}

	$(function() {
		var table = $("#example")
				.DataTable(
						{
							"responsive" : true,
							"lengthChange" : true,
							"autoWidth" : false,
							"buttons" : [ {
								extend : 'excelHtml5',
								title : function() {
									var session = $(
											'#yearDropdown option:selected')
											.text();

									var collegeName = $(
											'#testTakerNameId option:selected')
											.text();

									if (session == "All"
											&& collegeName == "All") {
										return 'Report For All Session And All Colleges ';
									} else if (session != ''
											&& collegeName == '') {
										return 'Report For- ' + session;
									} else if (session != ''
											&& collegeName != '') {
										return 'Report For- ' + session + ' - '
												+ collegeName;
									}

									else {
										return 'All';
									}
								}
							} ]
						});

		var wrapper = $('#example_wrapper');
		table.buttons().container().appendTo(wrapper.find('.col-md-6:eq(0)'));
		wrapper.find('.dataTables_length').appendTo(
				wrapper.find('.col-md-6:eq(0)'));
	});
</script>
<script src="../../plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="../../plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- DataTables  & Plugins -->
<script src="../../plugins/datatables/jquery.dataTables.min.js"></script>
<script
	src="../../plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
<script
	src="../../plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>
<script
	src="../../plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>
<script
	src="../../plugins/datatables-buttons/js/dataTables.buttons.min.js"></script>
<script
	src="../../plugins/datatables-buttons/js/buttons.bootstrap4.min.js"></script>
<script src="../../plugins/jszip/jszip.min.js"></script>
<script src="../../plugins/pdfmake/pdfmake.min.js"></script>
<script src="../../plugins/pdfmake/vfs_fonts.js"></script>
<script src="../../plugins/datatables-buttons/js/buttons.html5.min.js"></script>
<script src="../../plugins/datatables-buttons/js/buttons.print.min.js"></script>
<script src="../../plugins/datatables-buttons/js/buttons.colVis.min.js"></script>
</html>