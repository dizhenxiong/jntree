<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


    <title>My JSP 'index.jsp' starting page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

</head>

<body>
<form action="/file/uploadFile" method="post" enctype="multipart/form-data">
    文件 <input type="file" name="file"/>
    type<input type="input" name="type" value="0"/>
    name <input type="input" name="name" id="name" value=""/>
    <input type="submit"/>
</form>
</body>
</html>
