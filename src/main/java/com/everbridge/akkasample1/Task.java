package com.everbridge.akkasample1;


public class Task {

    private final String content;
    private final MQEndPoint mq;

    public Task(MQEndPoint mq, String content) {
        this.mq = mq;
        this.content = content;
    }

    public MQEndPoint getMQEndPoint() {
        return mq;
    }

    public String getContent() {
        return content;
    }

}
