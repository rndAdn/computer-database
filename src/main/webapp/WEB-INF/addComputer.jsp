<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			Database </a>
	</div>
	</header>

	<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1>Add Computer</h1>
				<form action="addComputer" method="POST">
					<fieldset>
						<div class="form-group">
							<label for="computerName">Computer name</label> <input
								type="text" name="computerName" class="form-control"
								id="computerName" placeholder="Computer name">
						</div>
						<div class="form-group">
							<label for="introduced">Introduced date</label> <input
								type="date" name="introduced" class="form-control"
								id="introduced" placeholder="Introduced date">
						</div>
						<div class="form-group">
							<label for="discontinued">Discontinued date</label> <input
								type="date" name="discontinued" class="form-control"
								id="discontinued" placeholder="Discontinued date">
						</div>
						<div class="form-group">
							<label for="companyId">Company</label> <select name="company"
								class="form-control" id="companyId">
								<option value="0:--">--</option>
								<c:forEach items="${companylist}" var="company">
									<option value="${company.id}:${company.name}">${company.name}</option>
								</c:forEach>
							</select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="Add" class="btn btn-primary">
						or <a href="" class="btn btn-default">Cancel</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	</section>
	<script src="resources/js/validation.js"></script>
</body>
</html>