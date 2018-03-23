package com.data;

import com.fasterxml.jackson.databind.JsonNode;

public class Response {
    String head;
    JsonNode entity;

    public void setHead(String head) {
        this.head = head;
    }

    public void setEntity(JsonNode entities) {
        this.entity = entities;
    }

    @Override
    public String toString() {
        return  head + entity;
    }
}
