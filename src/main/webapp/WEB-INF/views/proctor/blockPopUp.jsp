<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
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
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reason Login Popup</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: rgba(0, 0, 0, 0.5);
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }

        #popupContainer {
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            padding: 20px;
            border-radius: 8px;
            height: 400px;
            width: 300px;
            text-align: center;
        }

        h3 {
            margin-bottom: 15px;
            color: #3498db;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }

        textarea {
            width: 100%;
            height: 70%;
            padding: 8px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-bottom: 15px;
            
        }


        button:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>



<!-- Popup container -->
    <div id="popupContainer">
    <% String cId= request.getParameter("cId"); %>
    <% Integer sId = (Integer) session.getAttribute("subtesttakerId"); %>
        <label for="popupTextbox" >Enter Reason For Block:</label>
       <input type="hidden" id="candidateId" value=<%=cId %> >
        <textarea rows="" cols="" id="blockPopup" name="blockreason"></textarea>
        <div class="text-center">
        <button  onclick="submitPopupBlock()" class="btn btn-primary">Submit</button>
        <button onclick="cancelPopupBlock(<%= sId %>)" class="btn btn-danger" style="margin-left: 10px">Cancel</button>
       </div>
    </div>
<script>
 function submitPopupBlock() {
    var reason = document.getElementById("blockPopup").value;
    var cId =  document.getElementById("candidateId").value;
    /* alert(cId); */
if(reason != null){
$.ajax({
type : "POST",
url : "./updateCandidate",
data :{
cId : cId,
reason : reason,
},
success : function(res) {
/* alert(res); */
window.location.href="http://localhost:8088/exam/monitor?subtesttakerId="+res;
}
});
}
    // Make an AJAX call to save the data
    
} 

 function cancelPopupBlock(sId) {
	    
	    window.location.href = "http://localhost:8088/exam/monitor?subtesttakerId="+sId; 
   
	}
</script>

</body>
</html>
