package ch.itenengineering.websocket.chat.msg;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import ch.itenengineering.websocket.chat.Member;

public class MessageDecoder implements Decoder.Text<Message> {

	public static final String CHAT_MESSAGE_DELIMITER = ": ";
	public static final int CHAT_MESSAGE_MIN_LEN = CHAT_MESSAGE_DELIMITER.length() + 2;

	@Override
	public void init(EndpointConfig cfg) {
		// nothing to do
	}

	/**
	 * Decode String Format "[Member Name]: [Message Content]" to Message class.
	 */
	@Override
	public Message decode(String chatMessage) throws DecodeException {

		String[] buf = chatMessage.split(CHAT_MESSAGE_DELIMITER);

		String name = buf[0].trim();
		String content = buf[1].trim();

		return new Message(new Member(name), content);

	}

	@Override
	public boolean willDecode(String chatMessage) {

		if (chatMessage == null || chatMessage.isEmpty()) {
			return false;
		}

		if (!chatMessage.contains(CHAT_MESSAGE_DELIMITER)) {
			return false;
		}

		if (chatMessage.length() < CHAT_MESSAGE_MIN_LEN) {
			return false;
		}

		return true;
	}

	@Override
	public void destroy() {
		// nothing to do
	}

} // end
