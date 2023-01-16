<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="manager.*"%>
<%
	//회원정보 하나 가져오기.
	MemberService memberService = MemberService.getInstance();
	int num = Integer.parseInt(request.getParameter("num"));
	MemberVo vo = memberService.read(num);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정</title>
</head>
<body>

<h3>회원정보 수정하기</h3>
<form method="post" action="<%= request.getContextPath() %>/member/modify.jsp">
	<!-- num에 대한 정보가 데이터에 하나로 묶여있어서 불러오긴 해야되지만, 딱히 필요는 없어서 hidden처리 -->
	<input type="hidden" name="num" value="<%= vo.getNum() %>">
	<!-- disabled는 수정하지못하게 블럭처리 -->
	<input type="text" name="memberId" value="<%= vo.getMemberId() %>" placeholder="아이디" disabled><br>
	<input type="password" name="memberPwOld" value="" placeholder="기존비밀번호"><br>
	<input type="password" name="memberPwNew" value="" placeholder="새로운비밀번호"><br>
	<input type="text" name="nickName" value="<%= vo.getNickName() %>" placeholder="닉네임"><br>
<!-- 	등록버튼을 누르면 DB에 들어가도록 만든다.(요청하는 곳은 regist.jsp)  -->
	<input type="submit" value="수정">
</form>
</body>
</html>

