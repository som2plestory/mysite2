package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet(value = "/user", name = "user")
public class UserController extends HttpServlet{

	//필드 
	private static final long serialVersionUID = 1L;
	
	//메소드 일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//포스트 방식일때 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		//페이지이름 명명
		request.setAttribute("controller", "user");
		
		//action을 꺼낸다
		String action = request.getParameter("action");
		//System.out.println(action);
		
		if("joinForm".equals(action)) {//회원가입폼
			System.out.println("UserController>joinForm");
			
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			//회원가입 폼 포워드
			WebUtil.forward(request, response, "WEB-INF/views/user/joinForm.jsp");
		
		}else if("join".equals(action)){//회원가입
			System.out.println("UserController>join");
			
			//파라미터 꺼내기*4
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//System.out.println(id);
			//System.out.println(password);
			//System.out.println(name);
			//System.out.println(gender);
			
			//ox333= Vo만들기
			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo);
			
			//id 또는 비밀번호를 입력하지 않았을 때
			if(userVo.getId().equals("") || userVo.getPassword().equals("")) {
				System.out.println("회원가입 실패");
				WebUtil.forward(request, response, "WEB-INF/views/user/joinForm.jsp");
				
			}else {
			
				//Dao를 이용해서 저장하기
				UserDao userDao = new UserDao();
				userDao.insert(userVo);
				
				//포워드
				WebUtil.forward(request, response, "WEB-INF/views/user/joinOk.jsp");
			}
			
		}else if("loginForm".equals(action)) {//로그인 폼
			System.out.println("UserController>loginForm");
			
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			//로그인 폼 포워드
			WebUtil.forward(request, response, "WEB-INF/views/user/loginForm.jsp");
		
		}else if("login".equals(action)) {//로그인
			System.out.println("UserController>login");
			
			//파라미터 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			
			//System.out.println(id);
			//System.out.println(password);
			
			//Vo만들기
			UserVo userVo = new UserVo(id, password);
			userVo.setId(id);
			userVo.setPassword(password);
			
			//dao
			UserDao userDao = new UserDao();
			UserVo authUser = userDao.getUser(userVo); //id, password --> user 전체
			
			//authUser 주소값이 있으면 --> 로그인 성공
			//authUser null이면 --> 로그인 실패
			if(authUser == null ) {
				System.out.println("로그인 실패");
				
				//로그인 폼 포워드
				WebUtil.forward(request, response, "WEB-INF/views/user/loginForm.jsp");
				
			}else {
				System.out.println("로그인 성공");
				//로그인 한 자의 공간을 얻고 싶어
				//session - attribute
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser);
				
				//메인 리다이렉트
				WebUtil.redirect(request, response, "/mysite2/main");
			}
		
		}else if("logout".equals(action)) {//로그아웃
			System.out.println("UserController>logout");
			
			//세션값을 지운다
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			//메인으로 리다이렉트
			WebUtil.redirect(request, response, "/mysite2/main");
			
		}else if("modifyForm".equals(action)) {//회원정보수정폼
			System.out.println("UserController>modifyForm");
			
			//세션에서 사용자 정보 가져오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			//세션에서 불러온 정보가 없을 때
			if(authUser == null) {
				System.out.println("로그인이 되어있지않음");
				
				//로그인 폼 포워드
				WebUtil.forward(request, response, "WEB-INF/views/user/loginForm.jsp");
				
			//로그인이 되어있을 때
			}else {
			
				//로그인한 사용자의 no 값을 가져오기
				int no = authUser.getNo();
				
				//no로 사용자 정보 가져오기
				UserDao userDao = new UserDao();
				UserVo userVo = userDao.getUser(no);	//no id password name gender
				System.out.println(userVo);
			
				//request의 attrubute에 userVo를 넣어서 포워드
				request.setAttribute("userVo", userVo);
				WebUtil.forward(request, response, "WEB-INF/views/user/modifyForm.jsp");
			}
			
		}else if("modify".equals(action)){//회원정보수정
			System.out.println("UserController>modify");
			
			
			//세션에서 사용자 정보 가져오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			System.out.println(authUser);
			
			// no 가져오기
			int no = authUser.getNo();
			
			//파라미터*3
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			
			//묶기 : 세션에서 가져온 no + 받은 파라미터*3
			UserVo userVo = new UserVo();
			userVo.setNo(no);	//세션에서 가져온 no
			userVo.setPassword(password);	// ↓받은 파라미터*3
			userVo.setName(name);
			userVo.setGender(gender);
			System.out.println(userVo);
			
			//비밀번호가 없을 때
			if(userVo.getPassword().equals("")) {
				System.out.println("회원정보 수정 실패");
				UserDao userDao = new UserDao();
				userVo = userDao.getUser(no);
				request.setAttribute("userVo", userVo);
				WebUtil.forward(request, response, "WEB-INF/views/user/modifyForm.jsp");
				
			}else {
				System.out.println("회원정보 수정 성공");
			
				//dao 사용
				UserDao userDao = new UserDao();
				int count = userDao.update(userVo);	
				
				//포워드
				WebUtil.forward(request, response, "WEB-INF/views/user/modifyOk.jsp");
			}
			
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
		

}
