package com.everbridge.akkasample1;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;


public class BrokeManager extends UntypedActor
{
    private final ActorRef router;


    public BrokeManager()
    {
        this.router = this.getContext().actorOf(
                new Props(BrokeWorker.class).withRouter(
                        new RoundRobinRouter(Config.BROKE_WORKER_COUNT) ),
                "brokeRouter");
    }


    @Override
    public void onReceive(Object message)
    {
        try {
            if (message instanceof Messages.Init){
                System.out.println("Init broker over.");
            }
            else if (message instanceof ProduceResult){
                this.router.tell( message, getSelf() );
            }
            else if (message instanceof ConsumeResult){
                this.router.tell( message, getSelf() );
            }
            else{
                unhandled(message);
            }
        }
        catch (Throwable cause) {
            throw new RuntimeException(cause);
        }
    }

}
