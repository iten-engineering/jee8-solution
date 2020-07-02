package ch.itenengineering.news.ejb.sender;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import ch.itenengineering.news.domain.NewsItems;
import ch.itenengineering.news.ejb.Messaging;


@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class RequesterBean {

	@Resource(name = "java:/jms/TestConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(name = "java:/jms/TestJMSQueue")
	private Destination destination;

	@Resource(name = "java:/jms/TestJMSReplyQueue")
	private Destination replyDestination;

	/**
	 * Send a request to get all news.
	 * 
	 * <p>
	 * Notes:
	 * <ul>
	 * <li>
	 * The request is not within a transaction. This way the send will deliver the message immediately.<br />
	 * Otherwise the send would not deliver the request until the end of the transaction and the receiving of
	 * the reply would never work out!</li>
	 * <li>
	 * The request message (and also the response) have the time to life set. <br />
	 * This way the messages are cleaned up properly in case we are runnning in a timeout during the receive
	 * of the reply.</li>
	 * <li>
	 * Since we are not using a temporary queue for the reply, the listener must set a message selector on the
	 * 'JMSCorrelationID' field. This is set by the reply MDB with the messageID of the request.<br />
	 * This way, each requester will receive only the replies he asked for!</li>
	 * </ul>
	 * </p>
	 * 
	 * @return The news items or null in case of a timeout.
	 * 
	 * @throws JMSException
	 *             The request failed.
	 */
	public NewsItems request() throws JMSException {

		Connection connection = null;

		try {
			// init
			connection = this.connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// create request
			Message m = session.createMessage();
			m.setStringProperty(Messaging.ACTION_KEY, Messaging.ACTION_REQUEST);
			m.setJMSReplyTo(replyDestination);

			// send request
			MessageProducer sender = session.createProducer(this.destination);
			sender.setTimeToLive(Messaging.TIME_TO_LIVE);
			sender.send(m);
			
			
			// create consumer with message selector for the given message id
			String jmsMessageId = m.getJMSMessageID();
			String messageSelector = "JMSCorrelationID = '" + jmsMessageId + "'";
			MessageConsumer consumer = session.createConsumer(replyDestination, messageSelector);

			// start listening
			connection.start();
			Message message = consumer.receive(Messaging.TIME_TO_WAIT);

			if (message == null) {
				return null;
			}

			// get result
			ObjectMessage om = (ObjectMessage) message;
			return (NewsItems) om.getObject();

		} finally {
			this.closeConnection(connection);
		}

	}

	/**
	 * Close the connection and all related JMS resources.
	 */
	private void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (JMSException e) {
				System.out.println("RequesterBean failed to close the connection with exception: "
						+ e.toString());
			}
		}
	}

} // end
