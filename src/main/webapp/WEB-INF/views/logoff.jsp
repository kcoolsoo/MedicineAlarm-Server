<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Medicine Alarm System</title>
</head>
<body>
	<%  
        request.getSession().removeAttribute("patientId");  
        session.invalidate();
        response.sendRedirect("login");
	%>  
</body>
</html>
