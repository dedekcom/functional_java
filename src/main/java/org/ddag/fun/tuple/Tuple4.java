/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag.fun.tuple;

public class Tuple4<T1, T2, T3, T4> extends FunTuple {

  public Tuple4(T1 val1, T2 val2, T3 val3, T4 val4) {
    this.setSize(4);
    values[0] = val1;
    values[1] = val2;
    values[2] = val3;
    values[3] = val4;
  }

  public T1 _1() { return (T1) values[0]; }

  public T2 _2() { return (T2) values[1]; }

  public T3 _3() { return (T3)values[2]; }

  public T4 _4() { return (T4)values[3]; }
}
