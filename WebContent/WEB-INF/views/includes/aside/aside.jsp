<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="aside">
		<c:if test="${controller.equals('user')}">
			<h2>회원</h2>
			<ul>
				<li><a href="/mysite2/user?action=modifyForm">회원정보수정</a></li>
				<li><a href="/mysite2/user?action=loginForm">로그인</a></li>
				<li><a href="/mysite2/user?action=joinForm">회원가입</a></li>
			</ul>
		</c:if>
		<c:if test="${controller.equals('guestbook')}">
			<h2>방명록</h2>
			<ul>
				<li><a href="/mysite2/guestbook">일반방명록</a></li>
				<li>ajax방명록</li>
			</ul>
		</c:if>
		<c:if test="${controller.equals('board')}">
			<h2>게시판</h2>
			<ul>
				<li><a href="/mysite2/board">일반게시판</a></li>
				<li><a href="">댓글게시판</a></li>
			</ul>
		</c:if>
		
			
	</div>
	<!-- //aside -->

</body>
</html>