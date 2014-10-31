package com.everbridge.akka.sample1;

import akka.actor.*;
import static java.lang.Thread.sleep;


public class LaunchMain
{
    public static void main(String[] args) throws Exception
    {
        ActorSystem system = ActorSystem.create("taskSystem");

        // broker.
        final ActorRef broker = system.actorOf(
                new Props(new UntypedActorFactory() {
                    public UntypedActor create() {
                        return new BrokeManager();
                    }
                }),
                "brokeManager");
        broker.tell( new Messages.Init() );


        // producer.
        ActorRef producer = system.actorOf(
                new Props(new UntypedActorFactory() {
                    public UntypedActor create() {
                        return new ProduceManager(broker);
                    }
                }),
                "produceManager");
        producer.tell( new Messages.Init() );


        // consumer.
        ActorRef consumer = system.actorOf(
                new Props(new UntypedActorFactory() {
                    public UntypedActor create() {
                        return new ConsumeManager(broker);
                    }
                }),
                "consumeManager");
        consumer.tell(new Messages.Init());


        // schedule.
        while (true)
        {
            producer.tell(new Messages.MailType());
            producer.tell( new Messages.PhoneType() );
            producer.tell(new Messages.AppType());
            producer.tell( new Messages.FaxType() );
            sleep(10 * 1000);
        }

        // shutdown.
//        system.shutdown();

    }

}
