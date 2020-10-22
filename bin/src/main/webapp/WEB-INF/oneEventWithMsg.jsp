<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Show event</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="Description" content="Page that shows this particular event"> <!-- Explanation that shows up in search engines .-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <!-- <style>
    * {outline: 1px solid grey;}
    </style> -->
</head>
<body class="p-4">
	<div class="row mb-4"> <!-- Top bar -->
		<h3 class="col-8">${event.eventName}</h3>
		<a href="/events" class="mr-3 ml-5">View all events</a>
		<a href="/logout" >Log out</a>
	</div>
	<!-- Main content -->
	<div class="row">
		<!------------ Beginning of left side ----------------->
		<div class="col-5 mx-4"> 
			<p>Host: <c:out value="${event.eventHost.firstName}"/> <c:out value="${event.eventHost.lastName}"/></p>
			<p>Date: 
			<jsp:useBean id="date" class="java.util.Date"/>
            <fmt:formatDate value="${event.eventDate}" type="date" pattern="MMMM dd, yyyy"/>
			</p>
			<p>Location: <c:out value="${event.city}"/>, <c:out value="${event.state}"/></p>
			<p>People who are attending this event: <c:out value="${event.users.size()}"/></p>
			<table class="table table-bordered">
			    <thead>
			        <tr>
			            <th>Name</th>
			            <th>Location</th>
			        </tr>
			    </thead>
			    <tbody>
			        <c:forEach items="${event.users}" var="user">
				        <tr>
				            <td><c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/></td>
				            <td><c:out value="${user.city}"/></td>
				        </tr>
			        </c:forEach>
			    </tbody>
			</table>
		</div> <!------------ End of left side ----------------->
		<!------------ Beginning of right side ----------------->
		<div class="col-6 "> 
			<h3>Message wall:</h3>
			<div style="height:200px" class="overflow-auto p-2 border my-4"> <!------------Beginning of Message box--------------->
				<c:forEach items="${event.messages}" var="message">
		        <p><c:out value="${message.user.firstName}"/> <c:out value="${message.user.lastName}"/>: <c:out value="${message.messageText}"/></p>
		        <p>-----------------------</p>
			    </c:forEach>
			    <p><c:out value="${errorMessage}"/></p>
				<form:form method="POST" action="/events/${event.id}" modelAttribute="message">
		        <p>
			</div><!------------ End of Message box--------------->
	            <p>Add comment:</p>
	            <p class="text-danger"><form:errors path="message*" />
    				<c:out value="${error}"/>
   				 </p>
	            <form:textarea cols="60" rows="5" path="messageText"/>
	        </p>
	        <input type="submit" value="Submit" class="btn btn-info btn-sm offset-9"/>
	    </form:form>
		</div> <!------------ End of right side ----------------->
</body>
</html>