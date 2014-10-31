package com.everbridge.akka.sample1;


import redis.clients.jedis.Jedis;

public class RedisOperate
{
    public static Jedis getJedis()
    {
        Jedis redis = new Jedis(Config.REDIS_HOST, 6379);

//        jedis.auth(FinalCollention.PASSWORD);

        return redis;
    }

}
