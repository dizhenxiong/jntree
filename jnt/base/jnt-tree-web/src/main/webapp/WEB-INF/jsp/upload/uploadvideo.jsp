<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <form action="UploadVideo.json" method="post" enctype="multipart/form-data">
    	文件 <input type="file" name="video" /><br>
    	type<input type="input" name="type" value="0" /><br>
    	name<input type="input" name="videoName" value="" /><br>    	    	
    	token <input type="input" name="token" value="" /><br>
    	md5<input type="input" name="md5" value="" /><br>
    	params:<input type="input" name="params" value="" /><br>
    	totalPiece:<input type="input" name="totalPiece" value="1" /><br>
    	currentPiece:<input type="input" name="currentPiece" value="1" /><br>
    	<input type="submit" />
    </form>
  </body>
</html>
