/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag.fun.match;

import java.util.Optional;
import java.util.function.Function;

class FunCase<T, R> {
  private Object[] args;
  private Function<T, R> fun;

  FunCase(Function<T, R> fun, Object... args) {
    this.fun = fun;
    this.args = args;
  };

  Optional<R> getOpt(T o) {
    return FunMatch.caseObject(o, args) ? Optional.of(fun.apply(o)) : Optional.empty();
  }

}
