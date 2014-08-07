<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="zh-cn">
<head>
    <title>ECOTree Simple Tree 1</title>
    <script type="text/javascript" src="/r/tree/ECOTree.js"></script>
    <link type="text/css" rel="stylesheet" href="/r/tree/ECOTree.css"/>
    <xml:namespace ns="urn:schemas-microsoft-com:vml" prefix="v"/>
    <style>v\:* {
        behavior: url(#default#VML);
    }</style>
    <style>
        .copy {
            font-family: "Verdana";
            font-size: 10px;
            color: #CCCCCC;
        }
    </style>

    <script>
        var myTree = null;
        function CreateTree() {
            myTree = new ECOTree('${tree.name}', 'myTreeContainer');
            <c:forEach items="${tree.jntTreeInfoLs}" var="treeInfo">
            myTree.add(${treeInfo.nodeId}, ${treeInfo.parentId}, '${treeInfo.nodeName}'');
            </c:forEach>
            myTree.UpdateTree();
        }
    </script>
</head>
<body onload="CreateTree();">
<h4>ECOTree Simple Tree 1&nbsp;<span class="copy">&copy;2006 Emilio Cortegoso Lobato</span></h4>

<div id="myTreeContainer">
</div>
${name}
</body>
</html>