
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
  

    <title>Signin Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="/r/bootstrap3/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/r/sign.css" rel="stylesheet">


    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="container">

    <form action="/memory/login" method="post"   class="form-signin" role="form">
        <h2 class="form-signin-heading">欢迎登陆</h2>
        <input name="name"      type="text" class="form-control" placeholder="用户名" required autofocus>
        <input name="password"  type="password" class="form-control" placeholder="密码" required>
        <label class="checkbox">
            <input type="checkbox" value="remember-me"> 记住我
        </label>
        <button class="btn btn-lg btn-primary btn-block" type="submit">登 录</button>
    </form>

</div> <!-- /container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
</body>
</html>
