<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="resources/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
    </div>
</header>
<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <div class="label label-default pull-right">
                    id: ${computer.id}
                </div>
                <h1><spring:message code="local.editComputer"/></h1>

                <form action="editComputer" method="POST">
                    <input type="hidden" value="${computer.id}" name="computerId" id="id"/>
                    <!-- TODO: Change this value with the computer id -->
                    <fieldset>
                        <div class="form-group">
                            <label for="computerName"><spring:message code="local.computername"/></label>
                            <input type="text" class="form-control" name="computerName" id="computerName" value="${computer.name}"
                                   placeholder="<spring:message code="local.computername"/>">
                        </div>
                        <div class="form-group">
                            <label for="introduced"><spring:message code="local.introduced"/></label>
                            <input type="date" class="form-control" name="introduced" id="introduced" value="${computer.dateIntroduced}"
                                   placeholder="<spring:message code="local.introduced"/>">
                        </div>
                        <div class="form-group">
                            <label for="discontinued"><spring:message code="local.discontinued"/></label>
                            <input type="date" class="form-control" name="discontinued" id="discontinued"
                                   value="${computer.dateDiscontinued}" placeholder="<spring:message code="local.discontinued"/>">
                        </div>
                        <div class="form-group">
                            <label for="companyId"><spring:message code="local.company"/></label>
                            <select class="form-control" name="company" id="companyId">
                                <option value="0">--</option>
                                <c:forEach items="${companylist}" var="company">
                                    <c:choose>
                                        <c:when test="${companyId == company.id}">
                                            <option selected="true" value="${company.id}:${company.name}">${company.name}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${company.id}:${company.name}">${company.name}</option>
                                        </c:otherwise>
                                    </c:choose>


                                    </c:forEach>
                            </select>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" value="<spring:message code="local.edit"/>" class="btn btn-primary">
                        <spring:message code="local.or"/>
                        <a href="" class="btn btn-default"><spring:message code="local.cancel"/></a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
</body>
</html>