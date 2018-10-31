package org.devocative.dexample.akka.ask;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;

public class CheckerActor extends AbstractLoggingActor {

	@Override
	public Receive createReceive() {
		return receiveBuilder()
			.match(CheckUserMsg.class, msg -> {
				log().info("CheckerActor: msg={}", msg);
				if (msg.getUser().getUsername().equals("B")) {
					throw new RuntimeException("Oops!");
				}
				getSender().tell(new WhiteUserMsg(msg.getUser()), getSelf());
			})
			.build();
	}

	// ------------------------------

	public static Props create() {
		return Props.create(CheckerActor.class);
	}

	// ------------------------------

	public static class CheckUserMsg {
		private User user;

		public CheckUserMsg(User user) {
			this.user = user;
		}

		public User getUser() {
			return user;
		}

		@Override
		public String toString() {
			return "CheckUserMsg{" +
				"user=" + user +
				'}';
		}
	}

	public static class WhiteUserMsg {
		private User user;

		public WhiteUserMsg(User user) {
			this.user = user;
		}

		public User getUser() {
			return user;
		}

		@Override
		public String toString() {
			return "WhiteUserMsg{" +
				"user=" + user +
				'}';
		}
	}
}
