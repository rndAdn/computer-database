<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href="dashboard"> Application - Computer
			Database </a> <a href="user?mylocale=en">English </a> | <a
			href="user?mylocale=fr">Français </a>
	</div>
	</header>

	<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1>
					<spring:message code="local.addComputer" />
				</h1>
				
				
				<form:form action="addComputer" modelAttribute="computerFormAdd" method="POST" >
				<fieldset>
					<div class="form-group">
							<label for="computerName"><spring:message code="local.computername" /></label>
							<form:input 
							path="name" 
							type="text" 
							class="form-control" 
							name="computerName" 
							id="computerName" />
						</div>
						<div class="form-group">
							<label for="introduced"><spring:message
									code="local.introduced" /></label> 
							<form:input path="dateIntroduced" type="date"
								class="form-control" name="introduced" id="introduced"
								value="${computer.dateIntroduced}"/>
						</div>
						<div class="form-group">
							<label for="discontinued"><spring:message
									code="local.discontinued" /></label> 
							<form:input path="dateDiscontinued" type="date"
								class="form-control" name="discontinued" id="discontinued"
								value="${computer.dateDiscontinued}"/>
						</div>
						<div class="form-group">
							<label for="companyId"><spring:message code="local.company" /></label> 
							<form:select path="companyId" class="form-control" name="company" id="companyId">
								<form:option value="0">--</form:option>
								<c:forEach items="${companylist}" var="company">
									<c:choose>
										<c:when test="${companyId == company.id}">
											<form:option selected="true" value="${company.id}">${company.name}</form:option>
										</c:when>
										<c:otherwise>
											<form:option value="${company.id}">${company.name}</form:option>
										</c:otherwise>
									</c:choose>


								</c:forEach>
							</form:select>
						</div>
				</fieldset>
				<div class="actions pull-right">
					<input type="submit" value="<spring:message code="local.add"/>"
						class="btn btn-primary">
					<spring:message code="local.or" />
					<a href="" class="btn btn-default"><spring:message
							code="local.cancel" /></a>
				</div>
				</form:form>
			</div>
		</div>
	</div>
	</section>
	<script src="resources/js/validation.js"></script>
</body>
</html>