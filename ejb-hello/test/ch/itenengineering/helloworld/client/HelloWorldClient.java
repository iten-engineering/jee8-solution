package ch.itenengineering.helloworld.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.itenengineering.helloworld.ejb.HelloWorldRemote;

public class HelloWorldClient {

	public static Context getInitialContext() throws NamingException {

		Properties env = new Properties();
		
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jboss.naming.remote.client.InitialContextFactory");
		env.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
		env.put("jboss.naming.client.ejb.context", true);

		return new InitialContext(env);
	}

	public static void main(String[] args) {
		try {
			// get initial context
			Context ctx = getInitialContext();

			// get object reference
			HelloWorldRemote helloWorld = (HelloWorldRemote) ctx
					.lookup("ejb-hello/HelloWorldBean!ch.itenengineering.helloworld.ejb.HelloWorldRemote");
			
			// invoke bean method
			String echo = helloWorld
					.helloWorld("Hello EJB from standalone Client");
			System.out.println("\n" + echo);

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

} // end
