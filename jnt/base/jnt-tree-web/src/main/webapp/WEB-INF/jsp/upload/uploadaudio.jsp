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
    <form action="UploadAudio.json" method="post" enctype="multipart/form-data">
    	文件 <input type="file" name="file" />
    	tid<input type="input" name="tid" value="1234567890" />
    	token <input type="input" name="token" id="token" value=""/>
    	friendMobile<input type="input" name="friendMobile" value="13661390023" />
    	length<input type="input" name="length" value="12" />
    	<input type="submit" />
    </form>
  </body>
</html>
