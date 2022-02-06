package com.mycompany.webserviceclient.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author ariferol
 */
public class GsonCalendarSerializer implements JsonSerializer<Calendar>, JsonDeserializer<Calendar> {

    @Override
    public JsonElement serialize(Calendar t, Type type, JsonSerializationContext jsc) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("yyyy-MM-dd HH:mm:ss.SSS"));
        return t != null ? new JsonPrimitive(sdf.format(t.getTime())) : null;
    }

    @Override
    public Calendar deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(je.getAsJsonPrimitive().getAsLong());
        return cal;
    }
}
