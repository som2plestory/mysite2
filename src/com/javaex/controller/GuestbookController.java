package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;


@WebServlet("/guestbook")
public class GuestbookController extends HttpServlet{
	
	//필드 
	private static final long serialVersionUID = 1L;
	
	//get방식으로 요청시 호출 메소드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//포스트 방식일때 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		//페이지이름 명명
		request.setAttribute("aside", "guestbook");
		
		//action파라미터 꺼내기
		String action = request.getParameter("action");
		System.out.println(action);
		
		if ("add".equals(action)) { //방명록작성
			System.out.println("GuestbookController>add");
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");

			GuestbookDao guestDao = new GuestbookDao();
			GuestbookVo guestVo = new GuestbookVo(name, password, content);
			System.out.println(guestVo);

			//table에서 password : not null로 설정해둠  --> 비밀번호 입력 없이 작성 불가
			System.out.println("방명록 작성 성공");
			guestDao.insert(guestVo);

			WebUtil.redirect(request, response, "/mysite2/guestbook");
			
		} else if ("deleteForm".equals(action)) { //방명록삭제폼
			System.out.println("GuestbookController>deleteForm");
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			//no로 사용자 정보 가져오기
			GuestbookDao guestDao = new GuestbookDao(); 
			GuestbookVo wrGuestVo = guestDao.getGuest(no); // no name content
			System.out.println("wrGuestVo: "+wrGuestVo);
			
			if(wrGuestVo == null) {
				WebUtil.redirect(request, response, "/mysite2/guestbook");
				
			}else {
				//request의 attrubute에 guestVo를 넣어서 포워드
				request.setAttribute("wrGuestVo", wrGuestVo);
				WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
				System.out.println(wrGuestVo);
			}
			
		} else if ("delete".equals(action)) { //방명록삭제
			System.out.println("GuestbookController>delete");
			
			//파라미터 꺼내기
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			
			//vo
			GuestbookVo guestVo = new GuestbookVo(no, password);
			
			//dao
			GuestbookDao guestDao = new GuestbookDao();
			
			//비밀번호 매칭 확인용 vo - no로 매칭 확인용 비밀번호 가져오기 
			GuestbookVo wrGuestVo = guestDao.checkGuest(guestVo);
		
			//wrGuest : not null(입력된 비밀번호 == 저장된 비밀번호) --> 삭제 성공
			//wrGuest : null(입력된 비밀번호 != 저장된 비밀번호) --> 삭제 실패
			if(wrGuestVo != null) {
				guestDao.delete(guestVo);
				request.removeAttribute("wrGuestVo");
				System.out.println("삭제 성공");
				
				//리다이렉트
				WebUtil.redirect(request, response, "/mysite2/guestbook");
				
			}else {
				System.out.println("삭제 실패");
				guestVo = guestDao.getGuest(no);
				request.setAttribute("wrGuestVo", guestVo);
				//포워드
				WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			}
		

			
		} else {//리스트
			
			System.out.println("GuestbookController>addList");
			
			GuestbookDao guestDao = new GuestbookDao();
			List<GuestbookVo> guestList = guestDao.getGuestList();
			
			request.setAttribute("guestList", guestList);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/addList.jsp");
			rd.forward(request, response);
		}

	}

	
	//post방식으로 요청시 호출 메소드
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("여기는 post");
		
		doGet(request, response);
	}

}