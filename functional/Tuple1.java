/*
 *  Copyright 2018 ADVA Optical Networking SE. All rights reserved.
 *
 *  Owner: dominikda
 *
 *  $Id: $
 */
package functional;

public class Tuple1<T1> extends FunTuple {

  public Tuple1(T1 val1) {
    this.setSize(1);
    values[0] = val1;
  }

  public T1 _1() { return (T1) values[0]; }
}
