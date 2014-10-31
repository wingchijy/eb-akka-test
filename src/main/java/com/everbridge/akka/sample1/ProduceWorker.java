package com.everbridge.akka.sample1;

import akka.actor.UntypedActor;


public class ProduceWorker extends UntypedActor
{
    @Override
    public void onReceive(Object message)
    {
        try{
            if (message instanceof Task)
            {
                long startTime = System.currentTimeMillis();

                // get the msg-content.
                Task task = (Task) message;
                String content = task.getContent();

                // put into rabbit-mq.
                task.getMQEndPoint().channel.basicPublish(
                        "", Config.DEFAULT_QUEUE, null,content.getBytes() );

                // compute time cost. (unit == milli-second)
                long duration = System.currentTimeMillis() - startTime;

                // tell result to broker.
                String result = String.format("%s|%s| %d | %d | ok",
                        content.split("\\|")[0], content.split("\\|")[1], content.length(), duration);

                System.out.println(
                        String.format("%s produce-task: [%s]", getSelf(), result) );

                getSender().tell( new ProduceResult(result), getSelf() );
            }
            else {
                unhandled(message);
            }
        }
        catch (Throwable cause) {
            throw new RuntimeException(cause);
        }
    }

}
