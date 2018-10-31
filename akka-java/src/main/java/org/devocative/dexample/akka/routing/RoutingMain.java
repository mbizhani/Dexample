package org.devocative.dexample.akka.routing;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.routing.RandomGroup;
import akka.routing.RandomPool;
import akka.routing.RoundRobinPool;
import org.devocative.dexample.akka.routing.WorkerActor.WorkerMsg;

import java.util.Arrays;

public class RoutingMain {

	public static void main(String[] args) throws Exception {
		final ActorSystem system = ActorSystem.create("RoutingApp");

		roundRobin(system);

		Thread.sleep(1000);

		randGroup(system);

		Thread.sleep(1000);

		randPool(system);

		system.terminate();
	}

	private static void randPool(ActorSystem system) {
		final ActorRef randPool = system.actorOf(new RandomPool(3).props(WorkerActor.create()), "random-pool");
		randPool.tell(new WorkerMsg(), ActorRef.noSender());
		randPool.tell(new WorkerMsg(), ActorRef.noSender());
		randPool.tell(new WorkerMsg(), ActorRef.noSender());
		randPool.tell(new WorkerMsg(), ActorRef.noSender());
		randPool.tell(new WorkerMsg(), ActorRef.noSender());
	}

	private static void randGroup(ActorSystem system) {
		system.actorOf(WorkerActor.create(), "w1");
		system.actorOf(WorkerActor.create(), "w2");
		system.actorOf(WorkerActor.create(), "w3");

		final ActorRef randGrp = system.actorOf(new RandomGroup(Arrays.asList("/user/w1", "/user/w2", "/user/w3")).props(), "random-group");
		randGrp.tell(new WorkerMsg(), ActorRef.noSender());
		randGrp.tell(new WorkerMsg(), ActorRef.noSender());
		randGrp.tell(new WorkerMsg(), ActorRef.noSender());
		randGrp.tell(new WorkerMsg(), ActorRef.noSender());
		randGrp.tell(new WorkerMsg(), ActorRef.noSender());
	}

	private static void roundRobin(ActorSystem system) {
		final ActorRef rrPool = system.actorOf(new RoundRobinPool(3).props(WorkerActor.create()), "round-robin-pool");
		rrPool.tell(new WorkerMsg(), ActorRef.noSender());
		rrPool.tell(new WorkerMsg(), ActorRef.noSender());
		rrPool.tell(new WorkerMsg(), ActorRef.noSender());
		rrPool.tell(new WorkerMsg(), ActorRef.noSender());
		rrPool.tell(new WorkerMsg(), ActorRef.noSender());
	}

}
