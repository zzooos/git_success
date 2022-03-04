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



public class DeleteServlet extends HttpServlet{

	@Override

	protected void service(HttpServletRequest request, HttpServletResponse response)



			throws ServletException, IOException {

		Connection con = null;

		PreparedStatement pstmt=null;

		int n=0;

		

		request.setCharacterEncoding("utf-8");

		String id=request.getParameter("id");

		response.setContentType("text/html;charset=utf-8");

		PrintWriter pw = response.getWriter();

		

		try{

			Class.forName("com.mysql.cj.jdbc.Driver");

			String url ="jdbc:mysql://54.90.73.197:3306/jsp?serverTimezone=Asia/Seoul&useSSL=false";
			

			String user="Lion2";

			String password="1234";

			con = DriverManager.getConnection(url, user, password);

			String sql = "delete from members where id=?";
			

			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, id);
			System.out.println(id);
			n= pstmt.executeUpdate();

			

		}catch(ClassNotFoundException ce){

			System.out.println(ce.getMessage());

		}catch(SQLException se){

			System.out.println(se.getMessage());

		}finally{

			try{

				if(pstmt!=null) pstmt.close();

				if(con!=null) con.close();

			}catch(SQLException se){

				System.out.println(se.getMessage());

			}

		}

		if(n>0){

			response.sendRedirect("list.do?"+"curPage=1");//�赥�ϰ� �ٽ� ������ ������(�� url�κ��� , �ٽ� �����ض� )
					

		}else{

			pw.println("<html>");

			pw.println("<head></head>");

			pw.println("<body>");

			pw.println("ȸ�������� �����߽��ϴ�. ");

			pw.println("<a href='javascript:history.go(-1)'>������������ ����</a>");

			pw.println("</body>");

			pw.println("</html>");

			pw.close();

		}

	}

}

