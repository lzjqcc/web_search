
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/23 0023
  Time: 9:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path=request.getContextPath();
%>
<html>
<head>
    <title>用户登录</title>
    <link href="<%=path%>/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <style>
        .mainCss{
            width: 800px;
            height:500px;
            margin-top: 100px;
            margin-left: 300px;
        }
    </style>
</head>
<body>
<div class="mainCss">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">用户登录</h3>
        </div>
        <div class="panel-body">

            <form class="form-horizontal" role="form">
                <div class="form-group">
                    <label for="firstname" class="col-sm-2 control-label">帐号</label>
                    <div class="col-sm-6">
                        <input type="text" name="name" class="form-control" id="firstname" placeholder="请输入帐号">
                    </div>
                </div>
                <div class="form-group">
                    <label for="lastname" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-6">
                        <input type="password" name="password" class="form-control" id="lastname" placeholder="请输入密码">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <div class="checkbox">
                            <label>
                                <a href="<%=path%>/register/register.jsp" >注册帐号</a>
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">登录</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
