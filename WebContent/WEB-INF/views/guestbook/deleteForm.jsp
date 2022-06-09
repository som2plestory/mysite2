<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MYSITE-방명록 삭제</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="wrap">

		<!-- header -->
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<!-- //header -->

		<c:import url="/WEB-INF/views/includes/nav.jsp"></c:import>
		<!-- //nav -->

		<div id="container" class="clearfix">
			
			<c:import url="/WEB-INF/views/includes/aside_guestbook.jsp"></c:import>
			<!-- //aside -->

			<div id="content">
			
				<div id="content-head">
					<h3>일반방명록</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>방명록</li>
							<li class="last">일반방명록</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<!-- //content-head -->
	
				<div id="guestbook">
					<form action="/mysite2/guestbook" method="post">
						<table id="guestInfo">
							<colgroup>
								<col style="width: 10%;">
								<col style="width: 70%;">
								<col style="width: 20%;">
							</colgroup>
							<tbody>
								<tr>
									<td>${wrGuestVo.no}</td>
									<td>${wrGuestVo.name}</td>
									<td class="text-center">${wrGuestVo.regDate}</td>
								</tr>
								<tr>
									<td colspan=3 class="guestDeleteContent">
										${wrGuestVo.content}
									</td>
								</tr>
							</tbody>
						</table>
					
						<table id="guestDelete">
							<colgroup>
								<col style="width: 10%;">
								<col style="width: 40%;">
								<col style="width: 25%;">
								<col style="width: 25%;">
							</colgroup>
							<tbody>
								<tr>
									<td class="text-center"><label for="input-upassword">비밀번호</label></td>
									<td class="text-center"><input type="password" id="input-upassword" name="password" value=""></td>
									<td class="text-center"><button type="submit">삭제</button></td>
									<td class="text-center"><a href="/mysite2/guestbook">[목록으로 돌아가기]</a></td>
								</tr>
							</tbody>
						</table>
						<input type="hidden" name="no" value="${Param.no}"><br>
						<input type="hidden" name="action" value="delete">
					</form>
					
				</div>
				<!-- //guestbook -->
			</div>
			<!-- //content  -->

		</div>
		<!-- //container  -->
		
		<!-- footer -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		<!-- //footer -->

	</div>
	<!-- //wrap -->

</body>
</html>