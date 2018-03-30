/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package test;

public class Performance {
  public static boolean testPerformance = true;

  public static void testPerform(String msg, int loops, Runnable runnable) {
    if (!testPerformance)
      return;
    long startTime = System.currentTimeMillis();

    for (int i = 0; i < loops; i++) {
      runnable.run();
    }

    long stopTime = System.currentTimeMillis();
    long elapsedTime = stopTime - startTime;
    System.out.println("test: " + msg + "\nloops: " + loops + "\nmillis: "+ elapsedTime);

  }

}
