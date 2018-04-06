/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag.fun.col;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/*
  This is shared unmodifiable List which should be used only temporary within recursive algorithms.

  The only reason why the FunSharedList was implemented was O(1) tail() method.
 */
@SuppressWarnings("unchecked")
public class FunSharedList<T> implements List<T> {
  private Object[] listCopy;
  private int idHead;
  private int idLimit;

  FunSharedList(FunList<T> list) {
    listCopy = list.toArray();
    idLimit = listCopy.length;
    idHead = 0;
  }

  private FunSharedList(Object[] list, int idStart, int idStop) {
    listCopy = list;
    idLimit = idStop > list.length ? list.length : idStop;
    idHead = idStart > idLimit ? idLimit : idStart;
  }

  public boolean isEmpty() { return idHead >= idLimit; }

  public boolean nonEmpty() { return !isEmpty(); }

  public T head()           { return get(0);  }

  public T get(int id)      {
    id += idHead;
    if (id >= idLimit)
      throw new IndexOutOfBoundsException();
    return (T)listCopy[id];
  }

  public int size()         { return idLimit - idHead; }

  public FunSharedList<T> tail() {   return new FunSharedList<>(listCopy, idHead+1, idLimit);  }

  public FunList<T> toList() {
    FunList<T> list = new FunList<>();
    for(int i = idHead; i < idLimit; i++) {
      list.add((T)listCopy[i]);
    }
    return list;
  }

  public List<T> subList(int start, int stop) {    return new FunSharedList<>(listCopy, idHead+start, stop);  }

  public Object[] toArray() { return listCopy; }

  public int indexOf(Object object) {
    for (int i=idHead; i < idLimit; i++) {
      if (object.equals(listCopy[i]))
        return i - idHead;
    }
    return -1;
  }

  public int lastIndexOf(Object object) {
    for (int i=idLimit-1; i >= idHead; i--) {
      if (object.equals(listCopy[i]))
        return i - idHead;
    }
    return -1;
  }

  public boolean contains(Object object) {    return indexOf(object) != -1;  }

  @Override
  public Iterator<T> iterator() {    throw new UnsupportedOperationException();  }

  @Override
  public ListIterator<T> listIterator() {    throw new UnsupportedOperationException();  }

  public ListIterator<T> listIterator(int pos) {    throw new UnsupportedOperationException();  }

  public void clear() { throw new UnsupportedOperationException(); }

  public boolean add(T e) { throw new UnsupportedOperationException(); }

  public void add(int pos, T e) { throw new UnsupportedOperationException(); }

  public T set(int pos, T e) { throw new UnsupportedOperationException(); }

  public boolean remove(Object o) { throw new UnsupportedOperationException(); }

  public T remove(int pos) { throw new UnsupportedOperationException(); }

  public boolean addAll(Collection<? extends T> c) {    throw new UnsupportedOperationException();  }

  public boolean removeAll(Collection<?> c) {    throw new UnsupportedOperationException();  }

  public boolean retainAll(Collection<?> c) {    throw new UnsupportedOperationException();  }

  public boolean addAll(int n, Collection<? extends T> c) {    throw new UnsupportedOperationException();  }

  public <R> R[] toArray(R[] a) { throw new UnsupportedOperationException(); }

  public boolean containsAll(Collection<?> c) {    throw new UnsupportedOperationException();  }
}
