<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		
		input[type="text"]
		{	
			width:200px;
			background-color:transparent;
			border:inline;
		}		
    </style>  
    <script type="text/javascript" src="<c:url value="/resources/formValidation.js" />"> </script>
	<script type="text/javascript">
		function editMode() {
			document.getElementById("edit").style.display="none";
			document.getElementById("unregister").style.display = "inline";
			document.getElementById("save").style.display = "inline";
			document.getElementById("update").style.display = "inline";
			document.getElementById("delete").style.display = "inline";
						
			for(var i = 0; i < ${medicineIdList}.length; i++) {
				document.getElementById("medicine"+i).style.display = "";
			}
		
		    document.getElementById("name").disabled = false;
    		document.getElementById("age").disabled = false;
    		document.getElementById("address_street").disabled = false;
    		document.getElementById("address_city").disabled = false;
    		document.getElementById("address_state").disabled = false;
    		document.getElementById("zip").disabled = false;
    		document.getElementById("phone").disabled = false;

		}
		function checkRadioSelected() {
			for(var i = 0; i < ${medicineIdList}.length; i++) {
				if(document.getElementById("medicine"+i).checked)
					return true;
			}		
			alert("You must select a radio button.");
				
			return false;
		}
		function idChecking() {
			var errMsg = "${errMsg}";
		
			if(errMsg != "") {
				alert(errMsg);
				history.go(-1); 
				return true;
			}
		}
		function formValidation() {
			//var id = document.getElementById("id");
			//var password = document.getElementById("password");
			var name = document.getElementById("name");
			var age = document.getElementById("age");
			var address_street = document.getElementById("address_street");
			var address_city = document.getElementById("address_city");
			var address_state = document.getElementById("address_state");
			var zip = document.getElementById("zip");
			var phone = document.getElementById("phone");

			if (!checkEmptyFields(name, age, address_street, address_city,
					address_state, zip, phone)) {
				return false;
			}
			if(!allLetterValidation(name, "Invalid name.")) {
				return false;
			}
			if(!allNumericValidation(age, "Invalid age.")) {
				return false;
			}
			if(!letterNumericValidation(address_street, "Invalid address street.")) {
				return false;
			}
			if(!allLetterValidation(address_city, "Invalid address city.")) {
				return false;
			}
			if(!allLetterValidation(address_state, "Invalid address state.")) {
				return false;
			}
			if(!allNumericValidation(zip, "Invalid Zip.")) {
				return false;
			}
			if(!phoneValidation(phone)) {
				return false;
			}

			return true;
		}
		function checkEmptyFields(name, age, street, city, state, zip, phone) {
			if (name.value.length == 0
			|| age.value.length == 0 || street.value.length == 0
			|| city.value.length == 0 || state.value.length == 0
			|| zip.value.length == 0 || phone.value.length == 0) {
				alert("Required fields must be filled in.");
				if (name.value.length == 0)
					name.style.backgroundColor = "yellow";
				else
					name.style.backgroundColor = "white";
				if (age.value.length == 0)
					age.style.backgroundColor = "yellow";
				else
					age.style.backgroundColor = "white";
				if (street.value.length == 0)
					street.style.backgroundColor = "yellow";
				else
					street.style.backgroundColor = "white";
				if (city.value.length == 0)
					city.style.backgroundColor = "yellow";
				else
					city.style.backgroundColor = "white";
				if (state.value.length == 0)
					state.style.backgroundColor = "yellow";
				else
					state.style.backgroundColor = "white";
				if (zip.value.length == 0)
					zip.style.backgroundColor = "yellow";
				else
					zip.style.backgroundColor = "white";
				if (phone.value.length == 0)
					phone.style.backgroundColor = "yellow";
				else
					phone.style.backgroundColor = "white";
		
				return false;
			}
			return true;
		}
    </script>
</head>
<body>
	<h3>Patient Information</h3>	
	<table cellspacing=0 width="450px">
	<form name="form1" action="information_update" method="POST" modelAttribute="informationForm" onsubmit="return formValidation();">
		<tr>
			<td align="left"><input type="submit" id="unregister" value="Unregister" style="display:none" onclick="this.form.action='information_delete'"/></td>
			<td align="right"><a href="#" id="edit" onclick="editMode()">Edit</a></td>
		</tr>
		<tr class="top row">
			<td>Patient Number</td>
			<td><input type="text" value="${id}" id="id" name="id" path="id"  style="background-color:transparent;border:none;" readonly/></td>
		</tr>
		<tr class="top row">
			<td>Name</td>
			<td><input type="text" value="${name}" id="name" name="name" path="name" disabled=true/></td>
		</tr>
		<tr class="top row">
			<td>Age</td>
			<td><input type="text" value="${age}" id="age" name="age" path="age" disabled=true/></td>
		</tr>
		<tr class="top row">
			<td>Street</td>
			<td><input type="text" value="${address_street}" id="address_street" name="address_street" path="address_street" disabled=true/></td>
		</tr>
		<tr class="top row">
			<td>City</td>
			<td><input type="text" value="${address_city}" id="address_city" name="address_city" path="address_city" disabled=true/></td>
		</tr>
		<tr class="top row">
			<td>State</td>
			<td><input type="text" value="${address_state}" id="address_state" name="address_state" path="address_state" disabled=true/></td>
		</tr>
		<tr class="top row">
			<td>Zip</td>
			<td><input type="text" value="${zip}" id="zip" name="zip" path="zip" disabled=true/></td>
		</tr>
		<tr class="top row">
			<td>Phone Number</td>
			<td><input type="text" value="${phone}" id="phone" name="phone" path="phone" disabled=true/></td>
		</tr>
		<tr class="top bottom row">
			<td>Reward Points</td>
			<td><input type="text" value="${points}" id="points" name="points" path="points"  style="background-color:transparent;border: none;" readonly/></td>
		</tr>

		<tr>
			<td colspan="2" align="right"><input type="submit" id="save" value="Save" style="display:none" onclick="this.form.action='information_update'"></td>
		</tr>	
		</form>				
		<form name="form2" method="POST" modelAttribute="" onsubmit="return checkRadioSelected();">
		<tr class="top bottom row">
			<td>Medicines</td>
			<td>
				<c:forEach items="${typeList}" var="typeList" varStatus="status">
					<input type="text" value="${status.index+1}." style="background-color:transparent;border:none; width:12px;" readonly/> <input type="text" value="${typeList}" name="type" id="type" style="background-color:transparent;border:none;width:120px" readonly/>  <input type="text" value="${timeList[status.index]}" name="time_to_take" id="time_to_take" style="background-color:transparent;border:none;width:150px" readonly/> <input type="radio" name="medicine" id="medicine${status.index}" value="${medicineIdList[status.index]}" style="display:none"/><br/>
				</c:forEach>	
			</td>
		</tr>
		<tr>
			<td colspan="2" align="right"><input type="submit" id="delete" value="Delete" style="display:none" onclick="this.form.action='delete_medicine'"><input type="submit" id="update" value="Update" style="display:none" onclick="this.form.action='update_medicine'"/></td>
		</tr>	
		</form>
	</table>
</html>