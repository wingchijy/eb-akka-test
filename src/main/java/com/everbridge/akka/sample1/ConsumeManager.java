package com.everbridge.akka.sample1;

import akka.actor.*;
import akka.routing.RoundRobinRouter;
import com.rabbitmq.client.QueueingConsumer;


public class ConsumeManager extends UntypedActor
{
    private final ActorRef router;
    private final ActorRef broker;


    public ConsumeManager(ActorRef broker)
    {
        this.broker = broker;

        this.router = this.getContext().actorOf(
                new Props(ConsumeWorker.class).withRouter(
                        new RoundRobinRouter(Config.CONSUME_WORKER_COUNT)),
                "consumeRouter");
    }


    @Override
    public void onReceive(Object message)
    {
        try {
            if (message instanceof Messages.Init) {
                System.out.println("Init consumer over.");
                consumeTasks();
            }
            else{
                unhandled(message);
            }
        }
        catch (Throwable cause) {
            throw new RuntimeException(cause);
        }
    }


    private void consumeTasks() throws Exception
    {
        MQEndPoint mq = new MQEndPoint("consume");

        QueueingConsumer consumer = new QueueingConsumer( mq.channel );
        mq.channel.basicConsume( Config.DEFAULT_QUEUE, true, consumer );

        while (true) {

            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            router.tell(new Task(mq, new String( delivery.getBody() ) ), this.broker);
        }
    }

}
