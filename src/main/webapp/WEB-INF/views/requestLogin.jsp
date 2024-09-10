<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Request Login Popup</title>
    <link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.min.css">
<link
	rel="https://cdnjs.datatables/1.12.1/css/dataTables.bootstrap4.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script
	src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
<script
	src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap4.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!-- Popup container -->
    <div id="popupContainer">
    <form action="./savePopupData" method="post">
        <h3>Welcome back, ${candidate.candFirstname}!</h3>
        <label for="popupTextbox" >Enter Reason for Logout:</label>
       
        <textarea rows="" cols="" id="popupTextbox" name="reasonForLogout"></textarea>
        <div class="text-center" >
        <button type="submit" onclick="submitPopup()" class="btn btn-primary">Submit</button>
        
        <button onclick="cancelBlock()" class="btn btn-danger" style="margin-left: 10px">Cancel</button>
        </div>
        </form>
    </div>

   


<script>
 function submitPopup() {
    var inputValue = document.getElementById("popupTextbox").value;
    

    // Make an AJAX call to save the data
    $.ajax({
        type: "POST",
        url: "/savePopupData",
        data: {
            username: "${candidate.candidateemail}", // Pass the candidate's email
            
        }
    });
} 

 function cancelPopupBlock(sId) {
	    
	    window.location.href = "http://localhost:8088/exam/logout"; 

	}
</script>

</body>
</html>