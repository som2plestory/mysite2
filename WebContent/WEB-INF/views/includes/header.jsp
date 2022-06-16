<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		<div id="header" class="clearfix">
			<h1>
				<a href="/mysite2/main">MySite</a>
			</h1>
			<!-- 로그인 실패, 로그인전 -->
			<!-- 로그인 성공 -->
			<c:choose>
				<c:when test="${empty authUser}">
					<ul>
						<li><a href="/mysite2/user?action=loginForm" class="btn_s">로그인</a></li>
						<li><a href="/mysite2/user?action=joinForm" class="btn_s">회원가입</a></li>
					</ul>
				</c:when>
				<c:otherwise>
					<ul>
						<li>${authUser.name}님 안녕하세요^^</li>
						<li><a href="/mysite2/user?action=logout" class="btn_s">로그아웃</a></li>
						<li><a href="/mysite2/user?action=modifyForm" class="btn_s">회원정보수정</a></li>
					</ul>
				</c:otherwise>
			</c:choose>
				
		</div>
		<!-- //header -->

</body>
</html>