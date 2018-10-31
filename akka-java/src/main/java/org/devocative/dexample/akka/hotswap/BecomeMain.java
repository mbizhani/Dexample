package org.devocative.dexample.akka.hotswap;

import akka.actor.AbstractActorWithStash;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class BecomeMain {
	public static void main(String[] args) throws InterruptedException {
		final ActorSystem system = ActorSystem.create("Become");

		final ActorRef ref = system.actorOf(UserStorageActor.create(), "UserStorage");

		ref.tell(new OperationMsg("A"), ActorRef.noSender());
		ref.tell(new OperationMsg("B"), ActorRef.noSender());
		ref.tell(new ConnectMsg(), ActorRef.noSender());
		ref.tell(new OperationMsg("C"), ActorRef.noSender());
		ref.tell(new DisconnectMsg(), ActorRef.noSender());

		ref.tell(new OperationMsg("D"), ActorRef.noSender());
		ref.tell(new DisconnectMsg(), ActorRef.noSender());

		Thread.sleep(1000);

		system.terminate();
	}

	// ------------------------------

	public static class UserStorageActor extends AbstractActorWithStash {
		private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

		private Receive connected, disconnected;

		// TIP: following code must be in constructor not in preStart() (Note: createReceive() must not return null!)
		public UserStorageActor() {
			connected = receiveBuilder()
				.match(DisconnectMsg.class, msg -> {
					log.info("connected: Receive DisconnectMsg");

					getContext().unbecome(); // TIP: equal to getContext().become(disconnected);
				})
				.match(OperationMsg.class, msg -> {
					log.info("connected: Receive OperationMsg = {}", msg.opr);
				})
				.build();

			// TIP: the order of match call is important! First 'matchAny' results to absorb all messages!
			disconnected = receiveBuilder()
				.match(ConnectMsg.class, msg -> {
					log.info("disconnected: Receive ConnectMsg");
					unstashAll();
					getContext().become(connected);
				})
				.matchAny(o -> {
					log.info("disconnected: matchAny");
					stash();
				})
				.build();
		}

		@Override
		public Receive createReceive() {
			return disconnected;
		}

		public static Props create() {
			return Props.create(UserStorageActor.class);
		}
	}

	public static class ConnectMsg {
	}

	public static class DisconnectMsg {
	}

	public static class OperationMsg {
		private final String opr;

		public OperationMsg(String opr) {
			this.opr = opr;
		}
	}
}
