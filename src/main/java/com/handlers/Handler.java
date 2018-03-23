package com.handlers;

import com.data.Request;
import com.data.Response;

public interface Handler {
    Response getResponse(Request request);
}
