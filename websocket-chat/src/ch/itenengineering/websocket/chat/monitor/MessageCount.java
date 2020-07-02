package ch.itenengineering.websocket.chat.monitor;

import ch.itenengineering.websocket.chat.Member;

public class MessageCount {

	private Member member;
	private int count;

	public MessageCount(Member member, int count) {
		this.member = member;
		this.count = count;
	}

	public void incCount() {
		count++;
	}

	public Member getMember() {
		return member;
	}

	public int getCount() {
		return count;
	}

} // end
