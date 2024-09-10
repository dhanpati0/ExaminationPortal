<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Result</title>
<%@ include file="navbar.jsp"%>
<style>
/* Table Styles */
body {
	background-color: #f0f4f8;
	color: #333;
}

header {
	background-color: #3498db;
	color: white;
	text-align: center;
	padding: 15px;
	margin-bottom: 20px;
}

.form-container {
	background-color: white;
	padding: 20px;
	border-radius: 5px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	margin-bottom: 20px;
}

.table-container {
	background-color: white;
	padding: 20px;
	border-radius: 5px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.watermark {
	opacity: 0.5;
	color: BLACK;
	position: fixed;
	top: auto;
	left: 80%;
	font-size: 20px;
}

.table {
	width: 100%;
	border-collapse: collapse;
}

.table th, .table td {
	padding: 10px;
	text-align: center;
	border: 1px solid #ddd;
}

.table th {
	background-color: #f2f2f2;
}

.table tbody tr:nth-child(even) {
	background-color: #f9f9f9;
}

/* Additional Styles */
.table-container {
	margin: 20px auto;
	max-width: 600px;
}

.footer {
	clear: both;
	position: fixed;
	bottom: 0;
	width: 100%;
	background-color: #23395d;
	text-align: center;
	text-decoration: white;
	color: white;
}
</style>
</head>
<body>
	<br>
	<h1 class="text-center text-danger">Result Status Here</h1>
	<div class="h3"></div>
	<%-- ${regdList } --%>
	<form class="form-group" method="post" action="./searchTest">
		<div class="row">
			<div class="col-md-3 control-label offset-md-3">
				<label class="text-primary font-weight-bold">Select College
					Name<sup class="text-danger">*</sup>
				</label> <select class="form-control" name="testTakerName"
					id="testTakerNameId" onchange="getSub()">
					<option value="0">--select--</option>
					<c:forEach items="${testTakerList}" var="pv">
						<option value="${pv.testTakerId}"
							<c:if test = "${pv.testTakerId eq testtaker}">selected='selected' </c:if>>${pv.testTakerName}</option>
					</c:forEach>
				</select>

			</div>
			<div class="col-md-3 control-label">
				<label class="text-primary font-weight-bold">Select Batch<sup
					class="text-danger">*</sup>
				</label> <select class="form-control" name="subTestTakerName"
					id="subTestTakerNameId">
					<option value='0'>--select--</option>
				</select>

			</div>
			<div class="col-md-1 control-label">
				<div class="text-center " style="margin-top: 30px">
					<input type="submit" value="search" class="btn btn-success">
				</div>
			</div>
		</div>
	</form>
	<div class="card">
		<div class="card-header">
			<h3 class="card-title">View Result</h3>
		</div>
		<!-- /.card-header -->
		<div class="card-body">
			<table id="example" class="table table-bordered table-striped">
				<thead>
					<tr>
						<th>Candidate Id</th>
						<th>Candidate Name</th>
						<th>Candidate Email</th>
						<th>Candidate Phone No.</th>
						<th>College Name</th>
						<th>Batch</th>
						<th>Mark Got</th>
						<th>Total Mark</th>
						<th>Date of Exam</th>
						<!-- <th>RESULT STATUS</th> -->

					</tr>
				</thead>
				<tbody>
					<c:forEach var="al" items="${resultList}" varStatus="counter">
						<c:if test="${al.markAppear ne null}">
							<tr>
								<td>${al.candid}</td>
								<td>${al.candFirstname} ${al.candLastname}</td>
								<td>${al.candidateemail}</td>
								<td>${al.candMobile}</td>
								<td>${al.subTestTaker.testTaker.testTakerName}</td>
								<td>${al.subTestTaker.subTestTakerName}</td>
								<td>${al.markAppear}</td>
								<td>${al.totalMark}</td>
								<td>${al.configure.testDate}</td>
								<%-- <td>${al.resultStatus}</td> --%>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>


			</table>
		</div>
		<!-- /.card-body -->
	</div>
	<%@ include file="footer.jsp"%>
</body>
<script type="text/javascript">
	// new code for selcted dropdown
	$(document).ready(function() {
		getSub();
	});
	function getSub() {
		var tId = $('#testTakerNameId').val();
		$.ajax({
			url : "./getSubById",
			type : "GET",
			data : {
				testTakerId : tId
			},
			success : function(result) {
				$("#subTestTakerNameId").html(result);
			}
		});
	}

	$(function () {
	    var table = $("#example").DataTable({
	        "responsive": true,
	        "lengthChange": true,
	        "lengthMenu": [ [5,10, 25, 50, -1], [5,10, 25, 50, "All"] ],
	        "autoWidth": false,
	        "buttons": [
	            {
	                extend: 'excelHtml5',
	                title: function() {
	                    var collegeName = $('#testTakerNameId option:selected').text();
	                    var batchName = $('#subTestTakerNameId option:selected').text();
	                    if (collegeName != '--select--' && batchName != '--select--') {
	                        return 'Result for Candidates - ' + collegeName + ' - ' + batchName;
	                    } else if(collegeName != '--select--' && batchName == '--select--'){
	                        return 'Result for Candidates - ' + collegeName;
	                    }else{
							return 'Result for Candidates '
	                        }
	                }
	            }
	        ]
	    });

	    var wrapper = $('#example_wrapper');
	    table.buttons().container().appendTo(wrapper.find('.col-md-6:eq(0)'));
	    wrapper.find('.dataTables_length').appendTo(wrapper.find('.col-md-6:eq(0)'));
	});
</script>

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
