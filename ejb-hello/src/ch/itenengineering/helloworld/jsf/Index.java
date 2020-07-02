package ch.itenengineering.helloworld.jsf;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ch.itenengineering.helloworld.ejb.HelloWorldRemote;

@Named
@RequestScoped
public class Index implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	HelloWorldRemote remote;

	public Index() {
		super();
	}

	public String getGreeting() {
		return remote.helloWorld("Hello EJB from JSF");
	}

} // end
