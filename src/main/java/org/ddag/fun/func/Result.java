/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.func;

abstract public class Result<R> {
  boolean continueRecursion;
  R result;

  Result(boolean bContinue, R result) {
    this.result = result;
    this.continueRecursion = bContinue;
  }
}
