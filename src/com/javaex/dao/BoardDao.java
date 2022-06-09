package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	
	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
		
	//게시글 리스트
	public List<BoardVo> getBoardList() {
		// 리스트로 만들기
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		getConnection();
		
		try {
			// 3. SQL문  준비  / 바인딩  / 실행 
			// SQL문 준비
			String query = "";
			query += " select b.no, ";
			query += " 		  title, ";
			query += " 		  name, ";
			query += "   	  hit, ";
			query += "   	  reg_date, ";
			query += "   	  user_no ";
			query += " from	board b, users u ";
			query += " where b.user_no = u.no ";
			query += " order by b.no desc ";
			
			// 바인딩
			pstmt = conn.prepareStatement(query);
			
			//ResultSet 가져오기
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			//반복문으로 Vo 만들기	List에 추가하기
			while(rs.next()) {
				int no =  rs.getInt("no");
				String title = rs.getString("title");
				String name = rs.getString("name");
				int hit =  rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo =  rs.getInt("user_no");
				
				BoardVo boardVo = new BoardVo(no, title, hit, regDate, userNo);
				boardVo.setName(name);
				
				boardList.add(boardVo);
			}
		} catch (SQLException e) { 
			System.out.println("error:" + e);
		} 
		close();
		return boardList;
	}
	
	
	//게시글 삭제
	public int boardDelete(BoardVo boardVo) {
		int count = -1;
		this.getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " delete from board ";
			query += " where no = ? ";
			query += " and user_no = ? ";
			
			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardVo.getNo());
			pstmt.setInt(2, boardVo.getUserNo());
			
			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("["+count + "건이 삭제되었습니다.]");
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();
		
		return count;
		
	}
	
	
	//게시글 읽기(글 한개 조회)
	public BoardVo getBoard(int no) {
		BoardVo boardVo = null;
		this.getConnection();
		try {
			// 3. SQL문  준비  / 바인딩  / 실행 
			// SQL문 준비
			String query = "";
			query += " select b.no, ";
			query += " 		  title, ";
			query += " 		  content, ";
			query += " 		  name, ";
			query += "   	  hit, ";
			query += "   	  reg_date, ";
			query += "   	  user_no ";
			query += " from	board b, users u ";
			query += " where b.user_no = u.no ";
			query += " and b.no = ? ";
			
			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			//System.out.println(query);
			
			//ResultSet 가져오기
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			//반복문으로 Vo 만들기	List에 추가하기
			while(rs.next()) {
				int boardNo =  rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String name = rs.getString("name");
				int hit =  rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo =  rs.getInt("user_no");
				
				content = content.replace("\r\n","<br>");
				
				boardVo = new BoardVo(boardNo, title, content, hit, regDate, userNo);
				boardVo.setName(name);
				
				//System.out.println(boardVo);
			}
		} catch (SQLException e) { 
			System.out.println("error:" + e);
		} 
		close();
		return boardVo;
	}
	
	
	//다른 사람의 글을 읽었을 때 조회수 1 상승
	//게시글 읽기(글 한개 조회 - 조회수 상승)
	public int hitUp(BoardVo boardVo) {
		int count = -1 ;
		this.getConnection();
		try {
			// 3. SQL문  준비  / 바인딩  / 실행 
			// SQL문 준비
			String query = "";
			query += " update board ";
			query += " set hit = ? ";
			query += " where no = ? ";
			
			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardVo.getHit()+1);	//조회수 1 상승
			pstmt.setInt(2, boardVo.getNo());
			//System.out.println(query);
			
			//ResultSet 가져오기
			count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println("[게시글 "+boardVo.getNo()+"의 조회수가"+count + " 상승되었습니다.]");
			
		} catch (SQLException e) { 
			System.out.println("error:" + e);
		} 
		close();
		return count;
	}
	
	
	
	//게시글 작성
	public int boardWrite(BoardVo boardVo) {
		int count = -1;
		this.getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " insert into board "; //(no, title, content, hit, reg_date, userNo)
			query += " values (seq_board_no.nextval, ?, ?, 0, sysdate, ? ) ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUserNo());

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("["+count + "건 등록되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		return count;
	}
	
	
	//게시글 수정
	public int boardModify(BoardVo boardVo) {
		int count = -1;
		getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " update board "; 
			query += " set  title = ?, ";
			query += " 		content = ? ";
			query += " where no = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getNo());

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("["+count + "건 수정되었습니다.]");
		
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		return count;
	}
	
	
	//게시글 작성자 확인(수정되는지)
	public BoardVo checkWriter(int no) {
		BoardVo boardVo = null;
		this.getConnection();
		try {
			// 3. SQL문  준비  / 바인딩  / 실행 
			// SQL문 준비
			String query = "";
			query += " select no, ";
			query += "   	  user_no ";
			query += " from	board ";
			query += " where no = ? ";
			
			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			//ResultSet 가져오기
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			//반복문으로 Vo 만들기	List에 추가하기
			while(rs.next()) {
				int boardNo =  rs.getInt("no");
				int userNo =  rs.getInt("user_no");
				
				boardVo = new BoardVo();
				boardVo.setNo(boardNo);
				boardVo.setUserNo(userNo);
				
			}
		} catch (SQLException e) { 
			System.out.println("error:" + e);
		} 
		close();
		return boardVo;
	}

}
