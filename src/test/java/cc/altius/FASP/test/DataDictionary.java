/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.test;

import cc.altius.FASP.model.ProgramData;
import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author altius
 */
public class DataDictionary<K, V> extends ProgramData {

    public static void main(String[] args) throws IntrospectionException {
//        ProgramData.class.getDeclaredFields();
//all keys of the Hashtable
//        ObjectMapper mapper = new ObjectMapper();
//        Map m = mapper.convertValue(new ProgramData(), Map.class);
//        writeInLogger(m);
        for (Field declaredField : ProgramData.class.getDeclaredFields()) {
            try {
//                ProgramData c = new ProgramData();
                Class klass = declaredField.getClass();
                if (((Object) klass instanceof String) || (declaredField.getType().isPrimitive() || (Object) klass instanceof Integer || (Object) klass instanceof Double
                        || (Object) klass instanceof Boolean)) {
                    System.out.println("\n inside if condition " + declaredField.getName() + "==" + "==" + declaredField.getType() + "==" + klass.equals(String.class));
                } else {

                    Field[] fields = getAllFields(klass);
                    for (Field field : fields) {
                        System.out.println("\n inside else condition " + declaredField.getName() + "==" + "==" + declaredField.getType());
//                        System.out.println(field.getName()+"=="+field.);
                    }
                }
            } catch (Exception a_th) {
                a_th.printStackTrace();
            }
//            if (declaredField.getType().isPrimitive() || (Object) ProgramData.class instanceof Integer || (Object) ProgramData.class instanceof Double
//                    || (Object) ProgramData.class instanceof Boolean) {
//                System.out.println("\n inside if condition " + declaredField.getName() + "==" + "==" + declaredField.getType());
//            } else {
//                System.out.println("\n inside else condition " + declaredField.getName() + "==" + "==" + declaredField.getType());
//
//            }
//            System.out.println("\n" + declaredField.getAnnotatedType());
//            Object f = declaredField.get(obj);
//            System.out.println("===" + f);
        }
//        DataDictionary.writeInLogger(ProgramData.class, "");
//        System.out.println(Arrays.toString(ProgramData.class.getDeclaredFields()));
    }

    public static Field[] getAllFields(Class klass) {
        List<Field> fields = new ArrayList<Field>();
        fields.addAll(Arrays.asList(klass.getDeclaredFields()));
        if (klass.getSuperclass() != null) {
            fields.addAll(Arrays.asList(getAllFields(klass.getSuperclass())));
        }
        return fields.toArray(new Field[]{});
    }

    private static void writeInLogger(Map<String, Object> obj) {

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        System.out.println("==" + Arrays.toString(declaredFields));
        //        Class klazz = obj.getClass();
        //        System.out.println(obj + "===" + Arrays.toString(klazz.getDeclaredFields()));
        //
        //        if (klazz.isPrimitive() || obj instanceof String || obj instanceof Integer || obj instanceof Double
        //                || obj instanceof Boolean) {
        //            System.out.println(str + obj.toString());
        //        } else {
        //            try {
        //                for (Field declaredField : klazz.getDeclaredFields()) {
        //                    System.out.println("===" + declaredField.getName());
        ////                            writeInLogger(object, str);
        //
        //                }
        ////                for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(klazz).getPropertyDescriptors()) {
        ////                    if (propertyDescriptor.getWriteMethod() == null) {
        ////                        continue;
        ////                    }
        ////                    Method m = propertyDescriptor.getReadMethod();
        ////                    if (m != null) {
        ////                        Object object = m.invoke(obj);
        ////                        if (object != null) {
        ////                        }
        ////                    }
        ////                }
        ////            } catch (IllegalAccessException e) {
        ////                e.printStackTrace();
        ////            } catch (IllegalArgumentException e) {
        ////                e.printStackTrace();
        ////            } catch (InvocationTargetException e) {
        ////                e.printStackTrace();
        //            } catch (Exception e) {
        //                e.printStackTrace();
        //            }
        //        }
        ;
    }
}
//values from Hashtable
//        System.out.println(((Hashtable) programData).values());
