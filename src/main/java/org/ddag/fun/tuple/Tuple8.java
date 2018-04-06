/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.tuple;

@SuppressWarnings({"WeakerAccess","unchecked"})
public class Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> extends FunTuple {

  public Tuple8(T1 val1, T2 val2, T3 val3, T4 val4, T5 val5, T6 val6, T7 val7, T8 val8) {
    this.setSize(8);
    values[0] = val1;
    values[1] = val2;
    values[2] = val3;
    values[3] = val4;
    values[4] = val5;
    values[5] = val6;
    values[6] = val7;
    values[7] = val8;
  }

  public T1 _1() { return (T1) values[0]; }

  public T2 _2() { return (T2) values[1]; }

  public T3 _3() { return (T3)values[2]; }

  public T4 _4() { return (T4)values[3]; }

  public T5 _5() { return (T5)values[4]; }

  public T6 _6() { return (T6)values[5]; }

  public T7 _7() { return (T7)values[6]; }

  public T8 _8() { return (T8)values[7]; }
}
