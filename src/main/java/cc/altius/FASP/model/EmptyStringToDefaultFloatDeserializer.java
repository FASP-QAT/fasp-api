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

/**
 *
 * @author rohit
 */
public class EmptyStringToDefaultFloatDeserializer implements JsonDeserializer<Float> {
    @Override
    public Float deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            String jsonString = json.getAsString();
            return jsonString.isEmpty() ? 0.0f : Float.parseFloat(jsonString);
        } catch (NumberFormatException | IllegalStateException e) {
            // Handle exception or return a default value (e.g., 0.0f)
            return 0.0f;
        }
    }
}
