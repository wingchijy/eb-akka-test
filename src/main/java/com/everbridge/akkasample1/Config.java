package com.everbridge.akkasample1;

public class Config {

    public static final String DEFAULT_QUEUE = "tasks";

    public static final String HOST = "127.0.0.1";


    public static final int PRODUCE_WORKER_COUNT = 4;
    public static final int CONSUME_WORKER_COUNT = 10;
    public static final int BROKE_WORKER_COUNT =2;


    public static final int MESSAGE_COUNT_ONETIME = 3000;

    public static final String MESSAGE_TEST_FILE =
            "/Users/chijy/works/everBridge/data/eb_akka/demo/index.html";

}
