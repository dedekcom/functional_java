/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag.fun.col;

import org.ddag.fun.match.FunMatching;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/*
  This is shared unmodifiable List which should be used only temporary within recursive algorithms.

  The only reason why the FunSharedList was implemented was O(1) tail() method.
 */
@SuppressWarnings({"unchecked", "WeakerAccess"})
public class FunSharedList<T> implements List<T>, FunMatching {
  private Object[] listCopy;
  private int idHead;
  private int idLimit;

  public FunSharedList(FunSharedList<T> list) {
    listCopy = list.listCopy;
    idLimit = list.idLimit;
    idHead = list.idHead;
  }

  public FunSharedList(List<T> list) {
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
    if (id >= idLimit || id < 0)
      throw new IndexOutOfBoundsException();
    return (T)listCopy[id];
  }

  public int size()         { return idLimit - idHead; }

  public FunSharedList<T> tail() {   return new FunSharedList<>(listCopy, idHead+1, idLimit);  }

  public FunList<T> toFunList() {
    FunList<T> list = new FunList<>();
    for(int i = idHead; i < idLimit; i++) {
      list.add((T)listCopy[i]);
    }
    return list;
  }

  public List<T> subList(int fromIndex, int toIndex) {
    return new FunSharedList<>(listCopy, idHead+fromIndex, idHead+toIndex);
  }

  public Object[] toArray() {
    if (idHead==0 && idLimit == listCopy.length)
      return listCopy;
    Object[] result = new Object[this.size()];
    for (int i=idHead; i<idLimit; i++)  {
      result[i-idHead] = listCopy[i];
    }
    return result;
  }

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
  public Iterator<T> iterator() {    return this.listIterator();  }

  @Override
  public ListIterator<T> listIterator() {    return this.listIterator(0);  }

  public ListIterator<T> listIterator(int pos) {    return new ListIter<>(pos);  }

  public boolean containsAll(Collection<?> c) {
    for (Object e: c) {
      if (!this.contains(e))
        return false;
    }
    return true;
  }



  /*
    Unsupported writeable methods
   */
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

  /*
      Support of matching and iterator
   */

  public boolean matches(Object first, Object... params) {
    return MatchList.matches(this, first, params);
  }

  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof List))
      return false;

    ListIterator<T> e1 = listIterator();
    ListIterator<?> e2 = ((List<?>) o).listIterator();
    while (e1.hasNext() && e2.hasNext()) {
      T o1 = e1.next();
      Object o2 = e2.next();
      if (!(o1==null ? o2==null : o1.equals(o2)))
        return false;
    }
    return !(e1.hasNext() || e2.hasNext());
  }

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
