<%@ page import="com.google.gson.JsonObject"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Twitch Streaming</title>
</head>
<body>
	<%
	    if (request.getAttribute("JsonObject") == null) {
	%>
	<p>
		The user ${name} is currently offline
		<%
	    } else {
	%>
	
	<p>
		The user ${name} is currently online and streaming on this <a
			href="https://twitch.tv/${name}">page</a>
	</p>
	<%
	    }
	%>

</body>
</html>