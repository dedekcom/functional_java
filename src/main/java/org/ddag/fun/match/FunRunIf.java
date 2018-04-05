/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.match;

import java.util.function.Consumer;

public class FunRunIf {
  private Object first;
  private Object[] args;
  private Consumer<Object> fun;

  FunRunIf(Consumer<Object> fun, Object firstArg, Object... args) {
    this.first = firstArg;
    this.fun = fun;
    this.args = args;
  }

  boolean get(Object o) {
    if (FunMatch.matches(o, first, args)) {
      fun.accept(o);
      return true;
    } else
      return false;
  }

}
