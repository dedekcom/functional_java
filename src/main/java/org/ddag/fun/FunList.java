/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag.fun;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/*
  Scala-like List collection

  methods starting with 'm' are mutable (they don't create a new list as a result)
   - it makes faster computing but causes side effects
  use them after methods which always create a new instance i.e.:
  list.filter(e -> e > 0).mPushed(10).mReversed();
 */
public class FunList<T> extends LinkedList<T> implements FunObject {
  final private static FunList emptyList = new FunList();

  public FunList() { super(); }

  public FunList(Collection<? extends T> c) {    super(c);  }

  public <R> FunList<R> map(Function<? super T, R> fun) {
    FunList<R> r = new FunList<>();
    for (T e: this) {
      r.add(fun.apply(e));
    }
    return r;
  }

  public <R> FunList<R> mapWithIndex(BiFunction<? super T, Integer, R> fun) {
    FunList<R> r = new FunList<>();
    int id = 0;
    for (T e: this) {
      r.add(fun.apply(e, id));
      id ++;
    }
    return r;
  }

  public FunList<T> filter(Predicate<? super T> predicate) {
    FunList<T> r = new FunList<>();
    for (T e: this) {
      if (predicate.test(e))
        r.add(e);
    }
    return r;
  }

  public FunList<T> filterWithIndex(BiFunction<? super T, Integer, Boolean> fun) {
    FunList<T> r = new FunList<>();
    int id = 0;
    for (T e: this) {
      if (fun.apply(e, id))
        r.add(e);
      id ++;
    }
    return r;
  }

  public FunList<T> filterNot(Predicate<? super T> predicate) {
    FunList<T> r = new FunList<>();
    for (T e: this) {
      if (!predicate.test(e))
        r.add(e);
    }
    return r;
  }

  public Optional<T> find(Predicate<? super T> predicate) {
    for (T e: this) {
      if (predicate.test(e))
        return Optional.of(e);
    }
    return Optional.empty();
  }

  public boolean exists(Predicate<? super T> predicate) { return this.find(predicate).isPresent(); }

  public T head()     { return this.getFirst(); }

  public Optional<T>  headOpt() { return this.isEmpty() ? Optional.empty() : Optional.of(this.getFirst()); }

  public FunList<T> tail() {
    return new FunList<>(this).mTail();
  }

  public FunList<T> distinct() {
    FunList<T> r = new FunList<>();
    this.forEach( e -> {
      if (!r.contains(e)) r.add(e);
    });
    return r;
  }

  public FunList<T> mTail() {
    this.removeFirst();
    return this;
  }

  public void print() {
    System.out.println(this.toString());
  }

  public FunList<T> pushed(T el) {
    return new FunList<>(this).mPushed(el);
  }

  public FunList<T> mPushed(T el) {
    this.push(el);
    return this;
  }

  public FunList<T> take(int n) {
    return this.slice(0, n);
  }

  public FunList<T> takeRight(int n) {
    return this.slice(this.size() - n,  this.size());
  }

  public FunList<T> slice(final int start, final int stop) {
    return this.filterWithIndex((e, id) -> id >= start && id < stop);
  }

  public FunList<T> drop(int n) {
    return this.slice(n, this.size());
  }

  public FunList<T> reversed() {
    return new FunList<>(this).mReversed();
  }

  public FunList<T> mReversed() {
    Collections.reverse(this);
    return this;
  }

  public <R> R fold(R initial, BiFunction<R, T, R> fun) {    return _fold(this, initial, fun);  }

  private <R> R _fold(FunList<T> list, R result, BiFunction<R, T, R> fun)  {
    if (list.isEmpty()) return result;
    else return _fold(list.tail(), fun.apply(result, list.head()), fun);
  }

  public <R> R foldLeft(R initial, BiFunction<R, T, R> fun) {
    for (T el: this)  {
      initial = fun.apply(initial, el);
    }
    return initial;
  }

  public Number sum() {    return this.foldLeft(Fumeric.zero(), (sum, el) -> Fumeric.sum(sum, ((Number)el)));  }

  public double avg() {    return Fumeric.div(this.sum(), Double.valueOf(this.size())).doubleValue();  }

  public FunList<T> sortWith(BiFunction<T, T, Integer> compare)  {
    return new FunList<>(this).mSortWith(compare);
  }

  public FunList<T> mSortWith(BiFunction<T, T, Integer> compare)  {
    this.sort(compare::apply);
    return this;
  }

  public <T extends Comparable<? super T>> FunList<T> sorted() {
    FunList<T> list = new FunList<>();
    this.forEach(e -> list.add((T)e));
    list.sort(Comparator.naturalOrder());
    return list;
  }

  public Tuple2<FunList<T>,FunList<T>> partition(Predicate<? super T> predicate) {
    return this.partition((e, i) -> predicate.test(e));
  }

  public Tuple2<FunList<T>, FunList<T>> partition(BiFunction<? super T, Integer, Boolean> fun) {
    FunList<T> r1 = new FunList<>();
    FunList<T> r2 = new FunList<>();
    int id = 0;
    for (T e: this) {
      if (fun.apply(e, id)) r1.add(e); else r2.add(e);
      id ++;
    }
    return new Tuple2<>(r1, r2);
  }

  public FunList<Tuple2<T, Integer>> zipWithIndex() { return this.mapWithIndex(Tuple2::new); }

  public Tuple2<FunList<T>,FunList<T>> splitAt(int n) { return this.partition((e, i) -> i < n); }

  public FunList<T> addedCol(Collection<? extends T> list) {
    return new FunList<>(this).mAddedCol(list);
  }

  public FunList<T> mAddedCol(Collection<? extends T> col) {
    this.addAll(col);
    return this;
  }

  public FunString mkString(String separator) {
    StringBuilder res = new StringBuilder("");
    if (this.size() > 0) {
      T last = this.getLast();
      for (T e: this) {
        res.append(e);
        if (e != last)
          res.append(separator);
      }
    }
    return new FunString(res.toString());
  }

  public FunList<Object> flatten() {
    FunList<Object> res = new FunList<>();
    forEach(e -> {
      if (e instanceof Optional) {
        if (((Optional) e).isPresent()) res.add(((Optional) e).get());
      } else if (e instanceof Collection) {
        res.addAll((Collection)e);
      } else {
        res.add(e);
      }
    });
    return res;
  }

  /*
    Turbo Pascal zone
   */
  public boolean repeatUntil(Predicate<? super T> predicate) {
    for (T el: this) {
      if (predicate.test(el))
        return true;
    }
    return false;
  }
  /*
    End of prehistory
   */

  public boolean isHeadTail()       { return this.size() > 0;  }
  public boolean isHeadNil()        { return this.size() == 1; }
  public boolean isHeadTailNotNil() { return this.size() > 1; }
  public boolean isNil()            { return this.size() == 0; }
  public boolean nonEmpty()         { return this.size() != 0; }

  public boolean matches(Object... params) {
    if (params.length > 0 && (params[0] instanceof Class) && ((Class)params[0]).isInstance(this)) {
      if (params.length == 1)
        return true;  // match only collection type
      Iterator it = this.iterator();
      int last = params.length - 1;
      for (int i=1; i<last; i++) {
        if (i > this.size())
          return false;
        Object n = it.next();
        if (params[i] instanceof Class) {
          if (!((Class)params[i]).isInstance(n))
            return false;
        }  else if (!n.equals(params[i]))
          return false;
      }
      if (params[last].equals(FunList.of()))  {
        return !it.hasNext();
      } else if (params[last] instanceof Class && ((Class)params[last]).isInstance(this)) {
        return true;
      } else {
        return false;
      }
    } else {
      return (params.length == 1 && this.equals(params[0]));
    }
  }

  public static FunList of() { return emptyList; }

  @SafeVarargs
  public static <T> FunList<T> of(T... params) {
    return new FunList<>(Arrays.asList(params));
  }

  public static <T> FunList<T> ofSize(int n, T value) {
    FunList<T> res = new FunList<>();
    for (int i=0; i<n; i++) res.add(value);
    return res;
  }

}
