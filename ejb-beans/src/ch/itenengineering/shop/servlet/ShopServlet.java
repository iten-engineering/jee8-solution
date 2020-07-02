package ch.itenengineering.shop.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.NoSuchEJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.itenengineering.shop.domain.Item;
import ch.itenengineering.shop.ejb.Basket;

@WebServlet("/shop")
public class ShopServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// init
		String title = "Booshop";
		List<String> messages = new ArrayList<String>();

		// test order
		try {
			this.testOrder(messages);

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
	 * Gets a new stateful basket.
	 */
	private Basket getBasket() throws NamingException {
		Context ctx = new InitialContext();
		return (Basket) ctx.lookup("java:global/ejb-beans/BasketBean");
	}

	private void testOrder(List<String> messages) throws Exception {

		// add items
		Basket basketA = this.getBasket();
		Basket basketB = this.getBasket();

		basketA.addItem(new Item("0-596-00920-1", 1, 75.50));
		basketA.addItem(new Item("0-596-00920-2", 1, 25.00));
		basketA.addItem(new Item("0-596-00920-3", 3, 99.95));
		basketA.removeItem("0-596-00920-2");

		basketB.addItem(new Item("0-596-00920-4", 1, 15.20));

		// show items
		messages.add("Basket A:");
		addBasket(messages, basketA.getBasket());

		messages.add("Basket B:");
		addBasket(messages, basketB.getBasket());

		// close baskets
		basketA.order();
		try {
			basketA.getBasket();
			throw new Exception("Basket A is still alive!");
		} catch (NoSuchEJBException e) {
			messages.add("Basket A ordered and closed successfully");
		}

		basketB.cancel();
		try {
			basketB.getBasket();
			throw new Exception("Basket B is still alive!");
		} catch (NoSuchEJBException e) {
			messages.add("Basket B cancelled (and closed) successfully");
		}
	}

	private void addBasket(List<String> messages, Map<String, Item> basket) {
		for (Iterator<String> iter = basket.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			Item item = basket.get(key);
			messages.add("-" + item.toString());
		}
		messages.add("<br />");
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
