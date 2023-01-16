<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="manager.*"%>
<!-- 응답을 통해 Redirection시키는 것이 기능이기 때문에, html이 따로 없음 -->

<%
	/* 전체 객체생성 */
	MemberService memberService = MemberService.getInstance();
	int num = Integer.parseInt(request.getParameter("num"));
	/* num(해당번호의 회원)을 삭제하는기능*/
	/*Service클래스 내부의 삭제기능(remove) */
	if(memberService.remove(num)){
		response.sendRedirect(request.getContextPath() + "/member/list.jsp");
	}else{
		response.sendRedirect(request.getContextPath() + "/member/detail.jsp?num=" + num);
	}
%>