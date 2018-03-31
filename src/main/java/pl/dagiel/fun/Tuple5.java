/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package pl.dagiel.fun;

public class Tuple5<T1, T2, T3, T4, T5> extends FunTuple {

  public Tuple5(T1 val1, T2 val2, T3 val3, T4 val4, T5 val5) {
    this.setSize(5);
    values[0] = val1;
    values[1] = val2;
    values[2] = val3;
    values[3] = val4;
    values[4] = val5;
  }

  public T1 _1() { return (T1) values[0]; }

  public T2 _2() { return (T2) values[1]; }

  public T3 _3() { return (T3)values[2]; }

  public T4 _4() { return (T4)values[3]; }

  public T5 _5() { return (T5)values[4]; }
}
