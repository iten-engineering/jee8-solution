package ch.itenengineering.env.ejb;

import javax.annotation.Resource;
import javax.ejb.Stateless;

@Stateless
public class EnvServiceBean implements EnvService {

	/**
	 * injected by the container after the creation of the bean and before any
	 * method calls
	 */
	@Resource(name = "MessageA")
	private String messageA;

	/**
	 * injection defined within the xml deployment descriptor (ejb-jar.xml)
	 */
	private String messageB;

	public String getMessages() {
		return messageA + " " + messageB;
	}

} // end
