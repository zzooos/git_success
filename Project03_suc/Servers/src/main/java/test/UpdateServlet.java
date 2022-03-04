package test;



import java.io.IOException;

import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;



import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;



public class UpdateServlet extends HttpServlet{

	@Override

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)

			throws ServletException, IOException {

		// 1. parameter�� ���۵� id���.

		String id=req.getParameter("id");

		

		// 2. id�� �ش��ϴ� ������ db���� ��ȸ�ؼ� ���.

		resp.setContentType("text/html;charset=utf-8");

		PrintWriter pw = resp.getWriter();

		pw.println("<html>");

		pw.println("<head></head>");

		pw.println("<body>");

		

		PreparedStatement pstmt = null;

		Connection con = null;

		ResultSet rs=null;

		try{

			// 2. ���۵� ���� db�� ����.
			System.out.println(1);
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println(2);
			String url = "jdbc:mysql://54.90.73.197:3306/jsp?serverTimezone=Asia/Seoul&useSSL=false";
			
			

			String user="Lion2";

			String password="1234";

			con = DriverManager.getConnection(url, user, password);

			
			

			String sql = "select * from members where id=?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, id);

			

			//sql���� �����ϱ�

			rs = pstmt.executeQuery();

			rs.next();

			String pwd = rs.getString("pwd");

			String email=rs.getString("email");

			String phone=rs.getString("phone");

			

			pw.println("<form method='post' action='updateok.do'>");

			pw.println("<input type='hidden' name='id' value='" + id + "'/>");

			pw.println("���̵�<input type='text' name='id' value='" + id + "' disabled='disabled'/><br/>");

			pw.println("��й�ȣ<input type='text' name='pwd' value='" + pwd + "'/><br/>");

			pw.println("email<input type='text' name='email' value='" + email + "'/><br/>");

			pw.println("phone<input type='text' name='phone' value='" + phone + "'/><br/>");

			pw.println("<input type='submit' value='����'/><br/>");

			pw.println("</form>");

			

		}catch(ClassNotFoundException ce){

			System.out.println(ce.getMessage());

		}catch(SQLException se){

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

		pw.close();

	}

}

