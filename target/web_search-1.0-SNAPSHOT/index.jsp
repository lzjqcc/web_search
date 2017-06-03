<%@page import="java.util.List" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String path = request.getContextPath();
    %>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="<%=path %>/css/bootstrap.min.css"/>
    <title>站内检索</title>
    <style>

        .mainCSS {
            width: 800px;
            height: 100px;
            margin-top: 100px;
            margin-left: 300px;
        }

        .contentCSS {
            width: 800px;
            height: 80px;
            margin-top: 20px;
            margin-left: 300px;

        }
    </style>
</head>
<body>
<div class="mainCSS">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">站内搜索</h3>
        </div>
        <div class="panel-body">
            <form class="form-inline" role="form" name="form1" action="<%=path%>/searchAction" method="post">
                <div class="form-group">
                    <label class="sr-only" for="name">搜索</label>
                    <input type="text" size="70" class="form-control" id="name" name="weburl" placeholder="">&nbsp;&nbsp;&nbsp;
                    <button type="submit" class="btn btn-default">搜&nbsp;&nbsp;索</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="contentCSS">
    <c:if test="${list!=null}">
        <c:forEach items="${list}" var="entity">
            ${entity.url}</br>
        </c:forEach>
    </c:if>
</div>
</body>
</html>