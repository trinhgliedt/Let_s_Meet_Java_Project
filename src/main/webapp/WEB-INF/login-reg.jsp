
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Log-in/registration page</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body class="pt-3">
    <p class="text-danger"><form:errors path="user.*" />
    	<c:out value="${error}"/>
    </p>
    <div class="container row">
    <!------------------ Registration ------------------->
    <form:form method="POST" action="/register" modelAttribute="user" class="col-5 p-2 mr-3">
        <p>
            <form:label path="firstName" class="col-3">First name:</form:label>
            <form:input path="firstName" class="col-7"/>
        </p>
        <p>
            <form:label path="lastName" class="col-3">Last name:</form:label>
            <form:input path="lastName" class="col-7"/>
        </p>
        <p>
        	<form:label path="email" class="col-3">Email:</form:label>
            <form:input type="email" path="email" class="col-7"/>
        </p>
        <p>
            <form:label path="city" class="col-3">Location:</form:label>
            <form:input path="city" class="col-4"/>
        	<form:label path="state">State:</form:label>            
            <form:select path="state" class="col-2">
               <form:options items="${stateList}" />
            </form:select>
        </p>
        <p>
            <form:label path="password" class="col-3">Password:</form:label>
            <form:password path="password" class="col-7"/>
        </p>
        <p>
            <form:label path="passwordConfirmation" class="col-3">PW Conf:</form:label>
            <form:password path="passwordConfirmation" class="col-7"/>
        </p>
        <input type="submit" value="Register!" class="btn btn-sm btn-info offset-8"/>
    </form:form>
    <!------------------ Log in ------------------->
    <form method="post" action="/login" class="col-5 p-2 mr-3">
        <p>
            <label for="email" class="col-3">E-mail:</label>
            <input type="text" id="email" name="email" class="col-7" value=<c:out value="${email}" ></c:out>>
        </p>
        <p>
            <label for="password" class="col-3">Password:</label>
            <input type="password" id="password" name="password" class="col-7"/>
        </p>
        <input type="submit" value="Log in" class="btn btn-sm btn-info offset-8"/>
    </form>
    </div>
</body>
</html>