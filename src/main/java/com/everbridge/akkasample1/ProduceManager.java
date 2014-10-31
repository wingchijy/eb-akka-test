package com.everbridge.akkasample1;

import akka.actor.*;
import akka.routing.RoundRobinRouter;
import java.io.IOException;
import java.util.HashMap;


public class ProduceManager extends UntypedActor
{
    private final ActorRef router;
    private final ActorRef broker;
    private HashMap<String, MQEndPoint> mqList;


    public ProduceManager(ActorRef broker)
    {
        this.broker = broker;

        this.router = this.getContext().actorOf(
                new Props(ProduceWorker.class).withRouter(
                        new RoundRobinRouter(Config.PRODUCE_WORKER_COUNT) ),
                "produceRouter");

        // create demo data.
        Messages.getDemoMessage();
    }


    @Override
    public void onReceive(Object message)
    {
        try {
            if (message instanceof Messages.Init){
                initMQ();
                System.out.println("Init producer over.");
            }
            else if (message instanceof Messages.MailType){
                produceTasks("mail");
            }
            else if (message instanceof Messages.PhoneType){
                produceTasks("phone");
            }
            else if (message instanceof Messages.FaxType){
                produceTasks("fax");
            }
            else if (message instanceof Messages.AppType){
                produceTasks("app");
            }
            else{
                unhandled(message);
            }
        }
        catch (Throwable cause) {
            throw new RuntimeException(cause);
        }
    }


    public void initMQ() throws IOException
    {
        mqList = new HashMap<String, MQEndPoint>();

        mqList.put("mail", new MQEndPoint("mail") );
        mqList.put("phone", new MQEndPoint("phone") );
        mqList.put("fax", new MQEndPoint("fax") );
        mqList.put("app", new MQEndPoint("app") );
    }


    public void produceTasks(String type) throws IOException
    {
        // get channel by type.
        MQEndPoint mq = mqList.get(type);

        // pubish messsage.
        for (int index = 0; index < Config.MESSAGE_COUNT_ONETIME; index++) {

            Messages.MessageAmount++;

            String msg = String.format(
                    "%s | %s | %s", type, Messages.MessageAmount, Messages.DemoMessage );
//            System.out.println( msg );

            router.tell( new Task(mq, msg), this.broker );
        }
    }

}
