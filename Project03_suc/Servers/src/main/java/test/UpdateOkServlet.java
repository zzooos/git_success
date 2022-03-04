package test;



import java.io.IOException;

import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.SQLException;



import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;



public class UpdateOkServlet extends HttpServlet{

	@Override

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)

			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");

		String id=req.getParameter("id");

		String pwd=req.getParameter("pwd");

		String email=req.getParameter("email");

		String phone=req.getParameter("phone");

		

		PreparedStatement pstmt = null;

		Connection con = null;

		int n=0;

		try{

			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			 String url = "jdbc:mysql://54.90.73.197:3306/jsp?serverTimezone=Asia/Seoul&useSSL=false";
			String username="Lion2";
			String password="1234";
			con = DriverManager.getConnection(url, username, password);

			String sql = "update members set pwd=?,email=?,phone=? where id=?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, pwd);

			pstmt.setString(2, email);

			pstmt.setString(3, phone);

			pstmt.setString(4, id);

			

			n = pstmt.executeUpdate();

			

			if(n>0){
				resp.sendRedirect("list.do?"+"curPage=1"); //cur

			}else{

				PrintWriter pw = resp.getWriter();

				pw.println("<html><head></head>");

				pw.println("<body>½ÇÆÐ</body>");

				pw.println("</heal>");

				pw.close();

			}

			

		}catch(ClassNotFoundException ce){

			System.out.println(ce.getMessage());

		}catch(SQLException se){

			System.out.println(se.getMessage());

		}

	}

}