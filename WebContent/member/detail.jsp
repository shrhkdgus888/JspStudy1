<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="manager.*"%>
<%
/*getParameter의 값은 list.jsp 내부 a태그의 vo.getNum()부분  */
	String n = request.getParameter("num");
	int num = 0;
	if(n == null){
/* 		* 예외처리 개념)
		값이 없다면(번호가 제대로 전달이 안됬으면) 목록보여주는 창으로 다시 보여주도록, 
		즉 사용자가 url부분에 임의적으로 번호를 적어 들어가는 것을 방지하기 위해 */
		response.sendRedirect("list.jsp");
		
	}else{
		num = Integer.parseInt(n);
		MemberService memberService = MemberService.getInstance();
		/* 해당번호(num값)의 정보를 가져와서 vo에 담고, 출력해준다. */
		MemberVo vo = memberService.read(num);
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보</title>
</head>
<body>
	<%if(vo == null){%>
	회원정보가 존재하지 않습니다.
	<%}else{ %>
	번호: <%= vo.getNum() %><br>
	아이디: <%= vo.getMemberId() %><br>
	닉네임: <%= vo.getNickName() %><br>
	등록일: <%= vo.getRegdate() %><br>
	<a href="modifyForm.jsp?num=<%=vo.getNum()%>"><button>수정</button></a>
	<a href="deleteForm.jsp?num=<%=vo.getNum()%>"><button>삭제</button></a>
	<% } %>
</body>
</html>
<% } %>