<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<html>
<head>
    <style type="text/css">
        body {font-family:Arial, Sans-Serif;}
 
         #container {width:350px; margin:0 auto;}
 
        /* Nicely lines up the labels. */
        form label {display:inline-block; width:140px;}
 
        /* You could add a class to all the input boxes instead, if you like. That would be safer, and more backwards-compatible */
    	form input {width:200px;}
 
        form .line {clear:both;}
        form .line.submit {text-align:right;}
    </style>
    <script type="text/javascript" src="<c:url value="/resources/formValidation.js" />"> </script>
    <script type="text/javascript" >
    	function formValidation() {
			//var id = document.getElementById("id");
			var password = document.getElementById("password");
			var name = document.getElementById("name");
			var age = document.getElementById("age");
			var address_street = document.getElementById("address_street");
			var address_city = document.getElementById("address_city");
			var address_state = document.getElementById("address_state");
			var zip = document.getElementById("zip");
			var phone = document.getElementById("phone");

			if (!checkEmptyFields(password, name, age, address_street, address_city,
					address_state, zip, phone)) {
				return false;
			}
			if (!passwordValidation(password, 4, 12)) {
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
		function checkEmptyFields(pwd, name, age, street, city, state, zip, phone) {
			if (pwd.value.length == 0 || name.value.length == 0
					|| age.value.length == 0 || street.value.length == 0
					|| city.value.length == 0 || state.value.length == 0
					|| zip.value.length == 0 || phone.value.length == 0) {
				alert("Required fields must be filled in.");
				if (pwd.value.length == 0)
					pwd.style.backgroundColor = "yellow";
				else
					pwd.style.backgroundColor = "white";
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
    <div id="container">
        <h1 align="center">Register Patient</h1>
        <form name="registration" action="register_success" method="POST" modelAttribute="registerForm" onsubmit="return formValidation();"> 
        	<c:choose>
        		<c:when test="${!empty next_id}">
            		<div class="line"><label for="id">Patient Number *: </label><input type="text" name="id" id="id" value="${next_id}" path="id" readonly /></div>
            	</c:when>
            	<c:otherwise>
            		<div class="line"><label for="id">Admin Id *: </label><input type="text" name="id" id="id" value="0" path="id" readonly /></div>
            	</c:otherwise>
            </c:choose>	
            <div class="line"><label for="password">Password *: </label><input type="password" name="password" id="password" path="password" /></div>
            <!-- You may want to consider adding a "confirm" password box also -->
            <div class="line"><label for="name">Name *: </label><input type="text" name="name" id="name" path="name" ></div>
            <div class="line"><label for="age">Age *: </label><input type="text" name="age" id="age" path="age" /></div>
            <div class="line"><label for="address_street">Street*: </label><input type="text" name="address_street" id="address_street" path="address_street" /></div>
            <div class="line"><label for="address_city">City *: </label><input type="text" name="address_city" id="address_city" path="address_city" /></div>
            <div class="line"><label for="address_state">State *: </label><input type="text" name="address_state" id="address_state" path="address_state" /></div>
            <div class="line"><label for="zip">Zip *: </label><input type="text" name="zip" id="zip" path="zip" /></div>
            <div class="line"><label for="phone">Phone Number *: </label><input type="text" name="phone" id="phone" path="phone" /></div>
            <div class="line"><label for="phoneFormat">(xxx-xxx-xxxx)</label></div>   
            <div class="line submit"><input type="button" value="Cancel" onclick="history.go(-1); return true;" style="width:50px;" /> <input type="submit" value="Submit" style="width:50px;" /></div>
 
            <p>Note: Please make sure your details are correct before submitting form and that all fields marked with * are completed!.</p>
        </form>
    </div>
</body>
</html>