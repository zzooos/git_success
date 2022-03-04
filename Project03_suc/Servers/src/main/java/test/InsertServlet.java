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

import java.sql.SQLException;



import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;


public class InsertServlet extends HttpServlet{

	@Override

	protected void service(HttpServletRequest request, HttpServletResponse response)


			throws ServletException, IOException {
		// 1. 파라미터로 전송된 값을 얻어오기.

		request.setCharacterEncoding("euc-kr");

		String id = request.getParameter("id");

		String pwd= request.getParameter("pwd");
		String pwd2 = request.getParameter("pwd2");

		String gender= request.getParameter("gender");

		String em1 = request.getParameter("em1");
		String em2 = request.getParameter("em2");

		String hp =  request.getParameter("hp");
		String hp2 = request.getParameter("hp2");
		String hp3 =  request.getParameter("hp3");
		// string에서 적용이 되는데 null 자를게 없어 => exception  생김



		int n=0;

		PreparedStatement pstmt = null;//

		Connection con = null;

		String nullValue = "id";

		boolean errorCheck = false;

		try{
			if (id.isBlank()) {
				nullValue = "아이디";
				n = -3;
				errorCheck = true;
			} else if (pwd.isBlank()) {
				nullValue = "비밀번호";
				n = -3;
				errorCheck = true;
			} else if (em1.isBlank()) {
				nullValue = "이메일";
				n = -3;
				errorCheck = true;
			} else if (em2.isBlank()) {
				nullValue = "이메일";
				n = -3;
				errorCheck = true;
			} else if (hp2.isBlank()) {
				nullValue = "핸드폰 번호";
				n = -3;
				errorCheck = true;
			} else if (hp3.isBlank()) {
				nullValue = "핸드폰 번호";
				n = -3;
				errorCheck = true;
			}

			if ((!pwd.equals(pwd2))){
				n = -1;
				errorCheck = true;
			}

			id = id.trim();
			pwd = pwd.trim();
			pwd2 = pwd2.trim();
			gender = gender.trim();
			em1 = em1.trim();
			em2 = em2.trim();
			hp2 = hp2.trim();
			hp3 = hp3.trim();

			String em = em1 + "@" + em2;
			String phone = hp + "-" + hp2 + "-" + hp3; 
			// 2. 전송된 값을 db에 저장.

			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://54.90.73.197:3306/jsp?serverTimezone=Asia/Seoul&useSSL=false";

			String username="Lion2";
			String password="1234";
			con = DriverManager.getConnection(url, username, password);
			String sql = "insert into members values( ?,?,?,?,?, now())";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, id);

			pstmt.setString(2, pwd);

			pstmt.setString(3, em);

			pstmt.setString(4, phone);

			pstmt.setString(5, gender);



			//sql구문 실행하기
			if (!errorCheck) {
				n = pstmt.executeUpdate();//
			}

		}catch(ClassNotFoundException ce){
			ce.printStackTrace();
			System.out.println(ce.getMessage());

		}catch(SQLException se){
			if (se.getMessage().toString().equals("Duplicate entry 'asdf' for key 'members.PRIMARY'")) {
				n = -2;
			}

		}catch(Exception e) {
			n = -3;
			e.printStackTrace();
		}finally{

			try{

				if(pstmt!=null) pstmt.close();

				if(con!=null) con.close();

			} catch(SQLException se){

				System.out.println(se.getMessage());

			}

		}


		// 3. 사용자(클라이언트)에 결과를 응답하기.

		response.setContentType("text/html;charset=utf-8");

		PrintWriter pw = response.getWriter();

		pw.println("<html>");

		pw.println("<head></head>");

		pw.println("<body>");

		System.out.println("error number: " + n);

		// n > 0;
		if(n >= 0){
			pw.println("<p><img src=\"hi.gif\"></p>");
			pw.println( id + "님! 성공적으로 가입되었습니다.<br/>");
			pw.println(
					"<button type=\"button\" onclick=\"location.href='main.html' \">메인화면</button>"
					);

		} else if (n == -1) {
			pw.println("비밀번호와 비밀번호 확인이 다릅니다. <br/>");
			pw.println("<a href='javascript:history.go(-1)'>이전페이지로 가기</a>");
		} else if (n == -2) {
			pw.println("이미 존재하는 아이디입니다. <br/>");
			pw.println("<a href='javascript:history.go(-1)'>이전페이지로 가기</a>");
		} else if (n == -3) {
			pw.println(nullValue + "을(를) 입력하지 않았습니다. <br/>");
			pw.println("<a href='javascript:history.go(-1)'>이전페이지로 가기</a>");
		}else {
			pw.println("오류로 인해 가입에 실패했습니다.<br/>");
			pw.println("<a href='javascript:history.go(-1)'>이전페이지로 가기</a>");
		}

		pw.println("</body>");

		pw.println("</html>");

	}

}