package payment;

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
 * Servlet implementation class PaymentServlet2
 */
@WebServlet("/PaymentServlet2")
public class PaymentServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentServlet2() {
        super();
        // TODO Auto-generated constructor stub
    }

    // DBC
 	private static final String DB_URL = "jdbc:oracle:thin:@fsktmdbora.upm.edu.my:1521:fsktm";
 	private static final String DB_USER = "abc123";
 	private static final String DB_PASSWORD = "abc123";
 	
 	// SQL query
 	private static final String SQL_QUERY_BALANCE = "SELECT BALANCE FROM accounts WHERE ID = ? AND ONLINE_PIN = ?";
 	private static final String SQL_QUERY_UPDATE = "UPDATE accounts SET BALANCE = ? WHERE ID = ? AND ONLINE_PIN = ?";
     
 	/*
 	 *--------
 	 *  POST 
 	 *--------
 	 */
 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		
 		String accountId = request.getParameter("account");
 		String onlinePin = request.getParameter("pin");
 		double amount = Double.parseDouble(request.getParameter("amount"));
 		
 		Double accountBalance = null;
 		Double newBalance = null;
 		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
 			PreparedStatement stmt = conn.prepareStatement(SQL_QUERY_BALANCE)) {
 			stmt.setString(1, accountId);
 			stmt.setString(2, onlinePin);
 			ResultSet rs = stmt.executeQuery();
 			if (rs.next()) {
 				newBalance = accountBalance;
 				accountBalance = rs.getDouble("balance");
 				if(amount>accountBalance) {
 					throw new ServletException("Not enough balance!");
 				}
 				else {
 					newBalance = accountBalance - amount;
 				}
 				PreparedStatement stmt2 = conn.prepareStatement(SQL_QUERY_UPDATE);
 				stmt2.setDouble(1, newBalance);
 		 		stmt2.setString(2, accountId);
 		 		stmt2.setString(3, onlinePin);
 		 		ResultSet rs2 = stmt2.executeQuery();
 			}
 		} catch (SQLException e) {
 			throw new ServletException("There is an error", e);
 		}
 		
 		request.setAttribute("accountBalance", newBalance);
 		request.getRequestDispatcher("balance.jsp").forward(request, response);
 	}

}
