<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.javaex.vo.UserVo" %>

<%
	UserVo userVo = (UserVo)request.getAttribute("userVo");
	//System.out.println(userVo);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MYSITE-회원정보수정</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="wrap">

		<div id="header" class="clearfix">
			<h1>
				<a href="/mysite2/main">MySite</a>
			</h1>

			<ul>
				<li><%=userVo.getName() %>님 안녕하세요^^</li>
				<li><a href="/mysite2/user?action=logout" class="btn_s">로그아웃</a></li>
				<li><a href="/mysite2/user?action=modifyForm" class="btn_s">회원정보수정</a></li>
			</ul>
			
		</div>
		<!-- //header -->

		<div id="nav">
			<ul class="clearfix">
				<li><a href="">입사지원서</a></li>
				<li><a href="/mysite2/bc">게시판</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="/mysite2/guestbook">방명록</a></li>
			</ul>
		</div>
		<!-- //nav -->

		<div id="container" class="clearfix">
			<div id="aside">
				<h2>회원</h2>
				<ul>
					<li><a href="/mysite2/user?action=modifyForm">회원정보수정</a></li>
					<li><a href="/mysite2/user?action=loginForm">로그인</a></li>
					<li><a href="/mysite2/user?action=joinForm">회원가입</a></li>
				</ul>
			</div>
			<!-- //aside -->

			<div id="content">
			
				<div id="content-head">
					<h3>회원정보</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>회원</li>
							<li class="last">회원정보</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				 <!-- //content-head -->
	
				<div id="user">
					<div id="modifyForm">
						<form action="/mysite2/user" method="get">
							<input type="hidden" name="action" value="modify">
							
							<!-- 아이디 -->
							<div class="form-group">
								<label class="form-text" for="input-uid">아이디</label> 
								<span class="text-large bold"> <%=userVo.getId() %> </span>
							</div>
	
							<!-- 비밀번호 -->
							<div class="form-group">
								<label class="form-text" for="input-pass">패스워드</label> 
								<input type="password" id="input-pass" name="password" value="<%=userVo.getPassword() %>" placeholder=""	>
							</div>
	
							<!-- 이름 -->
							<div class="form-group">
								<label class="form-text" for="input-name">이름</label> 
								<input type="text" id="input-name" name="name" value="<%=userVo.getName() %>" placeholder="">
							</div>
	
							<!-- 성별 -->
							<div class="form-group">
								<span class="form-text">성별</span> 
								
								<%if(userVo.getGender().equals("male")){%>
									<label for="rdo-male">남</label> 
									<input type="radio" id="rdo-male" name="gender" value="male" checked="checked"> 
									
									<label for="rdo-female">여</label> 
								    <input type="radio" id="rdo-female" name="gender" value="female" > 
								<%}else if(userVo.getGender().equals("female")){%>
									<label for="rdo-male">남</label> 
									<input type="radio" id="rdo-male" name="gender" value="male"> 
									
									<label for="rdo-female">여</label> 
								    <input type="radio" id="rdo-female" name="gender" value="female" checked="checked"> 
								<%}%> 
	
							</div>
	
							<!-- 버튼영역 -->
							<div class="button-area">
								<button type="submit" id="btn-submit">회원정보수정</button>
							</div>
							
						</form>
					
					
					</div>
					<!-- //modifyForm -->
				</div>
				<!-- //user -->
			</div>
			<!-- //content  -->

		</div>
		<!-- //container  -->

		<div id="footer">
			Copyright ⓒ 2022 이지희. All right reserved
		</div>
		<!-- //footer -->
		
	</div>
	<!-- //wrap -->

</body>

</html>