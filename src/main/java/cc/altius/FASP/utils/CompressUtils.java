/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author rohit
 */
public class CompressUtils {
    public static Boolean isCompress(String jsonString) {
        int dataSize = jsonString.getBytes().length;
        return dataSize/1000000 > 10;
    }
    
    public static String compress(String jsonString) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(jsonString.length());
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(jsonString.getBytes(StandardCharsets.UTF_8));
        gzip.close();
        byte[] compressed = bos.toByteArray();
        bos.close();
        return DatatypeConverter.printBase64Binary(compressed);
    }
}
