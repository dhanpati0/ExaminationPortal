<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Comparator"%>
<%@page import="com.csmtech.model.Candidate"%>
<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@page import="com.csmtech.model.Answer"%>
<%@page import="java.util.List"%>
<%@page import="com.csmtech.bean.QuestionBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
List<QuestionBean> quests = (List<QuestionBean>) session.getAttribute("quests");
%>
<%
List<Answer> answeredQuestions = (List<Answer>) session.getAttribute("answeredQuestions");
%>



<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/sweetalert2@10.16.6/dist/sweetalert2.min.css">

<!-- Include SweetAlert2 JS -->
<script
	src="https://cdn.jsdelivr.net/npm/sweetalert2@10.16.6/dist/sweetalert2.all.min.js"></script>


<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.min.css">
<link
	rel="https://cdnjs.datatables/1.12.1/css/dataTables.bootstrap4.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script
	src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
<script
	src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap4.min.js"></script>

<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>


<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"
	integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous">
</script>


<script type="text/javascript">
	function preventBack() {
		window.history.forward();
	}
	preventBack();
	window.onload = preventBack;
	window.onpageshow = function(evt) { if(evt.persisted) preventBack() }
	window.onunload = function () { void (0) };
</script>

<style>
.choose {
	animation: colorChange 5s infinite;
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

.timer-minutes {
	font-size: 24px;
	font-weight: bold;
	color: black;
}

.timer-divider {
	font-size: 24px;
	margin: 0 4px;
	color: gray;
}

.timer-seconds {
	font-size: 24px;
	font-weight: normal;
	color: Black;
}

.top {
	height: 4em;
}

.quesbox {
	margin-left: -2px;
	r height: 450px;
	width: 905px;
	overflow: scroll;
}

#logoImg {
	height: 30px;
	width: 150px;
	margin-top: 10px;
	background-color: white;
}

.top {
	height: 4em;
}

.box {
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
	gap: 10px;
	margin-top: 10px;
}

.box button {
	width: 40px;
	height: 40px;
	border: none;
	border-radius: 50%;
	background-color: #71797E;
	color: white;
	font-weight: bold;
	font-size: 16px;
	box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.5);
}

.box button.answered {
	background-color: green;
	color: white;
}

.box button.skipped {
	background-color: red;
	color: white;
}

.box button.marked {
	background-color: orange;
	color: white;
}

.box button:hover {
	background-color: #4c5459;
}

.top {
	height: 4em;
}

.quesbox {
	margin-left: 2px;
	height: 450px;
	width: 900px;
	overflow: scroll;
}

.time {
	height: 4em;
}

.marks {
	height: 450px;
}

.box {
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
	gap: 10px;
	margin-top: 10px;
}

.box button {
	width: 40px;
	height: 40px;
	border: none;
	border-radius: 50%;
	background-color: #71797E;
	color: white;
	font-weight: bold;
	font-size: 16px;
	box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.5);
}

.box button.answered {
	background-color: green;
	color: white;
}

.box button.skipped {
	background-color: red;
	color: white;
}

.box button.marked {
	background-color: orange;
	color: white;
}

.box button:hover {
	background-color: #4c5459;
}
/* csm */
.tx {
	text-transform: uppercase;
	font-family: verdana;
	font-size: 1.5em;
	font-weight: 700;
	color: #f5f5f5;
	text-shadow: 2px 2px 2px rgba(0, 0, 0, 0.2), 3px 3px 3px
		rgba(0, 0, 0, 0.2), 4px 4px 4px rgba(0, 0, 0, 0.2);
}
/* chose a q*/
.tx1 {
	text-transform: uppercase;
	font-family: verdana;
	font-size: 1.5em;
	font-weight: 700;
	color: #000000;
	text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.4);
}

/* CSS */
.button-71 {
	background-color: #00d055;
	border: 0;
	border-radius: 56px;
	color: #fff;
	cursor: pointer;
	display: inline-block;
	font-family: system-ui, -apple-system, system-ui, "Segoe UI", Roboto,
		Ubuntu, "Helvetica Neue", sans-serif;
	font-size: 18px;
	font-weight: 600;
	outline: 0;
	padding: 16px 21px;
	position: relative;
	text-align: center;
	text-decoration: none;
	transition: all .3s;
	user-select: none;
	-webkit-user-select: none;
	touch-action: manipulation;
}

.button-71:before {
	background-color: initial;
	background-image: linear-gradient(#fff 0, rgba(255, 255, 255, 0) 100%);
	border-radius: 125px;
	content: "";
	height: 50%;
	left: 4%;
	opacity: .5;
	position: absolute;
	top: 0;
	transition: all .3s;
	width: 92%;
}

.button-71:hover {
	box-shadow: rgba(255, 255, 255, .2) 0 3px 15px inset, rgba(0, 0, 0, .1)
		0 3px 5px, rgba(0, 0, 0, .1) 0 10px 13px;
	transform: scale(1.05);
}

@media ( min-width : 768px) {
	.button-71 {
		padding: 2px 48px;
	}
}

#tophead {
	background-color: blue;
	color: cornsilk;
	text-align: center;
}

.h1 {
	text-align: center;
}

.question-text {
	font-weight: bold;
	font-size: 20px;
	margin-bottom: 10px;
}

.question-box {
	border: 1px solid #ccc;
	padding: 9px;
	margin-bottom: 20px;
	background-color: #f5f5f5;
}

input[type="checkbox"] {
	margin-right: 10px;
}

input[type="checkbox"]:checked+span {
	font-weight: bold;
}

.imgposition {
	
}

<!--
#popupContainer {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.5);
	display: flex;
	justify-content: center;
	align-items: center;
}

#popupContent {
	background-color: #fff;
	padding: 20px;
	border-radius: 5px;
	text-align: center;
}

#popupContent p {
	font-size: 18px;
	margin-bottom: 10px;
}

#popupContent button {
	font-size: 16px;
	padding: 10px 20px;
	background-color: #4CAF50;
	color: #fff;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}
</style>

<script>

</script>
<script type="text/javascript">
    
        var inactivityTimer; // Initialize a variable to store the timer
        
        // Function to redirect to the login page
        function redirectToLogin() {
            //window.location.href = 'pageLogin.jsp';
            
            window.location.href = "http://localhost:8088/exam/logout";
        }

        function redirectToBlockLogin() {
            //window.location.href = 'pageLogin.jsp';
            
            window.location.href = "http://localhost:8088/exam/blockLogout";
        }
        
        
     	// Start the inactivity timer (3 minutes)
        function startInactivityTimer() {
            inactivityTimer = setTimeout(function() {
            	captureAndSubmitTime();captureAndSubmitTime()
                //redirectToLogin(); 
            }, 600000); // 3 minutes = 180,000 milliseconds
        }
        //goToQuestion();
        // Reset the inactivity timer when the user interacts with the page
         function resetInactivityTimer() {
            clearTimeout(inactivityTimer);
            startInactivityTimer(); // Start the timer again
        }

         function showSweetAlert(blockedReason) {
        	    Swal.fire({
        	        title: 'Blocked',
        	        text: 'Blocked Reason: '+blockedReason,
        	        icon: 'error',
        	        confirmButtonText: 'Ok!',
        	    }).then((result) => {
        	        if (result.isConfirmed) {
        	            redirectToBlockLogin();
        	        }
        	    });
        	}
         var isCheckScheduled = false;

         function checkStatusOfCandidate() {
             // If a check is already scheduled, do nothing
             if (isCheckScheduled) {
                 return;
             }

             // Set the flag to indicate that a check is scheduled
             isCheckScheduled = true;

             // Delay the execution of the AJAX request by 1 second
             setTimeout(function() {
                 $.ajax({
                     url: "/exam/liveCandidateDetail",
                     method: "GET",
                     success: function(res) {
                         console.log(res);
                         console.log(res[0] + " - - - " + res[1]);
                         if (res[1] == 'Blocked') {
                             // Call SweetAlert after the AJAX call is completed
                             showSweetAlert(res[0]);
                         }

                         // Reset the flag after the check is complete
                         isCheckScheduled = false;

                         // Schedule the next check
                         checkStatusOfCandidate();
                     },
                     error: function(xhr, status, error) {
                         // Handle errors (if any)
                         console.error('Error:', error);

                         // Reset the flag in case of an error
                         isCheckScheduled = false;

                         // Schedule the next check
                         checkStatusOfCandidate();
                     }
                 });
             }, 1000);

             // Retrieve session attribute if needed
             // ...
         }
         document.addEventListener('mousemove', resetInactivityTimer);
         document.addEventListener('keypress', resetInactivityTimer);

         checkStatusOfCandidate();

        /*  document.addEventListener('DOMContentLoaded', function() {
        	    // Start the timer when the page loads
        	    startInactivityTimer();

        	    // Attach event listeners for inactivity check every 1 second
        	    setInterval(function() {
        	        checkStatusOfCandidate();
        	    }, 1000);

        	    // Attach event listeners for inactivity check after the DOM has loaded
        	    document.addEventListener('mousemove', resetInactivityTimer);
        	    document.addEventListener('keypress', resetInactivityTimer);
        	}); */

        	    // Attach event listeners for inactivity check after the DOM has loaded
        	    
        
        // Listen for user interactions (e.g., mousemove, keypress) to reset the timer
       /*  document.addEventListener('mousemove', resetInactivityTimer);
        document.addEventListener('keypress', resetInactivityTimer);
        document.addEventListener('mousemove', checkStatusOfCandidate); 
        document.addEventListener('keypress', checkStatusOfCandidate);  */
        // Start the timer when the page loads
    
    </script>

<script type="text/javascript">
	    function captureSubmitTime() {
	        var currentTime = new Date();
	        var hours = currentTime.getHours();
	        var minutes = currentTime.getMinutes();
	        var seconds = currentTime.getSeconds();
	
	        // Create a properly formatted time string (HH:mm:ss)
	        var formattedTime = ('0' + hours).slice(-2) + ':' + ('0' + minutes).slice(-2) + ':' + ('0' + seconds).slice(-2);
			 console.log("Formatted Time ::"+formattedTime);
	        return formattedTime;
	    }
	
	    function captureAndSubmitTime() {
	        // Show SweetAlert notification
	        Swal.fire({
	            title: 'Logout!',
	            text: 'You are Not Active! So You Are LogOut.',
	            icon: 'info',
	            confirmButtonText: 'OK'
	        }).then((result) => {
	            if (result.isConfirmed) {
	                // Continue with the rest of the function logic after the user clicks 'OK'
	                console.log("captureAndSubmitTime() :: Function called");
	                var submitTime = captureSubmitTime();

	                var id = document.getElementById('cid').value;

	                // Create an object to send to the server
	                var data = {
	                    candid: id,
	                    candEndTime: submitTime
	                };

	                // Send the data to the server
	                $.ajax({
	                    url: "/exam/updateCandEndTime",
	                    method: "POST",
	                    contentType: "application/json",
	                    data: JSON.stringify(data),
	                    success: function(response) {
	                        console.log('Time submitted successfully: ' + submitTime);
	                        
	                        // Redirect to login after successful submission
	                        redirectToLogin();
	                    },
	                    error: function(xhr, status, error) {
	                        // Handle errors (if any)
	                        console.error('Error:', error);
	                    }
	                });
	            }
	        });
	    }

	</script>

<script type="text/javascript">
		function performActions() {
			//alert("we are perform actions");
			captureAndSubmitTime();
			submitQuiz();
			//alert("all doone")
	    }
	</script>

</head>

<body>


	<input type="hidden" id="cid" value=<%=session.getAttribute("candId")%>>
	<script
		src="https://cdn.jsdelivr.net/npm/sweetalert2@10.16.0/dist/sweetalert2.all.min.js"></script>

	<div class="row" id="tophead"
		style="justify-content: center; align-items: center; display: flex;">
		<!-- <h5>CSM ONLINE WRITTEN TEST</h5> -->
		<h5 class="tx">CSM ONLINE WRITTEN TEST</h5>
	</div>


	<div class="row">
		<div class="col-8">
			<div class="time border m-1 margin-left:1.05rem!important; ">
				<fieldset>
					<fieldset>
						<legend>
							<div class="form-group">
								<div class="row" style="background-color: #003365;"
									id="background">
									<div class="imgposition col-md-2"
										style="margin-left: 1px; margin-bottom: 1em;">
										<img alt="" src="../img/logocsm.png" id="logoImg">
									</div>
									<div class="col">

										<label
											style="font-size: 0.7em; color: white; float: right; margin-top: 1em; margin-right: 3em">
											<b>Name : ${sessionData1.candidateemail}</b>
										</label>
									</div>
								</div>
							</div>
						</legend>
					</fieldset>

				</fieldset>
			</div>
			<div class="mt-1 border quesbox" id="questionContainer"></div>
			<button class="button-71" style="background-color: #71797E;"
				id="clearResponseButton">Clear Response</button>
			<!-- <button class="button-71" style="background-color: #ffc822;"
onclick="markForReview()">Mark For Review & Next</button> -->
			<button class="button-71" style="background-color: #dc3545;"
				onclick="previousQuestion()">Back</button>
			<button class="button-71" style="background-color: #007bff;"
				onclick="nextQuestion()">Save&Next</button>

		</div>

		<div class="col-4 border">
			<h1 class="text-right" id="remainTime">
				Time Left:- <span id="timeid"></span>
			</h1>

			<c:if test="${endExam-currentTime gt 0 }">
				<input type="hidden" value="${endExam }" id="endExam">
				<input type="hidden" value="${currentTime }" id="currentTime">
				<script type="text/javascript">
 
let endExam = $("#endExam").val()/1000;
let currentTime = $("#currentTime").val()/1000;
 
let diff = endExam-currentTime;
console.log(diff);
let mins = diff;
setInterval(() => {
diff--;
 
if(diff > mins/2 ){
$("#timeid").css("color", "green");
}
/* if(diff < mins/2 && diff >= mins/4){
$("#timeid").css("color", "orange");
} */else
if(diff < mins/4 && diff >= 2){
$("#timeid").css("color", "red");
}else
if(diff < 1){
window.location.href = "http://localhost:8088/exam/logout";
}
let sec = Math.floor(diff%60);
let min = Math.floor(diff/60)%60;
let hour = Math.floor(diff/(60*60))%24;
$("#timeid").text((hour < 10 ? "0"+hour : hour)+":"+(min < 10 ? "0"+min : min)+":"+(sec < 10 ? "0"+sec : sec));
}, 1000);
</script>
			</c:if>

			<c:if test="${currentTime-endExam gt 0}">
				<c:redirect url="http://localhost:8088/exam/logout" />
			</c:if>

			<!-- <div class="time border m-1 mr-3"
style="justify-content: center; align-items: center; display: flex;">
<label id="timer" class="ml-4"
style="font-size: 20px; text-shadow: 2px 2px 8px rgb(56, 50, 50);"></label>
</div> -->
			<div class="marks border mr-2">
				<div class="row">
					<label class="ml-4 d-flex align-items-center"> <img alt=""
						src="../img/greenSQ.png" id="greensquare"
						style="width: 20px; height: 20px;" class="mr-1"> <strong>Answered</strong>
					</label> <label class="ml-2 d-flex align-items-center"> <img alt=""
						src="../img/redsquare.png" id="redsquare"
						style="width: 20px; height: 20px;" class="mr-1"> <strong>Not
							Answered</strong>
					</label>
					
					<label class="ml-4 d-flex align-items-center"><img alt=""
						src="../img/graysquare.png" id="graysquare"
						style="width: 20px; height: 20px;" class="mr-1"> <strong>
							Not Visited</strong></label>
				</div>

				<div class="choose"
					style="background-color: cyan; text-align: center;">
					<strong class="tx1">Choose a question</strong>
				</div>

				<div class="box">

					<%
					for (int i = 1; i <= quests.size(); i++) {
					%>
					<button id='question_<%=i%>' onclick="goToQuestion()"><%=i%></button>
					<%
					}
					%>


				</div>

			</div>
			<button class="button-71" role="button" onclick="performActions()">Submit</button>

		</div>
	</div>

	

</body>


<script type="text/javascript">
var answeredQuestions ;
 $(document).ready(function(){
	 
	  answeredQuestions = [
		 <%for (int i = 0; i < answeredQuestions.size(); i++) {%>
		 {
			 ansId: <%=answeredQuestions.get(i).getAnsId()%>,
			 candidate: <%=answeredQuestions.get(i).getCandidate()%>,
			 mark: <%=answeredQuestions.get(i).getMark()%>,
			 optChoose:'<%=answeredQuestions.get(i).getOptChoose().trim()%>',
			 questionId: <%=answeredQuestions.get(i).getQuestion()%>,
			 getStatus: '<%=answeredQuestions.get(i).getStatus()%>',
		 }<%if (i < quests.size() - 1) {%>,<%}%>
		 <%}%>
		 ];
	  var noQuestion = <%=session.getAttribute("noQuestion")%>;
	  if(noQuestion != 0){
		  for(i=1;i<=noQuestion;i++){
				 nextQuestion();
			 }
		  for(i=1;i<=noQuestion;i++){
			  previousQuestion();
			 }
	  }
 });
 
 function updateTheContainer(){
	 answeredQuestions.forEach(function(question) {
		  const questionContainer =  document.getElementById("questionContainer");
		  const strongEli = questionContainer.querySelector('strong');
		  //console.log("strongEli -> "+strongEli.id);
		  var qID = strongEli.id;
		  qID = parseInt(qID.replace('question_code_', ''));
		  //console.log("Question Id -> "+qID);
		 // const questionBubble = document.getElementById("question_"+qID);
		   const labelElements = questionContainer.querySelectorAll("label");
			
		// Initialize an array to store the labels' text content

		// Extract the text content of each label
		labelElements.forEach(label => {
			console.log("label.textContent->"+label.textContent);
			if(label.textContent != null && question.optChoose != null){
				if(qID == question.questionId ){
					 if(label.textContent.trim() === question.optChoose.trim()){
					    	//console.log("Option is equivalent");
					    	 var inputEle ;
					     inputEle = label.querySelector('label input[type="radio"]');
					    
					    if(inputEle == null){
					    	inputEle = label.querySelector('label input[type="checkbox"]');
					    }
					    
					    inputEle.checked = true;
					    }
					    else{
					    	console.log("Not Answered");
					    }
				}
		}
		});
		});
 }
function updateBookmarkBox() {
var bookmarks = document.querySelectorAll(".bookmark");
for (var i = 0; i < bookmarks.length; i++) {
var bookmark = bookmarks[i];
var questionIndex = i + 1;
var question = quests[i]; // Get the question object from the list of questions
 
if (question.answered) {
bookmark.style.backgroundImage = "url('../img/greenSQ.png')";
} else if (question.visited) {
bookmark.style.backgroundImage = "url('../img/orangesquare.png')";
} else if (question.markedForReview) {
bookmark.style.backgroundImage = "url('../img/graysquare.png')";
} else {
bookmark.style.backgroundImage = "url('../img/redsquare.png')";
}
}
}
 
// Call the updateBookmarkBox function initially to set the initial state
updateBookmarkBox();

<%-- var Qids = [
	<%for (int j = 0; j < listOfIDS.size(); j++) {%>
    {
    	<%=listOfIDS[j]%>
	}
}%
	
]; --%>
var IDlist = [];

      if(JSON.stringify(${listOfIDS}) !== 'undefined')
{
	
	IDlist=  JSON.stringify(${listOfIDS});
}    
var questions = [
<%for (int i = 0; i < quests.size(); i++) {%>
{
questionId: <%=quests.get(i).getQuestionId()%>,
questionText: "<%=quests.get(i).getQuestionText()%>",
option1: "<%=quests.get(i).getOption1()%>",
option2: "<%=quests.get(i).getOption2()%>",
option3: "<%=quests.get(i).getOption3()%>",
option4: "<%=quests.get(i).getOption4()%>",
correctAns: "<%=quests.get(i).getCorrectAns()%>"
}<%if (i < quests.size() - 1) {%>,<%}%>
<%}%>
];
 
var currentQuestion = 0;
var selectedAnswers = [];
var selectedAnswersWithQuestion = [];
var currentQuestionWithId;
var answeredQuestions = []; // Array to keep track of answered questions
var markedForReview = [];



function displayQuestion() {
	var questionContainer = document.getElementById("questionContainer");
	questionContainer.innerHTML = "";
	 
	var question = questions[currentQuestion];
	currentQuestionWithId = question.questionId;
	 
	var questionText = document.createElement("p");
	questionText.innerHTML = "<strong id='question_code_"+ question.questionId+"'><span class='question-number'>Question " + (currentQuestion + 1) + ":</span> " + question.questionText;
	questionContainer.appendChild(questionText);
	 
	var options = [question.option1, question.option2, question.option3, question.option4];

	for (var i = 0; i < options.length; i++) {
		
	var optionLabel = document.createElement("label");
	var optionCheckbox = document.createElement("input");

	 if(IDlist !== undefined){
		 for(var k=0;k<IDlist.length;k++){
				if(IDlist.includes(currentQuestionWithId)){
					optionCheckbox.type = "checkbox";
					}
				else
					optionCheckbox.type = "radio";
			 }
	}
	else{
		optionCheckbox.type = "radio";	
	}
	optionCheckbox.name = "answer";
	optionCheckbox.value = options[i];
	//alert("Selected Values ->"+selectedValues[currentQuestion]);
	if (selectedValues[currentQuestion] && selectedValues[currentQuestion].includes(options[i])) {
	optionCheckbox.checked = true;
	}
	 
	optionLabel.appendChild(optionCheckbox);
	optionLabel.appendChild(document.createTextNode(options[i]));
	 
	questionContainer.appendChild(optionLabel);
	questionContainer.appendChild(document.createElement("br"));
	}
	 
	// Check if the current question is marked
	var questionButtons = document.getElementsByClassName('box')[0].getElementsByTagName('button');
	if (questionButtons[currentQuestion].classList.contains('marked')) {
	questionButtons[currentQuestion].classList.remove('marked');
	questionButtons[currentQuestion].classList.add('answered');
	}
	}



 
//nextQuestion
 
var selectedValues = []; // Array to store selected values for each question
var selectedQuestionIds = []; // Array to store corresponding questionIds for selected values
function nextQuestion() {
	updateTheContainer();
var selectedOptions = document.querySelectorAll('input[name="answer"]:checked');
var currentQuestionId = questions[currentQuestion].questionId; // Get the current question's ID
var tempSelectedAnswers = [];
//alert(typeof tempSelectedAnswers);
selectedValues[currentQuestion] = [];
selectedOptions.forEach(function(option) {
selectedValues[currentQuestion].push(option.value);
selectedQuestionIds[currentQuestion] = currentQuestionId;
//tempSelectedAnswers.push({option: option.value });
});
 tempSelectedAnswers.push({ questionId: currentQuestionId, option: selectedValues[currentQuestion] });
 //alert(tempSelectedAnswers+"tempSelectedAnswerstempSelectedAnswerstempSelectedAnswerstempSelectedAnswers");
// console.log(JSON.stringify(tempSelectedAnswers)+"tempSelectedAnswerstempSelectedAnswerstempSelectedAnswersJSSSSSSSSSSSSSOnnn");
  var tempvalues = JSON.stringify(tempSelectedAnswers);
 $.ajax({
	 url: "saveExam", // to save temp. options  
	 method: "POST",
	 contentType: "application/json",
	 data: tempvalues,
	 success: function(response) {
	 },
	 });	
if (selectedValues[currentQuestion].length > 0) {
// Update the question number button color to green
var questionButtons = document.getElementsByClassName("box")[0].getElementsByTagName("button");
questionButtons[currentQuestion].classList.remove("skipped"); // Remove 'skipped' class
questionButtons[currentQuestion].classList.add("answered"); // Add 'answered' class
} else {
// Add a class to mark the skipped question as orange
var questionButtons = document.getElementsByClassName("box")[0].getElementsByTagName("button");
questionButtons[currentQuestion].classList.add("skipped");
 
// Add the current question to answeredQuestions array if not already present
if (!answeredQuestions.includes(currentQuestion)) {
answeredQuestions.push(currentQuestion);
}
}
 
// Move to the next question
if (currentQuestion < questions.length - 1) {
currentQuestion++;
displayQuestion();
} else {
//alert("You have reached the end of the questions. Please submit.");
Swal.fire({
    title: 'Submit',
    text: '"You have reached the end of the questions. Please submit."',
    icon: 'info',
    confirmButtonText: 'OK'
})
}
}
 
function previousQuestion() {
if (currentQuestion > 0) {
currentQuestion--;
displayQuestion();
setSelectedAnswer();
}
}
//Event listener for "Clear Response" button
document.getElementById("clearResponseButton").addEventListener("click", clearResponse);
 
//Function to clear the selected option
function clearResponse() {
var options = document.querySelectorAll('input[name="answer"]');
for (var i = 0; i < options.length; i++) {
options[i].checked = false;
}
selectedAnswers[currentQuestion] = null;
selectedAnswersWithQuestion = selectedAnswersWithQuestion.filter(answer => answer.questionId !== currentQuestionWithId);
}
 
function markForReview() {
var questionButtons = document.getElementsByClassName('box')[0].getElementsByTagName('button');
var currentButton = questionButtons[currentQuestion];
 
if (currentButton.classList.contains('marked')) {
//alert(currentButton)
// If the current question is already marked, remove it from the markedForReview array and change the button color to default.
currentButton.classList.remove('marked');
markedForReview = markedForReview.filter(function (questionIndex) {
return questionIndex !== currentQuestion;
});
} else {
// If the current question is not marked, add it to the markedForReview array and change the button color to blue.
currentButton.classList.add('marked');
markedForReview.push(currentQuestion);
}
}
 
function getSelectedAnswer() {
var selectedOption = document.querySelector('input[name="answer"]:checked');
 
return selectedOption ? selectedOption.value : "";
}
 
function setSelectedAnswer() {
var selectedAnswer = getSelectedAnswer();
var options = document.querySelectorAll('input[name="answer"]');
 
for (var i = 0; i < options.length; i++) {
if (options[i].value === selectedAnswer) {
options[i].checked = true;
break;
}
}
}
function submitQuiz() {
	//alert("here submit quiz");
var jData = "";
recall();
 
Swal.fire({
title: 'Submit Quiz',
text: 'Are you sure you want to submit your answers?',
icon: 'question',
showCancelButton: true,
confirmButtonText: 'Yes',
cancelButtonText: 'Cancel',
customClass: {
content: 'custom-sweetalert-content'
}
}).then((result) => {
	//alert("we are here to set question");
if (result.isConfirmed) {
// Prepare an array to hold all selected answers with their respective question IDs
var allSelectedAnswers = [];
for (var i = 0; i < selectedValues.length; i++) {
var questionId = questions[i].questionId; // Use the question ID from questions array
var answer = selectedValues[i] || ""; // Handle null or undefined answers
allSelectedAnswers.push({ questionId: questionId, option: answer });
}
console.log(allSelectedAnswers+"  ==== Here is the selected question");
jData = JSON.stringify(allSelectedAnswers);
console.log("jDatajDatajDatajDatajData"+jData);
// Perform an AJAX request to save all selected answers in the database
$.ajax({
	 url: "./calculateResult", // to save temp. options  
	 method: "POST",
	 //contentType: "application/json",
	 //data: tempvalues,
	 success: function(response) {
	 },
	 });
//alert("we are here to set question is cnfrm");
Swal.fire({
	title: 'Success',
	text: 'Answers submitted successfully!',
	icon: 'success',
	confirmButtonText: 'OK'
	}).then((result) => {
	    // Redirect to the logout endpoint after clicking OK
	    if (result.isConfirmed) {
	      window.location.href = 'http://localhost:8088/exam/logout1'; 
	    }
	  });
}
});
}
 
 
 
// Start the quiz
displayQuestion();
 
</script>
<script type="text/javascript">
window.addEventListener('DOMContentLoaded', (event) => {
adjustBoxSize();
});
 
function adjustBoxSize() {
var buttons = document.getElementsByClassName('box')[0].getElementsByTagName('button');
var numRows = Math.ceil(buttons.length / 5); // Adjust the number of columns as per your requirement
var boxHeight = (numRows * 50) + 20; // Height of each button is 40px + 2 * margin (5px)
var boxWidth = 250; // Width of the box
 
var box = document.getElementsByClassName('box')[0];
box.style.height = boxHeight + 'px';
box.style.width = boxWidth + 'px';
 
// Add click event listeners to the question number buttons
for (var i = 0; i < buttons.length; i++) {
buttons[i].addEventListener('click', function() {
var questionIndex = parseInt(this.textContent) - 1;
goToQuestion(questionIndex);
});
}
}
 
function goToQuestion(questionIndex) {
if (questionIndex >= 0 && questionIndex < questions.length) {
currentQuestion = questionIndex;
//alert("go to question");
displayQuestion();
}
}
 
 
 
 
 
function recall() {
//alert("inside recall");
var currentQuestionIndex = currentQuestion;
var remainingQuestions = questions.length - currentQuestionIndex - 1;
 
var questionButtons = document.getElementsByClassName("box")[0].getElementsByTagName("button");
var skippedQuestionsCount = 0;
 
for (var i = 0; i < questionButtons.length; i++) {
if (questionButtons[i].classList.contains("skipped")) {
skippedQuestionsCount++;
}
}
 
var text = "<div class='quiz-info'>" +
"<p><strong>You are on question number:</strong>"+ (currentQuestion + 1) +"</p>"+
"<p><strong>Total Questions:</strong>"+ questions.length +"</p>"+
"<p><strong>Answered Questions:</strong> " + selectedAnswersWithQuestion.length +"</p>"+
"<p><strong>Skipped Questions:</strong> " + skippedQuestionsCount +"</p>"+
"<p><strong>Remaining Questions:</strong> " + remainingQuestions;
Swal.fire({
title: 'Quiz Information',
html: text,
icon: 'Save',
showCancelButton: true,
confirmButtonText: 'OK',
cancelButtonText: 'Save',
customClass: {
content: 'custom-sweetalert-content' // Add your custom class for styling
}
}
 ) .then((result) => {
if (result.isConfirmed) {
// User clicked "OK", navigate to the login endpoint
window.location.href = 'login'; // Replace with your actual login endpoint
}
});
}
function startTimer(duration, display) {
	var timer = duration, minutes, seconds;
	setInterval(function () {
	minutes = parseInt(timer / 60, 10);
	seconds = parseInt(timer % 60, 10);

	minutes = minutes < 10 ? "0" + minutes : minutes;
	seconds = seconds < 10 ? "0" + seconds : seconds;

	display.textContent = minutes + ":" + seconds;

	if (--timer < 0) {
	timer = duration;
	}
	}, 1000);
	}
	


window.onload = function () {
	var duration = 60 * 5, // 5 minutes
	display = document.querySelector('#time');
	startTimer(duration, display);
	};
	
</script>
</html>