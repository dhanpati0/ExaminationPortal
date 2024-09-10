<%@page import="com.google.gson.Gson"%>
<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@page import="com.csmtech.service.CorrectAnswerService"%>
<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@page import="com.csmtech.model.Question"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="com.csmtech.model.Answer"%>
<%@page import="com.csmtech.model.CorrectAnswer"%>
<%@page import="com.csmtech.bean.QuestionBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
List<QuestionBean> quests = (List<QuestionBean>) session.getAttribute("questCand");
System.out.println("////////////"+quests);
List<CorrectAnswer> correctAns = (List<CorrectAnswer>) session.getAttribute("allAnswer");

List<Answer> answerBycId = (List<Answer>) session.getAttribute("answerGivenBycandidate");
System.out.println("////////////"+answerBycId);

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>AnswerSheet</title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">

<link rel='stylesheet'
	href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>

<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css">
<link rel='stylesheet'
	href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>
<script src='https://kit.fontawesome.com/a076d05399.js'
	crossorigin='anonymous'></script>

<link rel='stylesheet'
	href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>

<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.min.js"></script>
<%@ include file="proctorNavbar.jsp" %>
</head>
<style>
.correct-answer {
    color: green;
}

.chosen-option {
    background-color: #cce5ff; /* Light blue background */
    border: 1px solid #007bff; /* Blue border */
    padding: 3px 5px; /* Add some padding for better visibility */
    border-radius: 3px; /* Add rounded corners */
}
</style>
<body>

<div class="container" style="background: lightblue">
	
<h2 class="text-center text-danger">Candidate Answer Sheet</h2>
<div>
<div align="left">
<a class="btn btn-primary" href="./backToAnswersheet">Back</a>&nbsp;&nbsp;

	<a id="generatePDF" class="btn btn-success">Download AnswerSheet</a>
</div>
</div>

 <div id="answersheetPage" class="col-md-8 offset-md-2 mt-2" style="border:2px solid black;background: white">
 	<div align="left">
 		<h3 class="text-center text-danger">Candidate Details</h3>
         
            <strong>Candidate Name:</strong> ${candDetails.candFirstname} ${candDetails.candLastname}
        <br>
            <strong>Candidate Email:</strong> ${candDetails.candidateemail}
       <br>
            <strong>Candidate Mobile:</strong> ${candDetails.candMobile}
        <br>
            <strong>College Name:</strong> ${candDetails.subTestTaker.testTaker.testTakerName}
        
 	</div>
   <hr>
 <div id="questionContainer" ></div>
</div>

</div>
<%@ include file="footer.jsp" %>
</body>
<script type="text/javascript">
var answerBycId = [
    <c:forEach items="${answerGivenBycandidate}" var="answer">
        {
            questionId: "${answer.question}",
            optionChoosed: "${answer.optChoose}"
        }<c:if test="${!loop.last}">,</c:if>
    </c:forEach>
];

var questions = [
    <% for (int i = 0; i < quests.size(); i++) {
        QuestionBean question = quests.get(i);
    %> 
    {
        questionId: "<%= question.getQuestionId() %>",
        questionText: "<%= question.getQuestionText() %>",
        options: [
            "<%= question.getOption1() %>",
            "<%= question.getOption2() %>",
            "<%= question.getOption3() %>",
            "<%= question.getOption4() %>",
            "<%= question.getOption5() %>"
        ],
        correctAns: [
            <% List<CorrectAnswer> correctAnswers = (List<CorrectAnswer>) session.getAttribute("allAnswer");
               boolean isFirst = true;
               for (CorrectAnswer correctAnswer : correctAnswers) {
                   if (question.getQuestionId().equals(correctAnswer.getQuestionId().getQuestionId())) {
                       if (!isFirst) { %>,<% }
                       isFirst = false;
            %>
                   "<%= correctAnswer.getCorrectAns() %>"
            <% }
               } %>
        ]
    }<% if (i < quests.size() - 1) { %>,<% } %>
    <% } %>
];

function displayQuestions() {
    var questionContainer = document.getElementById("questionContainer");
    questionContainer.innerHTML = "";

    for (var i = 0; i < questions.length; i++) {
        var question = questions[i];
        var questionText = document.createElement("p");
        questionText.innerHTML = "<strong>Question " + (i + 1) + ":</strong> " + question.questionText;
        questionContainer.appendChild(questionText);

        var options = question.options;
        var chosenOptions = getCandidateChosenOptions(question.questionId);

        options.forEach(function(option) {
            if (option.trim() !== "" && option.trim()!== null) { // Check if the option is not empty after trimming
                var optionLabel = document.createElement("label");
                var optionInput = document.createElement("input");
                optionInput.type = (question.correctAns.length === 1) ? "radio" : "checkbox";
                optionInput.name = "answer_" + question.questionId;
                optionInput.value = option;
                optionInput.disabled = true;

                if (chosenOptions.includes(option)) {
                    optionInput.checked = true;
                    optionLabel.style.color = "red"; // Apply red color to the chosen option
                }

                optionLabel.appendChild(optionInput);
                optionLabel.appendChild(document.createTextNode(option));

                questionContainer.appendChild(optionLabel);
                questionContainer.appendChild(document.createElement("br"));
            }
        });

        var correctAnsText = document.createElement("p");
        correctAnsText.innerHTML = "<strong class='text-success'>Correct Answer:</strong> ";

        // Append each correct answer on a new line
        question.correctAns.forEach(function(correctAnswer, index) {
            if (correctAnswer.trim() !== "") { // Check if the correct answer is not empty after trimming
                var correctAnswerElement = document.createElement("span");
                correctAnswerElement.textContent = correctAnswer;
                if (index < question.correctAns.length - 1) {
                    correctAnswerElement.textContent += ", "; // Add comma for all but the last correct answer
                }
                correctAnsText.appendChild(correctAnswerElement);
                correctAnsText.appendChild(document.createElement("br")); // Line break after each correct answer
            }
        });

        questionContainer.appendChild(correctAnsText);
        questionContainer.appendChild(document.createElement("hr")); // Add a horizontal line between questions
    }
}

function getCandidateChosenOptions(questionId) {
    var chosenOptions = [];
  
    answerBycId.forEach(function(answer) {
        if (answer.questionId === questionId) {
            chosenOptions.push(answer.optionChoosed);
        }
    });
  
    return chosenOptions;
}

displayQuestions();

</script>
<script type="text/javascript">
$('#generatePDF').click(function() {
    var doc = new jsPDF();
    
    // Add special element handlers
    var specialElementHandlers = {
        '#answersheetPage': function(element, renderer) {
            return true;
        }
    };

    // Generate PDF from HTML content
    doc.fromHTML($('#answersheetPage').html(), 15, 15, {
        'width': 170,
        'elementHandlers': specialElementHandlers
    });

    // Save the PDF
    doc.save('answersheet.pdf');
});
</script>

</html>