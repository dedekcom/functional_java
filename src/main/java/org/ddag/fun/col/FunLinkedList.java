/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag.fun.col;

import org.ddag.fun.match.FunMatch;
import org.ddag.fun.tuple.Tuple2;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.BiFunction;


/*
  Linked List
  size()    : O(1)
  pushed(x) : O(n)
  added(x)  : O(n)
  tail()    : O(n)

  Methods starting with 'm' are mutable (they don't create a new list as a result)
   - it makes faster computing but causes side effects
  use them after methods which always create a new instance i.e.:
  list.filter(e -> e > 0).mPushed(10).mReversed();

  In such case:
  size()      : O(1)
  mPushed(x)  : O(1)
  mAdded(x)   : O(1)
  mTail()     : O(1) *dangerous if called before head(), better to use Unmodifiable List
 */
@SuppressWarnings("WeakerAccess")
public class FunLinkedList<T> extends LinkedList<T> implements FunList<T> {

  public FunLinkedList() { super(); }

  public FunLinkedList(Collection<? extends T> c) {    super(c);  }

  @Override
  public FunLinkedList<T> toFunLinkedList() { return this; }

  /*
    Immutable methods that create a new list
   */

  @Override
  public FunList<T> pushed(T el)      {    return new FunLinkedList<>(this).mPushed(el);  }

  @Override
  public FunList<T> added(T el)       {    return new FunLinkedList<>(this).mAdded(el);  }

  @Override
  public FunList<T> removed(T el)     {    return new FunLinkedList<>(this).mRemoved(el);  }

  @Override
  public FunList<T> addedCol(Collection<? extends T> col) {    return new FunLinkedList<>(this).mAddedCol(col);  }

  @Override
  public FunList<T> reversed()  {    return foldLeft(new FunLinkedList<>(), FunLinkedList::mPushed);  }

  @Override
  public FunList<T> slice(int start, int stop) {    return filterWithIndex((el, id) -> id >= start, stop);  }

  public FunLinkedList<T> duplicate() { return new FunLinkedList<>(this); }

  @Override
  public T head()     { return this.getFirst(); }

  @Override
  public T last()   { return this.getLast(); }

  // don't use within recursive algorithms because it creates new collections every time
  // use Unmodified lists instead
  @Override
  public FunList<T> tail() {    return new FunLinkedList<>(this).mTail();  }

  // need to copy that to use SafeVarargs
  @SafeVarargs
  public final <R> FunLinkedList<R> collect(FunMatch.FunGetIf<T, R> firstCase, FunMatch.FunGetIf<T, R>... restCases) {
    return FunList.collect(this, firstCase, restCases);
  }

  /*
  Mutable methods that change current list
 */
  public FunLinkedList<T> mTail()           {    this.removeFirst();   return this;  }

  public FunLinkedList<T> mPushed(T el)     {    this.push(el);    return this;  }

  public FunLinkedList<T> mAdded(T el)      {    this.add(el);    return this;  }

  public FunLinkedList<T> mAddedAt(int pos, T el)    {    this.add(pos, el);    return this;  }

  public FunLinkedList<T> mRemoved(T el)    {    this.remove(el);    return this;  }

  public FunLinkedList<T> mRemovedAt(int pos)    {    this.remove(pos);    return this;  }

  public FunLinkedList<T> mReversed()       {    Collections.reverse(this);    return this;  }

  // more safe mutable way to get head::tail in O(1)
  public Tuple2<T, FunLinkedList<T>> mHeadTail() { return new Tuple2<>(this.head(), this.mTail()); }

  public FunLinkedList<T> mSortWith(BiFunction<T, T, Integer> compare)  {    this.sort(compare::apply);    return this;  }

  public FunLinkedList<T> mAddedCol(Collection<? extends T> col) {    this.addAll(col);    return this;  }

}
