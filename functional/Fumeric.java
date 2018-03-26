/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package functional;

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

}
