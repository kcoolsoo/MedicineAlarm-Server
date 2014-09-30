<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
	<title>Medicine Alarm System</title>
	<style type="text/css">
 
        body {font-family:Arial, Sans-Serif;}
            
		#container {width:500px; margin:0 auto;}
		
		tr.top td { border-top: thin solid black; }
		tr.bottom td { border-bottom: thin solid black; }
		tr.row td:first-child { border-left: thin solid black; border-right: thin solid black; }
		tr.row td:nth-child(2) { border-right: thin solid black; }		
		tr.row td:last-child { border-left: thin solid black; border-right: thin solid black; }	
		H2 {page-break-before: always}
    </style> 	 

</head>
<body>
	<div id="container">
	<h3>Records</h3>
	
	<c:forEach items="${dateList}" var="date">
		<a href="record?page_number=1&block_number=1&date=${date}">${date}</a><br/>
	</c:forEach>
	
	
	</div>
</body>
</html>