/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag.fun.col;

import org.ddag.fun.Fumeric;
import org.ddag.fun.FunObject;
import org.ddag.fun.FunString;
import static org.ddag.fun.match.FunMatch.match;
import static org.ddag.fun.match.FunMatch.Case;
import org.ddag.fun.match.FunMatching;
import org.ddag.fun.tuple.Tuple2;
import static org.ddag.fun.tuple.FunTuple.T2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
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
@SuppressWarnings("WeakerAccess")
public class FunList<T> extends LinkedList<T> implements FunObject, FunMatching {
  public static List Nil = Collections.emptyList();
  public static List List() { return Nil; }

  public FunList() { super(); }

  public FunList(Collection<? extends T> c) {    super(c);  }

  public FunList(T[] c) { super(Arrays.asList(c));  }

  /*
    Mutable methods that change current list
   */
  public FunList<T> mTail()           {    this.removeFirst();    return this;  }

  public FunList<T> mPushed(T el)     {    this.push(el);    return this;  }

  public FunList<T> mAdded(T el)      {    this.add(el);    return this;  }

  public FunList<T> mRemoved(T el)    {    this.remove(el);    return this;  }

  public FunList<T> mReversed()       {    Collections.reverse(this);    return this;  }

  // more safe mutable way to get head::tail in O(1)
  public Tuple2<T, FunList<T>> mHeadTail() { return new Tuple2<>(this.head(), this.mTail()); }

  public FunList<T> mSortWith(BiFunction<T, T, Integer> compare)  {    this.sort(compare::apply);    return this;  }

  public FunList<T> mAddedCol(Collection<? extends T> col) {    this.addAll(col);    return this;  }


  /*
    Immutable methods that create new lists
   */

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

  public FunList<T> filterWithIndex(BiFunction<? super T, Integer, Boolean> fun) {    return filterWithIndex(fun, this.size());  }

  public FunList<T> filterWithIndex(BiFunction<? super T, Integer, Boolean> fun, final int limit) {
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

  public FunList<T> filterNot(Predicate<? super T> predicate) {
    FunList<T> r = new FunList<>();
    for (T e: this) {
      if (!predicate.test(e))
        r.add(e);
    }
    return r;
  }

  // creates unmodifiable copy of the list - to use only within recursive algorithms
  public FunSharedList<T> toSharedList()  { return new FunSharedList<T>(this);  }

  // don't use within recursive algorithms because it creates new collections every time
  // use FunSharedList instead
  public FunList<T> tail() {    return new FunList<>(this).mTail();  }

  public FunList<T> distinct() { return foldLeft(new FunList<>(), (list, e) -> list.contains(e) ? list : list.mAdded(e) ); }

  public FunList<T> pushed(T el) {    return new FunList<>(this).mPushed(el);  }

  public FunList<T> added(T el) {    return new FunList<>(this).mAdded(el);  }

  public FunList<T> removed(T el) {    return new FunList<>(this).mRemoved(el);  }

  public FunList<T> take(int n)     {    return this.slice(0, n);  }

  public FunList<T> takeRight(int n) {    return this.slice(this.size() - n,  this.size());  }

  public FunList<T> slice(final int start, final int stop) { return filterWithIndex((el, id) -> id >= start, stop);  }

  public FunList<T> drop(int n) {    return this.slice(n, this.size());  }

  public FunList<T> reversed() {    return foldLeft(new FunList<>(), FunList::mPushed);  }

  public FunList<T> duplicate() { return new FunList<>(this); }

  public FunList<T> sortWith(BiFunction<T, T, Integer> compare)  {    return new FunList<>(this).mSortWith(compare);  }

  @SuppressWarnings("unchecked")
  public <T extends Comparable<? super T>> FunList<T> sorted() {
    FunList<T> list = new FunList<>();
    this.forEach(e -> list.add((T)e));
    list.sort(Comparator.naturalOrder());
    return list;
  }

  public FunList<Tuple2<T, Integer>> zipWithIndex() { return this.mapWithIndex(Tuple2::new); }

  public <U> FunList<Tuple2<T, U>> zip(List<U> list) {
    return foldLeft( T2(new FunList<Tuple2<T, U>>(), list.iterator()),
            (acc, el) -> acc._2().hasNext() ? T2(acc._1().mAdded(T2(el, acc._2().next())), acc._2()) : acc
    )._1();
  }

  public FunList<T> addedCol(Collection<? extends T> list) {    return new FunList<>(this).mAddedCol(list);  }

  @SuppressWarnings("unchecked")
  public <R> FunList<R> flatten() {
    return foldLeft(new FunList<R>(), (acc, e) -> match( e,
            Case(Optional.class, Any), o -> acc.mAdded(((Optional<R>) o).get()),
            Case(Collection.class), o -> acc.mAddedCol((Collection<? extends R>) o),
            Case(Optional.empty()), o -> acc,
            Case(Any), o -> acc.mAdded((R) o)
            )
    );
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
    return T2(r1, r2);
  }

  public Tuple2<FunList<T>,FunList<T>> splitAt(int n) { return this.partition((e, i) -> i < n); }


  /*
    Methods that return or compute values from list
   */

  public <R> R foldLeft(R initial, BiFunction<R,? super T, R> fun) {
    for (T el: this)  {
      initial = fun.apply(initial, el);
    }
    return initial;
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

  public int count(Predicate<? super T> predicate) { return foldLeft(0, (sum, el) -> predicate.test(el) ? sum + 1 : sum);  }

  public Number sum() {    return this.foldLeft(Fumeric.zero(), (sum, el) -> Fumeric.sum(sum, ((Number)el)));  }

  public double avg() {    return Fumeric.div(this.sum(), (double)this.size()).doubleValue();  }

  public FunString mkFunString(String separator) {  return new FunString( mkString(separator) );  }

  public String mkString(String separator) {
    return foldLeft( new StringBuilder(""), (acc, el) -> el == getLast() ? acc.append(el) : acc.append(el).append(separator)).toString();
  }

  public boolean isHeadTail()       { return this.size() > 0;  }
  public boolean isHeadNil()        { return this.size() == 1; }
  public boolean isHeadTailNotNil() { return this.size() > 1; }
  public boolean isNil()            { return this.size() == 0; }
  public boolean nonEmpty()         { return this.size() != 0; }


  /*
    Implementation of interfaces and static methods
   */

  public boolean matches(Object first, Object... params) {
    return MatchList.matches(this, first, params);
  }

  @SafeVarargs
  public static <T> FunList<T> of(T... params) {
    return new FunList<>(params);
  }

  public static <T> FunList<T> ofSize(int n, T value) {
    FunList<T> res = new FunList<>();
    for (int i=0; i<n; i++) res.add(value);
    return res;
  }

}
