/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag.fun;

import java.io.Serializable;
import java.util.Arrays;

abstract public class FunTuple implements Serializable, FunObject {
  protected Object[] values;

  protected void setSize(int size) { values = new Object[size]; }

  public int size() { return values.length; }

  public Object _id(int id) { return values[id]; }

  public void print() { System.out.println(this.toString()); }

  public FunList<Object> toList() { return new FunList<>(Arrays.asList(values)); }

  public boolean matches(Object... params) {
    if (params.length > 0 && (params[0] instanceof Class) && ((Class)params[0]).isInstance(this)) {
      if (params.length == 1)
        return true;    // match only FunObject type
      int last = params.length - 1;
      if (this.size() != last)
        return false;   // wrong tuple size
      for (int i=1; i<=last; i++) {
        if (params[i] instanceof Class )  {
          if (!((Class)params[i]).isInstance(this._id(i-1)))
            return false;
        } else {
          if (!this._id(i-1).equals(params[i]))
            return false;
        }
      }
      return true;
    } else {
      return params.length == 1 && this.equals(params[0]);
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
}
