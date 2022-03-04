package test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Timestamp;



import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;



public class ListServlet extends HttpServlet{

	@Override

	protected void service(HttpServletRequest resquest, HttpServletResponse response)

			throws ServletException, IOException {


		PreparedStatement pstmt=null;

		ResultSet rs = null;
		Connection con=null;
		

		response.setContentType("text/html;charset=utf-8");

		

		PrintWriter pw = response.getWriter();

		pw.println("<html>");

		pw.println("<head></head>");

		pw.println("<body>");
		
		int tableCnt = 0;
		
		resquest.setCharacterEncoding("utf-8");
		
		int curPage = Integer.parseInt(resquest.getParameter("curPage"));

		try{

			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
	
			 String url = "jdbc:mysql://54.90.73.197:3306/jsp?serverTimezone=Asia/Seoul&useSSL=false";
			String username="Lion2";
			String password="1234";
			con = DriverManager.getConnection(url, username, password);
			
			pstmt = con.prepareStatement("select count(*) as cnt from members");
			
			rs = pstmt.executeQuery();
			
	   	// DB에서 결괏괎을 받을 때, rs여기에 받아 옴.
	      // rs 첫번 째 값이 DB에서 가져온 값이 아니라, 이상한 값
	      // 
			
			while(rs.next()) {
				int rowCnt = rs.getInt(1);
				tableCnt = (rowCnt - 1) / 10 + 1;
			}
			
			pstmt.close();
			
			rs.close();
		
			String sql = "select  * from members" + " " + "ORDER BY regdate DESC LIMIT " + (curPage - 1)*10 + ", 10";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			pw.println("<div>");

			pw.println("<table border='1' width='1200'>");

			pw.println("<tr>");

			pw.println("<td>번호</td>");
			
			pw.println("<td>아이디</td>");

			pw.println("<td>비밀번호</td>");

			pw.println("<td>이메일</td>");

			pw.println("<td>성별</td>");
			
			pw.println("<td>전화번호</td>");

			pw.println("<td>등록일</td>");

			pw.println("<td>삭제</td>");

			pw.println("<td>수정</td>");

			pw.println("</tr>");
			
			int i = 0 + (curPage - 1) * 10;
			
			while(rs.next()){
				
				i += 1;

				String id = rs.getString("id");

				String pwd = rs.getString("pwd");

				String email = rs.getString("email");

				String phone = rs.getString("phone");
				
				String gender = rs.getString("gender");

				Timestamp regdate = rs.getTimestamp("regdate");

				pw.println("<tr>");
				
				pw.println("<td>" + i + "</td>");

				pw.println("<td>" + id + "</td>");

				pw.println("<td>" + pwd + "</td>");

				pw.println("<td>" + email + "</td>");
				
				pw.println("<td>" + gender + "</td>");

				pw.println("<td>" + phone + "</td>");

				pw.println("<td>" + regdate + "</td>");

				pw.println("<td><a href='delete.do?id=" + id + "'>삭제</a></td>");

				pw.println("<td><a href='update.do?id=" + id + "'>수정</a></td>");

				pw.println("</tr>");

			}

			pw.println("</table>");
			
			for (i = 1; i <= tableCnt; i++) {
				pw.println("<a href='list.do?curPage=" + i + "'>" + i + "</a>");
			}

			pw.println("</div>");

			pw.println("<a href='main.html'>메인페이지로 이동</a>");

		}catch(ClassNotFoundException ce){
			
			ce.printStackTrace();

			System.out.println(ce.getMessage());

		}catch(SQLException se){
			
			se.printStackTrace();

			System.out.println(se.getMessage());

		}finally{

			try{

				if(rs!=null) rs.close();

				if(pstmt!=null) pstmt.close();

				if(con!=null) con.close();

			}catch(SQLException se){

				System.out.println(se.getMessage());

			}

		}

		

		pw.println("</body>");

		pw.println("</html>");

	}

}

