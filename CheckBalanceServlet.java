package balance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class CheckBalanceServlet
 */
@WebServlet("/CheckBalanceServlet")
public class CheckBalanceServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckBalanceServlet() {
	    super();
	    // TODO Auto-generated constructor stub
	}
	
	// DBC
	private static final String DB_URL = "jdbc:oracle:thin:@fsktmdbora.upm.edu.my:1521:fsktm";
	private static final String DB_USER = "abc123";
	private static final String DB_PASSWORD = "abc123";
	
	// SQL query
	private static final String SQL_QUERY = "SELECT BALANCE FROM accounts WHERE ID = ? AND ONLINE_PIN = ?";
    
	/*
	 *--------
	 *  POST 
	 *--------
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String accountId = request.getParameter("account");
		String onlinePin = request.getParameter("pin");
		
		Double accountBalance = null;
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			PreparedStatement stmt = conn.prepareStatement(SQL_QUERY)) {
			stmt.setString(1, accountId);
			stmt.setString(2, onlinePin);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				accountBalance = rs.getDouble("balance");
			}
		} catch (SQLException e) {
			throw new ServletException("There is an error", e);
		}
		
		request.setAttribute("accountBalance", accountBalance);
		request.getRequestDispatcher("balance.jsp").forward(request, response);
	}

}
