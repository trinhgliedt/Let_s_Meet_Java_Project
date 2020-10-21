<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Edit event</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="Description" content="Edit your event"> <!-- Explanation that shows up in search engines goes here.-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
	<div class="col-8 mt-5 mb-5">
	<form:form action="/events/${event.id}/edit" method="post" modelAttribute="event">
	<div class="row mb-4">
		<h3 class="col-5">${event.eventName}</h3>
		<a href="/events" class="mr-3 ml-5">View all events</a>
		<a href="/logout" >Log out</a>
	</div>
    	<input type="hidden" name="_method" value="put">
    		<p>
    			<input type="hidden" name="_method" value="put">
	            <form:label path="eventName" class="col-2">Name:</form:label>
	            <form:input path="eventName" class="col-6"/>
	        </p>
	        <p>
	            <form:label path="eventDate" class="col-2">Date:</form:label>
		        <form:input type="date" path="eventDate" class="col-6"/>
	        </p>
	        <p>
	            <form:label path="city" class="col-2">City:</form:label>
	            <form:input path="city" class="col-4"/>
	        	<form:label path="state" class="ml-4 mr-2">State:</form:label>            
	            <form:select path="state">
	               <form:options items="${stateList}" />
	            </form:select>
	        </p>
        <input class="btn btn-sm btn-info offset-7" type="submit" value="Save changes"/>
   	</form:form>
   	</div>
</body>
</html>