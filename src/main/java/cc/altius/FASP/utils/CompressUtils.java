/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.utils;

import cc.altius.FASP.framework.GlobalConstants;
import jakarta.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author rohit
 */
public class CompressUtils {
    public static Boolean isCompress(String jsonString) {
        int dataSize = jsonString.getBytes().length;
        return dataSize/1000000 > GlobalConstants.COMPRESS_LIMIT_SIZE;
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
    
    public static String decompress(String jsonByte) throws IOException {
        try{
            byte[] compressedData = Base64.getDecoder().decode(jsonByte);
            try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(compressedData))) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = gzipInputStream.read(buffer)) > 0) {
                    baos.write(buffer, 0, len);
                }
                return baos.toString("UTF-8");
            }
        }catch(IllegalArgumentException ex){
            return jsonByte;
        }
    }
}
