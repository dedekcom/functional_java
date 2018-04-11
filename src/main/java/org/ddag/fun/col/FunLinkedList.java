/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag.fun.col;

import org.ddag.fun.tuple.Tuple2;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.BiFunction;

/*
  Scala-like List collection

  methods starting with 'm' are mutable (they don't create a new list as a result)
   - it makes faster computing but causes side effects
  use them after methods which always create a new instance i.e.:
  list.filter(e -> e > 0).mPushed(10).mReversed();
 */
@SuppressWarnings("WeakerAccess")
public class FunLinkedList<T> extends LinkedList<T> implements FunList<T> {

  public FunLinkedList() { super(); }

  public FunLinkedList(Collection<? extends T> c) {    super(c);  }

  public FunLinkedList<T> toFunLinkedList() { return this; }

  /*
    Immutable methods that create a new list
   */

  public FunLinkedList<T> pushed(T el)      {    return new FunLinkedList<>(this).mPushed(el);  }

  public FunLinkedList<T> added(T el)       {    return new FunLinkedList<>(this).mAdded(el);  }

  public FunLinkedList<T> removed(T el)     {    return new FunLinkedList<>(this).mRemoved(el);  }

  public FunLinkedList<T> duplicate() { return new FunLinkedList<>(this); }

  public FunLinkedList<T> addedCol(Collection<? extends T> list) {    return new FunLinkedList<>(this).mAddedCol(list);  }

  public FunList<T> reversed()  {    return foldLeft(new FunLinkedList<>(), FunLinkedList::mPushed);  }

  /*
    Implementation of interfaces and static methods
   */

  public T head()     { return this.getFirst(); }

  // don't use within recursive algorithms because it creates new collections every time
  // use Unmodified lists instead
  public FunList<T> tail() {    return new FunLinkedList<>(this).mTail();  }

  /*
  Mutable methods that change current list
 */
  public FunLinkedList<T> mTail()           {    this.removeFirst();    return this;  }

  public FunLinkedList<T> mPushed(T el)     {    this.push(el);    return this;  }

  public FunLinkedList<T> mAdded(T el)      {    this.add(el);    return this;  }

  public FunLinkedList<T> mRemoved(T el)    {    this.remove(el);    return this;  }

  public FunLinkedList<T> mReversed()       {    Collections.reverse(this);    return this;  }

  // more safe mutable way to get head::tail in O(1)
  public Tuple2<T, FunLinkedList<T>> mHeadTail() { return new Tuple2<>(this.head(), this.mTail()); }

  public FunLinkedList<T> mSortWith(BiFunction<T, T, Integer> compare)  {    this.sort(compare::apply);    return this;  }

  public FunLinkedList<T> mAddedCol(Collection<? extends T> col) {    this.addAll(col);    return this;  }

}
