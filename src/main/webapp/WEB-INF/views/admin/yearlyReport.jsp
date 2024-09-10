<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Yearly Report</title>

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


<style>
td[colspan] {
	border: none; /* Remove borders for the merged cells */
}
</style>

</head>
<script>
    window.addEventListener('DOMContentLoaded', (event) => {
        // Extract unique college names from queryResult
        var collegeNamesSet = new Set();
        <c:forEach items="${queryResult}" var="row">
            collegeNamesSet.add("${row.CollegeName}");
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
    });
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
			
			<a href="./downloadExcel" class="mr-20 btn btn-dark">Export To Excel</a>
		</div>
		<!-- /.card-header -->
		<div class="card-body">
			<table id="example1" class="table table-bordered table-striped">
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

							<c:forEach items="${queryResult}" var="row" varStatus="count">
								<tr>
									<td>${row.test_taker_id}</td>
									<td>${row.CollegeName}</td>
									<td>${row.BatchName}</td>
									<td><c:choose>
											<c:when
												test="${row.test_date >= '2023-04-01' && row.test_date <= '2024-03-31'}">
                   							 April 2023 - March 2024
               							 </c:when>
											<c:when
												test="${row.test_date >= '2022-04-01' && row.test_date <= '2023-03-31'}">
                   							 April 2022 - March 2023
               							 </c:when>
											<c:otherwise>
                  						  	NA
                						</c:otherwise>
										</c:choose></td>
									<td>${row.total_students}</td>
									<td>${row.appeared_students}</td>
									<td>${row.qualified_students}</td>
									<c:set var="totalTotalStudents"
										value="${totalTotalStudents + row.total_students}" />
									<c:set var="totalAppearedStudents"
										value="${totalAppearedStudents + row.appeared_students}" />
									<c:set var="totalQualifiedStudents"
										value="${totalQualifiedStudents + row.qualified_students}" />
								</tr>
							</c:forEach>


						</c:otherwise>
					</c:choose>
				</tbody>
				<tfoot>
					<tr>
						<th>Total</th>
						<th id="uniqueCollegeCount"></th>
						<th></th>
						<th></th>
						<th>${totalTotalStudents}</th>
						<th>${totalAppearedStudents}</th>
						<th>${totalQualifiedStudents}</th>
					</tr>
				</tfoot>

			</table>
		</div>
		<!-- /.card-body -->
	</div>

	

	<%@ include file="footer.jsp"%>
</body>
<!-- Page specific script -->

<script type="text/javascript">



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

// Define the selected session value from the server-side
var selectedSession = "${session}";

// Check if selectedSession is null or empty
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

// Add "All" as an option if it's not already added
if (selectedSession !== "") {
    var allOption = document.createElement("option");
    allOption.text = "All";
    allOption.value = ""; // Optionally set the value of the option to an empty string
    dropdown.add(allOption);
}


    /* var allOption = document.createElement("option");
    allOption.text = "All";
    allOption.value = ""; // Optionally set the value of the option to an empty string
    dropdown.add(allOption); */

    

    // Event listener to handle the selection change
    

		function mergeCells() {
			var table = document.getElementById("tableBody");
			var rows = table.getElementsByTagName("tr");

			var prevTestId = null;
			var prevCollegeName = null;
			var rowspanTestId = 1;
			var rowspanCollegeName = 1;

			for (var i = 0; i < rows.length; i++) {
				var currentRow = rows[i];
				var currentTestId = currentRow.cells[0].innerHTML;
				var currentCollegeName = currentRow.cells[1].innerHTML;

				// Merge cells for Test Taker ID
				if (prevTestId === currentTestId) {
					rowspanTestId++;
					currentRow.cells[0].style.display = "none"; // Hide the cell

					// If it's the last row for the test taker, set the rowspan
					if (i === rows.length - 1) {
						rows[i - rowspanTestId + 1].cells[0].rowSpan = rowspanTestId;
					}
				} else {
					// If it's not the first row, set the rowspan of the previous test taker
					if (prevTestId !== null && i > 1) {
						rows[i - rowspanTestId].cells[0].rowSpan = rowspanTestId;
					}
					prevTestId = currentTestId;
					rowspanTestId = 1;
				}

				// Merge cells for College Name
				if (prevCollegeName === currentCollegeName) {
					rowspanCollegeName++;
					currentRow.cells[1].style.display = "none"; // Hide the cell

					// If it's the last row for the college, set the rowspan
					if (i === rows.length - 1) {
						rows[i - rowspanCollegeName + 1].cells[1].rowSpan = rowspanCollegeName;
					}
				} else {
					// If it's not the first row, set the rowspan of the previous college
					if (prevCollegeName !== null && i > 1) {
						rows[i - rowspanCollegeName].cells[1].rowSpan = rowspanCollegeName;
					}
					prevCollegeName = currentCollegeName;
					rowspanCollegeName = 1;
				}
			}
		}

		// Call mergeCells function after the table is populated
		mergeCells();
	</script>
	


</html>