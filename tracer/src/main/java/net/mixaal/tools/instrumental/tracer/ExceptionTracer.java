package net.mixaal.tools.instrumental.tracer;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;

public class ExceptionTracer {

  /** Handle to instance of Instrumentation interface. */
  private static volatile Instrumentation globalInstrumentation;

  /**
   * Implementation of the overloaded premain method that is first invoked by
   * the JVM during use of instrumentation.
   *
   * @param agentArgs Agent options provided as a single String.
   * @param inst Handle to instance of Instrumentation provided on command-line.
   */
  public static void premain(final String agentArgs, final Instrumentation inst)
  {
    System.out.println("premain...");
    globalInstrumentation = inst;
  }

  /**
   * Implementation of the overloaded agentmain method that is invoked for
   * accessing instrumentation of an already running JVM.
   *
   * @param agentArgs Agent options provided as a single String.
   * @param inst Handle to instance of Instrumentation provided on command-line.
   */
  public static void agentmain(String agentArgs, Instrumentation inst)
  {
    System.out.println("agentmain...");
    globalInstrumentation = inst;
  }

  /**
   * Provide the memory size of the provided object (but not it's components).
   *
   * @param object Object whose memory size is desired.
   * @return The size of the provided object, not counting its components
   *    (described in Instrumentation.getObjectSize(Object)'s Javadoc as "an
   *    implementation-specific approximation of the amount of storage consumed
   *    by the specified object").
   * @throws IllegalStateException Thrown if my Instrumentation is null.
   */
  public static long getObjectSize(final Object object)
  {
    if (globalInstrumentation == null)
    {
      throw new IllegalStateException("Agent not initialized.");
    }
    return globalInstrumentation.getObjectSize(object);
  }

  public static void traceThrowable(Object root, Throwable throwable) {
    if(throwable==null) return;
    final StackTraceElement[] elements = throwable.getStackTrace();
    int recordNo = 0;
    for(final StackTraceElement element : elements) {
      final String method = element.getMethodName();
      final String clazz = element.getClassName().replaceAll("\\$", ".");
      final int lineNno = element.getLineNumber();
      System.out.println("method="+method);
      System.out.println("class="+clazz);
      Class[] classes = globalInstrumentation.getAllLoadedClasses();
      for(final Class klazz: classes) {
        System.out.println("loaded class:"+klazz );
      }
    }
  }

  @SuppressWarnings("rawtypes")
  public static Object getValueOf(Object clazz, String lookingForValue)
      throws Exception {
    Field field = clazz.getClass().getField(lookingForValue);
    Class clazzType = field.getType();
    if (clazzType.toString().equals("double"))
      return field.getDouble(clazz);
    else if (clazzType.toString().equals("int"))
      return field.getInt(clazz);
    else if (clazzType.toString().equals("float"))
      return field.getFloat(clazz);
    else if (clazzType.toString().equals("boolean"))
      return field.getBoolean(clazz);
    else if (clazzType.toString().equals("byte"))
      return field.getByte(clazz);
    else if (clazzType.toString().equals("char"))
      return field.getChar(clazz);
    // else other type ...
    // and finally
    return field.get(clazz);
  }

}
