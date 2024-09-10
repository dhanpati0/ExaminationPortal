<%@page import="java.util.List"%>
<%@page import="com.csmtech.bean.QuestionBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Online Exam Rules and Guidelines</title>
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


<link rel="stylesheet"
href="https://cdn.jsdelivr.net/npm/sweetalert2@10.16.6/dist/sweetalert2.min.css">

<!-- Include SweetAlert2 JS -->
<script
src="https://cdn.jsdelivr.net/npm/sweetalert2@10.16.6/dist/sweetalert2.all.min.js"></script>
<style>
body {
padding: 50px;
background-image:
url("https://www.pngplay.com/wp-content/uploads/6/Exam-Silhouette-Background-PNG-Image.png");
background-repeat: no-repeat;
background-size: 35% 100%;
background-position: top left;
background-size: 10em;
opacity: 0.8; /* Adjust the opacity value as needed */
}

#redsquare {
height: 100%;
width: 10%;
}

#greensquare {
height: 90%;
width: 7%;
}

#orangeBox {
height: 100%;
width: 12%;
}

#graysquare {
height: 90%;
width: 7%;
}


.container {
    /* You can adjust the size and color of the border */
    padding: 20px; /* Optional: Add padding to give some space between the content and the border */
    margin: 0 auto;
}

.btn-start {
margin-top: 20px;
}

.blinking {
animation: blink-animation 1s infinite;
color: blue;
}

.h1 {
background-color: transparent;
width: 20px;
height: 20px;
text-align: center;
}

.li {
text-align: right;
list-style-position: inside;
}
</style>

<!-- <script type="text/javascript">

function logCandidateStartTime() {
    var id = document.getElementById('cid').value;
    var currentTime = new Date();
    var hours = ('0' + currentTime.getHours()).slice(-2);
    var minutes = ('0' + currentTime.getMinutes()).slice(-2);
    var seconds = ('0' + currentTime.getSeconds()).slice(-2);

    var formattedTime = hours + ':' + minutes + ':' + seconds;
    alert("cid " + id);
    var myDto = {
        candid: parseInt(id),
        candStarTime: formattedTime,
        candEndTime: formattedTime
    };

    $.ajax({
        type: "POST",
        url: "/exam/updateCandStartTime",
        contentType: "application/json",
        data: JSON.stringify(myDto),
        success: function (response) {
            console.log("Server response: " + response);
        },
        error: function () {
            console.error("Error logging candidate start time.");
        }
    });
}
</script> -->
</head>
<body>
<input type="hidden" id="cid" value=<%=session.getAttribute("candId")%>>
<div id="alertId">

<c:if test="${TimeOut eq 'TimeFailed'}">
<script type="text/javascript">
swal({
title : "Login failed !",
text : "Invalid credentials !",
icon : "error",
button : "try again!",
});
</script>
</c:if>
</div>

<c:if test="${startExam-currentTime gt 0 }">
<h1 class="text-right" id="remainTime">
Exam will start in: <span id="timeid"></span>
</h1>
<input type="hidden" value="${startExam}" id="startExam">
<input type="hidden" value="${currentTime}" id="currentTime">

<script type="text/javascript">
            let startExam = parseInt($("#startExam").val());
            let currentTime = parseInt($("#currentTime").val());

            let diff = Math.floor((startExam - currentTime) / 1000);

            let countdown = setInterval(() => {
                if (diff <= 0) {
                    clearInterval(countdown);
                    $("button.btn-start").removeAttr('disabled');
                    $("#remainTime").hide();
                } else {
                    let hours = Math.floor(diff / 3600);
                    let minutes = Math.floor((diff % 3600) / 60);
                    let seconds = diff % 60;

                    $("#timeid").text(
                        (hours < 10 ? "0" + hours : hours) + ":" +
                        (minutes < 10 ? "0" + minutes : minutes) + ":" +
                        (seconds < 10 ? "0" + seconds : seconds)
                    );

                    if (diff > (startExam - currentTime) / 2) {
                        $("#timeid").css("color", "green");
                    } else if (diff <= (startExam - currentTime) / 2 && diff > (startExam - currentTime) / 4) {
                        $("#timeid").css("color", "orange");
                    } else {
                        $("#timeid").css("color", "red");
                    }
                    diff--;
                }
            }, 1000);
            
        </script>
</c:if>
<div class="container">
<h1 class="blinking">Online Exam Rules and Guidelines</h1>
<div class="container"  style="border: 2px solid #000;" >
<form action="./candidateQuestion" method="post">
<h5>Please read the following instructions carefully before
starting the exam:</h5>
<ul>
<li><strong>Time Allotted: </strong>You have a total of 60
minutes to complete the exam successfully.</li>
<li><strong>Question Format: </strong>The exam comprises
multiple-choice questions, each with only one or multiple correct
answers.</li>
<li><strong>Internet Stability: </strong>Ensure a stable
Internet connection throughout the duration of the exam to prevent
any disruptions.</li>
<li><strong>Avoid Page Actions: </strong>Do not refresh or
close the exam page while the exam is in progress, as doing so may
lead to disqualification.</li>
<li><strong>Prohibited Resources: </strong>Refrain from using
external aids such as books, calculators, or websites while
undertaking the exam.</li>
<li><strong>Stay Active: </strong>Remember to remain active
during the examination. If inactive for 3 minutes, you will be
automatically logged out.</li>
<li><strong>Begin the Exam: </strong>When the designated exam
time arrives, please proceed by clicking the 'Start Exam' button to
commence your examination.</li>
<li><strong>Save Answers: </strong>To save the answers and
proceed to next question click on the 'Save & Next' button.</li>
<li><strong>Back: </strong>To go back and edit the answers you
can click on the 'Back' button.</li>
<li><strong>Clear Response: </strong>If you want to clear the
answers which you have answered then click on 'Clear Response'
button.</li>
<li><strong>Question Navigation: </strong> Utilize the navigation buttons with the questions number provided within the exam interface to move between questions. However, refrain from refreshing or closing the exam page, as doing so may result in disqualification.</li>
<li>
<label class="ml-2 d-flex align-items-center"> <img alt=""
src="../img/greenSQ.png" id="greensquare"
style="width: 20px; height: 20px;" class="mr-1"> <strong>indicates
Answered Questions</strong>
</label>
</li>
<li><label class="ml-2 d-flex align-items-center"> <img
alt="" src="../img/redsquare.png" id="redsquare"
style="width: 20px; height: 20px;" class="mr-1"> <strong>indicates
Not Answered Questions </strong>
</label></li>
<li><label class="ml-2 d-flex align-items-center"><img
alt="" src="../img/graysquare.png" id="graysquare"
style="width: 20px; height: 20px;" class="mr-1"> <strong>
indicates Not Visited Questions</strong></label></li>
</ul>
<h6>We trust that you will approach this examination with
honesty, integrity, and diligence. Best of luck!</h6>
<h6>Warm regards,</h6>
<h6>CSM Technologies</h6>
<c:if test="${startExam-currentTime gt 0 }">
<button class="btn btn-primary btn-start" disabled="disabled">Start
Exam</button>
</c:if>
<c:if
test="${startExam-currentTime lt 0 and endExam-currentExam gt 0}">
<button class="btn btn-primary btn-start">Continue Exam</button>
</c:if>
<c:if test="${currentTime-endExam gt 0}">
<c:redirect url="http://localhost:8088/exam/logout" />
</c:if>
</form></div>
</div>

<!-- <script type="text/javascript">
        document.addEventListener('DOMContentLoaded', function() {
            let startExamButton = document.querySelector('.btn-start[disabled]');
            if (startExamButton) {
                startExamButton.addEventListener('click', logCandidateStartTime);
            }

            let continueExamButton = document.querySelector('.btn-start:not([disabled])');
            if (continueExamButton) {
                continueExamButton.addEventListener('click', logCandidateStartTime);
            }
        });
    </script> -->
<script type="text/javascript">
    function continueExam(){

    Swal.fire({
          title: 'Continue Exam',
          text: 'Are You want to Continue Your Exam !!',
          confirmButtonText: 'Ok!',
      }).then((result) => {
            // Check if the user clicked "Yes"
            if (result.isConfirmed) {
                // Call the function to perform the next action
              window.location.href = "http://localhost:8088/exam/candidateQuestion";
            }
        });
        
        }

    function startExam(){

    Swal.fire({
        title: 'Start Exam',
        text: 'Are You want to Start Your Exam !!',
        confirmButtonText: 'Ok!',
    })
       
       }
    </script>
</body>



</html>
