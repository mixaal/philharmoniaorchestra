package net.mixaal.tools.instrumental.example;


import net.mixaal.tools.instrumental.tracer.ExceptionTracer;

public class InstTest {


 public static void greet(String a) {
   throw new RuntimeException("Hello!");
 }

 public static void main(String [] args) {
   System.out.println(
     ExceptionTracer.getObjectSize(new String("aaaa"))
   );
  try {
   greet("Hi!");
  }
  catch(Exception ex) {
     ExceptionTracer.traceThrowable(null, ex);
  }
 }
}
