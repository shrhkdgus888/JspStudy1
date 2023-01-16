<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="manager.*"%>

	<!--  요청한 정보를 한글이 들어올수 있게끔 utf-8로 셋팅 -->
<% request.setCharacterEncoding("utf-8"); %>
<jsp:useBean id="modifyRequest" class="manager.ModifyRequest" />
<jsp:setProperty name="modifyRequest" property="*" />
	
<% 	
	MemberService memberService = MemberService.getInstance();

	MemberVo vo = new MemberVo(
		modifyRequest.getNum(),
		modifyRequest.getMemberId(),
		modifyRequest.getMemberPwNew(),
		modifyRequest.getNickName());
	
	/* 생성된 vo객체를 memberService를 이용하여, edit함. */
	/* if(수정에 성공했다면),위에 가져온 modifyRequest.getNum()값을 이용하여 detail.jsp에 요청한다. */
	   /* → "/member/detail.jsp?num" + modifyRequest.getNum()) 다시 detail로 해당 번호가 수정된 정보를 확인해볼수 있게끔 요청하는것 */
	if(memberService.edit(vo, modifyRequest.getMemberPwOld())){
		response.sendRedirect(request.getContextPath() + "/member/detail.jsp?num=" + modifyRequest.getNum());
	}else{
		/* 수정이 안됐다= 비밀번호가 맞지않다→ 다시입력하도록 modifyForm으로 리다이렉트 해준다. */
		response.sendRedirect(request.getContextPath()+ "/member/modifyForm.jsp?num=" + modifyRequest.getNum());
	}
%>