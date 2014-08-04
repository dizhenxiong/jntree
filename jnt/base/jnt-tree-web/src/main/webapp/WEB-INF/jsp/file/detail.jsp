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
    <!-- Bootstrap core CSS -->
    <link href="/r/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="/r/style.css" rel="stylesheet">
    <script type="text/javascript" src="/r/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/r/moment/moment.min.js"></script>
    <script type="text/javascript" src="/r/bootstrap2/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="/r/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
    <link rel="stylesheet"
          href="/r/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css"/>
</head>
<body>
<div class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand hidden-sm" href="/"><img
                    src="/r/img/shell.gif" alt=""/> 壳牌</a>
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
        <form role="form" action="/memory/uploadFile" method="post"   enctype="multipart/form-data" >
            <div class="row">
                <div class="form-group col-md-6">
                    <label for="">标题*</label>
                    <input name="title"  class="form-control" id="" placeholder="">
                </div>

                <div class="form-group col-md-6">
                    <label for="">材料长度*</label> <select class="form-control">
                    <option value="">Select</option>
                </select>
                </div>
            </div>
            <div class="row">

                <div class="form-group col-md-6">
                    <label for="">备注*</label> <select class="form-control">
                    <option value="">Select Material</option>
                </select>
                </div>
                <div class="form-group col-md-6">
                    <label for="">计划时间</label>

                    <div class='input-group date datetimepicker' id=''
                         data-date-format="YYYY/MM/DD">
                        <input name="firstTime" type='text' class="form-control"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-6">
                    <label for="">邮箱抄送地址</label>
                    <input type="email" name="sEmailcc" class="form-control" id="" placeholder="">
                </div>
                <div class="form-group col-md-6">
                    <label for="">截止日期*</label> <label>
                    <div class='input-group date datetimepicker' id='' data-date-format="YYYY/MM/DD">
                        <input name="deadline" type='text' class="form-control"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-6">
                    <label for="">合资公司*</label>
                    <select name="sCompany" class="form-control">
                        <option value="Intel">英特尔</option>
                    </select>
                </div>
                <div class="form-group col-md-6">
                    <label class="control-label">Job ID</label>

                    <div class="">
                        <p class="form-control-static">Job ID not set yet</p>
                    </div>
                </div>
            </div>
            <div class="row">

                <div class="form-group col-md-6">
                    <label for="">通讯类型*</label> <select class="form-control">
                    <option value="">Select Communication Type</option>
                </select>
                </div>
                <div class="form-group col-md-6">
                    <label for="">MediaHub Reference</label> <input type="email"
                                                                    class="form-control" id="" placeholder="">
                </div>

            </div>
            <div class="row">
                <div class="form-group col-md-6">
                    <label for="">壳牌地区*</label>
                    <select class="form-control" name="area">
                        <option value="">Select Area of Shell Business or
                            Function
                        </option>
                    </select>
                </div>
                <div class="form-group col-md-6">
                    <label for="exampleInputFile">上传文件</label>
                    <input type="file" name="file" id="exampleInputFile"/>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-12">
                    <lable>附言</lable>
                    <textarea class="form-control" rows="3"></textarea>

                </div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <button type="submit" class="btn btn-success">提交</button>
                    <%--<button type="button" class="btn btn-primary">审核</button>--%>
                </div>
            </div>
        </form>
    </div>
    <div class="footer">
        <p>&copy; Company 2014</p>
    </div>

</div>
<script type="text/javascript">
    $(function () {
        $('.datetimepicker').datetimepicker({
            pickTime: false
        });
    });
</script>
</body>
</html>
