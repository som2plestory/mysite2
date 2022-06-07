<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.javaex.vo.UserVo" %>

<%
	UserVo authUser = (UserVo)session.getAttribute("authUser");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MYSITE-일반게시판 글</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/board.css" rel="stylesheet" type="text/css">

</head>


<body>
	<div id="wrap">

		<div id="header" class="clearfix">
			<h1>
				<a href="/mysite2/main">MySite</a>
			</h1>
			
			<%if(authUser == null){%>   <!-- 로그인 실패, 로그인전 -->
				<ul>
					<li><a href="/mysite2/user?action=loginForm" class="btn_s">로그인</a></li>
					<li><a href="/mysite2/user?action=joinForm" class="btn_s">회원가입</a></li>
				</ul>
			<%}else {%> <!-- 로그인 성공 -->
				<ul>
					<li><%=authUser.getName() %>님 안녕하세요^^</li>
					<li><a href="/mysite2/user?action=logout" class="btn_s">로그아웃</a></li>
					<li><a href="/mysite2/user?action=modifyForm" class="btn_s">회원정보수정</a></li>
				</ul>
			<%}%>
			
		</div>
		<!-- //header -->

		<div id="nav">
			<ul class="clearfix">
				<li><a href="">입사지원서</a></li>
				<li><a href="/mysite2/bc">게시판</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="/mysite2/gbc">방명록</a></li>
			</ul>
		</div>
		<!-- //nav -->

		<div id="container" class="clearfix">
			<div id="aside">
				<h2>게시판</h2>
				<ul>
					<li><a href="/mysite2/bc">일반게시판</a></li>
					<li><a href="">댓글게시판</a></li>
				</ul>
			</div>
			<!-- //aside -->

			<div id="content">

				<div id="content-head">
					<h3>게시판</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>게시판</li>
							<li class="last">일반게시판</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<!-- //content-head -->
	
				<div id="board">
					<div id="read">
						<form action="#" method="get">
							<!-- 작성자 -->
							<div class="form-group">
								<span class="form-text">작성자</span>
								<span class="form-value">정우성</span>
							</div>
							
							<!-- 조회수 -->
							<div class="form-group">
								<span class="form-text">조회수</span>
								<span class="form-value">123</span>
							</div>
							
							<!-- 작성일 -->
							<div class="form-group">
								<span class="form-text">작성일</span>
								<span class="form-value">2020-03-02</span>
							</div>
							
							<!-- 제목 -->
							<div class="form-group">
								<span class="form-text">제 목</span>
								<span class="form-value">여기에는 글제목이 출력됩니다.</span>
							</div>
						
							<!-- 내용 -->
							<div id="txt-content">
								<span class="form-value" >
									여기에는 본문내용이 출력됩니다.<br>
									여기에는 본문내용이 출력됩니다.<br>
									여기에는 본문내용이 출력됩니다.<br>
									여기에는 본문내용이 출력됩니다.<br>
									여기에는 본문내용이 출력됩니다.<br>
									여기에는 본문내용이 출력됩니다.<br>
									여기에는 본문내용이 출력됩니다.<br>
									여기에는 본문내용이 출력됩니다.<br>
								</span>
							</div>
							
							<a id="btn_modify" href="">수정</a>
							<a id="btn_modify" href="">목록</a>
							
						</form>
						<!-- //form -->
					</div>
					<!-- //read -->
				</div>
				<!-- //board -->
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