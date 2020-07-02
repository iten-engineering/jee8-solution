package ch.itenengineering.websocket.chat.monitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import ch.itenengineering.websocket.chat.msg.Message;

@ApplicationScoped
public class MonitorSubject implements Monitor, Observable {

	private Map<String, MessageCount> messageCounts = Collections.synchronizedMap(new HashMap<>());
	private List<Observer> observers = Collections.synchronizedList(new ArrayList<>());

	//
	// Monitor chat
	//
	@Override
	public void start(String sessionId) {
		System.out.println("Start monitoring session: " + sessionId);
	}

	@Override
	public void message(String sessionId, Message message) {
		System.out.println("Monitoring session: " + sessionId + ", received message from member " + message.getMember().getName());

		if (!messageCounts.containsKey(sessionId)) {
			// add new message count entry
			MessageCount mc = new MessageCount(message.getMember(), 1);
			messageCounts.put(sessionId, mc);

			// notify create
			notifyCreate(sessionId, mc.getMember().getName(), mc.getCount());

		} else {
			// increment existing message count entry
			MessageCount mc = messageCounts.get(sessionId);
			mc.incCount();

			// notify update
			notifyUpdate(sessionId, mc.getCount());
		}
	}

	@Override
	public void stop(String sessionId) {
		System.out.println("Stop monitoring session: " + sessionId);
		if (messageCounts.containsKey(sessionId)) {
			// remove message count entry
			messageCounts.remove(sessionId);

			// notify delete
			notifyDelete(sessionId);
		}
	}

	//
	// Observable registration
	//
	@Override
	public void attach(Observer observer) {
		observers.add(observer);

		// notify new observer about all existing message counts
		for (String id : messageCounts.keySet()) {
			MessageCount mc = messageCounts.get(id);
			observer.notifyCreate(id, mc.getMember().getName(), mc.getCount());
		}
	}

	@Override
	public void detach(Observer observer) {
		observers.remove(observer);
	}

	//
	// Notify observers
	//
	private void notifyCreate(String id, String name, int count) {
		for (Observer observer : observers) {
			observer.notifyCreate(id, name, count);
		}
	}

	private void notifyUpdate(String id, int count) {
		for (Observer observer : observers) {
			observer.notifyUpdate(id, count);
		}
	}

	private void notifyDelete(String id) {
		for (Observer observer : observers) {
			observer.notifyDelete(id);
		}
	}

} // end
