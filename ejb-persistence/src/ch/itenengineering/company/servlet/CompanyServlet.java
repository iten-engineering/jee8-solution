package ch.itenengineering.company.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.itenengineering.company.domain.Company;
import ch.itenengineering.company.ejb.CompanyManager;

@WebServlet("/company")
public class CompanyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	CompanyManager companyManager;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// init
		String title = "Company";
		List<String> messages = new ArrayList<String>();

		// test order
		try {
			messages.add("<b>Test CRUD</b>");
			this.testCRUD(messages);

			messages.add("<br /><b>Test Version (optimistic locking)</b>");
			this.testVersion(messages);

		} catch (Exception e) {
			e.printStackTrace();
			messages.add("request failed with exception: " + e.toString());
		}

		// render response
		String html = this.renderHtml(title, messages);

		response.setContentType("text/html");
		response.getWriter().write(html);

	}

	private void testCRUD(List<String> messages) throws Exception {

		// test data
		Company c1 = new Company("BISE NOIRE", "Surfcenter Murten");
		Company c2 = new Company("Vaucher AG", "Sportgeschäft");
		Company c3 = new Company("Intersport International GmbH",
				"Wiederverkauf von Sportartikeln");

		// create
		c1 = this.companyManager.persist(c1);
		c2 = this.companyManager.persist(c2);
		c3 = this.companyManager.persist(c3);

		// read
		Company c = this.companyManager.find(c2.getCompanyId());
		messages.add("find single company:");
		messages.add(c.toString());

		List l = this.companyManager.find("%");
		messages.add("<br />find all companies:");
		addCompanies(l, messages);

		// update
		c3.setDescription("An- und Wiederverkauf von Sportartikeln");

		this.companyManager.merge(c3);

		c = this.companyManager.find(c3.getCompanyId());
		messages.add("<br />updated company:");
		messages.add(c.toString());

		// delete
		this.companyManager.remove(c1.getCompanyId());
		this.companyManager.remove(c2.getCompanyId());
		this.companyManager.remove(c3.getCompanyId());

		l = this.companyManager.find("%");
		messages.add("<br />remove all companies:");
		addCompanies(l, messages);
	}

	private void testVersion(List<String> messages) throws Exception {

		// insert test data
		Company c1 = new Company("BISE NOIRE", "Surfcenter Murten");
		int id = this.companyManager.persist(c1).getCompanyId();

		// test concurrent update
		Company cOrig = this.companyManager.find(id);
		messages.add("original company:");
		messages.add(cOrig.toString());

		Company cNew = this.companyManager.find(id);
		cNew.setDescription("Murten's Surfcenter Nr 1");

		cNew = this.companyManager.merge(cNew);

		messages.add("<br />modified company:");
		messages.add(cNew.toString());

		try {
			messages.add("<br />try to modify company with old version number:");
			this.companyManager.merge(cOrig);
			messages.add("oops, modify should fail!");

		} catch (Exception e) {
			messages.add("expected result: operation failed with: "
					+ e.getMessage());
		}

		// clean up
		this.companyManager.remove(id);
	}

	private void addCompanies(List list, List<String> messages) {
		if ((list == null) || (list.isEmpty())) {
			messages.add("no companies found (empty list)");
		} else {
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Company element = (Company) iter.next();
				messages.add(element.toString());
			}
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
