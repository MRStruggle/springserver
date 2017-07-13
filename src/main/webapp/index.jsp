<%@ page language="java" contentType="text/html; charset=utf-8"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<script type="text/javascript">
var url ;
	
	
	var url = encodeURI("http://127.0.0.1:11034/serverapi/hello/downloadAttachment?attachmentName=" + encodeURI("nihao.doc"));//屏幕快照 2017-07-05 下午3.33.28.png
	
	
	function dow(){
		location.href =url;
	}
</script>
<body>
<h2>Hello World!</h2>

<a  href="javascript:void(0)" onclick="dow();return false">下载</a>

</body>
</html>
