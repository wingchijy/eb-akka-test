package com.everbridge.akkasample1;

import akka.actor.UntypedActor;


public class BrokeWorker extends UntypedActor
{
    @Override
    public void onReceive(Object message)
    {
        try{
            if (message instanceof ProduceResult) {

                ProduceResult prdResult = (ProduceResult) message;

                System.out.println(
                        String.format("%s prd-result: [%s]", getSelf(), prdResult.getResult()) );
            }
            else if (message instanceof ConsumeResult) {

                ConsumeResult csmResult = (ConsumeResult) message;

                System.out.println(
                        String.format("%s csm-result: [%s]", getSelf(), csmResult.getResult()) );
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
