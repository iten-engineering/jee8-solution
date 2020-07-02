package ch.itenengineering.news.ejb.receiver;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import ch.itenengineering.news.domain.NewsItem;
import ch.itenengineering.news.ejb.Messaging;
import ch.itenengineering.news.ejb.store.StoreBean;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/TestJMSQueue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = Messaging.ACTION_KEY
		+ "='" + Messaging.ACTION_SEND + "'") })
public class ReceiverMDB implements MessageListener {

	@EJB
	StoreBean store;

	@Override
	public void onMessage(Message message) {
		try {
			ObjectMessage om = (ObjectMessage) message;
			NewsItem newsItem = (NewsItem) om.getObject();

			this.store.saveNewsItem(newsItem);

			System.out
					.println("ReceiverMDB successfully saved news message with id "
							+ message.getJMSMessageID());

		} catch (JMSException e) {
			System.out.println("ReceiverMDB failed with exception: "
					+ e.toString());
		}
	}

} // end
