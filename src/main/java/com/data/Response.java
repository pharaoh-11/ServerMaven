package com.data;

import java.util.Arrays;

public class Response {
    String head;
    String entity;

    public void setHead(String head) {
        this.head = head;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return  head + entity;
    }
}
