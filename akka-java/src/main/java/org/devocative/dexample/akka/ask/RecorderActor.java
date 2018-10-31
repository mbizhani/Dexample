package org.devocative.dexample.akka.ask;

import akka.actor.*;
import akka.pattern.PatternsCS;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

public class RecorderActor extends AbstractLoggingActor {

	@Override
	public Receive createReceive() {
		return receiveBuilder()
			.match(NewUserMsg.class, msg -> {
				log().info("RecorderActor Start: msg = {}", msg);

				CheckerActor.CheckUserMsg checkUserMsg = new CheckerActor.CheckUserMsg(msg.getNewUser());

				ActorSelection checkerActorRef = getContext().actorSelection("checkerActor");
				log().info("** ActorSelection = {}", checkerActorRef);

				/*
				TIP: before Java 8, which is Scala API

				Future<Object> ask1 = Patterns.ask(checkerActorRef, checkUserMsg, 1000);
				ask1.map(o -> {
					log.info("RecorderActor: ask result = {}", o);
					return null;
				}, getContext().dispatcher());
				*/

				CompletionStage<Object> ask = PatternsCS.ask(checkerActorRef, checkUserMsg, 1000);
				// TIP: use thenAccept() to process the returned result!
				ask.thenAccept(o -> {
					log().info("RecorderActor: ask result = {}", o);
				});

				log().info("RecorderActor End: msg = {}", msg);
			})
			.match(Terminated.class, msg -> log().warning("Terminated: msg={}", msg))
			.build();
	}

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return new OneForOneStrategy(1, Duration.ofMillis(10), param -> {
			if (param instanceof RuntimeException) {
				log().warning("RecorderActor.supervisorStrategy() - RuntimeException");
				return SupervisorStrategy.stop();
			}
			return SupervisorStrategy.resume();
		});
	}

	@Override
	public void preStart() {
		ActorRef checkerActorRef = getContext().actorOf(CheckerActor.create(), "checkerActor");

		// TIP: RecorderActor watched CheckerActor, so if CheckerActor stops, it receives Terminated message
		getContext().watch(checkerActorRef);
	}

	// ------------------------------

	public static Props create() {
		return Props.create(RecorderActor.class);
	}

	// ------------------------------

	public static class NewUserMsg {
		private User newUser;

		public NewUserMsg(User newUser) {
			this.newUser = newUser;
		}

		public User getNewUser() {
			return newUser;
		}

		@Override
		public String toString() {
			return "NewUserMsg{" +
				"newUser=" + newUser +
				'}';
		}
	}
}
