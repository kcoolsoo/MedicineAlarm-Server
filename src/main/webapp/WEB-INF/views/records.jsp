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
	<input type="button" value="Back" onclick="history.go(-1); return true;"/>
	<table cellspacing=0 width="450px" id="recordTable" class="H2">
		<tr align="center" class="top row">
			<td bgcolor="gray">Type</td>
			<td bgcolor="gray">Time to Take</td>
			<td bgcolor="gray">Records</td>
			<td bgcolor="gray">Date</td>
		</tr>
		<c:forEach items="${typeList}" var="value" varStatus="status">
			<tr align="center" class="top row">
				<td>${value}</td>
				<td>${timeList[status.index]}</td>
				<td>${recordList[status.index]}</td>
				<td>${dateList[status.index]}</td>
			</tr>
		</c:forEach>	
		<tr class="top">
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
	<center>
	<c:choose>
		<c:when test="${numOfBlocks == 1}">
			<c:forEach var="i" begin="${(currentBlock-1)*10+1}" end="${numOfPages-1}">
				<a href="record?page_number=${i}&block_number=${currentBlock}&date=${date}"><c:out value="${i}"/></a> | 
			</c:forEach>
			<a href="record?page_number=${numOfPages}&block_number=${currentBlock}&date=${date}">${numOfPages}</a>
		</c:when>
		<c:when test="${currentBlock == '1'}">
			<c:forEach var="i" begin="${(currentBlock-1)*10+1}" end="${currentBlock*10}">
				<a href="record?page_number=${i}&block_number=${currentBlock}&date=${date}"><c:out value="${i}"/></a> | 
			</c:forEach>
			<a href="record?page_number=${currentBlock*10+1}&block_number=${currentBlock+1}&date=${date}">Next</a>
		</c:when>
		<c:when test="${currentBlock == numOfBlocks}">
			<a href="record?page_number=${(currentBlock-1)*10}&block_number=${currentBlock-1}&date=${date}">Previous</a> |
			<c:forEach var="i" begin="${(currentBlock-1)*10+1}" end="${numOfPages-1}">
				<a href="record?page_number=${i}&block_number=${currentBlock}&date=${date}"><c:out value="${i}"/></a> | 
			</c:forEach>
			<a href="record?page_number=${numOfPages}&block_number=${currentBlock}&date=${date}">${numOfPages}</a>
		</c:when>
		<c:otherwise>
			<a href="record?page_number=${(currentBlock-1)*10}&block_number=${currentBlock-1}&date=${date}">Previous</a> |
			<c:forEach var="i" begin="${(currentBlock-1)*10+1}" end="${currentBlock*10}">
				<a href="record?page_number=${i}&block_number=${currentBlock}&date=${date}"><c:out value="${i}"/></a> | 
			</c:forEach>
			<a href="record?page_number=${currentBlock*10+1}&block_number=${currentBlock+1}&date=${date}">Next</a>
		</c:otherwise>
	</c:choose>
	</center>
	</div>
</body>
</html>