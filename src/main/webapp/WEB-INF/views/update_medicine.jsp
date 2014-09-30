<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
	<title>Medicine Alarm System</title>
	<style type="text/css">
 
        body {font-family:Arial, Sans-Serif;}
            
		#container {width:500px; margin:0 auto;}
		
		tr.top td { border-top: thin solid black; }
		tr.bottom td { border-bottom: thin solid black; }
		tr.row td:first-child { border-left: thin solid black; border-right: thin solid black; }
		tr.row td:last-child { border-right: thin solid black; }	
    </style>  
    <script type="text/javascript">
		function dataSetting() {
			if("${ampm}" == "AM") 
				document.getElementById("ampm").selectedIndex = "0";
			else
				document.getElementById("ampm").selectedIndex = "1";
			
			document.getElementById("medicineType").value = "${type}";
		}
		   	function validate() {
    		var sel = document.getElementById("medicineType");
    		var type = document.getElementById("type");
    		var hour = document.getElementById("hour");
    		var minute = document.getElementById("minute");
    			
    		if(sel.value == "other") {
    			if(type.value == "") {
    				alert("Type field is empty.");
    				type.style.backgroundColor = "yellow";
    				type.focus();
    				return false;
    			}
    		} else {
    			if(sel.value == "none") {
    				alert("Please select medicine type.");
    				sel.focus();
    				return false;
    			}  
    		} 
    		
    		if(hour.value == "" || !(hour.value >= 1 && hour.value <= 12)) {
    			alert("Invalid time format.");
    			hour.style.backgroundColor = "yellow";
    			hour.focus();
    			return false;
    		} else if (!allNumericValidation(hour, "Invalid time format.")) {
    			hour.style.backgroundColor = "yellow";
    			hour.focus();
    			return false;
    		} else {
     			hour.style.backgroundColor = "white";
     		}
    		
    		if(minute.value=="" || !(minute.value >= 0 && minute.value <=59)) {
    			alert("Invalid time format.");
    			minute.style.backgroundColor = "yellow";
    			minute.focus();
    			return false;
    		} else if (!allNumericValidation(minute, "Invalid time format.")) {
    		    minute.style.backgroundColor = "yellow";
    			minute.focus();
    			return false;
    		} else {
    			minute.style.backgroundColor = "white";
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
<body onload="dataSetting()">
	<h3>Update Medicine</h3>
	<form action="update_success" method="POST" id="updateForm" name="updateForm" onsubmit="return validate();">
		<table id="updateTable" name="updateTable" cellspacing="0" width="450px">
			<tr class="top row">
				<td>Patient Number</td>
				<td><input type="text" name="patient_id" value="${sessionScope.patientId}" style="background-color:transparent;border: none;" readonly/></td>
			</tr>
			<tr class="top row">
				<td>Type</td>
				<td>
					<select id="medicineType" name="medicineType">
						<c:forEach items="${medicineTypeList}" var="value">
							<option value="${value}">${value}</options>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr class="top bottom row">
				<td>Time</td>
				<td>
					<input type="number" min="1" max="12" step="1" value="${hour}" name="hour" id="hour">
					: <input type="number" min="0" max="59" step="5" value="${minute}" name="minute" id="minute">
					<select name="ampm" id="ampm">
						<option value="am">AM</option>
						<option value="pm">PM</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="button" value="Cancel" onclick="history.go(-1); return true;" /><input type="submit" value="Submit"></td>
			</tr>
		</table>
		<input type="text" value="${id}" name="medicineId" id="medicineId" style="display:none" >
	</form>
</body>
</html>