/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package pl.dagiel.functional;

import java.io.Serializable;
import java.util.Arrays;

abstract class FunTuple implements Serializable {
  protected Object[] values;

  protected void setSize(int size) { values = new Object[size]; }

  public int size() { return values.length; }

  public Object _id(int id) { return values[id]; }

  public void print() { System.out.println(this.toString()); }

  public FunList<Object> toList() { return new FunList<>(Arrays.asList(values)); }

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
