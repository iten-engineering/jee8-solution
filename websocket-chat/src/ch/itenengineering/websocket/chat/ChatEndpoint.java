package ch.itenengineering.websocket.chat;

import java.io.IOException;

import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import ch.itenengineering.websocket.chat.monitor.Monitor;
import ch.itenengineering.websocket.chat.msg.Message;
import ch.itenengineering.websocket.chat.msg.MessageDecoder;
import ch.itenengineering.websocket.chat.msg.MessageEncoder;

@ServerEndpoint(value = "/chat", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ChatEndpoint {

	@Inject
	Monitor monitor;

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("Session " + session.getId() + ": connection opened.");
		monitor.start(session.getId());
	}

	@OnMessage
	public void onMessage(Session session, Message message) throws EncodeException {
		System.out.println("Session " + session.getId() + ": " + message.getMember().getName() + " sent message: " + message.getContent());
		monitor.message(session.getId(), message);

		try {
			for (Session sess : session.getOpenSessions()) {
				if (sess.isOpen()) {
					sess.getBasicRemote().sendObject(message);
				}
			}
		} catch (IOException e) {
			System.out.println("Session " + session.getId() + ": failed with exception: " + e.toString());
		}
	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("Session " + session.getId() + ": connection closed.");
		monitor.stop(session.getId());
	}

} // end
