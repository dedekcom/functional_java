/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag.fun.col;


import java.util.ListIterator;

public class FunListIterated<T> {
  private ListIterator<T> iterator;
  private FunList<T> list;

  private FunListIterated(FunList<T> list, ListIterator<T> iterator) {
    this.list = list;
    this.iterator = iterator;
  }

  FunListIterated(FunList<T> list) {    this(list, list.listIterator());  }

  public boolean isEmpty() { return !iterator.hasNext(); }

  public boolean nonEmpty() { return iterator.hasNext(); }

  public T head()           {
    T h = iterator.next();
    iterator.previous();
    return h;
  }

  public FunListIterated<T> tail() {
    return new FunListIterated<>(list, list.listIterator(iterator.nextIndex()+1));
  }

  public FunList<T> toList() {
    FunList<T> list = new FunList<>();
    while(iterator.hasNext()) {
      list.add(iterator.next());
    }
    return list;
  }
}
