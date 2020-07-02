package ch.itenengineering.websocket.chat.msg;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {

	@Override
	public void init(EndpointConfig arg0) {
		// nothing to do
	}

	/**
	 * Create String in the Format: "[Member Name]: [Message Content]".
	 */
	@Override
	public String encode(Message message) throws EncodeException {

		StringBuilder buf = new StringBuilder();

		buf.append(message.getMember().getName());
		buf.append(MessageDecoder.CHAT_MESSAGE_DELIMITER);
		buf.append(message.getContent());

		return buf.toString();
	}

	@Override
	public void destroy() {
		// nothing to do
	}

} // end