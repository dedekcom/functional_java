/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package functional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/*
  Scala like List collection

  methods starting with 'm' are mutable (they don't create a new list as a result)
   - it makes faster computing but causes side effects
  use them after methods which always create a new instance i.e.:
  list.filter(e -> e > 0).mPushed(10).mReversed();
 */
public class FunList<T> extends LinkedList<T> implements FunObject {
  public FunList() { super(); }

  public FunList(Collection<? extends T> c) {    super(c);  }

  public <R> FunList<R> map(Function<? super T, R> fun) {
    FunList<R> r = new FunList<>();
    for (T e: this) {
      r.add(fun.apply(e));
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

  public FunList<T> filterNot(Predicate<? super T> predicate) {
    FunList<T> r = new FunList<>();
    for (T e: this) {
      if (!predicate.test(e))
        r.add(e);
    }
    return r;
  }

  public T head()     { return this.get(0); }

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

  public FunList<T> slice(int start, int stop) {
    FunList<T> list = new FunList<>();
    if (start < 0) start = 0;
    if (stop > this.size()) stop = this.size();
    while(start < stop) {
      list.add(this.get(start));
      start ++;
    }
    return list;
  }

  public FunList<T> reversed() {
    return new FunList<>(this).mReversed();
  }

  public FunList<T> mReversed() {
    Collections.reverse(this);
    return this;
  }

  public <R> R fold(R initial, BiFunction<R, T, R> fun) {
    return _fold(0, this.size(), initial, fun);
  }

  private <R> R _fold(int pos, int length, R result, BiFunction<R, T, R> fun)  {
    if (pos == length) return result;
    return _fold(pos + 1, length, fun.apply(result, this.get(pos)), fun);
  }

  public <R> R foldLeft(R initial, BiFunction<R, T, R> fun) {
    for (T el: this)  {
      initial = fun.apply(initial, el);
    }
    return initial;
  }

  public Number sum() {
    if (this.isEmpty()) return 0;
    T first = this.getFirst();
    if (first instanceof Double) return this.foldLeft(Double.valueOf(0), (sum, el) -> sum + ((Number)el).doubleValue());
    if (first instanceof Float) return this.foldLeft(Float.valueOf(0), (sum, el) -> sum + ((Number)el).floatValue());
    if (first instanceof Long) return this.foldLeft(Long.valueOf(0), (sum, el) -> sum + ((Number)el).longValue());
    if (first instanceof Integer) return this.foldLeft(Integer.valueOf(0), (sum, el) -> sum + ((Number)el).intValue());
    throw new IllegalArgumentException();
  }

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
    FunList<T> r1 = new FunList<>();
    FunList<T> r2 = new FunList<>();
    this.forEach(e ->{ if (predicate.test(e))  r1.add(e); else r2.add(e);});
    return new Tuple2<>(r1, r2);
  }

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
      for (int i=0; i < size()-1; i++) {
        res.append(this.get(i));
        res.append(separator);
      }
      res.append(this.getLast());
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

  public boolean isHeadTail()       { return this.size() > 0;  }
  public boolean isHeadNil()        { return this.size() == 1; }
  public boolean isHeadTailNotNil() { return this.size() > 1; }
  public boolean isNil()            { return this.size() == 0; }

  public static <T> FunList<T> newList(T... params) {
    return new FunList<>(Arrays.asList(params));
  }

}
