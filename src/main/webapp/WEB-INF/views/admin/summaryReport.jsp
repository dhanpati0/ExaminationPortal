<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Summary Report</title>
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
	<div class="card" id="registerCandidate">


		<h3 class="card-title text-center text-dark">Summary report</h3>

		<div class="card-header">
			<h4 class="text-center text-danger">Filter</h4>
			<form class="form-group" method="post"
				action="">
				<div class="row">
					
					
					<div class="col-md-3 control-label">
						<label class="text-primary font-weight-bold">Session</label> <select
							class="form-control" id="yearDropdown">
							
						</select>
					</div>
					
					<div class="col-md-3 control-label">
						<label class="text-primary font-weight-bold">Select
							College Name </label> <select class="form-control" name="testTakerName"
							id="testTakerNameId" onchange="getSub()">
							<option value="0">All</option>
							<c:forEach items="${testTakerList}" var="pv">
								<option value="${pv.testTakerId }">${pv.testTakerName}</option>
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
			<table id="example1" class="table table-bordered table-striped">
				<thead>

					<tr>
						<th>Sl.No</th>
						<th>College Name</th>
						<th>Session</th>
						<th>Enrolled</th>
						<th>Appeared</th>
						<th>Qualified</th>
						<th>Ratio</th>

					</tr>

				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty allCollege}">
							<tr>
								<td colspan="6" class="text-danger text-center">No data
									available</td>
							</tr>
						</c:when>
						<c:otherwise>

							<c:forEach items="${allCollege}" var="cand" varStatus="count">
								<tr>
									<td>
									<c:set var="totalCollege"
													value="${count.count}" />
												<c:out value="${count.count}" />
									
									</td>
									<td>${cand}</td>
									<td id="yearDropdown"></td>
									<td>
										<!-- Start of column 3 --> 
										<c:forEach items="${totalCandByCollege}" var="totalCand"
											varStatus="innerCount">
											<c:if test="${count.index == innerCount.index}">
												<c:set var="totalCandSum"
													value="${totalCandSum + totalCand}" />
												<c:out value="${totalCand}" />
											</c:if>
										</c:forEach>
									</td>
									<td>
										<!-- Start of column 3 --> 
										<c:forEach items="${totalAppearedCandidate}" var="totalAppear"
											varStatus="innerCount">
											<c:if test="${count.index == innerCount.index}">
												<c:set var="totalAppearSum"
													value="${totalAppearSum + totalAppear}" />
												<c:out value="${totalAppear}" />
											</c:if>
										</c:forEach>
									</td>
									<!-- End of column 4 -->
									<td>
										<!-- Start of column 3 --> <c:forEach
											items="${totalQualified}" var="totalQual"
											varStatus="innerCount">
											<c:if test="${count.index == innerCount.index}">
												<c:set var="totalQualSum"
													value="${totalQualSum + totalQual}" />
												<c:out value="${totalQual}" />
											</c:if>
										</c:forEach>
									</td>
								</tr>
								
							</c:forEach>
							

						</c:otherwise>
					</c:choose>
				</tbody>
				<tfoot>
						<tr>
								<th>Total</th>
								<th>${totalCollege}</th>
								<th></th>
								<th>${totalCandSum}</th>
								<th>${totalAppearSum}</th>
								<th>${totalQualSum}</th>
							</tr>
				</tfoot>

			</table>
		</div>
		<!-- /.card-body -->
	</div>
	<%@ include file="footer.jsp"%>

	<script>
		// Get the current date
		var currentDate = new Date();

		// Get the current month (0-11)
		var currentMonth = currentDate.getMonth();

		// Calculate the starting year based on whether the current month is before or after April
		var startYear;
		if (currentMonth >= 3) { // April is month index 3
			startYear = currentDate.getFullYear();
		} else {
			startYear = currentDate.getFullYear() - 1;
		}

		// Create the dropdown options for three years
		var dropdown = document.getElementById("yearDropdown");
		for (var i = 0; i < 3; i++) {
			var year = startYear - i;
			var option = document.createElement("option");
			option.text = option.value = "April " + year + " - March "
					+ (year + 1);
			option.value = year;
			dropdown.add(option);
		}

	    
		var allOption = document.createElement("option");
		allOption.text = "All";
		allOption.value = ""; // Optionally set the value of the option to an empty string
		dropdown.add(allOption);

//----------------------------------------------------

		var sessionCell = document.getElementById("session");

    // Set the text content of the session <td> element
    var selectedYear = dropdown.options[dropdown.selectedIndex].text;
    sessionCell.textContent = selectedYear;

    function updateSession() {
        var selectedOption = dropdown.options[dropdown.selectedIndex].text;
        document.getElementById("session").textContent = selectedOption;
    }

    // Add event listener to the dropdown to call updateSession function on change
    dropdown.addEventListener("change", updateSession);

    // Initialize the session <td> with the initially selected option
    updateSession();
		
	</script>
</body>
</html>