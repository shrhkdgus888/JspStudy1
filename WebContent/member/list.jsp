<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="manager.*, java.util.List"%>
<!--회원목록을 가져오기위한 기존 코드 반복-->
<%

	/* MemberService를 직접 생성하는 것이 아닌, getInstance를 호출함.  */
	MemberService ms = MemberService.getInstance();
	/*MemberVo를 담을 수 있는 list를 선언*/
	/* jsp에 필요한 정보를 MemberService ms를 통해 가져옴 */
	List<MemberVo> ls = ms.listAll();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>목록</title>
</head>
<body>
<h3>회원목록</h3>
<table border="1">
<!-- if(회원목록이 없으면){} -->
<%if(ls.size() == 0){%>
	<tr><td>목록이 없습니다.</td></tr>
<%} else {%>
	<tr><th>번호</th><th>아이디</th><th>닉네임</th></tr>
	<!--vo에 담고, ls를 하나하나 꺼내서  -->
	<% for(MemberVo vo : ls){%>
	<tr>
		<%-- vo에 있는 값들을 출력해줘야 되므로 <%= %>를 쓴다. --%>
		
		<td><%= vo.getNum()%></td>
		<td><a href="detail.jsp?num=<%=vo.getNum()%>"><%= vo.getMemberId()%></a></td>
		<!-- 비밀번호(vo.getMemberPw())에 대한 정보는 제외했기 때문에 넣지않음 -->
		<td><%= vo.getNickName()%></td>
	</tr>
<% 		} //for
	} //else 
%>
</table>
</body>
</html>