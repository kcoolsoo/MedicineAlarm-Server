<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
	String userId = request.getParameter("patientNumber");
	session.setAttribute("patientId", userId);
%>
<html>
	<head>
		<title>Medicine Alarm System</title>
		<style type="text/css">
 
            body {font-family:Arial, Sans-Serif;}
            
			#container {width:500px; margin:0 auto;}
			
        </style>   
        <script type="text/javascript">
        function idChecking() {
			var errMsg = "${errMsg}";
		
			if(errMsg != "") {
				alert(errMsg);
				history.go(-1); 
				return true;
			}
		}
        </script> 
	</head>
	<body onload="idChecking()">
	<div id="container">
		<h1>Medicine Alarm System Administration</h1><br/>
		<a href="logoff">Log Off</a> | <a href="information" target="mainFrame">Information</a> | <a href="add" target="mainFrame">Add Medicine</a> | <a href="records_date" target="mainFrame">Record</a>	
		<iframe name="mainFrame" src="information" frameborder="0" style="overflow:hidden;overflow-x:hidden;overflow-y:hidden;height:100%;width:100%;positionabsolute;top:0px;left:0px;right;0px;bottom:0px" height=100% width="100%"/>
	</div>
	</body>
</html>