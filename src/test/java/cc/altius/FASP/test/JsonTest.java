/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

/**
 *
 * @author akil
 */
public class JsonTest {
    public static void main(String[] args) {
        String json = "[\"5\",\"8\"]";
        Gson gson = new Gson();
        Type typeList = new TypeToken<int[]>() {
        }.getType();
        int[] intArr = gson.fromJson(json, typeList);
        for (int a : intArr) {
            System.out.println(a);
        }
    }
    
}
