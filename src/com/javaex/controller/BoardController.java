package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet{
	//필드 
	private static final long serialVersionUID = 1L;
	
	//get방식으로 요청시 호출 메소드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//포스트 방식일때 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		//action파라미터 꺼내기
		String action = request.getParameter("action");
		System.out.println(action);
		
		
		if ("read".equals(action)) {
			System.out.println("BoardController>read");
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getBoard(no);
			System.out.println(boardVo);
			
			//no 글이 없을 때
			if(boardVo == null) {
				//리다이렉트
				WebUtil.redirect(request, response, "/mysite2/board");
				
			}else {
				//포워드
				request.setAttribute("boardVo", boardVo);
				WebUtil.forward(request, response, "WEB-INF/views/board/read.jsp");
			}
			
		}else if("delete".equals(action)) {
			System.out.println("BoardController>delete");
			
			HttpSession session = request.getSession(false);
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			//System.out.println(authUser);
			
			if(authUser != null) {
				
				int no = Integer.parseInt(request.getParameter("no"));
				BoardDao boardDao = new BoardDao();
				
				int userNo = authUser.getNo();
				
				BoardVo boardVo = new BoardVo();
				boardVo.setNo(no);
				boardVo.setUserNo(userNo);
				System.out.println(boardVo);
				
				//로그인한 사용자(userNo)가 작성한글(no)이 맞을 때 삭제
				boardDao.boardDelete(boardVo);
			}
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite2/board");
			
		}else if("remove".equals(action)) {
			System.out.println("BoardController>remove");
			
			
		}else if("write".equals(action)) {
			System.out.println("BoardController>write");
			
			int userNo = Integer.parseInt(request.getParameter("user"));
			BoardDao boardDao = new BoardDao();
			
		}else {//리스트
			System.out.println("BoardController>list");
			
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getBoardList(); 
			
			request.setAttribute("boardList", boardList);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
			rd.forward(request, response);
		}
	}
	
	
	//post방식으로 요청시 호출 메소드
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("여기는 post");
		
		doGet(request, response);
	}
}
