<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Medicine Alarm System</title>
	<style type="text/css">
 
        body {font-family:Arial, Sans-Serif;}
            
		#container {width:500px; margin:0 auto;}
			
    </style>
    <script type="text/javascript">
    function formValidation() {
    	var pNum = document.getElementById("patientNumber");
    	
    	if(!allNumericValidation(pNum, "Invalid patient number.")) {
    		return false;
    	}
    	
    	return true;
    }
    function allNumericValidation(component, msg) {
		var numbers = /^[0-9]+$/;
		if(component.value.match(numbers)) {
			component.style.backgroundColor = "white";
			return true;
		} else {
			alert(msg);
			component.style.backgroundColor = "yellow";
			component.focus();
			return false;
		}
	}
    </script>  
</head>
<body>
<sec:authorize access="ROLE_ADMIN">
	<div id="container">
	<h1>Medicine Alarm System Administration</h1><br/>
	<form action="main" method="POST" onsubmit="return formValidation();">	
	<table border="0" style="width:360px">
		<tr>
			<td>Patient Number:</td>
			<td><input type="text" id="patientNumber" name="patientNumber"></td>
			<td><input type="submit" value="Submit"></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td><a href="register">sign up</a>.</td>
		</tr>
		</table>
	</form>	
	</div>
</sec:authorize>
</body>
</html>
