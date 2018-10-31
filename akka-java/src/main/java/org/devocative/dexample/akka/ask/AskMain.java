package org.devocative.dexample.akka.ask;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class AskMain {

	public static void main(String[] args) throws InterruptedException {
		final ActorSystem system = ActorSystem.create("AskAkka");

		final ActorRef recorderActor = system.actorOf(RecorderActor.create(), "recorderActor");

		recorderActor.tell(new RecorderActor.NewUserMsg(new User("A", "A")), ActorRef.noSender());
		recorderActor.tell(new RecorderActor.NewUserMsg(new User("B", "B")), ActorRef.noSender());
		recorderActor.tell(new RecorderActor.NewUserMsg(new User("C", "C")), ActorRef.noSender());

		Thread.sleep(2000);

		system.terminate();
	}

}
