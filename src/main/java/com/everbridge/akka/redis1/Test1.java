package com.everbridge.akka.redis1;


import redis.clients.jedis.Jedis;

public class Test1
{
    public static void main(String[] args) throws Exception
    {
        Jedis redis = new Jedis("localhost",6379);

        redis.set("foo", "bar");

        String value = redis.get("foo");
        System.out.println(value);
    }

}
