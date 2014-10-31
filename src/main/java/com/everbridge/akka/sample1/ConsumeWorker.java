package com.everbridge.akka.sample1;

import akka.actor.UntypedActor;

public class ConsumeWorker extends UntypedActor
{
    @Override
    public void onReceive(Object message)
    {
        try{
            if (message instanceof Task){

                // get the msg-content.
                Task task = (Task) message;
                String content = task.getContent();

                // tell result to broker.
                String result = String.format("%s|%s| %d | ok",
                        content.split("\\|")[0], content.split("\\|")[1], content.length());

                System.out.println(
                        String.format("%s consume-task: [%s]", getSelf(), result) );

                getSender().tell( new ConsumeResult(result), getSelf() );
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
