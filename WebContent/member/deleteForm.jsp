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
<title>삭제하기</title>
</head>
<body>
<h3>회원삭제</h3>
번호 : <%= vo.getNum() %><br>
아이디: <%= vo.getMemberId() %><br>
<!-- delete.jsp로 요청한다. -->
<form action="delete.jsp">
	해당 회원을 삭제하시겠습니까?<br>
	<!-- hidden을 사용하는 이유는 몇번의 회원인지? 알려주고 싶어서 사용함 --> 
	<!-- name="num"값이 있어야 vo.getNum()에 들어갈수있다.
	ex) 소스 <input type="hidden" value="17" name="num"> num이라는 파라미터로 17값이 넘어갈 수 있다 18:12-->
	<input type="hidden" value="<%=vo.getNum() %>" name="num">
	<input type="submit" value="삭제">
	<a href="<%=request.getContextPath() %>/member/detail.jsp?num=<%=vo.getNum()%>">취소</a>
	
</form>
</body>
</html>