package ch.itenengineering.websocket.chat;

import java.io.IOException;

import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import ch.itenengineering.websocket.chat.monitor.Observable;
import ch.itenengineering.websocket.chat.monitor.Observer;

@ServerEndpoint("/monitor")
public class MonitorEndpoint implements Observer {

	@Inject
	Observable monitorSubject;

	// store session for sending the notifications to the client
	Session session;

	//
	// Endpoint
	//

	@OnOpen
	public void onOpen(Session session) throws EncodeException {
		System.out.println("Monitor session " + session.getId() + ": connection opened.");
		this.session = session;
		monitorSubject.attach(this);
	}

	@OnClose
	public void onClose(Session session) throws EncodeException {
		System.out.println("Monitor session " + session.getId() + ": connection closed.");
		monitorSubject.detach(this);
		this.session = null;
	}

	//
	// Observer
	//

	@Override
	public void notifyCreate(String id, String name, int count) {
		send(createJson("create", id, name, count));
	}

	@Override
	public void notifyUpdate(String id, int count) {
		send(createJson("update", id, null, count));
	}

	@Override
	public void notifyDelete(String id) {
		send(createJson("delete", id, null, null));
	}

	private void send(String json) {
		if (session != null && session.isOpen()) {
			try {
				session.getBasicRemote().sendText(json);

			} catch (IOException e) {
				System.out.println("sending to session " + session.getId() + " failed with exception: " + e.toString());
			}
		}
	}

	private String createJson(String action, String id, String name, Integer count) {
		// "{"action": "create", "id": "1234", "name": "Tom", "count": 1}"
		StringBuilder buf = new StringBuilder();

		buf.append("{");
		buf.append("\"action\": \"").append(action).append("\"");
		buf.append(",\"id\": \"").append(id).append("\"");
		if (name != null) {
			buf.append(",\"name\": \"").append(name).append("\"");
		}
		if (count != null) {
			buf.append(",\"count\": \"").append(count).append("\"");
		}
		buf.append("}");

		return buf.toString();
	}

} // end
