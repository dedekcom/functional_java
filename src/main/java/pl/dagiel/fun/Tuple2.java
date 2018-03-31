/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package pl.dagiel.fun;

public class Tuple2<T1, T2> extends FunTuple {

  public Tuple2(T1 val1, T2 val2) {
    this.setSize(2);
    values[0] = val1;
    values[1] = val2;
  }

  public Tuple2<T2, T1> swap() { return new Tuple2<>(_2(), _1()); }

  public T1 _1() { return (T1) values[0]; }

  public T2 _2() { return (T2) values[1]; }

}
