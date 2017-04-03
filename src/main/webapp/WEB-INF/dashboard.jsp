<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<c:set var="href" scope="session" value="?" />
	<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href=""> Application - Computer
			Database </a>
	</div>
	</header>

	<section id="main">
	<div class="container">
		<h1 id="homeTitle">
			${totalRowNumber} <spring:message code="local.computerfound"/>
		</h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="" method="GET" class="form-inline">

					<input type="search" id="searchbox" name="search"
						class="form-control" placeholder="<spring:message code="local.searchname"/> : ${search}" /> <input
						type="submit" id="searchsubmit" value="<spring:message code="local.filter"/>"
						class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer" href="addComputer"><spring:message code="local.addComputer"/></a>
				 <a class="btn btn-default" id="editComputer" href="#"
					onclick="$.fn.toggleEditMode();">Edit</a>
			</div>
		</div>
	</div>

	<form id="deleteForm" action="" method="POST">
		<input type="hidden" name="selection" value="">
	</form>

	<div class="container" style="margin-top: 10px;">
		<table class="table table-striped table-bordered">
			<thead>
			<tr>
				<!-- Variable declarations for passing labels as parameters -->
				<!-- Table header for Computer Name -->

				<th class="editMode" style="width: 60px; height: 22px;"><input
						type="checkbox" id="selectall" /> <span
						style="vertical-align: top;"> - <a href=""
														   id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
						class="fa fa-trash-o fa-lg"></i>
						</a>
					</span></th>
				<th>
					<a href="?pageSize=${pageSize}&pageNumber=${pageNumber}&search=${search}&orderBy=name" onclick="">
						<spring:message code="local.computername"/>
					</a>
				</th>
				<th>
					<a href="?pageSize=${pageSize}&pageNumber=${pageNumber}&search=${search}&orderBy=dateIntro" onclick="">
						<spring:message code="local.introduced"/>
					</a>
				</th>
				<!-- Table header for Discontinued Date -->
				<th>
					<a href="?pageSize=${pageSize}&pageNumber=${pageNumber}&search=${search}&orderBy=dateFin" onclick="">
						<spring:message code="local.discontinued"/>
					</a>
				</th>
				<!-- Table header for Company -->
				<th >
					<a href="?pageSize=${pageSize}&pageNumber=${pageNumber}&search=${search}&orderBy=company" onclick="">
					    <spring:message code="local.company"/>
					</a>
				</th>

			</tr>
			</thead>
			<!-- Browse attribute computers -->
			<tbody id="results">
				<c:forEach items="${computersList}" var="computer">
					<tr>
						<td class="editMode"><input type="checkbox" name="cb"
							class="cb" value="${computer.id}"></td>
						<td><a href="editComputer?computerId=${computer.id}" onclick=""><c:out
									value="${computer.name}"></c:out></a></td>
						<td><c:out value="${computer.dateIntroduced}"></c:out></td>
						<td><c:out value="${computer.dateDiscontinued}"></c:out></td>
						<td><c:out value="${computer.company.name}"></c:out></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	</section>

	<footer class="navbar-fixed-bottom">
	<div class="container text-center">
		<ul class="pagination">
			<li><a
				href="?pageSize=${pageSize}&pageNumber=${pageNumber - 1}&search=${search}&orderBy=${orderBy}"
				aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
			</a></li>

			<c:forEach var="i"
				begin="${(pageNumber - 3 > 0) ? (pageNumber - 3) : 1}"
				end="${(pageNumber + 3 < totalPageNumber) ? (pageNumber + 3) : totalPageNumber}"
				step="1">
				<li><a
					href="?pageSize=${pageSize}&pageNumber=${i}&search=${search}&orderBy=${orderBy}"><c:out
							value="${i}" /></a></li>
			</c:forEach>

			<li><a
				href="?pageSize=${pageSize}&pageNumber=${pageNumber + 1}&search=${search}&orderBy=${orderBy}"
				aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</ul>

		<div class="btn-group btn-group-sm pull-right" role="group">
			<a type="button" class="btn btn-default" href="?pageSize=10&pageNumber=1&search=${search}">10</a> <a
				type="button" class="btn btn-default" href="?pageSize=50&pageNumber=1&search=${search}">50</a> <a
				type="button" class="btn btn-default" href="?pageSize=100&pageNumber=1&search=${search}">100</a>
		</div>
	</div>
	</footer>
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/dashboard.js"></script>

</body>
</html>
