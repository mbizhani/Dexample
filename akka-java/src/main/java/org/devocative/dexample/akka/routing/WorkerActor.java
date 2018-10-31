package org.devocative.dexample.akka.routing;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;

public class WorkerActor extends AbstractLoggingActor {
	@Override
	public Receive createReceive() {
		return receiveBuilder()
			.match(WorkerMsg.class, msg -> {
				log().info("WorkerActor MSG received: {}", Thread.currentThread().getName());
			})
			.build();
	}

	public static Props create() {
		return Props.create(WorkerActor.class);
	}

	// ------------------------------

	public static class WorkerMsg {
	}
}
