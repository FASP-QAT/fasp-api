/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/**
 *
 * @author akil
 */
public class EmptyIntegerTypeAdapter extends TypeAdapter<Integer> {

    @Override
    public void write(JsonWriter jsonWriter, Integer number) throws IOException {
        if (number == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.value(number);
    }

    @Override
    public Integer read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }

        try {
            String value = jsonReader.nextString();
            if ("".equals(value)) {
                return null;
            }
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }
}
