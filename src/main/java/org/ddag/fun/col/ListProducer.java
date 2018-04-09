/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag.fun.col;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

interface ListProducer<T> extends List<T> {

  default <R> FunList<R> map(Function<? super T, R> fun) {
    FunList<R> r = new FunList<>();
    for (T e: this) {
      r.add(fun.apply(e));
    }
    return r;
  }

  default <R> FunList<R> mapWithIndex(BiFunction<? super T, Integer, R> fun) {
    FunList<R> r = new FunList<>();
    int id = 0;
    for (T e: this) {
      r.add(fun.apply(e, id));
      id ++;
    }
    return r;
  }

  default FunList<T> filter(Predicate<? super T> predicate) {
    FunList<T> r = new FunList<>();
    for (T e: this) {
      if (predicate.test(e))
        r.add(e);
    }
    return r;
  }

  default FunList<T> filterWithIndex(BiFunction<? super T, Integer, Boolean> fun) {    return filterWithIndex(fun, this.size());  }

  default FunList<T> filterWithIndex(BiFunction<? super T, Integer, Boolean> fun, final int limit) {
    FunList<T> r = new FunList<>();
    int id = 0;
    for (T e: this) {
      if (id >= limit)
        return r;
      if (fun.apply(e, id))
        r.add(e);
      id ++;
    }
    return r;
  }

  default FunList<T> filterNot(Predicate<? super T> predicate) {    return filter(p -> !predicate.test(p));  }


}
