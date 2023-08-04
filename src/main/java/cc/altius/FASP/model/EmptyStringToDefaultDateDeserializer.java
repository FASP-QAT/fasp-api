/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author rohit
 */
public class EmptyStringToDefaultDateDeserializer implements JsonDeserializer<Date> {
    private static final String[] DATE_FORMATS = {
        "yyyy-MM-dd",       // Add more date formats if needed
        "yyyy-MM-dd'T'HH:mm:ss",
        "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    };

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String jsonString = json.getAsString();
        if (jsonString.isEmpty()) {
            return null; // Return null for empty strings or handle it as needed.
        }

        for (String format : DATE_FORMATS) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.parse(jsonString);
            } catch (ParseException e) {
                // Failed to parse with this format, try the next one.
            }
        }

        throw new JsonParseException("Unparseable date: " + jsonString);
    }
}
