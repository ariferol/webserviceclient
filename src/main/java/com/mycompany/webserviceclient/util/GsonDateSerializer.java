package com.mycompany.webserviceclient.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GsonDateSerializer implements JsonSerializer<Date> {

    @Override
    public JsonElement serialize(Date t, Type type, JsonSerializationContext jsc) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        return t == null ? null : new JsonPrimitive(sdf.format(t.getTime()));
    }

}
