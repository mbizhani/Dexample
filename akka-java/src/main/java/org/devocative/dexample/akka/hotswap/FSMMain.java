package org.devocative.dexample.akka.hotswap;

import akka.actor.AbstractFSMWithStash;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class FSMMain {

	public static void main(String[] args) throws InterruptedException {
		final ActorSystem system = ActorSystem.create("FSM");

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

	public static class UserStorageActor extends AbstractFSMWithStash<DBState, Data> {

		public UserStorageActor() {
			startWith(DBState.Disconnected, Data.EMPTY);

			when(DBState.Connected,
				matchEvent(DisconnectMsg.class, (disconnectMsg, data) -> {
					log().info("Connected: received DisconnectMsg");

					return goTo(DBState.Disconnected);
				})
			);

			when(DBState.Connected,
				matchEvent(OperationMsg.class, (operationMsg, data) -> {
					log().info("Connected: received OperationMsg = {}", operationMsg.opr);
					return stay();
				})
			);

			when(DBState.Disconnected,
				matchEvent(ConnectMsg.class, (connectMsg, data) -> {
					log().info("Disconnected: received ConnectMsg");
					unstashAll();
					return goTo(DBState.Connected);
				})
			);

			when(DBState.Disconnected,
				matchAnyEvent((o, data) -> {
					log().info("Disconnected: matchAnyEvent");
					stash();
					return stay();
				})
			);

			initialize();
		}

		public static Props create() {
			return Props.create(UserStorageActor.class);
		}
	}

	public enum DBState {
		Connected, Disconnected
	}

	public interface Data {
		Data EMPTY = new Data() {
		};
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
