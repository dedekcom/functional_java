/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag.fun.col;

import org.ddag.fun.Fumeric;
import org.ddag.fun.FunObject;
import org.ddag.fun.FunString;
import org.ddag.fun.match.FunMatch.FunGetIf;
import static org.ddag.fun.match.FunMatch.partialMatch;
import org.ddag.fun.match.FunMatching;
import org.ddag.fun.tuple.Tuple2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.ddag.fun.match.FunMatch.Case;
import static org.ddag.fun.match.FunMatch.match;
import static org.ddag.fun.tuple.FunTuple.T2;

public interface FunList<T> extends List<T>, FunMatching, FunObject {

  List Nil = Collections.emptyList();

  static List List() { return Nil; }

  @SafeVarargs
  static <T> FunList<T> of(T... params) {
    return new FunUnmodifLinkedList<>(Arrays.asList(params));
  }

  static <T> FunList<T> ofSize(int n, T value) {
    Object[] o = new Object[n];
    for (int i=0; i<n; i++) o[i] = value;
    return new FunUnmodifArrayList<>(o);
  }

  default FunUnmodifArrayList<T> toUnmodifArrayList()  { return new FunUnmodifArrayList<>(this);  }

  default FunUnmodifLinkedList<T> toUnmodifLinkedList() { return new FunUnmodifLinkedList<>(this); }

  default FunLinkedList<T> toFunLinkedList()  { return new FunLinkedList<>(this); }

  T head();

  T last();

  default Optional<T>  headOpt() { return this.isEmpty() ? Optional.empty() : Optional.of(this.head()); }

  FunList<T> tail();

  FunList<T> added(T el);

  FunList<T> pushed(T el);

  FunList<T> removed(T el);

  FunList<T> reversed();

  FunList<T> addedCol(Collection<? extends T> col);

  default boolean nonEmpty() { return !isEmpty(); }

  default <R> FunLinkedList<R> map(Function<? super T, R> fun) {
    FunLinkedList<R> r = new FunLinkedList<>();
    for (T e : this) {
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

  default FunLinkedList<T> filterWithIndex(BiFunction<? super T, Integer, Boolean> fun) {    return filterWithIndex(fun, -1);  }

  default FunLinkedList<T> filterWithIndex(BiFunction<? super T, Integer, Boolean> fun, final int limit) {
    FunLinkedList<T> r = new FunLinkedList<>();
    int id = 0;
    for (T e: this) {
      if (limit != -1 && id >= limit)
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

  default FunList<T> take(int n)     {    return this.slice(0, n);  }

  default FunList<T> takeRight(int n) {    return this.slice(this.size() - n,  -1);  }

  FunList<T> slice(final int start, final int stop);

  default FunList<T> drop(int n) {    return this.slice(n, -1);  }

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

  /*
    min & max throw NoSuchElementException if empty list
   */
  @SuppressWarnings("unchecked")
  default <T extends Comparable<? super T>> T min() {
    return foldLeft((T)this.head(), (min, el) -> ((T)el).compareTo(min) < 0 ? (T)el : min);
  }

  @SuppressWarnings("unchecked")
  default <T extends Comparable<? super T>> T max() {
    return foldLeft((T)this.head(), (max, el) -> ((T)el).compareTo(max) > 0 ? (T)el : max);
  }

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

  default FunString mkFunString(String separator) {  return new FunString( mkString(separator) );  }

  default String mkString(String separator) {
    StringBuilder s = new StringBuilder("");
    Iterator<T> it = this.iterator();
    while (it.hasNext())  {
      T el = it.next();
      if (it.hasNext()) {
        s.append(el).append(separator);
      } else {
        s.append(el);
      }
    }
    return s.toString();
  }

  default boolean matches(Object firstPattern, Object... restPatterns) {
    return ListMatches.matches(this, firstPattern, restPatterns);
  }

  @SuppressWarnings("unchecked")
  default <R> FunLinkedList<R> collect(FunGetIf<T, R> firstCase, FunGetIf<T, R>... restCases) {
    FunLinkedList<R> r = new FunLinkedList<>();
    for (T e: this) {
      Optional<R> res = partialMatch(e, firstCase, restCases);
      res.ifPresent(r::add);
    }
    return r;
  }

  @SuppressWarnings("unchecked")
  default <R> FunLinkedList<R> collect(FunGetIf<T, R> case1) {
    FunLinkedList<R> r = new FunLinkedList<>();
    for (T e: this) {
      Optional<R> res = partialMatch(e, case1);
      res.ifPresent(r::add);
    }
    return r;
  }

  @SuppressWarnings("unchecked")
  default <R> FunLinkedList<R> collect(FunGetIf<T, R> case1, FunGetIf<T, R> case2) {
    FunLinkedList<R> r = new FunLinkedList<>();
    for (T e: this) {
      Optional<R> res = partialMatch(e, case1, case2);
      res.ifPresent(r::add);
    }
    return r;
  }

  @SuppressWarnings("unchecked")
  default <R> FunLinkedList<R> collect(FunGetIf<T, R> case1, FunGetIf<T, R> case2, FunGetIf<T, R> case3) {
    FunLinkedList<R> r = new FunLinkedList<>();
    for (T e: this) {
      Optional<R> res = partialMatch(e, case1, case2, case3);
      res.ifPresent(r::add);
    }
    return r;
  }

  @SuppressWarnings("unchecked")
  default <R> FunLinkedList<R> collect(FunGetIf<T, R> case1, FunGetIf<T, R> case2, FunGetIf<T, R> case3,
                                       FunGetIf<T, R> case4) {
    FunLinkedList<R> r = new FunLinkedList<>();
    for (T e: this) {
      Optional<R> res = partialMatch(e, case1, case2, case3, case4);
      res.ifPresent(r::add);
    }
    return r;
  }

  @SuppressWarnings("unchecked")
  default <R> FunLinkedList<R> collect(FunGetIf<T, R> case1, FunGetIf<T, R> case2, FunGetIf<T, R> case3,
                                       FunGetIf<T, R> case4, FunGetIf<T, R> case5) {
    FunLinkedList<R> r = new FunLinkedList<>();
    for (T e: this) {
      Optional<R> res = partialMatch(e, case1, case2, case3, case4, case5);
      res.ifPresent(r::add);
    }
    return r;
  }

  @SuppressWarnings("unchecked")
  default <R> FunLinkedList<R> collect(FunGetIf<T, R> case1, FunGetIf<T, R> case2, FunGetIf<T, R> case3,
                                       FunGetIf<T, R> case4, FunGetIf<T, R> case5, FunGetIf<T, R> case6) {
    FunLinkedList<R> r = new FunLinkedList<>();
    for (T e: this) {
      Optional<R> res = partialMatch(e, case1, case2, case3, case4, case5, case6);
      res.ifPresent(r::add);
    }
    return r;
  }

  @SuppressWarnings("unchecked")
  default <R> FunLinkedList<R> collect(FunGetIf<T, R> case1, FunGetIf<T, R> case2, FunGetIf<T, R> case3,
                                       FunGetIf<T, R> case4, FunGetIf<T, R> case5, FunGetIf<T, R> case6,
                                       FunGetIf<T, R> case7) {
    FunLinkedList<R> r = new FunLinkedList<>();
    for (T e: this) {
      Optional<R> res = partialMatch(e, case1, case2, case3, case4, case5, case6, case7);
      res.ifPresent(r::add);
    }
    return r;
  }

  @SuppressWarnings("unchecked")
  default <R> FunLinkedList<R> collect(FunGetIf<T, R> case1, FunGetIf<T, R> case2, FunGetIf<T, R> case3,
                                       FunGetIf<T, R> case4, FunGetIf<T, R> case5, FunGetIf<T, R> case6,
                                       FunGetIf<T, R> case7, FunGetIf<T, R> case8) {
    FunLinkedList<R> r = new FunLinkedList<>();
    for (T e: this) {
      Optional<R> res = partialMatch(e, case1, case2, case3, case4, case5, case6, case7, case8);
      res.ifPresent(r::add);
    }
    return r;
  }

  @SuppressWarnings("unchecked")
  default <R> FunLinkedList<R> collect(FunGetIf<T, R> case1, FunGetIf<T, R> case2, FunGetIf<T, R> case3,
                                       FunGetIf<T, R> case4, FunGetIf<T, R> case5, FunGetIf<T, R> case6,
                                       FunGetIf<T, R> case7, FunGetIf<T, R> case8, FunGetIf<T, R> case9) {
    FunLinkedList<R> r = new FunLinkedList<>();
    for (T e: this) {
      Optional<R> res = partialMatch(e, case1, case2, case3, case4, case5, case6, case7, case8, case9);
      res.ifPresent(r::add);
    }
    return r;
  }
}
