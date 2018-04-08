/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag.fun.tuple;

import org.ddag.fun.FunObject;
import org.ddag.fun.col.FunList;
import org.ddag.fun.match.FunMatching;

import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
abstract public class FunTuple implements FunObject, FunMatching {
  protected Object[] values;

  protected void setSize(int size) { values = new Object[size]; }

  public int size() { return values.length; }

  public Object _id(int id) { return values[id]; }

  public FunList<Object> toList() { return new FunList<>(Arrays.asList(values)); }

  public boolean matches(Object first, Object... params) {
    if ((first instanceof Class) && ((Class)first).isInstance(this)) {
      if (params.length == 0)
        return true;    // match only FunObject type
      if (this.size() != params.length)
        return false;   // wrong tuple size
      for (int i=0; i<params.length; i++) {
        if (params[i] instanceof Class )  {
          if (!((Class)params[i]).isInstance(this._id(i)))
            return false;
        } else {
          if (!this._id(i).equals(params[i]))
            return false;
        }
      }
      return true;
    } else {
      return params.length == 0 && this.equals(first);
    }
  }

  @Override
  public String toString() {
    String res = "(";
    for (int i=0; i < values.length-1; i++) {
      res += values[i] + ", ";
    }
    return res + values[values.length-1] + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof FunTuple) {
      FunTuple tuple = (FunTuple) o;
      return Arrays.equals(this.values, tuple.values);
    }
    return false;
  }

  public static <T1> Tuple1<T1> T1(T1 val1) { return new Tuple1<>(val1); }

  public static <T1, T2> Tuple2<T1, T2> T2(T1 val1, T2 val2) { return new Tuple2<>(val1, val2); }

  public static <T1, T2, T3> Tuple3<T1, T2, T3> T3(T1 val1, T2 val2, T3 val3) { return new Tuple3<>(val1, val2, val3); }

  public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> T4(T1 val1, T2 val2, T3 val3, T4 val4) {
    return new Tuple4<>(val1, val2, val3, val4);
  }

  public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> T5(T1 val1, T2 val2, T3 val3, T4 val4, T5 val5) {
    return new Tuple5<>(val1, val2, val3, val4, val5);
  }

  public static <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> T6(T1 val1, T2 val2, T3 val3, T4 val4, T5 val5,
                                                                           T6 val6) {
    return new Tuple6<>(val1, val2, val3, val4, val5, val6);
  }

  public static <T1, T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> T7(T1 val1, T2 val2, T3 val3, T4 val4, T5 val5,
                                                                           T6 val6, T7 val7) {
    return new Tuple7<>(val1, val2, val3, val4, val5, val6, val7);
  }

  public static <T1, T2, T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> T8(T1 val1, T2 val2, T3 val3, T4 val4, T5 val5,
                                                                            T6 val6, T7 val7, T8 val8) {
    return new Tuple8<>(val1, val2, val3, val4, val5, val6, val7, val8);
  }
}
