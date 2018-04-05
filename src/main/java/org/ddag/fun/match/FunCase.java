/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag.fun.match;

class FunCase {
  private Object first;
  private Object[] args;

  FunCase(Object firstArg, Object... args) {
    this.first = firstArg;
    this.args = args;
  }

  boolean get(Object o) {    return FunMatch.matches(o, first, args);  }
}
