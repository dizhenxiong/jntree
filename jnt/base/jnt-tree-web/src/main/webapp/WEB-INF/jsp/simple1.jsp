
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html lang="zh-cn">
	<head>
		<title>ECOTree Simple Tree 1</title>
		<script type="text/javascript" src="<%=request.getContextPath()%>/r/tree/ECOTree.js"></script>
		<link type="text/css" rel="stylesheet" href="/r/tree/ECOTree.css" />
		<xml:namespace ns="urn:schemas-microsoft-com:vml" prefix="v"/>
		<style>v\:*{ behavior:url(#default#VML);}</style> 
		<style>
			.copy {
				font-family : "Verdana";				
				font-size : 10px;
				color : #CCCCCC;
			}
		</style>
			
		<script>
			var myTree = null;
			function CreateTree() {
				myTree = new ECOTree('myTree','myTreeContainer');							
				myTree.add(0,-1,"Apex Node");
				myTree.add(1,0,"Left Child");
				myTree.add(2,0,"${name}");
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