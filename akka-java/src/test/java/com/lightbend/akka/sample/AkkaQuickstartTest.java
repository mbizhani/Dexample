package com.lightbend.akka.sample;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import com.lightbend.akka.sample.Greeter.Greet;
import com.lightbend.akka.sample.Greeter.WhoToGreet;
import com.lightbend.akka.sample.Printer.Greeting;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AkkaQuickstartTest {
	static ActorSystem system;

	@BeforeClass
	public static void setup() {
		system = ActorSystem.create();
	}

	@AfterClass
	public static void teardown() {
		TestKit.shutdownActorSystem(system);
		system = null;
	}

	@Test
	public void testGreeterActorSendingOfGreeting() {
		final TestKit testProbe = new TestKit(system);
		final ActorRef helloGreeter = system.actorOf(Greeter.props("Hello", testProbe.getRef()));
		helloGreeter.tell(new WhoToGreet("Akka"), ActorRef.noSender());
		helloGreeter.tell(new Greet(), ActorRef.noSender());
		Greeting greeting = testProbe.expectMsgClass(Greeting.class);
		assertEquals("Hello, Akka", greeting.message);
	}
}
