/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag.fun.col;

import org.ddag.fun.Fumeric;
import org.ddag.fun.match.FunMatching;
import org.ddag.fun.tuple.Tuple2;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.ddag.fun.FunObject.Any;
import static org.ddag.fun.match.FunMatch.Case;
import static org.ddag.fun.match.FunMatch.match;
import static org.ddag.fun.tuple.FunTuple.T2;

public interface FunList<T> extends List<T>, FunMatching {

  List Nil = Collections.emptyList();

  static List List() { return Nil; }

  T head();

  default Optional<T>  headOpt() { return this.isEmpty() ? Optional.empty() : Optional.of(this.head()); }

  FunList<T> tail();

  default <R> FunLinkedList<R> map(Function<? super T, R> fun) {
    FunLinkedList<R> r = new FunLinkedList<>();
    for (T e: this) {
      r.add(fun.apply(e));
    }
    return r;
  }

  default <R> FunLinkedList<R> mapWithIndex(BiFunction<? super T, Integer, R> fun) {
    FunLinkedList<R> r = new FunLinkedList<>();
    int id = 0;
    for (T e: this) {
      r.add(fun.apply(e, id));
      id ++;
    }
    return r;
  }

  default FunLinkedList<T> filter(Predicate<? super T> predicate) {
    FunLinkedList<T> r = new FunLinkedList<>();
    for (T e: this) {
      if (predicate.test(e))
        r.add(e);
    }
    return r;
  }

  default FunLinkedList<T> filterWithIndex(BiFunction<? super T, Integer, Boolean> fun) {    return filterWithIndex(fun, this.size());  }

  default FunLinkedList<T> filterWithIndex(BiFunction<? super T, Integer, Boolean> fun, final int limit) {
    FunLinkedList<T> r = new FunLinkedList<>();
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

  default FunLinkedList<T> filterNot(Predicate<? super T> predicate) {    return filter(p -> !predicate.test(p));  }

  default <R> R foldLeft(R initial, BiFunction<R,? super T, R> fun) {
    for (T el: this)  {
      initial = fun.apply(initial, el);
    }
    return initial;
  }

  default FunLinkedList<T> reversed() {    return foldLeft(new FunLinkedList<>(), FunLinkedList::mPushed);  }

  default FunLinkedList<T> sortWith(BiFunction<T, T, Integer> compare)  {    return new FunLinkedList<>(this).mSortWith(compare);  }

  @SuppressWarnings("unchecked")
  default  <T extends Comparable<? super T>> FunLinkedList<T> sorted() {
    FunLinkedList<T> list = new FunLinkedList<>();
    this.forEach(e -> list.add((T)e));
    list.sort(Comparator.naturalOrder());
    return list;
  }

  default FunLinkedList<Tuple2<T, Integer>> zipWithIndex() { return this.mapWithIndex(Tuple2::new); }

  default  <U> FunLinkedList<Tuple2<T, U>> zip(List<U> list) {
    return foldLeft( T2(new FunLinkedList<Tuple2<T, U>>(), list.iterator()),
            (acc, el) -> acc._2().hasNext() ? T2(acc._1().mAdded(T2(el, acc._2().next())), acc._2()) : acc
    )._1();
  }

  default FunLinkedList<T> take(int n)     {    return this.slice(0, n);  }

  default FunLinkedList<T> takeRight(int n) {    return this.slice(this.size() - n,  this.size());  }

  default FunLinkedList<T> slice(final int start, final int stop) { return filterWithIndex((el, id) -> id >= start, stop);  }

  default FunLinkedList<T> drop(int n) {    return this.slice(n, this.size());  }

  default FunLinkedList<T> distinct() { return foldLeft(new FunLinkedList<>(), (list, e) -> list.contains(e) ? list : list.mAdded(e) ); }

  default Optional<T> find(Predicate<? super T> predicate) {
    for (T e: this) {
      if (predicate.test(e))
        return Optional.of(e);
    }
    return Optional.empty();
  }

  default boolean exists(Predicate<? super T> predicate) { return this.find(predicate).isPresent(); }

  default int count(Predicate<? super T> predicate) { return foldLeft(0, (sum, el) -> predicate.test(el) ? sum + 1 : sum);  }

  default Number sum() {    return this.foldLeft(Fumeric.zero(), (sum, el) -> Fumeric.sum(sum, ((Number)el)));  }

  default double avg() {    return Fumeric.div(this.sum(), (double)this.size()).doubleValue();  }

  @SuppressWarnings("unchecked")
  default  <R> FunLinkedList<R> flatten() {
    return foldLeft(new FunLinkedList<R>(), (acc, e) -> match( e,
            Case(Optional.class, Any), o -> acc.mAdded(((Optional<R>) o).get()),
            Case(Collection.class), o -> acc.mAddedCol((Collection<? extends R>) o),
            Case(Optional.empty()), o -> acc,
            Case(Any), o -> acc.mAdded((R) o)
            )
    );
  }

  default Tuple2<FunLinkedList<T>,FunLinkedList<T>> partition(Predicate<? super T> predicate) {
    return this.partition((e, i) -> predicate.test(e));
  }

  default Tuple2<FunLinkedList<T>, FunLinkedList<T>> partition(BiFunction<? super T, Integer, Boolean> fun) {
    FunLinkedList<T> r1 = new FunLinkedList<>();
    FunLinkedList<T> r2 = new FunLinkedList<>();
    int id = 0;
    for (T e: this) {
      if (fun.apply(e, id)) r1.add(e); else r2.add(e);
      id ++;
    }
    return T2(r1, r2);
  }

  default Tuple2<FunLinkedList<T>,FunLinkedList<T>> splitAt(int n) { return this.partition((e, i) -> i < n); }

  default boolean matches(Object first, Object... params) {
    return ListMatches.matches(this, first, params);
  }

}
