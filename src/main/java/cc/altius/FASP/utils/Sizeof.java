/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.utils;

/**
 *
 * @author rohit
 */
import java.lang.instrument.Instrumentation;
final public class Sizeof 
{
    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) 
    {
        instrumentation = inst;
    }
    public static long sizeof(Object o) 
    {
        return instrumentation.getObjectSize(o);
    }
}
