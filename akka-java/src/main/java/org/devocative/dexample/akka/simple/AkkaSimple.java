package org.devocative.dexample.akka.simple;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AkkaSimple {

	public static void main(String[] args) throws Exception {
		final boolean sendMessageSeq = true;

		final ActorRef howdyGreeter;
		final ActorRef helloGreeter;

		final ActorSystem system = ActorSystem.create("helloakka");

		try {
			//#create-actors
			system.actorOf(Printer.props(), "printerActor");

			howdyGreeter = system.actorOf(Greeter.props("Howdy"), "howdyGreeter");
			helloGreeter = system.actorOf(Greeter.props("Hello"), "helloGreeter");
			//#create-actors

			List<Callable<Void>> list = new ArrayList<>();

			//#main-send-messages
			list.add(() -> {
				howdyGreeter.tell(new Greeter.WhoToGreet("Akka"), ActorRef.noSender());
				howdyGreeter.tell(new Greeter.Greet(), ActorRef.noSender());
				return null;
			});

			list.add(() -> {
				howdyGreeter.tell(new Greeter.WhoToGreet("Lightbend"), ActorRef.noSender());
				howdyGreeter.tell(new Greeter.Greet(), ActorRef.noSender());
				return null;
			});

			list.add(() -> {
				helloGreeter.tell(new Greeter.WhoToGreet("Java"), ActorRef.noSender());
				helloGreeter.tell(new Greeter.Greet(), ActorRef.noSender());
				return null;
			});

			list.add(() -> {
				helloGreeter.tell(new Greeter.WhoToGreet("Play"), ActorRef.noSender());
				helloGreeter.tell(new Greeter.Greet(), ActorRef.noSender());
				return null;
			});

			//#main-send-messages

			if (sendMessageSeq) {
				list.forEach(c -> {
					try {
						c.call();
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			} else {
				ExecutorService threadPool = Executors.newFixedThreadPool(list.size());
				threadPool.invokeAll(list);
				threadPool.shutdown();
			}

			//System.out.println(">>> Press ENTER to exit <<<");
			//System.in.read();
		} finally {
			system.terminate();
		}
	}
}
