package org.devocative.dexample.akka.simple;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;

//#greeter-messages
public class Greeter extends AbstractLoggingActor {
	private final String message;
	private final String targetActorPath;
	private String greeting = "";

	// ------------------------------

	public Greeter(String message) {
		// TIP relative or absolute addressing
		// "akka://helloakka/user/printerActor"
		this(message, "../printerActor");
	}

	public Greeter(String message, String targetActorPath) {
		this.message = message;
		this.targetActorPath = targetActorPath;

		System.out.println("!!! - Greeter Const - " + Thread.currentThread().getName());
	}

	// ------------------------------

	@Override
	public Receive createReceive() {
		System.out.println("### - Greeter - " + Thread.currentThread().getName());

		return receiveBuilder()
			.match(WhoToGreet.class, wtg -> {
				this.greeting = message + ", " + wtg.who;

				//System.out.printf("### - match - WhoToGreet: %s - %s\n", this.hashCode(), greeting);
			})
			.match(Greet.class, x -> {
				// TIP finding another Actor by name/addressing
				ActorRef printerActor = getContext().actorFor(targetActorPath);

				//#greeter-send-message
				printerActor.tell(new Printer.Greeting(greeting), getSelf());

				//#greeter-send-message
			})
			.build();
	}

	@Override
	public void preStart() {
		System.out.println("$$$ - Greeter.preStart() - " + Thread.currentThread().getName());
	}

	@Override
	public void postStop() {
		System.out.println("$$$ - Greeter.postStop() - " + Thread.currentThread().getName());
	}

	// ------------------------------

	// TIP use static props for an Actor
	static public Props props(String message) {
		return Props.create(Greeter.class, () -> new Greeter(message));
	}

	static public Props props(String message, String targetActorPath) {
		return Props.create(Greeter.class, () -> new Greeter(message, targetActorPath));
	}

	// ------------------------------

	static public class WhoToGreet {
		public final String who;

		public WhoToGreet(String who) {
			this.who = who;
		}
	}

	static public class Greet {
		public Greet() {
		}
	}
}
