<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<!-- <meta charset="UTF-8"> -->
<meta charset="ISO-8859-1">
<title>Proctoring Dashboard</title>
<%@ include file="proctorNavbar.jsp"%>

<!-- Your scripts and styles here -->

<link rel="stylesheet"
href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>


<!--  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> -->
<script src="/webjars/sockjs-client/1.0.2/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/2.3.3/stomp.js"></script>
<link rel="stylesheet"
href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel='stylesheet'
href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>
<script>
    var $j = jQuery.noConflict();
    $(document).ready(function() {
        var stompClient = Stomp.over(new SockJS('http://localhost:8088/stomp-endpoint'));

        stompClient.connect({}, function(frame) {
            stompClient.subscribe('/topic/combinedData', function(message) {
            var combinedData = JSON.parse(message.body);
                console.log(combinedData); 
                var candidates = combinedData.candidates;
                var configurList = combinedData.configurations;
                var reasons = combinedData.reasons;
                console.log(candidates); 
                console.log(configurList); 
                console.log(reasons);
                updateTable(candidates,reasons,configurList); 
                
            });
        });

        function updateRemainingTime(configList, candidateList) {
            $(".remainingTime").each(function(index, element) {
            var candidateId = candidateList[index].subTestTaker.subTestTakerId;
                  for (var i = 0; i < configList.length; i++) {
                    var config = configList[i];
                    if (config.subTestTaker.subTestTakerId === candidateId) {
                    var endTimeString = config.endTime; 
                    var endTimeComponents = endTimeString.split(':'); 
                    var currentTime = new Date(); 

                    var hours = currentTime.getHours(); 
                    var minutes = currentTime.getMinutes(); 
                    var seconds = currentTime.getSeconds(); 

                    var endTime = new Date(0, 0, 0, endTimeComponents[0], endTimeComponents[1], endTimeComponents[2]);
                    var currentTime = new Date(0, 0, 0, hours, minutes, seconds);
                   
                    var remainingTime = endTime - currentTime;

                        if (remainingTime > 0) {
                            var hours = Math.floor(remainingTime / (1000 * 60 * 60));
                            var minutes = Math.floor((remainingTime % (1000 * 60 * 60)) / (1000 * 60));
                            var seconds = Math.floor((remainingTime % (1000 * 60)) / 1000);

                            $(this).text(hours + "h " + minutes + "m " + seconds + "s");
                        } else {
                            $(this).text("Time's Up");
                        }
                        break; 
                    }
                }
            });
        }
        function updateExtendedTime(row, candidateId, configurList) {
            var extendedTimeCell = row.find(".extendedTime");
            var currentExtendedTime = 0;

           
            var config = configurList.find(config => config.subTestTaker.subTestTakerId === candidateId);
            if (config) {
                
                currentExtendedTime = parseFloat(config.extendedTime) || 0;
            }

            extendedTimeCell.data("extendedtime", currentExtendedTime);
            extendedTimeCell.text("ExtendedTime " + currentExtendedTime + " min");
        }
        function updateTable(candidates,reasons, configurList) {
            var userTableBody = $("#userTable");
            userTableBody.empty(); // Clear existing table content
            if (candidates && candidates.length > 0) {
                candidates.forEach(function(candidate) {
                    var statusText = candidate.status === '1' ? 'Active' : 'Inactive';
                    var pauseIcon = candidate.pause.toLowerCase() === 'yes' ? 'pause' : 'play';
                    var row = $("<tr>");
                    row.append(
                        $("<td>").text(candidate.candid),
                        $("<td>").text(candidate.candFirstname),
                        $("<td>").text(candidate.candLastname),
                        $("<td>").text(candidate.candStartTime),
                        $("<td>").append(
                            $("<a>")
                                .attr("href", "updateStatus?id=" + candidate.candid)
                                .attr("class", "text-danger")
                                //.css("color", "red")
                                .text(() => {
                                    if (candidate.status === "inactive") {
                                        return 'Inactive';
                                    } else {
                                        return 'Active';
                                    }
                                })
                                .append("</a>")
                        ),
                        $("<td>").text("Authorization"),   //`candidate-${candidate.candid}-${candidate.pause}`
                        $("<td>").append(
                            $("<a>").addClass("toggle-pause")
                                .attr("id", "candidate-"+candidate.candid+"-"+candidate.pause)
                                .attr("data-current-state", candidate.pause)
                                .attr("style",'pointer-events: none;')
                                .attr("style",()=>{
                                if(candidate.progress.toLowerCase()=='started' || candidate.progress.toLowerCase()=='blocked')
                                return "pointer-events: all;";
                                else
                                return "pointer-events: none;";
                                })
                                .attr("onclick",'changePStatus(event)')
                                .append(()=> {
                                console.log(pauseIcon);
                                  if (pauseIcon == 'pause') {
                                          return $("<i>").addClass("bi bi-pause-fill bi-4x d-block mx-auto text-black custom-icon-size text-center");
                                      } else {
                                          return $("<i>").addClass("bi bi-play-fill bi-4x d-block mx-auto text-black custom-icon-size text-center");
                                      }
                                }
                                )
                        ),
                        $("<td>").addClass("remainingTime"), 
                        $("<td>").text("ExtendedTime"), 
                       /*  $("<td>").html("<button class=\"addTimeButton\">+15 min</button>"), */
                        /* $("<td>").text("ExtendedTime").addClass("extendedTime"), */
                         $("<td>").text(candidate.candEndTime),
                       $("<td>").text(candidate.progress), 
                        $("<td>").text(candidate.reasonForLogOut),
                    );

                   /*  var candidateStartTime = new Date(candidate.candStartTime);
                    var candidateEndTime = new Date(candidate.candEndTime);
                    var formattedCurrTime = new Date();

                    var progressCell = $("<td>");

                    if (
                        candidateStartTime instanceof Date &&
                        !isNaN(candidateStartTime) &&
                        candidateStartTime < formattedCurrTime &&!isNaN(candidateEndTime)
                    ) {
                        if (candidate.status === '1'&& candidateStartTime<formattedCurrTime) {
                            progressCell.text("Started");
                        } else if(candidate.status === 'inactive'&& candidateEndTime<formattedCurrTime ){
                        progressCell.text("Not started");
                        }else{
                        progressCell.text("Completed");
                        }
                    } else {
                        progressCell.text("Not started");
                    }

                    row.append(progressCell, $("<td>").text("Administration"));
 */
                    userTableBody.append(row);
                    updateExtendedTime(row, candidate.subTestTaker.subTestTakerId, configurList);
                });

                
                if (configurList) {
                    updateRemainingTime(configurList, candidates);
                   
                } 
            }
        }
        
    }); 
   // changePauseStatus()
    function changePStatus(event){
    var reason = null;
        var cId =  event.target.parentNode;
       
        if(cId.nodeName != "A"){
       
        cId=cId.firstChild;
        }
    cId=cId.id;
    /* var numericPart = cId.replaceAll("[^0-9]", "");
    alert(numericPart);
var Id = Integer.parseInt(numericPart); */
    var iconElement = document.getElementById(cId).querySelector("i");
    if(iconElement.className == "bi bi-pause-fill bi-4x d-block mx-auto text-black custom-icon-size text-center"){
    $(iconElement).removeClass("bi bi-pause-fill bi-4x d-block mx-auto text-black custom-icon-size text-center").addClass("bi bi-play-fill bi-4x d-block mx-auto text-black custom-icon-size text-center");
    $.ajax({
    type : "POST",
    url : "./updateCandidate",
    data :{
    cId : cId,
    reason : reason,
    },
    success : function(res) {
    }
    });
    }
    else{
    $(iconElement).removeClass("bi bi-play-fill bi-4x d-block mx-auto text-black custom-icon-size text-center").addClass("bi bi-pause-fill bi-4x d-block mx-auto text-black custom-icon-size text-center");
// reason=window.prompt("Reason of Block"); 
  window.location.href = "http://localhost:8088/exam/reasonPopupForBlock?cId="+cId;
    }
   
    
    }
</script>

<style type="text/css">
a {
text-decoration: none;
color: white;
}

.custom-icon-size {
font-size: 1.5rem; /* Adjust the size as needed */
}
</style>
</head>
<body style="background-color: lightcyan;">
<div class="text-white p-1 w-100 text-left"
style="background-color: black">
<a href="./getListOfExams">Dashboard</a>
</div>

<table class="table table-striped table-bordered">
<thead class="thead-dark">
<tr>
<th>Candidate Id</th>
<th>First Name</th>
<th>Last Name</th>
<th>Started At</th>
<th>Status</th>
<th>Authorize</th>
<th>Pause</th>
<th>Remaining</th>
<th>Extra Time</th>
<th>End Time</th>
<th>Progress</th>
<th>Logout Reason</th>
</tr>
</thead>
<tbody id="userTable">
<c:forEach items="${candidates}" var="candidate">
<tr>
<td>${candidate.candid}</td>
<td>${candidate.candFirstname}</td>
<td>${candidate.candLastname}</td>
<td>${candidate.candStartTime}</td>
<td><a href='updateStatus?id=${candidate.candid}'
style="color: blue;"> <c:choose>
<c:when test="${candidate.status eq '1'}">Active</c:when>
<c:otherwise>Inactive</c:otherwise>
</c:choose>
</a></td>
<td>Authorization</td>
<td><a class="toggle-pause" onclick="changePStatus(event)"
id="candidate-${candidate.candid}-${candidate.pause}"
data-current-state="${candidate.pause}"
<c:choose>
<c:when test="${candidate.progress.toLowerCase() eq 'started'}">style="pointer-events: all;"</c:when>
<c:when test="${candidate.progress.toLowerCase() eq 'logout'}">style="pointer-events: all;"</c:when>
<c:otherwise>style="pointer-events: none;"</c:otherwise>
</c:choose>>
<i
class="bi bi-${candidate.pause eq 'yes' ? 'pause-fill' : 'play-fill'} bi-4x d-block mx-auto text-black custom-icon-size text-center"></i>
</a></td>
<td class="remainingTime"><c:forEach items="${configList}"
var="configure">
<c:if
test="${candidate.subTestTaker.subTestTakerId == configure.subTestTaker.subTestTakerId}">

<script>
            var currentTime = new Date();  
                var endTimeString = "${configure.endTime}";
                var endTime = new Date(currentTime.toDateString() + ' ' + endTimeString);  

                var remainingTime = endTime - currentTime;
                if (remainingTime > 0) {
                    var hours = Math.floor(remainingTime / (1000 * 60 * 60));
                    var minutes = Math.floor((remainingTime % (1000 * 60 * 60)) / (1000 * 60));
                    var seconds = Math.floor((remainingTime % (1000 * 60)) / 1000);
                    document.write(hours + "h " + minutes + "m " + seconds + "s");
                   
                } else {
                   document.write("Time's Up");
                }
            </script>
</c:if>
</c:forEach></td>
<!--  <td><button class="addTimeButton">+15 min</button></td> -->
<!--  <td class="extendedTime">ExtendedTime</td> -->
<td>ExtendedTime</td>
<td>${candidate.candEndTime}</td>
<td>${candidate.progress}</td>
<td>${candidate.reasonForLogOut}</td>
</tr>
</c:forEach>
</tbody>
</table>

<script type="text/javascript">
</script>
<!-- <script>

$(document).ready(function () {
    $(".addTimeButton").click(function () {
        var row = $(this).closest("tr");
        updateRemainingTime(row);
        updateExtendedTime(row);
    });

    function updateRemainingTime(row) {
        var remainingTimeCell = row.find(".remainingTime");
        var endTime = new Date(remainingTimeCell.data("endtime"));
        var newEndTime = addMinutes(endTime, 15);
        var formattedNewEndTime = formatTime(newEndTime);

        remainingTimeCell.data("endtime", newEndTime);
        remainingTimeCell.text(formattedNewEndTime);
    }

    function updateExtendedTime(row) {
        var extendedTimeCell = row.find(".extendedTime");
        var currentExtendedTime = parseFloat(extendedTimeCell.data("extendedtime")) || 0;
        var newExtendedTime = currentExtendedTime + 15;

        extendedTimeCell.data("extendedtime", newExtendedTime);
        extendedTimeCell.text("ExtendedTime " + newExtendedTime + " min");
    }

    function addMinutes(date, minutes) {
        return new Date(date.getTime() + minutes * 60000);
    }

    function formatTime(date) {
        var hours = date.getHours();
        var minutes = date.getMinutes();
        var ampm = hours >= 12 ? 'PM' : 'AM';
        hours = hours % 12;
        hours = hours ? hours : 12;
        minutes = minutes < 10 ? '0' + minutes : minutes;
        var strTime = hours + ':' + minutes + ' ' + ampm;
        return strTime;
    }
});

</script> -->

<%@ include file="footer.jsp" %>
</body>
</html>