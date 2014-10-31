package com.everbridge.akka.sample1;

import akka.actor.UntypedActor;
import redis.clients.jedis.Jedis;


public class BrokeWorker extends UntypedActor
{
    private Jedis redis = null;


    public BrokeWorker()
    {
        RedisOperate opr = new RedisOperate();
        redis = opr.getJedis();
    }


    @Override
    public void onReceive(Object message)
    {
        try{
            if (message instanceof ProduceResult) {

                ProduceResult prdResult = (ProduceResult) message;

                System.out.println(
                        String.format("%s prd-result: [%s]", getSelf(), prdResult.getResult()) );

                //insert into redis.
                SetProduceResultIntoRedis( prdResult.getResult() );
            }
            else if (message instanceof ConsumeResult) {

                ConsumeResult csmResult = (ConsumeResult) message;

                System.out.println(
                        String.format("%s csm-result: [%s]", getSelf(), csmResult.getResult()) );

                // update redis recode;
                SetConsumeResultIntoRedis( csmResult.getResult() );
            }
            else {
                unhandled(message);
            }
        }
        catch (Throwable cause) {
            throw new RuntimeException(cause);
        }
    }


    public void SetProduceResultIntoRedis(String result)
    {
        String fields[] = result.split("\\|");

        String key = String.format("%s-%s", fields[0].trim(), fields[1].trim());

        this.redis.rpush(key, fields[0].trim());
        this.redis.rpush(key, fields[1].trim());
        this.redis.rpush(key, fields[2].trim());
        this.redis.rpush(key, "0");
    }

    public void SetConsumeResultIntoRedis(String result)
    {
        String fields[] = result.split("\\|");

        String key = String.format("%s-%s", fields[0].trim(), fields[1].trim());

        this.redis.rpush(key, "1");
    }

}
