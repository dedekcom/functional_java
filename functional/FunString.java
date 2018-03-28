/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package functional;

import java.util.Arrays;

public class FunString implements FunObject {
  private String str;
  private final static String MAX_INT = String.valueOf(Integer.MAX_VALUE);
  private final static String MIN_INT = String.valueOf(Integer.MIN_VALUE).substring(1);

  public FunString() {}
  public FunString(String s)   { this.set(s); }
  public FunString(int i)      {this.set(i); }
  public FunString(long l)     {this.set(l); }
  public FunString(boolean b)  {this.set(b); }
  public FunString(char c)     {this.set(c); }
  public FunString(double d)   {this.set(d); }
  public FunString(float f)    {this.set(f); }

  public String get()         { return str; }

  public void set(String s)   { str = s; }
  public void set(int i)      { str = String.valueOf(i); }
  public void set(long l)     { str = String.valueOf(l); }
  public void set(boolean b)  { str = String.valueOf(b); }
  public void set(char c)     { str = String.valueOf(c); }
  public void set(double d)   { str = String.valueOf(d); }
  public void set(float f)    { str = String.valueOf(f); }

  public Integer toInteger()  { return Integer.valueOf(str);}
  public Long toLong()        { return Long.valueOf(str);}
  public Boolean toBoolean()  { return Boolean.valueOf(str);}
  public Double toDouble()    { return Double.valueOf(str);}
  public Float toFloat()      { return Float.valueOf(str);}

  private boolean isNegative() {    return str.charAt(0) == '-';  }
  public boolean isInteger()  {
    if (str == null || str.isEmpty())
      return false;
    if (str.matches("[+,-]?[0]*[0-9]+")) {
      String number = str.replaceFirst("[+,-]?[0]*", "");
      if (number.isEmpty())
        return true;  // zero
      if (isNegative()) {
        return (number.length() < MIN_INT.length() ||
                (number.length() == MIN_INT.length() && number.compareTo(MIN_INT) <= 0));
      } else {
        return (number.length() < MAX_INT.length() ||
                (number.length() == MAX_INT.length() && number.compareTo(MAX_INT) <= 0));
      }
    }
    return false;
  }

  public boolean isLong()  {
    try {
      Long.parseLong(str);
      return true;
    } catch (NumberFormatException nfe) {
    }
    return false;
  }

  public boolean isFloat()  {
    try {
      Float.parseFloat(str);
      return true;
    } catch (NumberFormatException nfe) {
    }
    return false;
  }

  public boolean isDouble()  {
    try {
      Double.parseDouble(str);
      return true;
    } catch (NumberFormatException nfe) {
    }
    return false;
  }

  public boolean isBoolean() { return str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false"); }

  public void print() { System.out.println(str); }

  public FunList<String> split(String regex) {
    FunList<String> list = new FunList<>();
    Arrays.stream(str.split(regex)).forEach(list::add);
    return list;
  }

  public FunList<Character> toList() {
    FunList<Character> list = new FunList<>();
    char[] chars = str.toCharArray();
    for (char c: chars) { list.add(new Character(c)); }
    return list;
  }

  public static FunString of(Object... params) {
    StringBuilder s = new StringBuilder("");
    for (Object p: params) {
      s.append(p.toString());
    }
    return new FunString(s.toString());
  }
}
