package test;/*
 *
 * Dominik Dagiel 03.2018
 *
 */


public class Performance {

  public static void testPerform(String msg, int loops, Runnable runnable) {
    long startTime = System.currentTimeMillis();

    for (int i = 0; i < loops; i++) {
      runnable.run();
    }

    long stopTime = System.currentTimeMillis();
    long elapsedTime = stopTime - startTime;
    System.out.println("test: " + msg + "\nloops: " + loops + "\nmillis: "+ elapsedTime);

  }

}
