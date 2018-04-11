/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag.fun.col;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/*
  This is shared unmodifiable List which should be used only temporary within recursive algorithms.

  The only reason why the FunUnmodifArrayList was implemented was O(1) tail() method.
 */
@SuppressWarnings({"unchecked", "WeakerAccess"})
public class FunUnmodifArrayList<T> extends AbstractList<T> implements FunList<T> {
  private Object[] listCopy;
  private int idHead;
  private int idLimit;

  public FunUnmodifArrayList(FunUnmodifArrayList<T> list) {
    listCopy = list.listCopy;
    idLimit = list.idLimit;
    idHead = list.idHead;
  }

  public FunUnmodifArrayList(Object[] ar) {
    listCopy = ar;
    idHead = 0;
    idLimit = listCopy.length;
  }

  public FunUnmodifArrayList(Collection<? extends T> c) {
    listCopy = c.toArray();
    idLimit = listCopy.length;
    idHead = 0;
  }

  private FunUnmodifArrayList(Object[] list, int idStart, int idStop) {
    listCopy = list;
    idLimit = idStop > list.length ? list.length : idStop;
    idHead = idStart > idLimit ? idLimit : idStart;
  }

  public FunList<T> added(T el) {
    Object[] o = new Object[this.size()+1];
    copyArray(o,  0, idHead, idLimit);
    o[this.size()] = el;
    return new FunUnmodifArrayList<>(o, 0, o.length);
  }

  public FunList<T> pushed(T el) {
    Object[] o = new Object[this.size()+1];
    copyArray(o,  1,idHead, idLimit);
    o[0] = el;
    return new FunUnmodifArrayList<>(o, 0, o.length);
  }

  public FunList<T> removed(T el) {
    int id = indexOf(el);
    if (id != -1) {
      Object[] o = new Object[this.size()-1];
      copyArray(o,0,  idHead, id);
      copyArray(o, id,idHead + id + 1, idLimit);
      return new FunUnmodifArrayList<>(o, 0, o.length);
    } else
      return new FunUnmodifArrayList<>(this);
  }

  public FunList<T> reversed() {
    Object[] o = new Object[this.size()];
    for (int i = idHead; i<idLimit; i++) {
      o[i-idHead] = listCopy[idLimit-1-i];
    }
    return new FunUnmodifArrayList<>(o);
  }

  public boolean isEmpty() { return idHead >= idLimit; }

  public T head()           { return get(0);  }

  public T get(int id)      {
    id += idHead;
    if (id >= idLimit || id < 0)
      throw new IndexOutOfBoundsException();
    return (T)listCopy[id];
  }

  public int size()         { return idLimit - idHead; }

  public FunList<T> tail() {   return new FunUnmodifArrayList<>(listCopy, idHead+1, idLimit);  }

  public FunLinkedList<T> toFunLinkedList() {
    FunLinkedList<T> list = new FunLinkedList<>();
    for(int i = idHead; i < idLimit; i++) {
      list.add((T)listCopy[i]);
    }
    return list;
  }

  public List<T> subList(int fromIndex, int toIndex) {
    return new FunUnmodifArrayList<>(listCopy, idHead+fromIndex, idHead+toIndex);
  }

  public Object[] toArray() {
    Object[] result = new Object[this.size()];
    for (int i=idHead; i<idLimit; i++)  {
      result[i-idHead] = listCopy[i];
    }
    return result;
  }

  private void copyArray(Object[] result, int destFrom, int srcFrom, int srcTo) {
    for (int i=srcFrom; i<srcTo; i++)  {
      result[destFrom + i - srcFrom] = listCopy[i];
    }
  }

  public boolean contains(Object object) {    return indexOf(object) != -1;  }

  @Override
  public Iterator<T> iterator() {    return this.listIterator();  }

  @Override
  public ListIterator<T> listIterator() {    return this.listIterator(0);  }

  public ListIterator<T> listIterator(int pos) {    return new ListIter<>(pos);  }

  /*
    Unsupported writeable methods
   */
  public void clear() { throw new UnsupportedOperationException(); }

  public boolean add(T e) { throw new UnsupportedOperationException(); }

  public void add(int pos, T e) { throw new UnsupportedOperationException(); }

  public boolean remove(Object o) { throw new UnsupportedOperationException(); }

  public boolean addAll(Collection<? extends T> c) {    throw new UnsupportedOperationException();  }

  public boolean removeAll(Collection<?> c) {    throw new UnsupportedOperationException();  }

  public boolean retainAll(Collection<?> c) {    throw new UnsupportedOperationException();  }

  public boolean addAll(int n, Collection<? extends T> c) {    throw new UnsupportedOperationException();  }

  public <R> R[] toArray(R[] a) { throw new UnsupportedOperationException(); }

  protected void removeRange(int fromIndex, int toIndex) {  throw new UnsupportedOperationException();  }


  private class ListIter<E> implements ListIterator<E> {
    int lastReturned;
    int next;

    ListIter(int index) {
      index += idHead;
      next = (index  >= idLimit) ? idLimit : index;
    }

    public boolean hasNext() {      return next < idLimit;    }

    public E next() {
      if (!hasNext())
        throw new NoSuchElementException();

      lastReturned = next;
      next++;
      return (E)listCopy[lastReturned];
    }

    public boolean hasPrevious() {
      return next > idHead;
    }

    public E previous() {
      if (!hasPrevious())
        throw new NoSuchElementException();

      lastReturned = next;
      next--;
      return (E)listCopy[lastReturned];
    }

    public int nextIndex() {
      return next;
    }

    public int previousIndex() {
      return next - 1;
    }

    public void remove() {      throw new UnsupportedOperationException();    }

    public void set(E e) {      throw new UnsupportedOperationException();   }

    public void add(E e) {      throw new UnsupportedOperationException();   }

    public void forEachRemaining(Consumer<? super E> action) {  throw new UnsupportedOperationException(); }
  }
}
