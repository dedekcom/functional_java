/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.match;

import java.util.Optional;
import java.util.function.Function;

class FunCase<T, R> {
  private Object first;
  private Object[] args;
  private Function<T, R> fun;

  FunCase(Function<T, R> fun, Object firstArg, Object... args) {
    this.first = firstArg;
    this.fun = fun;
    this.args = args;
  };

  Optional<R> getOpt(T o) {
    return FunMatch.matches(o, first, args) ? Optional.of(fun.apply(o)) : Optional.empty();
  }

}
