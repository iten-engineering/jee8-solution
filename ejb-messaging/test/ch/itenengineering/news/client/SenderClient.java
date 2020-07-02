package ch.itenengineering.news.client;

import java.io.Serializable;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.itenengineering.news.domain.NewsItem;
import ch.itenengineering.news.ejb.Messaging;

public class SenderClient {

	public static final String JNDI_NAME_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";

	public static final String JNDI_NAME_QUEUE = "jms/RemoteTestJMSQueue";

	public static final String USER = "jmsuser";

	public static final String PW = "jmspw";

	public static Context getInitialContext() throws NamingException {

		Properties env = new Properties();

		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jboss.naming.remote.client.InitialContextFactory");

		env.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");

		env.put(Context.SECURITY_PRINCIPAL, USER);

		env.put(Context.SECURITY_CREDENTIALS, PW);

		return new InitialContext(env);
	}

	public void send(String agency, String news) throws NamingException,
			JMSException {

		NewsItem newsItem = new NewsItem(agency, news);

		Context ctx = getInitialContext();

		ConnectionFactory connectionFactory = (ConnectionFactory) ctx
				.lookup(JNDI_NAME_CONNECTION_FACTORY);
		
		Destination destination = (Destination) ctx.lookup(JNDI_NAME_QUEUE);

		try (Connection connection = connectionFactory.createConnection(USER,
				PW)) {

			Session session = connection.createSession();

			MessageProducer messageProducer = session
					.createProducer(destination);

			ObjectMessage om = session
					.createObjectMessage((Serializable) newsItem);

			om.setStringProperty(Messaging.ACTION_KEY, Messaging.ACTION_SEND);

			messageProducer.send(om);

			System.out
					.println("Sending news from client successfully done with "
							+ om.getJMSMessageID());

		}
	}

	public static void main(String[] args) {
		try {
			SenderClient client = new SenderClient();

			client.send("dpa",
					"Sven Busch (41) ist neuer Sportchef der Deutschen Presse-Agentur (dpa).");

			client.send("AFP", "Tornado hits cuba.");

			client.send("AP",
					"G8 pledges 60 billion dollars to fight disease in Africa");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
