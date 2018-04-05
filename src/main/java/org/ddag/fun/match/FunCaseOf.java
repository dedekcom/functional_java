/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag.fun.match;

class FunCaseOf {
  private Object first;
  private Object[] args;

  FunCaseOf(Object firstArg, Object... args) {
    this.first = firstArg;
    this.args = args;
  };

  boolean get(Object o) {    return FunMatch.matches(o, first, args);  }
}
