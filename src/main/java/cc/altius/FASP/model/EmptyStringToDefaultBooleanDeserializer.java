/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Type;

/**
 *
 * @author rohit
 */
public class EmptyStringToDefaultBooleanDeserializer implements JsonDeserializer<Boolean> {
    @Override
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
            if (jsonPrimitive.isBoolean()) {
                return jsonPrimitive.getAsBoolean();
            } else if (jsonPrimitive.isNumber()) {
                // Define your logic here to interpret numeric values as booleans if required
                // For example, considering 1 as true and 0 as false:
                return jsonPrimitive.getAsInt() == 1;
            } else if (jsonPrimitive.isString()) {
                String jsonString = jsonPrimitive.getAsString();
                if (jsonString.isEmpty()) {
                    // Handle empty string as needed
                    return false; // Return a default value (false) for empty string
                } else if (jsonString.equalsIgnoreCase("true")) {
                    return true;
                } else if (jsonString.equalsIgnoreCase("false")) {
                    return false;
                }
                // Handle other string representations, if required
            }
        }

        // Handle other cases, if needed
        throw new JsonParseException("Cannot deserialize to boolean: " + json.toString());
    }
}
