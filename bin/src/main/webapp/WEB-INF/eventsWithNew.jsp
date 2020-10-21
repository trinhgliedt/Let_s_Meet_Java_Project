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
	<title>Home page</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="Description" content="Home page"> <!-- Explanation that shows up in search engines goes here.-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
	<div class="row">
		<h2 class="col-7 ml-2">Welcome, <c:out value="${user.firstName}"/>!</h2>
		<a href="/logout" class="col-2">Log out</a>
	</div>
	<!-- -------------Top table-------------- -->
	<div class="col">
		<h4>Here are some events in your state:</h4>
		<table class="table table-bordered table-hover col-8">
		    <thead>
		        <tr>
		            <th>Name</th>
		            <th>Date</th>
		            <th>Location</th>
		            <th>Host</th>
		            <th>Action/status</th>
		        </tr>
		    </thead>
		    <tbody>
		        <c:forEach items="${inStateEvents}" var="event">
			        <tr>
			            <td><a href="/events/${event.id}"><c:out value="${event.eventName}"/></a></td>
			            <td>
			            <jsp:useBean id="date" class="java.util.Date"/>
			            <fmt:formatDate value="${event.eventDate}" type="date" pattern="MMMM dd, yyyy"/>
			            </td>
			            <td><c:out value="${event.city}"/></td>
			            <td><c:out value="${event.eventHost.firstName}"/></td>
			            <td>
			            	<c:choose>
			            		<c:when test="${user == event.eventHost}">
			            			<a href="/events/${event.id}/edit">Edit</a>
			            			<form style="display:inline" action="/events/${event.id}/delete" method="post" >
									    <input type="hidden" name="_method" value="delete">
									    <input type="submit" value="Delete" class="btn btn-link mb-1 ml-2"  >
									</form>
			            		</c:when>
			            		<c:when test="${event.users.contains(user)}">
			            			Joined <a href="/events/${event.id}/cancel" class="ml-2">Cancel</a>
			            		</c:when>
			            		<c:otherwise>
			            			<a href="/events/${event.id}/join">Join</a>
			            		</c:otherwise>
			            	</c:choose>
			            </td>
			        </tr>
		        </c:forEach>
		    </tbody>
		</table>
	
	<!-- -------------Second table-------------- -->
	
	
		<h4>Here are some events in other states:</h4>
		<table class="table table-bordered table-hover col-8">
		    <thead>
		        <tr>
		            <th>Name</th>
		            <th>Date</th>
		            <th>Location</th>
		            <th>State</th>
		            <th>Host</th>
		            <th>Action</th>
		        </tr>
		    </thead>
		    <tbody>
		        <c:forEach items="${outOfStateEvents}" var="event">
			        <tr>
			            <td><a href="/events/${event.id}"><c:out value="${event.eventName}"/></a></td>
			            <td>
			            <jsp:useBean id="date2" class="java.util.Date"/>
			            <fmt:formatDate value="${event.eventDate}" type="date" pattern="MMMM dd, yyyy"/>
			            </td>
			            <td><c:out value="${event.city}"/></td>
			            <td><c:out value="${event.state}"/></td>
			            <td><c:out value="${event.eventHost.firstName}"/></td>
		            	<td>
			            	<c:choose>
			            		<c:when test="${user == event.eventHost}">
			            			<a href="/events/${event.id}/edit">Edit</a>
			            			<form action="/events/${event.id}/delete" method="post" style="display:inline" >
									    <input type="hidden" name="_method" value="delete">
									    <input type="submit" value="Delete" class="btn btn-link mb-1 ml-2">
									</form>
			            		</c:when>
			            		<c:when test="${event.users.contains(user)}">
			            			Joined <a href="/events/${event.id}/cancel" class="ml-2">Cancel</a>
			            		</c:when>
			            		<c:otherwise>
			            			<a href="/events/${event.id}/join">Join</a>
			            		</c:otherwise>
			            	</c:choose>
			            </td>
			        </tr>
		        </c:forEach>
		    </tbody>
		</table>
	</div>
	<!-- --------------------Bottom form------------------------------- -->
	<div class="col-8 mt-5 mb-5">
		<h3 class="mb-4">Create an event</h3>
		<p class="text-danger">
			<c:out value="${error}"/>
			<form:errors path="event.*" />
		</p>
		<form:form method="POST" action="/new-event" modelAttribute="event">
	        <p>
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
	        <input class="btn btn-sm btn-info offset-7" type="submit" value="Submit"/>
	    </form:form>
    </div>
</body>
</html>