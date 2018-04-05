/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag.fun.tuple;

public class Tuple1<T1> extends FunTuple {

  public Tuple1(T1 val1) {
    this.setSize(1);
    values[0] = val1;
  }

  public T1 _1() { return (T1) values[0]; }
}
