/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.match;

import java.util.Optional;
import java.util.function.Function;

public class FunCaseObject {
  private Object first;
  private Object[] args;
  private Function<Object, Object> fun;

  FunCaseObject(Function<Object, Object> fun, Object firstArg, Object... args) {
    this.first = firstArg;
    this.fun = fun;
    this.args = args;
  };

  Optional<Object> getOpt(Object o) {
    return FunMatch.matches(o, first, args) ? Optional.of(fun.apply(o)) : Optional.empty();
  }
}
