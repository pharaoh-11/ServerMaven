package com.handlers;

import com.data.Request;
import com.data.Response;
import com.entity.DBIntern;

public interface Handler {
    Response getResponse(Request request, DBIntern dbIntern);
}
