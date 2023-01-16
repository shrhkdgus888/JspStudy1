<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
</head>
<body>
	<ul>
	
<!--getContextPath() : 최상위경로/멤버폴더의/ 인덱스jsp를 만든다.(=managerjsp)  -->
		<li><a href="<%= request.getContextPath()%>/member/index.jsp">멤버관리페이지</a></li>
	</ul>
</body>
</html>