/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag.fun.col;

import org.ddag.fun.FunObject;
import org.ddag.fun.FunString;
import org.ddag.fun.tuple.Tuple2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.BiFunction;

/*
  Scala-like List collection

  methods starting with 'm' are mutable (they don't create a new list as a result)
   - it makes faster computing but causes side effects
  use them after methods which always create a new instance i.e.:
  list.filter(e -> e > 0).mPushed(10).mReversed();
 */
@SuppressWarnings("WeakerAccess")
public class FunLinkedList<T> extends LinkedList<T> implements FunObject, FunList<T> {

  public FunLinkedList() { super(); }

  public FunLinkedList(Collection<? extends T> c) {    super(c);  }

  public FunLinkedList(T[] c) { super(Arrays.asList(c));  }

  /*
    Mutable methods that change current list
   */
  public FunLinkedList<T> mTail()           {    this.removeFirst();    return this;  }

  public FunLinkedList<T> mPushed(T el)     {    this.push(el);    return this;  }

  public FunLinkedList<T> mAdded(T el)      {    this.add(el);    return this;  }

  public FunLinkedList<T> mRemoved(T el)    {    this.remove(el);    return this;  }

  public FunLinkedList<T> mReversed()       {    Collections.reverse(this);    return this;  }

  public FunLinkedList<T> pushed(T el)      {    return new FunLinkedList<>(this).mPushed(el);  }

  public FunLinkedList<T> added(T el)       {    return new FunLinkedList<>(this).mAdded(el);  }

  public FunLinkedList<T> removed(T el)     {    return new FunLinkedList<>(this).mRemoved(el);  }

  // more safe mutable way to get head::tail in O(1)
  public Tuple2<T, FunLinkedList<T>> mHeadTail() { return new Tuple2<>(this.head(), this.mTail()); }

  public FunLinkedList<T> mSortWith(BiFunction<T, T, Integer> compare)  {    this.sort(compare::apply);    return this;  }

  public FunLinkedList<T> mAddedCol(Collection<? extends T> col) {    this.addAll(col);    return this;  }

  // creates unmodifiable copy of the list - to use only within recursive algorithms
  public FunSharedList<T> toSharedList()  { return new FunSharedList<>(this);  }

  public FunLinkedList<T> duplicate() { return new FunLinkedList<>(this); }

  public FunLinkedList<T> addedCol(Collection<? extends T> list) {    return new FunLinkedList<>(this).mAddedCol(list);  }

  public FunString mkFunString(String separator) {  return new FunString( mkString(separator) );  }

  public String mkString(String separator) {
    return foldLeft( new StringBuilder(""), (acc, el) -> el == getLast() ? acc.append(el) : acc.append(el).append(separator)).toString();
  }

  /*
    Implementation of interfaces and static methods
   */

  public T head()     { return this.getFirst(); }

  // don't use within recursive algorithms because it creates new collections every time
  // use FunSharedList instead
  public FunList<T> tail() {    return new FunLinkedList<>(this).mTail();  }

  @SafeVarargs
  public static <T> FunLinkedList<T> of(T... params) {
    return new FunLinkedList<>(params);
  }

  public static <T> FunLinkedList<T> ofSize(int n, T value) {
    FunLinkedList<T> res = new FunLinkedList<>();
    for (int i=0; i<n; i++) res.add(value);
    return res;
  }

}
