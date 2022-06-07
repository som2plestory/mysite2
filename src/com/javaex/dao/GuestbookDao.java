package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;
import com.javaex.vo.UserVo;

public class GuestbookDao {
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
		
		//방명록 리스트
		public List<GuestbookVo> getGuestList() {
			// 리스트로 만들기
			List<GuestbookVo> guestList = new ArrayList<GuestbookVo>();
			getConnection();
			
			try {
				// 3. SQL문  준비  / 바인딩  / 실행 
				// SQL문 준비
				String query = "";
				query += " select no, ";
				query += " 		  name, ";
				query += " 		  password, ";
				query += "   	  content, ";
				query += "   	  reg_date ";
				query += " from	guestbook ";
				query += " order by no asc ";
				
				// 바인딩
				pstmt = conn.prepareStatement(query);
				
				//ResultSet 가져오기
				rs = pstmt.executeQuery();
				
				// 4.결과처리
				//반복문으로 Vo 만들기	List에 추가하기
				while(rs.next()) {
					int no =  rs.getInt("no");
					String name = rs.getString("name");
					String password = rs.getString("password");
					String content = rs.getString("content");
					String regDate = rs.getString("reg_date");
					
					GuestbookVo guestVo = new GuestbookVo(no, name, password, content, regDate);
					
					guestList.add(guestVo);
				}
			} catch (SQLException e) { 
				System.out.println("error:" + e);
			} 
			close();
			return guestList;
		}
		
		//방명록 등록
		public int insert(GuestbookVo guestbookVo) {
			int count = 0;

			this.getConnection();

			try {

				// 3. SQL문 준비 / 바인딩 / 실행
				// SQL문 준비
				String query = "";
				query += " insert into guestbook (no, name, password, content, reg_date) ";
				query += " values (seq_guestbook_no.nextval, ?, ?, ?, sysdate) ";

				// 바인딩
				pstmt = conn.prepareStatement(query);

				pstmt.setString(1, guestbookVo.getName());
				pstmt.setString(2, guestbookVo.getPassword());
				pstmt.setString(3, guestbookVo.getContent());

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
		

		//방명록 삭제
		public int delete(GuestbookVo guestVo) {
			int count = 0;

			this.getConnection();

			try {

				// 3. SQL문 준비 / 바인딩 / 실행
				// SQL문 준비
				String query = "";
				query += " delete from guestbook ";
				query += " where no= ?  ";
				query += " and password= ?  ";

				// 바인딩
				pstmt = conn.prepareStatement(query);

				// 실행
				pstmt.setInt(1, guestVo.getNo());
				pstmt.setString(2, guestVo.getPassword());

				count = pstmt.executeUpdate();

				// 4.결과처리
				System.out.println("["+count + "건 삭제되었습니다.]");

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

			this.close();

			return count;
		}
		
		
		//방명록 작성자 정보 가져오기(비밀번호 확인용)
		public GuestbookVo checkGuest(GuestbookVo guestVo) {
			GuestbookVo wrGuest = null;
			
			this.getConnection();
			
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				//SQL문 준비
				String query = ""; // 쿼리문 문자열만들기, ? 주의
				query += " select no, ";
				query += " 		  password ";
				query += " from guestbook ";
				query += " where no = ? ";
				query += " and password = ? ";
				
				//바인딩
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, guestVo.getNo()); 
				pstmt.setString(2, guestVo.getPassword()); 
				
				//실행
				rs = pstmt.executeQuery(); // 쿼리문 실행

				// 4.결과처리
				while(rs.next()) {
					int guestNo = rs.getInt("no");
					String password = rs.getString("password");
					
					wrGuest = new GuestbookVo(guestNo, password);
					System.out.println("wrGuest: "+wrGuest);
				}

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			this.close();
			
			return wrGuest;
		}
		
		//방명록 정보 가져오기(방명록 삭제 폼- 삭제할 방명록 정보 확인) no name password content reg_date
		public GuestbookVo getGuest(int no) {
			GuestbookVo wrGuestVo = null;
			
			this.getConnection();
			
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				//SQL문 준비
				String query = ""; // 쿼리문 문자열만들기, ? 주의
				query += " select no, ";
				query += " 		  name, ";
				query += " 		  password, ";
				query += " 		  content, ";
				query += " 		  reg_date ";
				query += " from guestbook ";
				query += " where no = ? ";
				//System.out.println(query);
				
				//바인딩
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, no); 
				
				//실행
				rs = pstmt.executeQuery(); // 쿼리문 실행

				// 4.결과처리
				while(rs.next()) {
					int guestNo = rs.getInt("no");
					String name = rs.getString("name");
					String password = rs.getString("password");
					String content = rs.getString("content");
					String regDate = rs.getString("reg_date");
					
					wrGuestVo = new GuestbookVo(guestNo, name, password, content, regDate);
					System.out.println("wrGuestVo: "+wrGuestVo);
				}

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			this.close();
			
			return wrGuestVo;
			
		}
}
