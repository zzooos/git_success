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
		// 1. �Ķ���ͷ� ���۵� ���� ������.

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
		// string���� ������ �Ǵµ� null �ڸ��� ���� => exception  ����



		int n=0;

		PreparedStatement pstmt = null;//

		Connection con = null;

		String nullValue = "id";

		boolean errorCheck = false;

		try{
			if (id.isBlank()) {
				nullValue = "���̵�";
				n = -3;
				errorCheck = true;
			} else if (pwd.isBlank()) {
				nullValue = "��й�ȣ";
				n = -3;
				errorCheck = true;
			} else if (em1.isBlank()) {
				nullValue = "�̸���";
				n = -3;
				errorCheck = true;
			} else if (em2.isBlank()) {
				nullValue = "�̸���";
				n = -3;
				errorCheck = true;
			} else if (hp2.isBlank()) {
				nullValue = "�ڵ��� ��ȣ";
				n = -3;
				errorCheck = true;
			} else if (hp3.isBlank()) {
				nullValue = "�ڵ��� ��ȣ";
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
			// 2. ���۵� ���� db�� ����.

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



			//sql���� �����ϱ�
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


		// 3. �����(Ŭ���̾�Ʈ)�� ����� �����ϱ�.

		response.setContentType("text/html;charset=utf-8");

		PrintWriter pw = response.getWriter();

		pw.println("<html>");

		pw.println("<head></head>");

		pw.println("<body>");

		System.out.println("error number: " + n);

		// n > 0;
		if(n >= 0){
			pw.println("<p><img src=\"hi.gif\"></p>");
			pw.println( id + "��! ���������� ���ԵǾ����ϴ�.<br/>");
			pw.println(
					"<button type=\"button\" onclick=\"location.href='main.html' \">����ȭ��</button>"
					);

		} else if (n == -1) {
			pw.println("��й�ȣ�� ��й�ȣ Ȯ���� �ٸ��ϴ�. <br/>");
			pw.println("<a href='javascript:history.go(-1)'>������������ ����</a>");
		} else if (n == -2) {
			pw.println("�̹� �����ϴ� ���̵��Դϴ�. <br/>");
			pw.println("<a href='javascript:history.go(-1)'>������������ ����</a>");
		} else if (n == -3) {
			pw.println(nullValue + "��(��) �Է����� �ʾҽ��ϴ�. <br/>");
			pw.println("<a href='javascript:history.go(-1)'>������������ ����</a>");
		}else {
			pw.println("������ ���� ���Կ� �����߽��ϴ�.<br/>");
			pw.println("<a href='javascript:history.go(-1)'>������������ ����</a>");
		}

		pw.println("</body>");

		pw.println("</html>");

	}

}