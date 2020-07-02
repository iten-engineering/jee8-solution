package ch.itenengineering.bank.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.itenengineering.bank.ejb.AccountManager;

@WebServlet("/bank")
public class BankServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// init
		String title = "Bank Account";
		List<String> messages = new ArrayList<String>();

		// test order
		try {
			messages.add("<b>Test 1</b>");
			this.testAccount(messages);

			messages.add("<br /><b>Test 2</b>");
			this.testAccount2(messages);

		} catch (Exception e) {
			messages.add("request failed with exception: " + e.toString());
		}

		// render response
		String html = this.renderHtml(title, messages);

		response.setContentType("text/html");
		response.getWriter().write(html);

	}

	/**
	 * Gets a new stateful account manager session.
	 */
	private AccountManager getAccountManager() throws NamingException {
		Context ctx = new InitialContext();
		return (AccountManager) ctx.lookup("java:global/ejb-persistence/AccountManagerBean");
	}
	
	private void testAccount(List<String> messages) throws Exception {

		AccountManager am = this.getAccountManager();

		am.open(7);
		messages.add("open account: 7");

		am.setOwner("James Bond");
		messages.add("set owner: James Bond");

		// check database: data will be persistet after each end of transaction
		// scope, i.e. after each function call

		am.deposit(100.0);
		messages.add("deposit: 100.0");

		messages.add("get balance: " + am.getBalance());

		am.close();
		messages.add("close account");
	}

	private void testAccount2(List<String> messages) throws Exception {

		AccountManager am = this.getAccountManager();

		am.open(7);
		messages.add("open existing account: 7");

		Double amount = am.getBalance();
		messages.add("get balance: " + amount);

		try {
			Double amount2 = amount * 2;
			messages.add("try to withdraw: " + amount2);
			am.withdraw(amount2);
		} catch (EJBException e) {
			// transaction is rolled back, and bean destroyed
			messages.add(e.toString());
		}

	}

	private String renderHtml(String title, List<String> messages) {
		StringBuilder html = new StringBuilder();

		// begin
		html.append("<html>");

		// head
		html.append("<head><title>");
		html.append(title);
		html.append("</title></head>");

		// body
		html.append("<body>");

		html.append("<h1>");
		html.append(title);
		html.append("</h1>");

		for (String message : messages) {
			html.append(message);
			html.append("<br />");
		}

		html.append("</body>");

		// end
		html.append("</html>");

		return html.toString();
	}

} // end
