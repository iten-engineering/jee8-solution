package ch.itenengineering.tx.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.itenengineering.tx.ejb.AppException;
import ch.itenengineering.tx.ejb.AppWithRollbackFalseException;
import ch.itenengineering.tx.ejb.AppWithRollbackTrueException;
import ch.itenengineering.tx.ejb.SysAsAppWithRollbackFalseException;
import ch.itenengineering.tx.ejb.SysAsAppWithRollbackTrueException;
import ch.itenengineering.tx.ejb.SysException;
import ch.itenengineering.tx.ejb.TxManager;

@WebServlet("/tx")
public class TxServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// init
		String title = "Transaction Test";
		List<String> messages = new ArrayList<String>();

		// tests
		try {

			// system exception
			messages.add("<b>testSysException</b>");
			this.testSysException(messages);

			messages.add("<br /><b>testSysAsAppWithRollbackTrueException</b>");
			this.testSysAsAppWithRollbackTrueException(messages);

			messages.add("<br /><b>testSysAsAppWithRollbackFalseException</b>");
			this.testSysAsAppWithRollbackFalseException(messages);

			// application exception
			messages.add("<br /><b>testAppException</b>");
			this.testAppException(messages);

			messages.add("<br /><b>testAppWithRollbackTrueException</b>");
			this.testAppWithRollbackTrueException(messages);

			messages.add("<br /><b>testAppWithRollbackFalseException</b>");
			this.testAppWithRollbackFalseException(messages);

		} catch (Exception e) {
			e.printStackTrace();
			messages.add("request failed with exception: " + e.toString());
		}

		// render response
		String html = this.renderHtml(title, messages);

		response.setContentType("text/html");
		response.getWriter().write(html);

	}

	/**
	 * Gets a new stateful transaction manager bean
	 */
	private TxManager getTxManager() throws NamingException {
		Context ctx = new InitialContext();
		return (TxManager) ctx
				.lookup("java:global/ejb-persistence/TxManagerBean");
	}

	public void testSysException(List<String> messages) throws NamingException {
		TxManager txManager = this.getTxManager();
		try {
			txManager.testSysException();
			messages.add("> Test NOK");
		} catch (SysException se) {
			messages.add("> Test OK");
		} catch (Exception e) {
			messages.add("> Test NOK");
		}
		try {
			txManager.isBeanAlive();
			messages.add("> Test NOK");
		} catch (Exception e) {
			messages.add("> Test OK");
		}
	}

	public void testSysAsAppWithRollbackTrueException(List<String> messages)
			throws NamingException {
		TxManager txManager = this.getTxManager();
		try {
			txManager.testSysAsAppWithRollbackTrueException();
			messages.add("> Test NOK");
		} catch (SysAsAppWithRollbackTrueException se) {
			messages.add("> Test OK");
		} catch (Exception e) {
			messages.add("> Test NOK");
		}
		try {
			txManager.isBeanAlive();
			messages.add("> Test OK");
		} catch (Exception e) {
			messages.add("> Test NOK");
		}
	}

	public void testSysAsAppWithRollbackFalseException(List<String> messages)
			throws NamingException {
		TxManager txManager = this.getTxManager();
		try {
			txManager.testSysAsAppWithRollbackFalseException();
			messages.add("> Test NOK");
		} catch (SysAsAppWithRollbackFalseException se) {
			messages.add("> Test OK");
		} catch (Exception e) {
			messages.add("> Test NOK");
		}
		try {
			txManager.isBeanAlive();
			messages.add("> Test OK");
		} catch (Exception e) {
			messages.add("> Test NOK");
		}
	}

	public void testAppException(List<String> messages) throws NamingException {
		TxManager txManager = this.getTxManager();
		try {
			txManager.testAppException();
			messages.add("> Test NOK");
		} catch (AppException ae) {
			messages.add("> Test OK");
		} catch (Exception e) {
			messages.add("> Test NOK");
		}
		try {
			txManager.isBeanAlive();
			messages.add("> Test OK");
		} catch (Exception e) {
			messages.add("> Test NOK");
		}
	}

	public void testAppWithRollbackTrueException(List<String> messages)
			throws NamingException {
		TxManager txManager = this.getTxManager();
		try {
			txManager.testAppWithRollbackTrueException();
			messages.add("> Test NOK");
		} catch (AppWithRollbackTrueException ae) {
			messages.add("> Test OK");
		} catch (Exception e) {
			messages.add("> Test NOK");
		}
		try {
			txManager.isBeanAlive();
			messages.add("> Test OK");
		} catch (Exception e) {
			messages.add("> Test NOK");
		}
	}

	public void testAppWithRollbackFalseException(List<String> messages)
			throws NamingException {
		TxManager txManager = this.getTxManager();
		try {
			txManager.testAppWithRollbackFalseException();
			messages.add("> Test NOK");
		} catch (AppWithRollbackFalseException ae) {
			messages.add("> Test OK");
		} catch (Exception e) {
			messages.add("> Test NOK");
		}
		try {
			txManager.isBeanAlive();
			messages.add("> Test OK");
		} catch (Exception e) {
			messages.add("> Test NOK");
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
