<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="manager.*"%>

<!-- 닉네임같은경우 한글이 들어올수 있으니까 Utf-8로 설정 -->
<% request.setCharacterEncoding("utf-8"); %>
<!-- Bean객체 생성 / MemberVo vo = new MemberVo();와 같은 뜻 -->
<jsp:useBean id="vo" class="manager.MemberVo"/>
<!-- useBean 액션태그로 생성한 자바빈 객체에 대해서 프로퍼티(필드)에 값을 설정 -->
<!-- property 속성에 * 를 사용하면 프로퍼티와 동일한 이름의 파라미터를 이용하여 setter 메서드를 생성한 모든 프로퍼티(필드)에 대해 값을 설정 -->
<%-- <jsp:setProperty="memberId" name="vo"/>
<jsp:setProperty="memberPw" name="vo"/>
<jsp:setProperty="nickName" name="vo"/>
위의 동일한 내용 --%>
<jsp:setProperty name="vo" property="*"/>
<% 
	MemberService memberService = MemberService.getInstance();
 	/* memberService를 이용하여, 정상적으로 등록(regist)이가 되었냐? 됬다면  */
	if(memberService.regist(vo)){
 		/* 정상적으로 등록이 됬다면 응답을 Redirect(다시요청)해라 index.jsp로 */
		response.sendRedirect(request.getContextPath()+"/member/index.jsp");
	}else{
 		/* 정상적으로 등록이 X, 응답을 Redirect(다시요청)해라 registForm.jsp로 */
		response.sendRedirect(request.getContextPath()+"/member/registForm.jsp");
	
	}
		
%>
