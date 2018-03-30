/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package pl.dagiel.functional;

import java.util.Optional;
import java.util.regex.Pattern;

public class Fumeric {

  private enum Operation { Plus, Minus, Multiply, Divide, Modulo }

  public static Number zero() { return Integer.valueOf(0); }

  public static Number sum(Number n1, Number n2) { return opNumber(n1, n2, Operation.Plus); }
  public static Number sub(Number n1, Number n2) { return opNumber(n1, n2, Operation.Minus); }
  public static Number mul(Number n1, Number n2) { return opNumber(n1, n2, Operation.Multiply); }
  public static Number div(Number n1, Number n2) { return opNumber(n1, n2, Operation.Divide); }
  public static Number mod(Number n1, Number n2) { return opNumber(n1, n2, Operation.Modulo); }

  private static Number opNumber(Number n1, Number n2, Operation op)  {
    if (n1 instanceof Double || n2 instanceof Double) {
      return op(n1.doubleValue(), n2.doubleValue(), op);
    } else if (n1 instanceof Float || n2 instanceof Float) {
      return op(n1.floatValue(), n2.floatValue(), op);
    } else if (n1 instanceof Long || n2 instanceof Long) {
      return op(n1.longValue(), n2.longValue(), op);
    } else if (n1 instanceof Integer || n2 instanceof Integer) {
      return op(n1.intValue(), n2.intValue(), op);
    } else if (n1 instanceof Short || n2 instanceof Short) {
      return op(n1.shortValue(), n2.shortValue(), op);
    } else {
      throw new UnsupportedOperationException("Number: missing support for classes " + n1.getClass() + ", " + n2.getClass());
    }
  }

  private static Double op(double n1, double n2, Operation op) {
    switch (op) {
      case Plus: return n1 + n2;
      case Minus: return n1 - n2;
      case Multiply: return n1 * n2;
      case Divide: return n1 / n2;
      case Modulo: return n1 % n2;
      default: return Double.valueOf(0);
    }
  }

  private static Float op(float n1, float n2, Operation op) {
    switch (op) {
      case Plus: return n1 + n2;
      case Minus: return n1 - n2;
      case Multiply: return n1 * n2;
      case Divide: return n1 / n2;
      case Modulo: return n1 % n2;
      default: return Float.valueOf(0);
    }
  }

  private static Integer op(int n1, int n2, Operation op) {
    switch (op) {
      case Plus: return n1 + n2;
      case Minus: return n1 - n2;
      case Multiply: return n1 * n2;
      case Divide: return n1 / n2;
      case Modulo: return n1 % n2;
      default: return Integer.valueOf(0);
    }
  }

  private static Long op(long n1, long n2, Operation op) {
    switch (op) {
      case Plus: return n1 + n2;
      case Minus: return n1 - n2;
      case Multiply: return n1 * n2;
      case Divide: return n1 / n2;
      case Modulo: return n1 % n2;
      default: return Long.valueOf(0);
    }
  }

  private static Short op(short n1, short n2, Operation op) {
    switch (op) {
      case Plus: return (short)(n1 + n2);
      case Minus: return (short)(n1 - n2);
      case Multiply: return (short)(n1 * n2);
      case Divide: return (short)(n1 / n2);
      case Modulo: return (short)(n1 % n2);
      default: return (short)0;
    }
  }

  public static Optional<Short> getShort(String str) {
    return getLong(str, Short.MAX_VALUE).map(Long::shortValue);
  }

  public static Optional<Integer> getInteger(String str) {
    return getLong(str, Integer.MAX_VALUE).map(Long::intValue);
  }

  public static Optional<Long> getLong(String str) {
    return getLong(str, Long.MAX_VALUE);
  }

  private static Optional<Long> getLong(String str, long MAX_VALUE) {
    if (str == null || str.isEmpty()) return Optional.empty();
    char c = str.charAt(0);
    int pos = 0;
    int len = str.length();
    boolean positive = true;
    if (!Character.isDigit(c)) {
      if (c == '-') {
        positive = false;
        pos = 1;
      } else if (c=='+') {
        pos = 1;
      } else return Optional.empty();
      if (pos == len) return Optional.empty();
    }
    long tocmp = -MAX_VALUE;
    long result = 0;
    long limit = positive ? 0 : 1;
    do {
      c = str.charAt(pos);
      if (!Character.isDigit(c)) return Optional.empty();
      result = 10 * result + Character.digit(c, 10);
      if (result + tocmp > limit) return Optional.empty();
      pos ++;
    } while (pos != len);
    return Optional.of ( (positive ? result : -result));
  }

  /*
    Consider using getDouble instead of try-catch Double.valueOf(str) only when
    there is a high risk of many non-compatible numbers
   */

  private static Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");

  public static Optional<Double> getDouble(String str) {
    if (DOUBLE_PATTERN.matcher(str).matches()) {
      try {
        return Optional.of(Double.valueOf(str));
      } catch (NumberFormatException nfe) { // when number beyond the limits
      }
    }
    return Optional.empty();
  }
}
