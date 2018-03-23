package com.data;

import java.util.Arrays;

public class Response {
    String head;
    Object[] entity;

    public void setHead(String head) {
        this.head = head;
    }

    public void setEntity(Object[] entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return  head + Arrays.toString(entity);
    }
}
