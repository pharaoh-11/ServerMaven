package com.data;

public class Response {
    String head;
    String body;

    public void setHead(String head) {
        this.head = head;
    }

    public void setBody(String entities) {
        this.body = entities;
    }

    public boolean isBodyNull() {
        return (body == null);
    }

    @Override
    public String toString() {
        return  head + body;
    }
}
