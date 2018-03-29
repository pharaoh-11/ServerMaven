package com.handlers;

import com.data.Request;
import com.data.Response;
import com.server.controller.MemoryDataBase;

public interface Handler {
    Response getResponse(Request request);
}
