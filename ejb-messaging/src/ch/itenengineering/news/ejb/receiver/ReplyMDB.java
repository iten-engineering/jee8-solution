package ch.itenengineering.news.ejb.receiver;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import ch.itenengineering.news.domain.NewsItems;
import ch.itenengineering.news.ejb.Messaging;
import ch.itenengineering.news.ejb.store.StoreBean;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/TestJMSQueue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = Messaging.ACTION_KEY
		+ "='" + Messaging.ACTION_REQUEST + "'") })
public class ReplyMDB implements MessageListener {

	@EJB
	StoreBean store;

	@EJB
	ReplyBean reply;

	@Override
	public void onMessage(Message message) {
		try {
			NewsItems newsItems = this.store.getNewsItems();

			this.reply.send(message.getJMSMessageID(), message.getJMSReplyTo(),
					newsItems);

			System.out.println("ReplyMDB successfully replied #"
					+ newsItems.getNewsItems().size()
					+ " news items from the store");

		} catch (JMSException e) {
			System.out.println("ReplyMDB failed with exception: "
					+ e.toString());
		}
	}

} // end
