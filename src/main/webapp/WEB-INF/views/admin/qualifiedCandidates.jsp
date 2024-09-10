<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Analysis Report For Qualified Candidates</title>
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





<link rel="https://code.jquery.com/jquery-3.7.1.js">

<link rel="https://cdn.datatables.net/2.0.1/js/dataTables.js">


<link rel='stylesheet' href='https://cdn.datatables.net/2.0.1/css/dataTables.dataTables.css'>


</head>
<body>
<div class="card" id="registerCandidate">
				
              
                <h3 class="card-title text-center text-dark">Qualified Candidates List</h3>
                
              <div class="card-header">
              <h4 class="text-center text-danger" >Filter</h4>
					<form class="form-group" method="post"
				action="./searchQualifiedCandidateByCutoff">
				<div class="row">
					<div class="col-md-3 control-label">
						<label class="text-primary font-weight-bold">Select
							College Name </label> <select class="form-control" name="testTakerName"
							id="testTakerNameId" onchange="getSub();getcollegeName()">
							<option value="0">--select--</option>
							<c:forEach items="${testTakerList}" var="pv">
								<option value="${pv.testTakerId }"
									<c:if test ="${pv.testTakerId eq testtaker}">selected = 'selected' </c:if>>${pv.testTakerName}</option>
							</c:forEach>
						</select>

					</div>
					<div class="col-md-3 control-label">
						<label class="text-primary font-weight-bold">Select Batch
						</label> <select class="form-control" name="subTestTakerName"
							id="subTestTakerNameId">
							<option value='0'>--select--</option>
						</select>

					</div>
					<div class="col-md-3 control-label">
						<label class="text-primary font-weight-bold">Enter the
							cutoff</label> <input type="number" value='${cutoffMark}' name="cutoff"
							id="cutoffid" class="form-control">
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
									<th>Candidate Name</th>
									<th>Candidate Email</th>
									<th>Candidate Phone No.</th>
									<th>College Name</th>
									<th>College Batch Name</th>
									<th>Obtained Mark</th>
									<th>Total Mark</th>
									<th>Placement Officer Email</th>
									<th>Placement Officer Phone No.</th>
									<th>Date of Exam</th>
					</tr>
                  
                  </thead>
                  <tbody>
						<c:choose>
							<c:when test="${empty allQualifiedCandidates}">
								<tr>
									<td colspan="9">No data available</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${allQualifiedCandidates}" var="cand" varStatus="count">
									<tr>
										<td>${count.count}</td>
										<td>${cand.candFirstname} ${cand.candLastname}</td>
										<td>${cand.candidateemail}</td>
										<td>${cand.candMobile}</td>
										<td>${cand.subTestTaker.testTaker.testTakerName}</td>
										<td>${cand.subTestTaker.subTestTakerName}</td>
										<td>${cand.markAppear}</td>
										<td>${cand.totalMark}</td>
										<td>${cand.subTestTaker.testTaker.officerEmail}</td>
										<td>${cand.subTestTaker.testTaker.phoneNumber}</td>
										<td>${cand.configure.testDate}</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
                 
                </table>
              </div>
              <!-- /.card-body -->
            </div>
            <%@ include file="footer.jsp" %>
</body>
<script type="text/javascript">
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
</script>
<script src="../../plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="../../plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- DataTables  & Plugins -->
<script src="../../plugins/datatables/jquery.dataTables.min.js"></script>
<script src="../../plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
<script src="../../plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>
<script src="../../plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>
<script src="../../plugins/datatables-buttons/js/dataTables.buttons.min.js"></script>
<script src="../../plugins/datatables-buttons/js/buttons.bootstrap4.min.js"></script>
<script src="../../plugins/jszip/jszip.min.js"></script>
<script src="../../plugins/pdfmake/pdfmake.min.js"></script>
<script src="../../plugins/pdfmake/vfs_fonts.js"></script>
<script src="../../plugins/datatables-buttons/js/buttons.html5.min.js"></script>
<script src="../../plugins/datatables-buttons/js/buttons.print.min.js"></script>
<script src="../../plugins/datatables-buttons/js/buttons.colVis.min.js"></script>

<!-- Page specific script -->
<script>
$(document).ready(function() {
	getSub();
});

$(function() {
	var table = $("#example")
			.DataTable(
					{
						"responsive" : true,
						"lengthChange" : true,
						"lengthMenu" : [ [ 5, 10, 25, 50, -1 ],
								[ 5, 10, 25, 50, "All" ] ],
						"autoWidth" : false,
						"buttons" : [ {
							extend : 'excelHtml5',
							title : function() {
								var collegeName = $(
										'#testTakerNameId option:selected')
										.text();
								var batchName = $(
										'#subTestTakerNameId option:selected')
										.text();
								if (collegeName != '--select--'
										&& batchName != '--select--') {
									return 'Analysis Report of Qualified Candidate- '
											+ collegeName + ' - '
											+ batchName;
								} else if (collegeName != '--select--'
										&& batchName == '--select--') {
									return 'Analysis Report of Qualified Candidate- '
											+ collegeName;
								} else {
									return 'Analysis Report - of Qualified Candidates '
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
</html>