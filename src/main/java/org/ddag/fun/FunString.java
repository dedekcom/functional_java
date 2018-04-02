/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag.fun;

import java.util.Arrays;
import java.util.Optional;

public class FunString implements FunObject {
  private String str;

  public FunString()           {this.set(""); }
  public FunString(String s)   {this.set(s); }
  public FunString(int i)      {this.set(i); }
  public FunString(long l)     {this.set(l); }
  public FunString(boolean b)  {this.set(b); }
  public FunString(char c)     {this.set(c); }
  public FunString(double d)   {this.set(d); }
  public FunString(float f)    {this.set(f); }
  public FunString(Object o)   {this.set(o); }

  public String get()         { return str; }

  @Override
  public String toString() { return this.get(); }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (o instanceof FunString) {
      return this.get().equals(((FunString) o).get());
    } else if (o instanceof String) {
      return this.get().equals(o);
    } else
      return false;
  }

  public FunString set(String s)   { str = s; return this; }
  public FunString set(int i)      { str = String.valueOf(i); return this; }
  public FunString set(long l)     { str = String.valueOf(l); return this; }
  public FunString set(boolean b)  { str = String.valueOf(b); return this; }
  public FunString set(char c)     { str = String.valueOf(c); return this; }
  public FunString set(double d)   { str = String.valueOf(d); return this; }
  public FunString set(float f)    { str = String.valueOf(f); return this; }
  public FunString set(Object o)   { str = o.toString();      return this; }

  public Optional<Integer> getInteger()   {   return Fumeric.getInteger(this.str);  }

  public Optional<Long> getLong()         {   return Fumeric.getLong(this.str); }

  public Optional<Short> getShort()     { return Fumeric.getShort(this.str); }

  public Optional<Float> getFloat()  {
    try {
      float f = Float.parseFloat(str);
      return Optional.of(f);
    } catch (NumberFormatException nfe) {
    }
    return Optional.empty();
  }

  public Optional<Double> getDouble()  {
    try {
      double d = Double.parseDouble(str);
      return Optional.of(d);
    } catch (NumberFormatException nfe) {
    }
    return Optional.empty();
  }

  public Optional<Boolean> getBoolean() {
    if (str.equalsIgnoreCase("true")) return Optional.of(true);
    else if (str.equalsIgnoreCase("false")) return Optional.of(false);
    else return Optional.empty();
  }

  public void print() { System.out.println(str); }

  public FunList<String> split(String regex) {    return new FunList<>(Arrays.asList(str.split(regex)));  }

  public FunList<Character> toList() {
    FunList<Character> list = new FunList<>();
    char[] chars = str.toCharArray();
    for (char c: chars) { list.add(new Character(c)); }
    return list;
  }

  public boolean matches(Object... params) {
    if (params.length > 0 && (params[0] instanceof Class) && ((Class)params[0]).isInstance(this)) {
      if (params.length == 1)
        return true;  // test only class type
      else if (params.length == 2) {
        return this.equals(params[1]);
      } else return false;
    } else {
      return params.length == 1 && this.equals(params[0]);
    }
  }

  public static FunString of(Object... params) {
    StringBuilder s = new StringBuilder("");
    for (Object p: params) {
      s.append(p.toString());
    }
    return new FunString(s.toString());
  }
}
