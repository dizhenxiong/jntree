<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<title>Product Detail Table</title>

<title>My JSP 'index.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">




<!-- Bootstrap core CSS -->
<link href="/r/bootstrap3/css/bootstrap.min.css" rel="stylesheet">


<!-- Custom styles for this template -->
<link href="/r/style.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->


<script type="text/javascript" src="/r/jquery/jquery.min.js"></script>
<script type="text/javascript" src="/r/moment/moment.min.js"></script>
<script type="text/javascript" src="/r/bootstrap2/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="/r/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet"
	href="/r/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css" />

</head>

<body>
	<div class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand hidden-sm" href="/"><img
					src="/r/img/shell.gif" alt="" /> 壳牌</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
                    <li><a type="button" href="/" class="btn">退出</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="container">

		<div class="row marketing">
			<div class="row">
				<div class="col-md-12">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>序号</th>
								<th>文件名称</th>
								<th>姓名</th>
								<th>公司</th>
								<th>上传时间</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>壳牌发展一期规划</td>
								<td>康杨</td>
								<td>搜狐</td>
								<td>2014-05-15 10:22:11</td>
								<td>未通过</td>
								<td><a type="button" class="btn btn-default btn-xs"
									href="/memory/detail">查看</a></td>
							</tr>

						</tbody>
					</table>
				</div>
			</div>
		</div>

		<div class="footer">
			<p>&copy; Company 2014</p>
		</div>


	</div>
	<!-- /container -->


	<!-- Bootstrap core JavaScript
================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
</body>
</html>
