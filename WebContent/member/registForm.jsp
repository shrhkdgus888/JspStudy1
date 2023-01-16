<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>등록</title>
</head>
<body>
<f:view>
<h3>회원 등록 하기</h3>
<form method="post" action="<%= request.getContextPath() %>/member/regist.jsp">
	<input type="text" name="memberId" value="" placeholder="아이디"><br>
	<input type="password" name="memberPw" value="" placeholder="비밀번호"><br>
	<input type="text" name="nickName" value="" placeholder="닉네임"><br>
<!-- 	등록버튼을 누르면 DB에 들어가도록 만든다.(요청하는 곳은 regist.jsp)  -->
	<input type="submit" value="등록">

</form>

</f:view>
</body>
</html>