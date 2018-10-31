package org.devocative.dexample.akka.simple;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;

public class Printer extends AbstractLoggingActor {

	@Override
	public Receive createReceive() {
		System.out.println("### - Printer - " + Thread.currentThread().getName());

		return receiveBuilder()
			.match(Greeting.class, greeting -> log().info(greeting.message))
			.build();
	}

	@Override
	public void preStart() {
		System.out.println("$$$ - Printer.preStart() - " + Thread.currentThread().getName());
	}

	@Override
	public void postStop() {
		System.out.println("$$$ - Printer.postStop() - " + Thread.currentThread().getName());
	}

	// ------------------------------

	// TIP use static props for an Actor
	static public Props props() {
		return Props.create(com.lightbend.akka.sample.Printer.class);
	}

	static public class Greeting {
		public final String message;

		public Greeting(String message) {
			this.message = message;
		}
	}
}
